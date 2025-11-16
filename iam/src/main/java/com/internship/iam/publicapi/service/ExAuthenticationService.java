package com.internship.iam.publicapi.service;

import com.internship.iam.model.entity.Privilege;
import com.internship.iam.model.entity.Role;
import com.internship.iam.model.entity.RolePrivilege;
import com.internship.iam.model.entity.User;
import com.internship.iam.model.response.BaseSingleResultResponse;
import com.internship.iam.publicapi.model.request.AuthenticationRequest;
import com.internship.iam.publicapi.model.response.AuthenticationResponse;
import com.internship.iam.publicapi.model.response.AuthenticationResponseData;
import com.internship.iam.repository.RolePrivilegeRepository;
import com.internship.iam.repository.UserRepository;
import com.internship.iam.utils.JwtHandler;
import com.internship.iam.utils.constant.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ExAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        String token = request.getToken();
        String userId = JwtHandler.getUserId(token);
        User user = userRepository.findById(userId).orElse(null);
        if (Objects.isNull(user)) {
            BaseSingleResultResponse.ErrorInfo error = new BaseSingleResultResponse.ErrorInfo();
            error.setCode(ErrorInfo.UNAUTHORIZED.getCode());
            error.setMessage(ErrorInfo.UNAUTHORIZED.getText());
            response.setError(error);
            return response;
        }

        Role role = user.getUserRoles().get(0).getRole();
        List<RolePrivilege> rolePrivileges = rolePrivilegeRepository.findByRoleId(role.getId());
        if (CollectionUtils.isEmpty(rolePrivileges)) {
            BaseSingleResultResponse.ErrorInfo error = new BaseSingleResultResponse.ErrorInfo();
            error.setCode(ErrorInfo.UNAUTHORIZED.getCode());
            error.setMessage(ErrorInfo.UNAUTHORIZED.getText());
            response.setError(error);
            return response;
        }

        Set<String> privilegeCodes = new HashSet<>();
        for (RolePrivilege rolePrivilege : rolePrivileges) {
            Privilege privilege = rolePrivilege.getPrivilege();
            privilegeCodes.add(privilege.getCode());
        }

        AuthenticationResponseData authenticationResponseData = new AuthenticationResponseData();
        authenticationResponseData.setUserId(user.getId());
        authenticationResponseData.setUsername(user.getUsername());
        authenticationResponseData.setEmail(user.getEmail());
        authenticationResponseData.setRoleCode(role.getCode());
        authenticationResponseData.setPrivilegeCodes(privilegeCodes);


        response.setData(authenticationResponseData);
        return response;
    }


}

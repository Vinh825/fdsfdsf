package com.internship.iam.internalapi.service;

import com.internship.iam.internalapi.model.request.AuthenticationRequest;
import com.internship.iam.internalapi.model.response.AuthenticationResponse;
import com.internship.iam.model.entity.User;
import com.internship.iam.model.response.BaseSingleResultResponse;
import com.internship.iam.repository.UserRepository;
import com.internship.iam.utils.JwtHandler;
import com.internship.iam.utils.constant.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Mentor
 */
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @author Mentor
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        AuthenticationResponse response = new AuthenticationResponse();
        if (Objects.isNull(user)) {
            BaseSingleResultResponse.ErrorInfo error = new BaseSingleResultResponse.ErrorInfo();
            error.setCode(ErrorInfo.UNAUTHORIZED.getCode());
            error.setMessage(ErrorInfo.UNAUTHORIZED.getText());
            response.setError(error);
            return response;
        }
        response.setData(JwtHandler.generateToken(user.getId()));
        return response;
    }

}

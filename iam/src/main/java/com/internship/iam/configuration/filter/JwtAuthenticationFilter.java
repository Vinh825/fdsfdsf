package com.internship.iam.configuration.filter;

import com.internship.iam.internalapi.service.RolePrivilegeService;
import com.internship.iam.internalapi.service.UserRoleService;
import com.internship.iam.internalapi.service.UserService;
import com.internship.iam.model.entity.*;
import com.internship.iam.utils.JwtHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (!Objects.isNull(jwt)
            && JwtHandler.isValid(jwt)) {
            String userId = JwtHandler.getUserId(jwt);
            User user = userService.getOneById(userId);
            if (!Objects.isNull(user)) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                Set<String> privilegeCodes = new HashSet<>();
                List<UserRole> useRoles = userRoleService.getManyByUserId(userId);
                for (UserRole userRole : useRoles) {
                    Role role = userRole.getRole();
                    String roleId = role.getId();
                    List<RolePrivilege> rolePrivileges = rolePrivilegeService.getManyByRoleId(roleId);
                    for (RolePrivilege rolePrivilege : rolePrivileges) {
                        Privilege privilege = rolePrivilege.getPrivilege();
                        privilegeCodes.add(privilege.getCode());
                    }
                }
                for (String privilegeCode : privilegeCodes) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(privilegeCode);
                    authorities.add(grantedAuthority);
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        user.getPassword(),
                        authorities
                );
                authenticationToken.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

package com.healthcare.patient.filter;

import com.healthcare.patient.IamServiceClient;
import com.healthcare.patient.dto.request.AuthenticationRequest;
import com.healthcare.patient.dto.response.AuthenticationResponse;
import com.healthcare.patient.dto.response.AuthenticationResponseData;
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

    // 1. Tiêm Feign Client vào
    @Autowired
    private IamServiceClient iamServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (!Objects.isNull(jwt)) {
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setToken(jwt);

            // 2. GỌI BẰNG FEIGN CLIENT (thay vì RestTemplate + ObjectMapper)
            // Feign tự động gọi, tự thêm API key, tự parse JSON
            AuthenticationResponse authenticationResponse = iamServiceClient.login(authenticationRequest);

            AuthenticationResponseData authenticationResponseData = authenticationResponse.getData();
            Set<String> privilegeCodes = authenticationResponseData.getPrivilegeCodes();
            System.out.println(">>>>>>>>>>>>>>>>>> " + privilegeCodes);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String privilegeCode : privilegeCodes) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(privilegeCode);
                authorities.add(grantedAuthority);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationResponseData.getUserId(),
                    authenticationResponseData.getRoleCode(),
                    authorities
            );
            authenticationToken.setDetails(authenticationResponseData);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
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
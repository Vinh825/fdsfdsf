package com.healthcare.patient.filter;

import com.healthcare.patient.dto.response.AuthenticationResponseData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor // Sử dụng Lombok để tự động tiêm bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 1. Tiêm Service trung gian (thay vì tiêm Client)
    private final IamAuthService iamAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Xóa cache cũ
        SecurityContextHolder.clearContext();

        String jwt = getJwtFromRequest(request);
        if (jwt != null) {
            try {
                // 2. Gọi Service (thay vì client.login)
                AuthenticationResponseData authData = iamAuthService.getAuthenticationData(jwt);

                // 3. Lấy Privilege từ response (đã giống code cũ)
                Set<String> privilegeCodes = authData.getPrivilegeCodes();
                if (privilegeCodes == null) {
                    privilegeCodes = Collections.emptySet();
                }

                List<GrantedAuthority> authorities = privilegeCodes.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // 4. Tạo AuthenticationToken
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        authData.getUserId(), // Principal (quan trọng)
                        null, // Credentials (không cần)
                        authorities // Danh sách quyền (Privileges)
                );

                // 5. Ghi chi tiết thông tin user vào context (để Controller lấy nếu cần)
                authenticationToken.setDetails(authData);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (Exception e) {
                // Nếu FeignErrorDecoder ném lỗi, hoặc IamAuthService ném lỗi
                logger.warn("JWT Authentication failed: " + e.getMessage());
                // Không set SecurityContext -> request sẽ bị từ chối ở SecurityConfig
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
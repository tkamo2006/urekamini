package com.uplus.miniproject2.jwt;

import com.uplus.miniproject2.entity.user.CustomUserDetails;
import com.uplus.miniproject2.entity.user.Role;
import com.uplus.miniproject2.entity.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 리소스 파일 요청은 필터를 통과시키도록 설정
        String path = request.getRequestURI();
        if (path.startsWith("/img/") || path.endsWith(".png") || path.endsWith(".jpg")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 요청에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            // 토큰이 만료된 경우, 리프레시 토큰을 사용하여 새 액세스 토큰 발급 시도
            String refreshToken = getRefreshTokenFromCookies(request);
            if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                String username = jwtUtil.getUsername(refreshToken);
                String role = jwtUtil.getRole(refreshToken).split("_")[1];
                Long id = jwtUtil.getId(refreshToken);

                String newAccessToken = jwtUtil.createJwt(id, username, role, 60 * 60 * 100L);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"accessToken\": \"" + newAccessToken + "\"}");

                // 선택적으로, 응답 헤더나 쿠키에 새로운 액세스 토큰을 설정할 수 있음
                response.setHeader("Authorization", "Bearer " + newAccessToken);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않거나 만료된 리프레시 토큰입니다.");
                return;
            }
        } else {
            // 토큰에서 사용자 이름과 역할을 획득
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token).split("_")[1];
            Long userId = jwtUtil.getId(token);

            // UserEntity를 생성하여 값 설정
            User user = User.builder()
                    .id(userId)
                    .username(username)
                    .password("temppassword")
                    .role(Role.valueOf(role))
                    .build();

            // UserDetails에 사용자 정보 객체 담기
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            // 스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                    customUserDetails.getAuthorities());

            // 세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    // 쿠키에서 리프레시 토큰을 가져오는 헬퍼 메소드
    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if ("Refresh-Token".equals(cookie.getName())) {
                return cookie.getValue().replace("Bearer ", "");
            }
        }
        return null;
    }
}

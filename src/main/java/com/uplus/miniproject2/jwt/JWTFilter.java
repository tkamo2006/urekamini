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
import org.springframework.http.ResponseEntity;
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

        // 요청 헤더에서 Authorization 헤더 가져오기
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우 필터 체인으로 넘어감
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 JWT 토큰 추출
        String token = authorization.split(" ")[1];

        // 토큰이 만료된 경우 처리
        if (token == null || jwtUtil.isExpired(token)) {
            logger.info("isExpired Token");

            // 쿠키에서 리프레시 토큰 가져오기
            String refreshToken = getRefreshTokenFromCookies(request);
            logger.info("refresh Token " + refreshToken);

            // 리프레시 토큰이 존재하고 유효한 경우
            if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                logger.info("Refresh Token not Expired");

                // 리프레시 토큰에서 사용자 정보 추출
                String username = jwtUtil.getUsername(refreshToken);
                String role = jwtUtil.getRole(refreshToken);
                Long id = jwtUtil.getId(refreshToken);

                // 새로운 액세스 토큰 생성
                String newAccessToken = jwtUtil.createJwt(id, username, role, 60 * 60 * 10 * 100L);
                logger.info("New access token: " + newAccessToken);

                // 응답 헤더에 새 액세스 토큰 추가
                response.addHeader("Authorization", "Bearer " + newAccessToken);

                // 응답을 작성하고 필터 체인 종료
                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().flush();

                saveAuthentication(newAccessToken);

                filterChain.doFilter(request, response);
                return;
            } else {
                // 리프레시 토큰이 없거나 만료된 경우 오류 응답 반환
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않거나 만료된 리프레시 토큰입니다.");
                return;
            }
        }

        saveAuthentication(token);

        // 다음 필터로 처리 넘김
        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(String token) {
        // 액세스 토큰이 유효한 경우 사용자 정보 추출 및 인증 설정
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token).split("_")[1];

        Long userId = jwtUtil.getId(token);

        // 사용자 정보로 User 객체 생성
        User user = User.builder()
                .id(userId)
                .username(username)
                .password("temppassword") // 적절한 비밀번호 설정 필요
                .role(Role.valueOf(role))
                .build();

        // CustomUserDetails 객체 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 인증 토큰 생성 및 SecurityContext에 설정
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


    // 쿠키에서 리프레시 토큰을 가져오는 헬퍼 메소드
    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("Refresh-Token".equals(cookie.getName())) {
                    logger.info(cookie.getName());
                    return cookie.getValue();
                }
            }
        }
        logger.info("cookie not found");
        return null;
    }
}

package com.uplus.miniproject2.jwt;

import com.uplus.miniproject2.entity.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
                null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        Long id = customUserDetails.getId();
        String accessToken = jwtUtil.createJwt(id, username, role, 60 * 60 * 10 * 10L); // 1 hour
        String refreshToken = jwtUtil.createJwt(id, username, role, 24 * 60 * 60 * 1000L); // 1 day

        // Set Refresh Token in HttpOnly cookie
        // 리프레시 토큰이 HttpOnly, Secure 속성으로 쿠키에 저장되어 있을 때, 브라우저는 이를 서버로 자동으로 전송
        // Cookie 객체를 생성
        Cookie cookie = new Cookie("Refresh-Token", refreshToken);
        cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없게
        cookie.setSecure(true); // Secure 속성이 true로 설정된 쿠키는 HTTPS 프로토콜을 사용하는 경우에만 전송
        cookie.setPath("/"); // 쿠키의 유효 경로를 설정, /로 설정하면 쿠키는 웹 애플리케이션의 모든 경로에서 접근할 수 있다.

        response.addCookie(cookie); // 이 메서드는 생성된 쿠키를 HTTP 응답에 추가
        // 쿠키는 이후의 요청에서 자동으로 서버에 포함

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {

        response.setStatus(401);
    }
}
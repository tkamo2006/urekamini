package com.uplus.miniproject2.config;

import com.uplus.miniproject2.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 세션 방식은 세션이 항상 고정되어 csrf 공격을 필수적으로 방어가 필요,
        // jwt방식은 세션을 stateless 상태로 관리되기 때문에 방어하지 않아도 됨
        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        // jwt 방식으로 로그인을 진핼하기 때문
        // From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());
        // jwt 방식으로 로그인을 진핼하기 때문
        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업 ( 필요한 권한을 가져야 하는지 로그인이 필요한지)
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll() // 누구나
                        .requestMatchers("/admin").hasRole("ADMIN") // 어드민 권한
                        .anyRequest().authenticated()); // 로그인시
        // jwt 방식에서는 세션을 항상 stateless 상태로 관리

        http
                .addFilterAt(new LoginFilter(), UsernamePasswordAuthenticationFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();


    }
}

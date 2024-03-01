package com.bokyoung.userService.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/user-service/**",
                                // TODO: 외부 마이크로서비스와 feignClinet로 통신 정상 여부 확인하기 위한 임시 허용. API gateway 적용 후 삭제 필요
                                // TODO: API gateway 필터 단에서 사용자 인증 처리(토큰 검증)하고 internal api 호출함. 그럼 internal은 permitAll 해줘도 되는 거 아닌지?
                                "/api/internal/**")
                        .permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

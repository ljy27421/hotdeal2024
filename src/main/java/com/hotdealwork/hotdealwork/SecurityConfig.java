package com.hotdealwork.hotdealwork;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지 접근에는 ROLE_ADMIN 필요
                        .requestMatchers("/user/login", "/user/signup", "/css/**", "/js/**", "/images/**").permitAll() // 로그인, 회원가입, 정적 자원은 모두 접근 허용
                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증 필요
                .formLogin(formLogin -> formLogin
                        .loginPage("/user/login") // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/")   // 로그인 성공 시 기본 경로
                        .permitAll())             // 로그인 페이지는 모두 접근 가능
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 경로
                        .logoutSuccessUrl("/")    // 로그아웃 성공 시 리디렉션 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .permitAll());            // 로그아웃은 모두 접근 가능
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
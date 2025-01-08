package com.newcine.back.config;

import java.io.PrintWriter;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 특정 http 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)// Cross site Request forgery : 정상적인 사용자가 의도치 않은 위조 요청 보내는것
                .cors((cors) -> cors.configurationSource(corsConfigurationSource())) // 교차 출처 리소스 공유, 출처가 다른 리소스 간의 공유
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/movie/**") // 특정 요청과 일치하는 url에 대한 access 설정
                        .permitAll() // requestMatchers에서 지정된 url 인증,인가 없이도 접근 허용 (ignoing() -> 허락x)
                        .anyRequest().authenticated())
                .exceptionHandling((exception) -> exception // 401,403 예외처리
                        .authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler))
                .formLogin((formLogin) -> formLogin.disable()) // 사용자 정의 로그인 페이지 만들어 사용하기 위해 비활성화
                .httpBasic((httpBasic) -> httpBasic.disable()) // 토큰 기반 인증(예: JWT, OAuth 2.0) 사용하는 경우 비활성화
                .logout((logout) -> logout
                        .logoutSuccessUrl("/movie/login")
                        .invalidateHttpSession(true)) // 세션에 저장된 모든 데이터 삭제됨(현재 세션을 완전히 무효화)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));// HTTP 세션을 사용하지 않도록 설정하는 역할. RESTful
                                                                                 // API와 같은 상태 없는(stateless) 인증 방식에서 사용.
                                                                                 // JWT(JSON Web Token) 또는 다른 토큰 기반 인증
                                                                                 // 방식을 사용할 때 주로 사용되며, 서버는 상태를 저장하지 않고,
                                                                                 // 클라이언트가 제공하는 토큰을 통해 인증을 처리한다.

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration(); // 허용할 origin 설정
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowCredentials(true); // 쿠키 허용
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 http 메소드 허용

        config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers",
                "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, ",
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")); // 헤더 요청 열어둠
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 위 설정 적용

        return source;
    }

    // 401 Unauthorized 응답을 반환
    private final AuthenticationEntryPoint unauthorizedEntryPoint = (request, response, authException) -> {
        ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String json = new ObjectMapper().writeValueAsString(fail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    };

    // 403 Forbidden 응답을 반환
    private final AccessDeniedHandler accessDeniedHandler = (request, response, accessDeniedException) -> {
        ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String json = new ObjectMapper().writeValueAsString(fail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    };

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {
        private final HttpStatus status;
        private final String message;
    }
}
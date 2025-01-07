package com.newcine.back.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
                .csrf(AbstractHttpConfigurer::disable) // Cross site Request forgery : 정상적인 사용자가 의도치 않은 위조 요청 보내는것
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/signup", "/login") // 특정 요청과 일치하는 url에 대한 access 설정
                        .permitAll() // requestMatchers에서 지정된 url 인증,인가 없이도 접근 허용 (ignoing() -> 허락x)
                        .anyRequest().authenticated())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)) // 로그아웃 이후 전체 세션 삭제 여부 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 생성 및 사용여부 설정

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers",
                "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                        "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")); // 헤더 요청 열어둠

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

/*
 * @Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * // 로그인 페이지 나오지 않게 anyRequest().permitAll() 추가 > 모든 URL 허용
 * http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
 * return http.build();
 * }
 */
/*
 * @Bean
 * //anyRequest > any 요청이 들어오면 authenticated 됨
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * http.authorizeHttpRequests(auth ->
 * auth.anyRequest().authenticated()).formLogin(formLogin -> formLogin
 * .usernameParameter("username")
 * .passwordParameter("password")
 * .defaultSuccessUrl("/test", true));
 * 
 * return http.build();
 * }
 */
/*
 * @Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * http.authorizeHttpRequests(auth -> auth
 * .requestMatchers("/test1").hasRole("user")
 * .requestMatchers("/test2").hasRole("admin")
 * .anyRequest().authenticated())
 * .formLogin(formLogin -> formLogin
 * .usernameParameter("username")
 * .passwordParameter("password")
 * .defaultSuccessUrl("/", true));
 * 
 * return http.build();
 * }
 * 
 * @Bean
 * public UserDetailsService userDetailsService() {
 * // roles 추가
 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
 * manager.createUser(User.withUsername("user1").password("1234").roles("user").
 * build());
 * return manager;
 * }
 * 
 * @Bean
 * public PasswordEncoder passwordEncoder() {
 * return NoOpPasswordEncoder.getInstance();
 * }
 */
/*
 * @Bean
 * public BCryptPasswordEncoder bCryptPasswordEncoder() {
 * return new BCryptPasswordEncoder();
 * }
 */
/*
 * @Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * http.csrf(AbstractHttpConfigurer::disable)
 * .formLogin(Customizer.withDefaults())
 * .authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers(
 * AntPathRequestMatcher.antMatcher("/auth/**")).authenticated() // "/auth/**"
 * 요청은 인증 필요
 * .anyRequest().permitAll())
 * .headers(
 * headersConfigurer -> headersConfigurer
 * .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
 * 
 * return http.build();
 * }
 */

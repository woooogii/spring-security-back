package com.newcine.back.global.login.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newcine.back.global.login.dto.LoginReq;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String DEFAULT_LOGIN_REQUEST_URL = "/user/login"; // "/api/login/*" 로 오는 요청 처리
    private static final String HTTP_METHOD = "POST"; // HTTP 메서드 방식
    private static final String CONTENT_TYPE = "application/json"; // json 타입의 데이터로만 로그인 진행
    private final ObjectMapper objectMapper;
    private static final String USEREMAIL_KEY = "userName";
    private static final String PASSWORD_KEY = "userPwd";

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); // /login 요청에 POST로 온 요청 매칭

    public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); // 위에서 설정한 "/login/*" 의 요청에 GET으로 온 요청 처리하기 위해 설정
        this.objectMapper = objectMapper;
    }

    // 로그인 요청
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null || !request.getContentType().equals((CONTENT_TYPE))) {
            throw new AuthenticationServiceException(
                    "Authentication Content-Type not supported" + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        /*
         * Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody,
         * Map.class);
         * 
         * String userName = usernamePasswordMap.get(USEREMAIL_KEY);
         * String userPwd = usernamePasswordMap.get(PASSWORD_KEY);
         */

        LoginReq loginRequest = objectMapper.readValue(messageBody, LoginReq.class);
        String userName = loginRequest.getUserName();
        String userPwd = loginRequest.getUserPwd();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, userPwd);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}

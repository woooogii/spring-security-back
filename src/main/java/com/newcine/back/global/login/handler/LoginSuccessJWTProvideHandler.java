package com.newcine.back.global.login.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.newcine.back.global.jwt.JwtService;
import com.newcine.back.users.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String userName = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(userName);
        String refreshToken = jwtService.createRefreshToken();
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        userRepository.findByUserName(userName).ifPresent(users -> {
            users.updateRefreshToken(refreshToken);
            userRepository.save(users);
        });

        log.info("로그인 성공: userName - {}", userName);
        log.info("AccessToken 발급: AccessToken - {}", accessToken);
        log.info("RefreshToken 발급: RefreshToken - {}", refreshToken);
        // response.getWriter().write("success");
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}

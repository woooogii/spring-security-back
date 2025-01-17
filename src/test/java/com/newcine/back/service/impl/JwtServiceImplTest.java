package com.newcine.back.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.newcine.back.global.jwt.JwtService;
import com.newcine.back.users.entity.UserEntity;
import com.newcine.back.users.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JwtServiceImplTest {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenValidityInSeconds;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "email";
    private static final String BEARER = "Bearer";

    String username = "username";

    @BeforeEach
    public void init() {
        UserEntity user = UserEntity.builder()
                .userName(username)
                .userPwd("123456")
                .userEmail("test@naver.com")
                .userNikname("usernick").build();
        userRepository.save(user);
        clear();
    }

    private void clear() {
        em.flush();
        em.clear();
    }

    // 토큰 유효성 확인
    private DecodedJWT getVerify(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    @Test
    void createAccessToken_AccessToken_발급테스트() throws Exception {
        // given, when
        String accessToken = jwtService.createAccessToken(username);
        DecodedJWT verify = getVerify(accessToken);

        String subject = verify.getSubject();
        String findUsername = verify.getClaim(USERNAME_CLAIM).asString();

        // then
        assertThat(findUsername).isEqualTo(username);
        assertThat(subject).isEqualTo(ACCESS_TOKEN_SUBJECT);
    }

    @Test
    void createAccessToken_RefreshToken_발급테스트() throws Exception {
        // given, when
        String refreshToken = jwtService.createRefreshToken();
        DecodedJWT verify = getVerify(refreshToken);
        String subject = verify.getSubject();
        String username = verify.getClaim(USERNAME_CLAIM).asString();

        // then
        assertThat(subject).isEqualTo(REFRESH_TOKEN_SUBJECT);
        assertThat(username).isNull();
    }

    @Test
    void updateRefreshToken_refreshToken_업데이트() throws Exception {
        // given
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(username, refreshToken);
        clear();
        Thread.sleep(5000);

        // when
        String reIssueRefreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(username, reIssueRefreshToken);
        clear();

        // then
        assertThrows(NoSuchElementException.class, () -> userRepository.findByRefreshToken(refreshToken).get());
        assertThat(userRepository.findByRefreshToken(reIssueRefreshToken).get().getUserName()).isEqualTo(username);
    }

    @Test
    void isTokenValid_토큰_유효성_검사() {
        // given
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();

        // when, then
        assertThat(jwtService.isTokenValid(accessToken)).isTrue();
        assertThat(jwtService.isTokenValid(refreshToken)).isTrue();
    }

    @Test
    void destoryRefreshToken_refreshToken_제거() throws Exception {
        // given
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(username, refreshToken);
        clear();

        // when
        jwtService.destoryRefreshToken(username);
        clear();

        // then
        assertThrows(Exception.class, () -> userRepository.findByRefreshToken(refreshToken).get());
        UserEntity users = userRepository.findByUserName(username).get();
        assertThat(users.getRefreshToken()).isNull();
    }

    @Test
    void setAccessTokenHeader_AccessToken_헤더_설정() throws Exception {
        // given
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.setAccessTokenHeader(mockHttpServletResponse, accessToken);

        // when
        jwtService.sendAccessAndRefreshToken(mockHttpServletResponse, accessToken, refreshToken);

        // then
        String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
        assertThat(headerAccessToken).isEqualTo(accessToken);
    }

    @Test
    void setRefreshTokenHeader_RefreshToken_헤더_설정() throws Exception {
        // given
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.setRefreshTokenHeader(mockHttpServletResponse, refreshToken);

        // when
        jwtService.sendAccessAndRefreshToken(mockHttpServletResponse, accessToken, refreshToken);

        // then
        String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);
        assertThat(headerRefreshToken).isEqualTo(refreshToken);
    }

    @Test
    void sendAccessAndRefreshToken_토큰_전송() {
        // given
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();

        // when
        jwtService.sendAccessAndRefreshToken(mockHttpServletResponse, accessToken, refreshToken);

        // then
        String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
        String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

        assertThat(headerAccessToken).isEqualTo(accessToken);
        assertThat(headerRefreshToken).isEqualTo(refreshToken);
    }

    @Test
    void extractAccessToken_AccessToken_추출() throws Exception {
        // given
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();
        HttpServletRequest request = setRequset(accessToken, refreshToken);

        // when
        String extractAccessToken = jwtService.extractAccessToken(request)
                .orElseThrow(() -> new Exception("예외 - Access 토큰이 없습니다."));

        // then
        assertThat(extractAccessToken).isEqualTo(accessToken);
        assertThat(getVerify(extractAccessToken).getClaim(USERNAME_CLAIM).asString()).isEqualTo(username);
    }

    @Test
    void extractRefreshToken_RefreshToken_추출() throws Exception {
        // given
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();
        HttpServletRequest request = setRequset(accessToken, refreshToken);

        // when
        String extractRefreshToken = jwtService.extractRefreshToken(request)
                .orElseThrow(() -> new Exception("예외 - Refresh 토큰이 없습니다."));

        // then
        assertThat(extractRefreshToken).isEqualTo(refreshToken);
        // refreshToken은 username 없음
        assertThat(getVerify(extractRefreshToken).getSubject()).isEqualTo(REFRESH_TOKEN_SUBJECT);
    }

    private HttpServletRequest setRequset(String accessToken, String refreshToken) throws IOException {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        jwtService.sendAccessAndRefreshToken(mockHttpServletResponse, accessToken, refreshToken);
        String headerAccessToken = mockHttpServletResponse.getHeader(accessHeader);
        String headerRefreshToken = mockHttpServletResponse.getHeader(refreshHeader);

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        mockHttpServletRequest.addHeader(accessHeader, BEARER + headerAccessToken);
        mockHttpServletRequest.addHeader(refreshHeader, BEARER + headerRefreshToken);

        return mockHttpServletRequest;
    }

    @Test
    void extractUsername_username_추출() throws Exception {
        // given
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();
        HttpServletRequest request = setRequset(accessToken, refreshToken);
        String requestAccessToken = jwtService.extractAccessToken(request)
                .orElseThrow(() -> new Exception("예외 - 토큰이 없습니다."));

        // when
        String extractUsername = jwtService.extractUsername(requestAccessToken)
                .orElseThrow(() -> new Exception("예외 - 토큰 없음"));

        // then
        assertThat(extractUsername).isEqualTo(username);
    }
}

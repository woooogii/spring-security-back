package com.newcine.back.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.newcine.back.global.jwt.JwtService;
import com.newcine.back.users.entity.UserEntity;
import com.newcine.back.users.repository.UserRepository;

import jakarta.persistence.EntityManager;

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

    // 토큰 유효성 검사
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
        assertThat(userRepository.findByRefreshToken(reIssueRefreshToken).get()).isEqualTo(username);
    }

    /*
     * @Test
     * void testDestoryRefreshToken() {
     * 
     * }
     */

    /*
     * @Test
     * void testExtractAccessToken() {
     * 
     * }
     * 
     * @Test
     * void testExtractEmail() {
     * 
     * }
     * 
     * @Test
     * void testExtractRefreshToken() {
     * 
     * }
     * 
     * @Test
     * void testIsTokenValid() {
     * 
     * }
     * 
     * @Test
     * void testSendAccessAndRefreshToken() {
     * 
     * }
     * 
     * @Test
     * void testSendAccessToken() {
     * 
     * }
     * 
     * @Test
     * void testSetAccessTokenHeader() {
     * 
     * }
     * 
     * @Test
     * void testSetRefreshTokenHeader() {
     * 
     * }
     * 
     */

}

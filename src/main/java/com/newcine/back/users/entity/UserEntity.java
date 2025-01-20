package com.newcine.back.users.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.newcine.back.common.BaseTimeEntity;
import com.newcine.back.common.constant.Role;
import com.newcine.back.common.constant.SocialType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userinfo_id")
    private Long id; // pk

    @Column(nullable = false, unique = true)
    private String userName; // 아이디

    @Column(nullable = false)
    private String userPwd; // 비밀번호

    @Column(nullable = false, unique = true)
    private String userEmail; // 이메일

    @Column(nullable = false, unique = true)
    private String userNikname; // 닉네임

    @Column(length = 1000)
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // == 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPwd = passwordEncoder.encode(userPwd);
    }

    // 회원가입시, USER의 권한을 부여
    public void addUserAuthority() {
        this.role = Role.USER;
    }
}

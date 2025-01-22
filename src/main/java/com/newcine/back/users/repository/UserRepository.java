package com.newcine.back.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newcine.back.users.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserEmail(String email);

    Optional<UserEntity> findByUserName(String username);

    boolean existsByUserName(String username);

    boolean existsByUserEmail(String email);

    boolean existsByUserNickname(String nickName);

    Optional<UserEntity> findByRefreshToken(String refreshToken);

}

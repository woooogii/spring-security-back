package com.newcine.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newcine.back.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserEmail(String email);

    boolean existsByUserEmail(String email);

    Optional<UserEntity> findByRefreshToken(String refreshToken);

}

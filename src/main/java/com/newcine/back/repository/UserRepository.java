package com.newcine.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newcine.back.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}

package com.newcine.back.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.newcine.back.dto.UserRequestDTO;
import com.newcine.back.dto.UserResponseDTO;
import com.newcine.back.entity.UserEntity;

@Component
public class UserMapper {
    public UserEntity toUserEntity(UserRequestDTO requestDTO) {
        return UserEntity.builder()
                .userId(requestDTO.getUserId())
                .userPwd(requestDTO.getUserPwd())
                .userEmail(requestDTO.getUserEmail())
                .userNikname(requestDTO.getUserNikname())
                .build();
    }

    public UserResponseDTO toResponseDto(UserEntity userEntity) {
        return UserResponseDTO.of(
                userEntity.getUserId(),
                userEntity.getUserPwd(),
                userEntity.getUserEmail(),
                userEntity.getUserNikname());
    }

    public List<UserResponseDTO> toResponseDtos(List<UserEntity> userEntity) {
        return userEntity.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

}

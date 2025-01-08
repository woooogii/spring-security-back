package com.newcine.back.service;

import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.newcine.back.dto.UserRequestDTO;
import com.newcine.back.dto.UserResponseDTO;
import com.newcine.back.entity.UserEntity;
import com.newcine.back.repository.UserRepository;
import com.newcine.back.service.impl.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /*
     * // 로그인
     * public UserResponseDTO userLogin(UserRequestDTO userRequest){
     * try {
     * 
     * } catch (Exception e) {
     * // TODO: handle exception
     * }
     * }
     */

    // 회원가입
    public UserResponseDTO userSignup(UserRequestDTO userRequest) {
        try {
            UserEntity userEntity = userMapper.toUserEntity(userRequest);
            userRepository.save(userEntity);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public UserResponseDTO saveUser(UserRequestDTO userDTO) {
        try {
            UserEntity userEntity = userMapper.toUserEntity(userDTO);
            userRepository.save(userEntity);
            UserResponseDTO userResponseDTO = userMapper.toResponseDto(userEntity);
            return userResponseDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<UserResponseDTO> getAllList() {
        try {
            List<UserEntity> userEntity = userRepository.findAll();
            List<UserResponseDTO> userResponseDTO = userMapper.toResponseDtos(userEntity);
            return userResponseDTO;
        } catch (Exception e) {
            throw e;
        }
    }
}

package com.newcine.back.users.service;

import org.springframework.http.ResponseEntity;

import com.newcine.back.global.common.exception.ErrorResponseDTO;
import com.newcine.back.users.domain.dto.UserRequestDTO;
import com.newcine.back.users.domain.dto.UserResponseDTO;

public interface UserService {
    // 회원가입
    ResponseEntity<ErrorResponseDTO> signup(UserRequestDTO userRequestDTO) throws Exception;

    // 회원조회
    UserResponseDTO findUser(Long userRequestDTO) throws Exception;

    // 회원정보수정
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);

    // 회원탈퇴
    void deleteUser(UserRequestDTO userRequestDTO);

    // 아이디 중복 조회
    ResponseEntity<ErrorResponseDTO> checkUserName(UserRequestDTO userRequestDTO) throws Exception;

    // 이메일 중복 조회
    ResponseEntity<ErrorResponseDTO> checkUserEmail(UserRequestDTO userRequestDTO) throws Exception;

    // 닉네임 중복 조회
    ResponseEntity<ErrorResponseDTO> checkNickname(UserRequestDTO userRequestDTO) throws Exception;
}

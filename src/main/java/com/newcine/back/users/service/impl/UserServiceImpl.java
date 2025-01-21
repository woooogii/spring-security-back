package com.newcine.back.users.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcine.back.global.common.constant.ResponseCode;
import com.newcine.back.global.common.exception.ErrorResponseDTO;
import com.newcine.back.users.domain.UserMapper;
import com.newcine.back.users.domain.dto.UserRequestDTO;
import com.newcine.back.users.domain.dto.UserResponseDTO;
import com.newcine.back.users.entity.UserEntity;
import com.newcine.back.users.repository.UserRepository;
import com.newcine.back.users.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public ResponseEntity<ErrorResponseDTO> signup(UserRequestDTO userRequestDTO) throws Exception {
        UserEntity userEntity = userMapper.toUserEntity(userRequestDTO);
        userEntity.addUserAuthority();
        userEntity.encodePassword(passwordEncoder);

        boolean checkUser = userRepository.existsByUserName(userEntity.getUserName());
        if (checkUser) {
            return ErrorResponseDTO.customError(
                    HttpStatus.BAD_REQUEST.value(), ResponseCode.JOIN_NOT_MATCHED.getCode(),
                    ResponseCode.JOIN_NOT_MATCHED.getMessage());
        }
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResponseDTO(
                        ResponseCode.JOIN_SUCCESS.getCode(),
                        ResponseCode.JOIN_SUCCESS.getMessage()));
    }

    // 아이디 중복 확인 (true면 아이디 중복, false면 회원가입 가능)
    @Override
    public ResponseEntity<ErrorResponseDTO> checkUserName(UserRequestDTO userRequestDTO) throws Exception {
        boolean result = userRepository.existsByUserName(userRequestDTO.getUserName());
        if (result) {
            return ErrorResponseDTO.customError(
                    HttpStatus.BAD_REQUEST.value(), ResponseCode.VALIDATION_FAILED.getCode(),
                    ResponseCode.VALIDATION_FAILED.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResponseDTO(
                        ResponseCode.VALIDATION_SUCCESS.getCode(),
                        ResponseCode.VALIDATION_SUCCESS.getMessage()));
    }

    // 이메일 중복 확인 (true면 아이디 중복, false면 회원가입 가능)
    @Override
    public ResponseEntity<ErrorResponseDTO> checkUserEmail(UserRequestDTO userRequestDTO) throws Exception {
        boolean result = userRepository.existsByUserEmail(userRequestDTO.getUserEmail());
        if (result) {
            return ErrorResponseDTO.customError(
                    HttpStatus.BAD_REQUEST.value(), ResponseCode.VALIDATION_FAILED.getCode(),
                    ResponseCode.VALIDATION_FAILED.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResponseDTO(
                        ResponseCode.VALIDATION_SUCCESS.getCode(),
                        ResponseCode.VALIDATION_SUCCESS.getMessage()));
    }

    // 회원조회
    @Override
    public UserResponseDTO findUser(Long userRequestDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");

    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(UserRequestDTO userRequestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

}

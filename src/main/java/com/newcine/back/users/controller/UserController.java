package com.newcine.back.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newcine.back.global.common.constant.ResponseCode;
import com.newcine.back.global.common.domain.ResponseDomain;
import com.newcine.back.global.common.exception.ErrorResponseDTO;
import com.newcine.back.users.domain.dto.UserRequestDTO;
import com.newcine.back.users.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseDomain<Integer> userSignup(@RequestBody UserRequestDTO userDTO) throws Exception {
        userService.signup(userDTO);
        return ResponseDomain.<Integer>builder()
                .code(ResponseCode.JOIN_SUCCESS.getCode())
                .message(ResponseCode.JOIN_SUCCESS.getMessage())
                .build();
    }

    // 아이디 중복 조회
    @PostMapping("/checkId")
    public ResponseDomain<Integer> checkUserName(@RequestBody UserRequestDTO userDTO) throws Exception {
        ResponseEntity<ErrorResponseDTO> response = userService.checkUserName(userDTO);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseDomain.<Integer>builder()
                    .code(ResponseCode.VALIDATION_SUCCESS.getCode())
                    .message(ResponseCode.VALIDATION_SUCCESS.getMessage())
                    .data(1)
                    .build();
        }
        return ResponseDomain.<Integer>builder()
                .code(ResponseCode.VALIDATION_FAILED.getCode())
                .message(ResponseCode.VALIDATION_FAILED.getMessage())
                .data(0)
                .build();
    }

    // 이메일 중복 조회
    @PostMapping("/checkEmail")
    public ResponseDomain<Integer> checkUserEmail(@RequestBody UserRequestDTO userDTO) throws Exception {
        ResponseEntity<ErrorResponseDTO> response = userService.checkUserEmail(userDTO);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseDomain.<Integer>builder()
                    .code(ResponseCode.VALIDATION_SUCCESS.getCode())
                    .message(ResponseCode.VALIDATION_SUCCESS.getMessage())
                    .data(1)
                    .build();
        }
        return ResponseDomain.<Integer>builder()
                .code(ResponseCode.VALIDATION_FAILED.getCode())
                .message(ResponseCode.VALIDATION_FAILED.getMessage())
                .data(0)
                .build();
    }

}

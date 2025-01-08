package com.newcine.back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newcine.back.dto.UserRequestDTO;
import com.newcine.back.dto.UserResponseDTO;
import com.newcine.back.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> userLogin(@RequestBody UserRequestDTO userDTO) {
        UserResponseDTO userResponseDTO = userService.saveUser(userDTO);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> userSignup(@RequestBody UserRequestDTO userDTO) {
        UserResponseDTO userResponseDTO = userService.saveUser(userDTO);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> getUserList() {
        List<UserResponseDTO> userResponseDTO = userService.getAllList();
        return ResponseEntity.ok().body(userResponseDTO);
    }

}

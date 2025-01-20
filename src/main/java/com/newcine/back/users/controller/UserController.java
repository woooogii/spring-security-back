package com.newcine.back.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newcine.back.users.domain.dto.UserRequestDTO;
import com.newcine.back.users.domain.dto.UserResponseDTO;
import com.newcine.back.users.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    /*
     * // 로그인
     * public ResponseEntity<UserResponseDTO> userLogin(@RequestBody UserRequestDTO
     * userDTO) {
     * UserResponseDTO userResponseDTO = userService.saveUser(userDTO);
     * return ResponseEntity.ok().body(userResponseDTO);
     * }
     */

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Void> userSignup(@RequestBody UserRequestDTO userDTO) throws Exception {
        userService.signup(userDTO);
        return ResponseEntity.ok().build(); // void를 반환하므로 build() 사용
    }

    /*
     * @GetMapping("/list")
     * public ResponseEntity<List<UserResponseDTO>> getUserList() {
     * List<UserResponseDTO> userResponseDTO = userService.getAllList();
     * return ResponseEntity.ok().body(userResponseDTO);
     * }
     */

}

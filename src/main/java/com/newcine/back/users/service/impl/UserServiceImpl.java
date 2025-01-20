package com.newcine.back.users.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void signup(UserRequestDTO userRequestDTO) throws Exception {
        UserEntity userEntity = userMapper.toUserEntity(userRequestDTO);
        userEntity.addUserAuthority();
        userEntity.encodePassword(passwordEncoder);

        if (userRepository.findByUserName(userRequestDTO.getUserName()).isPresent()) {
            throw new Exception("이미 존재하는 아이디입니다.");
        }
        userRepository.save(userEntity);
    }

    // 아이디 중복 확인
    /*
     * private UserEntity checkUserName(String userName) {
     * Optional<UserEntity> checkUser = userRepository.findByUserName(userName);
     * if (checkUser.isPresent()) {
     * throw new Exception("이미 존재하는 아이디입니다.");
     * } else {
     * return checkUser;
     * }
     * }
     */

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

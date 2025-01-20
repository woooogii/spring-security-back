package com.newcine.back.users.service;

import com.newcine.back.users.domain.dto.UserRequestDTO;
import com.newcine.back.users.domain.dto.UserResponseDTO;

public interface UserService {

    // private final UserRepository userRepository;
    // private final UserMapper userMapper;

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
    /*
     * public UserResponseDTO userSignup(UserRequestDTO userRequest) {
     * try {
     * UserEntity userEntity = userMapper.toUserEntity(userRequest);
     * userRepository.save(userEntity);
     * } catch (Exception e) {
     * // TODO: handle exception
     * }
     * }
     */
    /*
     * public UserResponseDTO saveUser(UserRequestDTO userDTO) {
     * try {
     * UserEntity userEntity = userMapper.toUserEntity(userDTO);
     * userRepository.save(userEntity);
     * UserResponseDTO userResponseDTO = userMapper.toResponseDto(userEntity);
     * return userResponseDTO;
     * } catch (Exception e) {
     * throw e;
     * }
     * }
     * 
     * public List<UserResponseDTO> getAllList() {
     * try {
     * List<UserEntity> userEntity = userRepository.findAll();
     * List<UserResponseDTO> userResponseDTO =
     * userMapper.toResponseDtos(userEntity);
     * return userResponseDTO;
     * } catch (Exception e) {
     * throw e;
     * }
     * }
     */
    // 회원가입
    void signup(UserRequestDTO userRequestDTO) throws Exception;

    // 회원조회
    UserResponseDTO findUser(Long userRequestDTO) throws Exception;

    // 회원정보수정
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);

    // 회원탈퇴
    void deleteUser(UserRequestDTO userRequestDTO);
}

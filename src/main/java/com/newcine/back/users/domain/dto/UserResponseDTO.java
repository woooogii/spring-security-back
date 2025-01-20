package com.newcine.back.users.domain.dto;

public record UserResponseDTO(
        String userName,
        String userPwd,
        String userEmail,
        String userNikname) {

    public static UserResponseDTO of(String userName, String userPwd, String userEmail, String userNikname) {
        return new UserResponseDTO(userName, userPwd, userEmail, userNikname);
    }

}

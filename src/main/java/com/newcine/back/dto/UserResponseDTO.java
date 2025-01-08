package com.newcine.back.dto;

public record UserResponseDTO(
        String userId,
        String userPwd,
        String userEmail,
        String userNikname) {

    public static UserResponseDTO of(String userId, String userPwd, String userEmail, String userNikname) {
        return new UserResponseDTO(userId, userPwd, userEmail, userNikname);
    }

}

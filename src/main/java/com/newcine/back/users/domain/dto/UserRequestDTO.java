package com.newcine.back.users.domain.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String userName;
    private String userPwd;
    private String userEmail;
    private String userNikname;
}

package com.example.myschool.commons.data.response.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String phoneNumber;
    private String email;
    private Boolean firstLogin;
    private Integer status;
    private String avataUrl;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

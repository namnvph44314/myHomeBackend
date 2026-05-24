package com.example.myschool.commons.data.request.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginRequest {
    private String phone;
    private String plainPassword;
}

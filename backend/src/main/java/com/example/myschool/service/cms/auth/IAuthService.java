package com.example.myschool.service.cms.auth;

import com.example.myschool.commons.data.request.auth.LoginRequest;
import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.TokenResponse;
import com.example.myschool.commons.data.response.user.UserResponse;

public interface IAuthService {
    TokenResponse authenticate(LoginRequest loginRequest);

    UserResponse register(UserRequest request);

    TokenResponse refreshToken(String token);
}

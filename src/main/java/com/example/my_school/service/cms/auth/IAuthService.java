package com.example.my_school.service.cms.auth;

import com.example.my_school.commons.data.request.auth.LoginRequest;
import com.example.my_school.commons.data.request.user.UserRequest;
import com.example.my_school.commons.data.response.TokenResponse;
import com.example.my_school.commons.data.response.user.UserResponse;

public interface IAuthService {
    TokenResponse authenticate(LoginRequest loginRequest);

    UserResponse register(UserRequest request);

    TokenResponse refreshToken(String token);
}

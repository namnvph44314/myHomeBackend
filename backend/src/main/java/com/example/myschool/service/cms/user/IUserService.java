package com.example.myschool.service.cms.user;

import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.user.UserResponse;
import org.springframework.security.core.Authentication;

public interface IUserService {
    UserResponse findById(Integer id, Authentication authentication);

    UserResponse createUser(UserRequest request, Authentication authentication);

    UserResponse getMe();
}

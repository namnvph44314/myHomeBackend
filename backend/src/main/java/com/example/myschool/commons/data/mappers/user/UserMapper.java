package com.example.myschool.commons.data.mappers.user;

import com.example.myschool.commons.data.mappers.BaseMapper;
import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.user.UserResponse;
import com.example.myschool.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper extends BaseMapper<UserRequest, UserResponse, User> {
    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterMapping
    protected void afterPojo(@MappingTarget User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }
}

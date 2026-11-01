package com.example.my_school.commons.data.mappers.userRole;

import com.example.my_school.commons.data.mappers.BaseMapper;
import com.example.my_school.commons.data.mappers.role.RoleMapper;
import com.example.my_school.commons.data.mappers.user.UserMapper;
import com.example.my_school.commons.data.request.userRole.UserRoleRequest;
import com.example.my_school.commons.data.response.userRole.UserRoleResponse;
import com.example.my_school.entity.UserRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserRoleMapper extends BaseMapper<UserRoleRequest, UserRoleResponse,UserRole> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;


    @AfterMapping
    protected void afterResponse(@MappingTarget UserRoleResponse response,UserRole userRole){
        response.setRole(roleMapper.toResponse(userRole.getRole()));
        response.setUser(userMapper.toResponse(userRole.getUser()));
    }
}

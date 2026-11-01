package com.example.my_school.commons.data.mappers.role;

import com.example.my_school.commons.data.mappers.BaseMapper;
import com.example.my_school.commons.data.request.role.RoleRequest;
import com.example.my_school.commons.data.response.role.RoleResponse;
import com.example.my_school.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoleMapper extends BaseMapper<RoleRequest, RoleResponse, Role> {
}

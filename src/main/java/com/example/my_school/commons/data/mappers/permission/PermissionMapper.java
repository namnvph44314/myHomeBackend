package com.example.my_school.commons.data.mappers.permission;

import com.example.my_school.commons.data.mappers.BaseMapper;
import com.example.my_school.commons.data.request.permission.PermissionRequest;
import com.example.my_school.commons.data.response.permission.PermissionResponse;
import com.example.my_school.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PermissionMapper extends BaseMapper<PermissionRequest, PermissionResponse, Permission> {
}

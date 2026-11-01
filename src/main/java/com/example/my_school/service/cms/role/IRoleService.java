package com.example.my_school.service.cms.role;

import com.example.my_school.commons.data.request.role.RoleRequest;
import com.example.my_school.commons.data.response.role.RoleResponse;
import com.example.my_school.entity.Role;

import java.util.List;

public interface IRoleService {
    List<RoleResponse> getAllRole();
    Role findByName(String name);
    RoleResponse createRole(RoleRequest request);
}

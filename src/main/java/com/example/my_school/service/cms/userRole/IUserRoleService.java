package com.example.my_school.service.cms.userRole;

import com.example.my_school.commons.data.request.userRole.UserRoleRequest;
import com.example.my_school.commons.data.response.userRole.UserRoleResponse;
import com.example.my_school.entity.User;

import java.util.List;

public interface IUserRoleService {
    UserRoleResponse createNew(UserRoleRequest request);
    List<String> getALlRoleByUser(User user);
    void deleteByIdUserAndRoleName(Integer idUser,String roleName);
}

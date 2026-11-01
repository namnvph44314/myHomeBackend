package com.example.my_school.commons.data.response.userRole;

import com.example.my_school.commons.data.response.role.RoleResponse;
import com.example.my_school.commons.data.response.user.UserResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRoleResponse {
    private UserResponse user;
    private RoleResponse role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

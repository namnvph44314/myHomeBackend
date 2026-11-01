package com.example.my_school.commons.data.request.userRole;

import com.example.my_school.entity.Role;
import com.example.my_school.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRoleRequest {
    @NotNull(message = "user is not null")
    private User user;
    @NotNull(message = "role is not null")
    private Role role;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

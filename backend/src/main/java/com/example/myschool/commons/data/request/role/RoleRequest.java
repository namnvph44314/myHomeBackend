package com.example.myschool.commons.data.request.role;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleRequest {
    private String name;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

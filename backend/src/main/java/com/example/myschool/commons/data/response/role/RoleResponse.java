package com.example.myschool.commons.data.response.role;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleResponse {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

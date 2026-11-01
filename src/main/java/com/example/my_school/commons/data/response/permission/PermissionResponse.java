package com.example.my_school.commons.data.response.permission;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PermissionResponse {
    private Integer id;
    private String name;
    private String displayName;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

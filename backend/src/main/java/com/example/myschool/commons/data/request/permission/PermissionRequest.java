package com.example.myschool.commons.data.request.permission;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PermissionRequest {
    private String name;
    private String displayName;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

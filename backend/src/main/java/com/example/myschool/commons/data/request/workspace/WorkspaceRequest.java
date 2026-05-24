package com.example.myschool.commons.data.request.workspace;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceRequest {
    private String name;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

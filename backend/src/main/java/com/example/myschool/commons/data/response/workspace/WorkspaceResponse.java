package com.example.myschool.commons.data.response.workspace;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceResponse {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

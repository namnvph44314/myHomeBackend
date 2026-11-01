package com.example.my_school.commons.data.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleRequest {
    @NotBlank(message = "name is not empty")
    private String name;
    @NotBlank(message = "displayName is not empty")
    private String displayName;
    @NotBlank(message = "description is not empty")
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}

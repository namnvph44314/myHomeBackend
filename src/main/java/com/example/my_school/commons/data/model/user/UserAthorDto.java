package com.example.my_school.commons.data.model.user;

import lombok.Data;

import java.util.List;

@Data
public class UserAthorDto {
    private Integer userId;
    private String username;
    private List<String> roles;
}

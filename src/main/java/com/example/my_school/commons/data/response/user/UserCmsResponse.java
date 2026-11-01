package com.example.my_school.commons.data.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCmsResponse {
    private Integer id;
    private String username;
    private String phoneNumber;
    private String email;
    private Boolean firstLogin;
    private Integer status;
    private String avataUrl;
    private String publicIdAvata;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<String> roles;
}

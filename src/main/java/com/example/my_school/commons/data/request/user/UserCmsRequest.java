package com.example.my_school.commons.data.request.user;

import com.example.my_school.commons.data.constant.RegexConstant;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UserCmsRequest {
    @NotBlank(message = "username is not empty")
    private String username;
    @NotBlank(message = "phoneNumber is not empty")
    @Pattern(regexp = RegexConstant.PHONE_NUMBER_PATTERN, message = "Phone number is invalid")
    private String phoneNumber;
    @NotBlank(message = "Email is not empty")
    @Size(max = 256, message = "email with at most 256 characters")
    @Email(message = "Email invalidate")
//    @Pattern(regexp = RegexConstant.EMAIL_PATTERN, message = "Email invalidate")
    private String email;
    @NotEmpty(message = "roles is not empty")
    private List<String> roles;
    private String avataUrl;
    private String publicIdAvata;
    private Integer status;
}

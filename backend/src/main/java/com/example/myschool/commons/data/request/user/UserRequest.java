package com.example.myschool.commons.data.request.user;

import com.example.myschool.commons.data.constant.RegexConstant;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {
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
    @NotBlank(message = "Password is not empty")
    @Size(min = 6, max = 50, message = "password must be between 6 and 50 characters")
    @Pattern(regexp = RegexConstant.PASSWORD_PATTERN, message = "Password must contain uppercase, lowercase and number")
    private String password;
    private String avataUrl;
}

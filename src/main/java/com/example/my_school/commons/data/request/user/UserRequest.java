package com.example.my_school.commons.data.request.user;

import com.example.my_school.commons.data.constant.RegexConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {
    @NotBlank(message = "username is not blank")
    private String username;
    @NotBlank(message = "phoneNumber is not blank")
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
//    @NotNull(message = "status is not empty")
    private Integer status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean firstLogin;
}

package com.example.my_school.controller.cms.auth;

import com.example.my_school.commons.data.model.DfResponse;
import com.example.my_school.commons.data.request.auth.LoginRequest;
import com.example.my_school.commons.data.request.user.UserRequest;
import com.example.my_school.commons.data.response.TokenResponse;
import com.example.my_school.commons.data.response.user.UserResponse;
import com.example.my_school.service.cms.auth.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class AuthController {
    @Autowired
    private IAuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(service.authenticate(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid UserRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
//        return DfResponse.okEntity(service.register(request));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<DfResponse<TokenResponse>> logout(HttpServletRequest request){
        String header =request.getHeader("Authorization");
        String token=header.split(" ")[1].trim();
        TokenResponse tokenResponse= service.refreshToken(token);
        return DfResponse.okEntity(tokenResponse);
    }

}

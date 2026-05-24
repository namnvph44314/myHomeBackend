package com.example.myschool.controler.cms.auth;

import com.example.myschool.commons.data.model.DfResponse;
import com.example.myschool.commons.data.request.auth.LoginRequest;
import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.TokenResponse;
import com.example.myschool.commons.data.response.user.UserResponse;
import com.example.myschool.service.cms.auth.IAuthService;
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
        System.out.println(token+"///////////////////////////");
        TokenResponse tokenResponse= service.refreshToken(token);
        return DfResponse.okEntity(tokenResponse);
    }

}

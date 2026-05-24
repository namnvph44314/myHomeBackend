package com.example.myschool.service.cms.auth;

import com.example.myschool.commons.data.constant.user.UserConstant;
import com.example.myschool.commons.data.mappers.user.UserMapper;
import com.example.myschool.commons.data.request.auth.LoginRequest;
import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.TokenResponse;
import com.example.myschool.commons.data.response.user.UserResponse;
import com.example.myschool.config.auth_config.JwtUtil;
import com.example.myschool.config.exception.ApiException;
import com.example.myschool.config.exception.ValidationException;
import com.example.myschool.entity.User;
import com.example.myschool.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService implements IAuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private UserMapper mapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    public TokenResponse authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByPhoneNumber(loginRequest.getPhone()).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND.value()));
        String requestPassword = loginRequest.getPlainPassword();
        String truePassword = user.getPassword();
        if (!passwordEncoder.matches(requestPassword, truePassword)) {
            return null;
        } else {
            String token = jwtUtil.generateToken(user);
            return new TokenResponse(token);
        }
    }

    @Override
    public UserResponse register(UserRequest request) {
        try {
            if(userRepository.findByEmail(request.getEmail()).isPresent()){
                Map<String,String> errors = new HashMap<>();
                errors.put("email", "Email already exists");
                throw new ValidationException(errors);
            }
            if(userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
                Map<String,String> errors = new HashMap<>();
                errors.put("phoneNumber", "Phone number already exists");
                throw new ValidationException(errors);
            }
            User user = mapper.toPojo(request);
            user.setStatus(1);
            user.setCreateAt(LocalDateTime.now());
            user.setFirstLogin(false);
            userRepository.save(user);
            UserResponse response = mapper.toResponse(user);
            return response;
        } catch (ApiException e) {
            log.error("[ERROR] createUser {} " + e.getMessage());
            throw new ApiException(UserConstant.CREATE_USER_FAILED);
        }
    }

    @Override
    public TokenResponse refreshToken(String token) {
        String newToken=jwtUtil.refereshToken(token);
        return new TokenResponse(newToken);
    }

}

package com.example.my_school.service.cms.auth;

import com.example.my_school.commons.data.constant.user.UserConstant;
import com.example.my_school.commons.data.mappers.user.UserMapper;
import com.example.my_school.commons.data.request.auth.LoginRequest;
import com.example.my_school.commons.data.request.user.UserRequest;
import com.example.my_school.commons.data.response.TokenResponse;
import com.example.my_school.commons.data.response.user.UserResponse;
import com.example.my_school.config.auth_config.JwtUtil;
import com.example.my_school.config.exception.ApiException;
import com.example.my_school.config.exception.ValidationException;
import com.example.my_school.entity.User;
import com.example.my_school.repository.user.UserRepository;
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
                Map<String,String> error = new HashMap<>();
                error.put("email", "Email already exists");
                throw new ValidationException(error);
            }
            if(userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
                Map<String,String> error = new HashMap<>();
                error.put("phoneNumber", "Phone number already exists");
                throw new ValidationException(error);
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

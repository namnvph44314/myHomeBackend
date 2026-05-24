package com.example.myschool.service.cms.user;

import com.example.myschool.commons.data.constant.user.UserConstant;
import com.example.myschool.commons.data.mappers.user.UserMapper;
import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.user.UserResponse;
import com.example.myschool.config.exception.ApiException;
import com.example.myschool.entity.Permission;
import com.example.myschool.entity.Role;
import com.example.myschool.entity.User;
import com.example.myschool.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;

@Service
@Slf4j
public class UserService implements IUserService {
    private UserRepository repository;
    private UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserResponse findById(Integer id, Authentication authentication) {
        try {
            User user = repository.findById(id).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND.value()));
            UserResponse userResponse = mapper.toResponse(user);
            return userResponse;
        } catch (ApiException e) {
            log.error("[ERROR] findById {} " + e.getMessage());
            throw new ApiException(UserConstant.USER_NOT_FOUND);
        }
    }

    @Override
    public UserResponse createUser(UserRequest request, Authentication authentication) {
        try {
            User user = mapper.toPojo(request);
            user.setStatus(1);
            user.setCreateAt(LocalDateTime.now());
            user.setFirstLogin(true);
            repository.save(user);
            UserResponse response = mapper.toResponse(user);
            return response;
        } catch (ApiException e) {
            log.error("[ERROR] createUser {} " + e.getMessage());
            throw new ApiException(UserConstant.CREATE_USER_FAILED);
        }
    }

    @Override
    public UserResponse getMe() {
        try {
            Integer userId = getLoggedUserId();
            User userInfo = repository.findById(userId).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND.value()));
            return this.setUserResponse(userInfo, null, null);
        } catch (ApiException e) {
            log.error("[ERROR] getMe {} " + e.getMessage());
            throw new ApiException("");
        }
    }

    public UserResponse setUserResponse(User user, List<Role> roles, List<Permission> permissions) {
        if (user == null) {
            throw new ApiException("Người dùng không tồn tại trong hệ thống");
        }
        return mapper.toResponse(user);
    }

    public static Integer getLoggedUserId() {
        final Authentication authentication = getContext().getAuthentication();
        if (authentication == null || !isNumeric(authentication.getPrincipal().toString())) {
            return null;
        }
        return createInteger(authentication.getPrincipal().toString());
    }
}

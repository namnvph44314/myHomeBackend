package com.example.my_school.service.cms.userRole;

import com.example.my_school.commons.data.mappers.userRole.UserRoleMapper;
import com.example.my_school.commons.data.request.userRole.UserRoleRequest;
import com.example.my_school.commons.data.response.userRole.UserRoleResponse;
import com.example.my_school.config.exception.ApiException;
import com.example.my_school.entity.User;
import com.example.my_school.entity.UserRole;
import com.example.my_school.repository.userRole.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserRoleService implements IUserRoleService{

    private UserRoleRepository repository;
    private UserRoleMapper mapper;

    public UserRoleService(UserRoleRepository repository,UserRoleMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }

    @Override
    public UserRoleResponse createNew(UserRoleRequest request) {
        try{
            UserRole userRole= mapper.toPojo(request);
            userRole.setCreateAt(LocalDateTime.now());
            repository.save(userRole);
            UserRoleResponse response=mapper.toResponse(userRole);
            return response;
        }catch (ApiException apiException){
            log.error("[ERROR] createNew {} "+apiException.getMessage());
            throw new ApiException("create new userRole fail");
        }
    }

    @Override
    public List<String> getALlRoleByUser(User user) {
        try {
            List<String> roles=repository.getAllRoleByUser(user);
            return roles;
        }catch (ApiException apiException){
            log.error("[ERROR] getAllRoleByPhoneNumber {} "+apiException.getMessage());
            throw new ApiException("getAllRoleByPhoneNumber failse");
        }
    }

    @Override
    public void deleteByIdUserAndRoleName(Integer idUser, String roleName) {
        try{
            repository.deleteByIdUserAnRolenName(idUser,roleName);
        }catch (ApiException apiException){
            log.error("[ERROR] deleteByIdUerAndRoleName {} "+apiException.getMessage());
            throw new ApiException("delete userRole failse");
        }
    }
}

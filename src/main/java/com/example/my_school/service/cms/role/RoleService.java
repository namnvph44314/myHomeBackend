package com.example.my_school.service.cms.role;

import com.example.my_school.commons.data.constant.role.RoleConstant;
import com.example.my_school.commons.data.mappers.role.RoleMapper;
import com.example.my_school.commons.data.request.role.RoleRequest;
import com.example.my_school.commons.data.response.role.RoleResponse;
import com.example.my_school.config.exception.ApiException;
import com.example.my_school.config.exception.ValidationException;
import com.example.my_school.entity.Role;
import com.example.my_school.repository.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RoleService implements IRoleService{

    private RoleRepository repository;
    private RoleMapper mapper;

    public RoleService(RoleRepository repository,RoleMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }

    @Override
    public List<RoleResponse> getAllRole() {
        try{
            List<Role> roles=repository.findAll();
            List<RoleResponse> roleResponses=mapper.toResponses(roles);
            return roleResponses;
        }catch (ApiException apiException){
            log.error("[ERROR] getAllRole {} "+apiException.getMessage());
            throw new ApiException("find all role false");
        }
    }

    @Override
    public Role findByName(String name) {
        try{
            Role role=repository.findByName(name).orElseThrow(()->new ApiException("Role not found", HttpStatus.NOT_FOUND.value()));
            return role;
        }catch (ApiException apiException){
            log.error("[ERROR] findByName {} "+apiException.getMessage());
            throw new ApiException("Role not found");
        }
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        try{
            if(repository.findByName(request.getName()).isPresent()){
                Map<String,String> error=new HashMap<>();
                error.put("name","name already exists");
                throw new ValidationException(error);
            }
            Role role=mapper.toPojo(request);
            role.setCreateAt(LocalDateTime.now());
            repository.save(role);
            RoleResponse response= mapper.toResponse(role);
            return response;
        }catch (ApiException apiException){
            log.error("[ERROR] insertRole {} "+apiException.getMessage());
            throw new ApiException(RoleConstant.CREATE_ROLE_FAILED);
        }
    }
}

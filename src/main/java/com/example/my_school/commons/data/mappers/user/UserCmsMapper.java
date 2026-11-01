package com.example.my_school.commons.data.mappers.user;

import com.example.my_school.commons.data.mappers.BaseMapper;
import com.example.my_school.commons.data.request.user.UserCmsRequest;
import com.example.my_school.commons.data.response.user.UserCmsResponse;
import com.example.my_school.entity.User;
import com.example.my_school.service.cms.userRole.IUserRoleService;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserCmsMapper extends BaseMapper<UserCmsRequest, UserCmsResponse, User> {

    @Autowired
    private IUserRoleService userRoleService;

    public abstract void updateUserFromRequest(UserCmsRequest request,@MappingTarget User user);

    @AfterMapping
    @IterableMapping(qualifiedByName = "toResponse")
    protected void afterResponse(@MappingTarget UserCmsResponse response,User user){
        List<String> roles=userRoleService.getALlRoleByUser(user);
        response.setRoles(roles);
    }
}

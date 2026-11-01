package com.example.my_school.controller.cms.userRole;

import com.example.my_school.commons.data.model.DfResponse;
import com.example.my_school.commons.data.request.userRole.UserRoleRequest;
import com.example.my_school.commons.data.response.userRole.UserRoleResponse;
import com.example.my_school.service.cms.userRole.IUserRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    private IUserRoleService service;

    public UserRoleController(IUserRoleService service){
        this.service=service;
    }

    @PostMapping("/create")
    public ResponseEntity<DfResponse<UserRoleResponse>> createNew(
            @RequestBody @Valid UserRoleRequest request
    ){
        return DfResponse.okEntity(service.createNew(request));
    }
}

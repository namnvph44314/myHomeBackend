package com.example.my_school.controller.cms.role;

import com.example.my_school.commons.data.model.DfResponse;
import com.example.my_school.commons.data.request.role.RoleRequest;
import com.example.my_school.commons.data.response.role.RoleResponse;
import com.example.my_school.service.cms.role.IRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private IRoleService service;

    public RoleController(IRoleService service){
        this.service=service;
    }

    @GetMapping("")
    public ResponseEntity<DfResponse<List<RoleResponse>>> getAllRole(){
        return DfResponse.okEntity(service.getAllRole());
    }

    @PostMapping("")
    public ResponseEntity<DfResponse<RoleResponse>> createRole(
            @RequestBody @Valid RoleRequest request
    ){
        return DfResponse.okEntity(service.createRole(request));
    }
}

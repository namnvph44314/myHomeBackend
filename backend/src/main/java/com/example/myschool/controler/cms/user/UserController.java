package com.example.myschool.controler.cms.user;

import com.example.myschool.commons.data.request.user.UserRequest;
import com.example.myschool.commons.data.response.user.UserResponse;
import com.example.myschool.service.cms.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("id") Integer id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(service.findById(id, authentication));
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponse> create(
            @RequestBody UserRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(service.createUser(request, authentication));
    }

    @GetMapping("/get-me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(service.getMe());
    }
}

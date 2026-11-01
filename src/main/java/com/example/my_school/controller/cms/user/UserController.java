package com.example.my_school.controller.cms.user;

import com.example.my_school.commons.data.model.DfResponse;
import com.example.my_school.commons.data.request.user.UserCmsRequest;
import com.example.my_school.commons.data.response.MessageResponse;
import com.example.my_school.commons.data.response.user.UserCmsResponse;
import com.example.my_school.commons.data.response.user.UserResponse;
import com.example.my_school.service.cms.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("/find-all")
    public ResponseEntity<DfResponse<List<UserResponse>>> findAll(){
        return DfResponse.okEntity(service.findAll());
    }

    @GetMapping("/find-all-user")
    public ResponseEntity<DfResponse<Page<UserResponse>>> findAllUser(
            @RequestParam("page") Optional<Integer> pageParam
    ){
        int page = pageParam.orElse(0);
        return DfResponse.okEntity(service.findAllUser(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DfResponse<UserCmsResponse>> getUser(
            @PathVariable("id") Integer id,
            Authentication authentication
    ) {
        return DfResponse.okEntity(service.findById(id, authentication));
    }

    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DfResponse<UserCmsResponse>> create(
            @Valid @RequestPart("user") UserCmsRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return DfResponse.okEntity(service.createUser(request,image));
    }

    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DfResponse<UserCmsResponse>> updateUser(
            @PathVariable("id") Integer id,
            @Valid @RequestPart("user") UserCmsRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return DfResponse.okEntity(service.updateUser(id,request,image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DfResponse<MessageResponse>> deleteUser(
            @PathVariable("id") Integer id,
            Authentication authentication
    ){
        return DfResponse.okEntity(service.deleteUser(id,authentication));
    }

    @GetMapping("/get-me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(service.getMe());
    }

    @GetMapping("/search")
    public ResponseEntity<DfResponse<Page<UserResponse>>> search(
            @RequestParam("page") Optional<Integer> pageParam,
            @RequestParam("keyword") String key
    ){
        int page = pageParam.orElse(0);
        return DfResponse.okEntity(service.search(key,page));
    }

    @DeleteMapping("/avata/{id}")
    public ResponseEntity<DfResponse<MessageResponse>> deleteAvata(
            @PathVariable("id") Integer id
    ){
        return DfResponse.okEntity(service.deleteAvata(id));
    }

}

package com.example.my_school.service.cms.user;

import com.example.my_school.commons.data.request.user.UserCmsRequest;
import com.example.my_school.commons.data.response.MessageResponse;
import com.example.my_school.commons.data.response.user.UserCmsResponse;
import com.example.my_school.commons.data.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    List<UserResponse> findAll();

    Page<UserResponse> findAllUser(int page);

    Page<UserResponse> search(String key,int page);

    UserCmsResponse findById(Integer id, Authentication authentication);

    UserCmsResponse createUser(UserCmsRequest request, MultipartFile image) throws IOException;

    UserCmsResponse updateUser(Integer id,UserCmsRequest request, MultipartFile image) throws IOException;

    MessageResponse deleteUser(Integer id, Authentication authentication);

    MessageResponse deleteAvata(Integer id);

    UserResponse getMe();

}

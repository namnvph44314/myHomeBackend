package com.example.my_school.service.cms.user;

import com.example.my_school.commons.data.constant.user.UserConstant;
import com.example.my_school.commons.data.mappers.user.UserCmsMapper;
import com.example.my_school.commons.data.mappers.user.UserMapper;
import com.example.my_school.commons.data.request.user.UserCmsRequest;
import com.example.my_school.commons.data.request.userRole.UserRoleRequest;
import com.example.my_school.commons.data.response.MessageResponse;
import com.example.my_school.commons.data.response.user.UserCmsResponse;
import com.example.my_school.commons.data.response.user.UserResponse;
import com.example.my_school.config.exception.ApiException;
import com.example.my_school.config.exception.ValidationException;
import com.example.my_school.entity.Permission;
import com.example.my_school.entity.Role;
import com.example.my_school.entity.User;
import com.example.my_school.repository.user.UserRepository;
import com.example.my_school.service.cms.cloudinary.CloudinaryService;
import com.example.my_school.service.cms.role.IRoleService;
import com.example.my_school.service.cms.userRole.IUserRoleService;
import com.example.my_school.utils.random.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository repository;
    private IUserRoleService userRoleService;
    private IRoleService roleService;
    private UserMapper mapper;
    private UserCmsMapper userCmsMapper;
    private CloudinaryService cloudinaryService;

    public UserService(UserRepository repository,CloudinaryService cloudinaryService,IRoleService roleService,IUserRoleService userRoleService,UserCmsMapper userCmsMapper, UserMapper mapper) {
        this.repository = repository;
        this.userRoleService=userRoleService;
        this.roleService=roleService;
        this.cloudinaryService=cloudinaryService;
        this.userCmsMapper=userCmsMapper;
        this.mapper = mapper;
    }

    @Override
    public List<UserResponse> findAll() {
        try{
            List<User> users=repository.findAll();
            List<UserResponse> userResponses=mapper.toResponses(users);
            return userResponses;
        }catch (ApiException apiException){
            log.error("[ERROR] findAll {} "+apiException.getMessage());
            throw new ApiException("find all uesr false");
        }
    }

    @Override
    public Page<UserResponse> findAllUser(int page) {
        try{
            Pageable p= PageRequest.of(page,5);
            Page<User> userPage=repository.findAllUser(p);
            Page<UserResponse> userResponses=userPage.map(mapper::toResponse);
            return userResponses;
        }catch (ApiException apiException){
            log.error("[ERROR] findAllUser {} "+apiException.getMessage());
            throw new ApiException("find all page uesr false");
        }
    }

    @Override
    public Page<UserResponse> search(String key, int page) {
        try{
            Pageable p=PageRequest.of(page,5);
            Page<User> userPage=repository.search(key,p);
            Page<UserResponse> userResponsePage=userPage.map(mapper::toResponse);
            return userResponsePage;
        }catch (ApiException apiException){
            log.error("[ERROR] search {} "+apiException.getMessage());
            throw new ApiException("search page user false");
        }
    }

    @Override
    public UserCmsResponse findById(Integer id, Authentication authentication) {
        try {
            User user = repository.findById(id).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND.value()));
            UserCmsResponse response = userCmsMapper.toResponse(user);
            return response;
        } catch (ApiException e) {
            log.error("[ERROR] findById {} " + e.getMessage());
            throw new ApiException(UserConstant.USER_NOT_FOUND);
        }
    }


    public User findByPhoneNumber(String phoneNumber) {
        try{
            User user=repository.findByPhoneNumber(phoneNumber).orElseThrow(()->new ApiException("User not found",HttpStatus.NOT_FOUND.value()));
            return user;
        }catch (ApiException apiException){
            log.error("[ERROR] findByPhoneNumber {} " + apiException.getMessage());
            throw new ApiException("User not found");
        }
    }

    @Override
    public UserCmsResponse createUser(UserCmsRequest request, MultipartFile image) throws IOException {
        try {
            if(repository.findByEmail(request.getEmail()).isPresent()){
                Map<String,String> error=new HashMap<>();
                error.put("email","Email already exists");
                throw new ValidationException(error);
            }
            if(repository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
                Map<String,String> error=new HashMap<>();
                error.put("phoneNumber","Phone number already exists");
                throw new ValidationException(error);
            }
            if (image != null && !image.isEmpty()) {
                Map<String,String> dataImage = cloudinaryService.upload(image,"my_school/users");
                request.setAvataUrl(dataImage.get("url"));
                request.setPublicIdAvata(dataImage.get("publicId"));
            }
            String plainPassword=PasswordGenerator.generatePassword();
            User userPojo = userCmsMapper.toPojo(request);
            userPojo.setPassword(passwordEncoder.encode(plainPassword));
            userPojo.setStatus(1);
            userPojo.setCreateAt(LocalDateTime.now());
            userPojo.setFirstLogin(true);
            User user=repository.save(userPojo);
            List<String> roles=request.getRoles();
            for(int i=0;i<roles.size();i++){
                Role role= roleService.findByName(request.getRoles().get(i));
                this.saveRole(user,role);
            }
            UserCmsResponse response = userCmsMapper.toResponse(user);
            return response;
        } catch (ApiException e) {
            Map<String,String> dataImage = cloudinaryService.upload(image,"my_school/users");
            cloudinaryService.deleteImage(dataImage.get("publicId"));
            log.error("[ERROR] createUser {} " + e.getMessage());
            throw new ApiException(UserConstant.CREATE_USER_FAILED);
        }
    }

    @Override
    public UserCmsResponse updateUser(Integer id, UserCmsRequest request, MultipartFile image) {
        try {
            User user=repository.findById(id).orElseThrow(()->new ApiException("User not found",HttpStatus.NOT_FOUND.value()));

            if(!user.getEmail().equals(request.getEmail()) && repository.findByEmail(request.getEmail()).isPresent()){
                Map<String,String> error=new HashMap<>();
                error.put("email","Email already exists");
                throw new ValidationException(error);
            }
            if(!user.getPhoneNumber().equals(request.getPhoneNumber()) && repository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
                Map<String,String> error=new HashMap<>();
                error.put("phoneNumber","Phone number already exists");
                throw new ValidationException(error);
            }
            if (image != null && !image.isEmpty()) {
                if(user.getPublicIdAvata()!=null){
                    cloudinaryService.deleteImage(user.getPublicIdAvata());
                }
                Map<String,String> dataImage = cloudinaryService.upload(image,"my_school/users");
                request.setAvataUrl(dataImage.get("url"));
                request.setPublicIdAvata(dataImage.get("publicId"));
            }
            userCmsMapper.updateUserFromRequest(request, user);
            user.setUpdateAt(LocalDateTime.now());
            User userUpdate=repository.save(user);
            this.updateRole(user,userRoleService.getALlRoleByUser(user),request.getRoles());
            UserCmsResponse response= userCmsMapper.toResponse(userUpdate);
            return response;
        }catch (ApiException | IOException e){
            log.error("[ERROR] updateUser {} "+e.getMessage());
            throw new ApiException(UserConstant.UPDATE_USER_FAILED);
        }
    }

    @Override
    public MessageResponse deleteUser(Integer id, Authentication authentication){
        try {
            Integer isDeleted=repository.deleteUser(id);
            if(isDeleted!=1){
                throw new ApiException(UserConstant.DELETE_USER_FAILED);
            }else{
                MessageResponse messageResponse=new MessageResponse("Success");
                return messageResponse;
            }
        }catch (ApiException apiException){
            log.error("[ERROR] deleteUser {} "+apiException.getMessage());
            throw new ApiException(UserConstant.DELETE_USER_FAILED);
        }
    }

    @Override
    public MessageResponse deleteAvata(Integer id) {
        try{
            User user=repository.findById(id).orElseThrow(()->new ApiException("User not found",HttpStatus.NOT_FOUND.value()));
            cloudinaryService.deleteImage(user.getPublicIdAvata());
            user.setAvataUrl(null);
            user.setPublicIdAvata(null);
            repository.save(user);
            MessageResponse messageResponse=new MessageResponse("Success");
            return messageResponse;
        }catch (ApiException | IOException apiException){
            log.error("[ERROR] deleteAvata {} "+apiException.getMessage());
            throw new ApiException(UserConstant.DELETE_AVATA_FAILED);
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

    public void saveRole(User user,Role role){
        UserRoleRequest request=new UserRoleRequest();
        request.setRole(role);
        request.setUser(user);
        userRoleService.createNew(request);
    }

    public void updateRole(User user,List<String> roleRepo,List<String> roleRequest){
//        Set<String> repoSet = new HashSet<>(roleRepo);
//        Set<String> requestSet = new HashSet<>(roleRequest);
//
//        List<String> listRemove = roleRepo.stream()
//                .filter(role -> !requestSet.contains(role))
//                .toList();
//
//        List<String> listAdd = roleRequest.stream()
//                .filter(role -> !repoSet.contains(role))
//                .toList();
//        if(!listRemove.isEmpty()){
//            for(int i=0;i<listRemove.size();i++){
//                userRoleService.deleteByIdUserAndRoleName(user.getId(),listRemove.get(i));
//            }
//        }
//        if(!listAdd.isEmpty()){
//            UserRoleRequest userRoleRequest=new UserRoleRequest();
//            userRoleRequest.setUser(user);
//            for(int i=0;i<listAdd.size();i++){
//                Role role=roleService.findByName(listAdd.get(i));
//                userRoleRequest.setRole(role);
//                userRoleService.createNew(userRoleRequest);
//            }
//        }
        Set<String> repoRoles = new HashSet<>(roleRepo);
        Set<String> requestRoles = new HashSet<>(roleRequest);

        // Xóa role không còn được gán
        repoRoles.stream()
                .filter(role -> !requestRoles.contains(role))
                .forEach(role ->
                        userRoleService.deleteByIdUserAndRoleName(user.getId(), role)
                );

        // Thêm role mới
        requestRoles.stream()
                .filter(role -> !repoRoles.contains(role))
                .map(roleService::findByName)
                .forEach(role -> {
                    UserRoleRequest request = new UserRoleRequest();
                    request.setUser(user);
                    request.setRole(role);
                    userRoleService.createNew(request);
                });
    }
}

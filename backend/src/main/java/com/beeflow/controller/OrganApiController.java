package com.beeflow.controller;

import com.beeflow.model.ApiResponse;
import com.beeflow.model.Dept;
import com.beeflow.model.Role;
import com.beeflow.model.User;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class OrganApiController {
    private static final Logger logger = LoggerFactory.getLogger(OrganApiController.class);

    @GetMapping("/organ/getLoginUserDetail")
    public ApiResponse<User> getLoginUserDetail() {
        logger.info("Received request for getLoginUserDetail");
        User user = new User();
        user.setId("1");
        user.setName("Admin User");
        user.setTenantId("1");
        user.setEmail("admin@example.com");
        user.setMobile("1234567890");
        user.setAvatar("https://avatars.githubusercontent.com/u/1");
        ApiResponse<User> response = ApiResponse.success(user);
        logger.info("Returning response: {}", response);
        return response;
    }

    @GetMapping("/organ/listDepts")
    public ApiResponse<List<Dept>> listDepts() {
        Dept dept1 = new Dept();
        dept1.setId("1");
        dept1.setPid("0");
        dept1.setName("Main Department");

        Dept dept2 = new Dept();
        dept2.setId("2");
        dept2.setPid("1");
        dept2.setName("Sub Department");

        return ApiResponse.success(Arrays.asList(dept1, dept2));
    }

    @GetMapping("/organ/listRoles")
    public ApiResponse<List<Role>> listRoles() {
        Role role1 = new Role();
        role1.setId("1");
        role1.setName("Admin");

        Role role2 = new Role();
        role2.setId("2");
        role2.setName("User");

        return ApiResponse.success(Arrays.asList(role1, role2));
    }

    @GetMapping("/organ/listUsers")
    public ApiResponse<List<User>> listUsers() {
        User user1 = new User();
        user1.setId("1");
        user1.setName("Admin User");
        user1.setTenantId("1");
        user1.setEmail("admin@example.com");
        user1.setAvatar("https://avatars.githubusercontent.com/u/1");

        User user2 = new User();
        user2.setId("2");
        user2.setName("Regular User");
        user2.setTenantId("1");
        user2.setEmail("user@example.com");
        user2.setAvatar("https://avatars.githubusercontent.com/u/2");

        return ApiResponse.success(Arrays.asList(user1, user2));
    }

    @GetMapping("/opentest/getOrgTree")
    public ApiResponse<List<Dept>> getOrgTree() {
        return listDepts();
    }

    @GetMapping("/orgApi/getSuperior")
    public ApiResponse<String> getSuperior(@RequestParam("tenantId") String tenantId,
                            @RequestParam("userId") String userId,
                            @RequestParam("layerType") int layerType,
                            @RequestParam("level") int level) {
        return ApiResponse.success("2");
    }

    @GetMapping("/orgApi/getDeptLeader")
    public ApiResponse<String> getDeptLeader(@RequestParam("tenantId") String tenantId,
                              @RequestParam("userId") String userId,
                              @RequestParam("layerType") int layerType,
                              @RequestParam("level") int level) {
        return ApiResponse.success("3");
    }

    @GetMapping("/orgApi/getUserById")
    public ApiResponse<User> getUserById(@RequestParam("tenantId") String tenantId, @RequestParam("userId") String userId) {
        User user = new User();
        user.setId(userId);
        user.setName("User " + userId);
        user.setTenantId(tenantId);
        user.setEmail("user" + userId + "@example.com");
        user.setAvatar("https://avatars.githubusercontent.com/u/" + userId);
        return ApiResponse.success(user);
    }

    @GetMapping("/orgApi/belong")
    public ApiResponse<Boolean> belong(@RequestParam("tenantId") String tenantId,
                         @RequestParam("userId") String userId,
                         @RequestParam("organIds") List<String> organIds) {
        return ApiResponse.success(true);
    }
} 
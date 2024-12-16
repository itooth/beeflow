package com.beeflow.workflow.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beeflow.workflow.backend.model.organization.Dept;
import com.beeflow.workflow.backend.model.organization.Role;
import com.beeflow.workflow.backend.model.organization.User;
import com.beeflow.workflow.backend.repository.DeptMapper;
import com.beeflow.workflow.backend.repository.RoleMapper;
import com.beeflow.workflow.backend.repository.UserMapper;
import com.beeflow.workflow.backend.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final DeptMapper deptMapper;

    @Override
    public User getUserById(String tenantId, String userId) {
        // Return mock user
        User user = new User();
        user.setId(userId);
        user.setName("User " + userId);
        user.setEmail("user" + userId + "@example.com");
        user.setTenantId(tenantId);
        return user;
    }

    @Override
    public List<User> listUsers(String tenantId) {
        // Return mock users
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setId(String.valueOf(i));
            user.setName("User " + i);
            user.setEmail("user" + i + "@example.com");
            user.setTenantId(tenantId);
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> listUsersByIds(String tenantId, List<String> userIds) {
        return userIds.stream()
                .map(id -> getUserById(tenantId, id))
                .toList();
    }

    @Override
    public List<String> listUserRoleIds(String tenantId, String userId) {
        return Arrays.asList("1", "2");
    }

    @Override
    public List<String> listUserDeptIds(String tenantId, String userId) {
        return Arrays.asList("1", "2");
    }

    @Override
    public List<Role> listRoles(String tenantId) {
        // Return mock roles
        List<Role> roles = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Role role = new Role();
            role.setId(String.valueOf(i));
            role.setName("Role " + i);
            role.setTenantId(tenantId);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<String> listUserIdsByRoleId(String tenantId, String roleId) {
        return Arrays.asList("1", "2", "3");
    }

    @Override
    public boolean hasRole(String tenantId, String userId, List<String> roleIds) {
        return true;
    }

    @Override
    public Dept getDeptById(String tenantId, String deptId) {
        // Return mock department
        Dept dept = new Dept();
        dept.setId(deptId);
        dept.setName("Department " + deptId);
        dept.setTenantId(tenantId);
        return dept;
    }

    @Override
    public List<Dept> listDepts(String tenantId) {
        // Return mock departments
        List<Dept> depts = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Dept dept = new Dept();
            dept.setId(String.valueOf(i));
            dept.setName("Department " + i);
            dept.setTenantId(tenantId);
            depts.add(dept);
        }
        return depts;
    }

    @Override
    public boolean inDept(String tenantId, String userId, List<String> deptIds) {
        return true;
    }

    @Override
    public String getSuperior(String tenantId, String userId, int layerType, int level) {
        return "1"; // Mock superior ID
    }

    @Override
    public String getDeptLeader(String tenantId, String userId, int layerType, int level) {
        return "1"; // Mock department leader ID
    }

    @Override
    public List<String> getMultiLayerSuperior(String tenantId, String userId, int layerType, int level) {
        return Arrays.asList("1", "2"); // Mock superior IDs
    }

    @Override
    public List<String> getMultiLayerDeptLeader(String tenantId, String userId, int layerType, int level) {
        return Arrays.asList("1", "2"); // Mock department leader IDs
    }

    @Override
    public boolean belong(String tenantId, String userId, List<String> organIds) {
        return true;
    }
} 
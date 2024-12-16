package com.beeflow.workflow.backend.service;

import com.beeflow.workflow.backend.model.organization.Dept;
import com.beeflow.workflow.backend.model.organization.Role;
import com.beeflow.workflow.backend.model.organization.User;

import java.util.List;

public interface OrganizationService {
    // User operations
    User getUserById(String tenantId, String userId);
    List<User> listUsers(String tenantId);
    List<User> listUsersByIds(String tenantId, List<String> userIds);
    List<String> listUserRoleIds(String tenantId, String userId);
    List<String> listUserDeptIds(String tenantId, String userId);
    
    // Role operations
    List<Role> listRoles(String tenantId);
    List<String> listUserIdsByRoleId(String tenantId, String roleId);
    boolean hasRole(String tenantId, String userId, List<String> roleIds);
    
    // Department operations
    Dept getDeptById(String tenantId, String deptId);
    List<Dept> listDepts(String tenantId);
    boolean inDept(String tenantId, String userId, List<String> deptIds);
    
    // Superior operations
    String getSuperior(String tenantId, String userId, int layerType, int level);
    String getDeptLeader(String tenantId, String userId, int layerType, int level);
    List<String> getMultiLayerSuperior(String tenantId, String userId, int layerType, int level);
    List<String> getMultiLayerDeptLeader(String tenantId, String userId, int layerType, int level);
    
    // General operations
    boolean belong(String tenantId, String userId, List<String> organIds);
} 
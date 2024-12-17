package com.wflow.service;

import com.wflow.vo.OrgTreeVo;
import java.util.List;

public interface OrgRepositoryService {
    /**
     * Get organization tree
     * @param deptId Current department ID
     * @param type Type: user/dept/role
     * @return Organization tree nodes
     */
    List<OrgTreeVo> getOrgTree(String deptId, String type);

    /**
     * Search users by name
     * @param userName Username to search
     * @return Matching users
     */
    List<OrgTreeVo> getUserByName(String userName);

    /**
     * Get user's departments
     * @param userId User ID
     * @return User's departments
     */
    List<String> getUserDepts(String userId);

    /**
     * Get department's parent departments
     * @param deptId Department ID
     * @return Parent departments
     */
    List<String> getDeptParents(String deptId);
} 
package com.wflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wflow.entity.WflowDepartments;
import com.wflow.entity.WflowUsers;
import com.wflow.mapper.WflowDepartmentsMapper;
import com.wflow.mapper.WflowUsersMapper;
import com.wflow.service.OrgRepositoryService;
import com.wflow.vo.OrgTreeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultOrgRepositoryServiceImpl implements OrgRepositoryService {

    private final WflowUsersMapper usersMapper;
    private final WflowDepartmentsMapper departmentsMapper;

    @Override
    public List<OrgTreeVo> getOrgTree(String deptId, String type) {
        List<OrgTreeVo> result = new ArrayList<>();
        
        // Get departments
        if ("dept".equals(type) || "user".equals(type)) {
            List<WflowDepartments> depts;
            if (StringUtils.hasText(deptId)) {
                depts = departmentsMapper.selectList(
                    new LambdaQueryWrapper<WflowDepartments>()
                        .eq(WflowDepartments::getParentId, deptId)
                );
            } else {
                depts = departmentsMapper.selectList(
                    new LambdaQueryWrapper<WflowDepartments>()
                        .isNull(WflowDepartments::getParentId)
                );
            }
            
            // Add departments to result
            result.addAll(depts.stream().map(dept -> {
                OrgTreeVo vo = new OrgTreeVo();
                vo.setId(dept.getDeptId());
                vo.setName(dept.getDeptName());
                vo.setType("dept");
                return vo;
            }).collect(Collectors.toList()));
        }

        // Get users if type is user and deptId is provided
        if ("user".equals(type) && StringUtils.hasText(deptId)) {
            // Get users for specific department using the relationship table
            result.addAll(usersMapper.selectUsersByDept(deptId));
        }

        return result;
    }

    @Override
    public List<OrgTreeVo> getUserByName(String userName) {
        List<WflowUsers> users = usersMapper.selectList(
            new LambdaQueryWrapper<WflowUsers>()
                .like(WflowUsers::getUserName, userName)
        );

        return users.stream().map(user -> {
            OrgTreeVo vo = new OrgTreeVo();
            vo.setId(user.getUserId());
            vo.setName(user.getUserName());
            vo.setAvatar(user.getAvatar());
            vo.setType("user");
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getUserDepts(String userId) {
        return usersMapper.selectUserDepts(userId);
    }

    @Override
    public List<String> getDeptParents(String deptId) {
        List<String> parents = new ArrayList<>();
        findParents(deptId, parents);
        return parents;
    }

    private void findParents(String deptId, List<String> parents) {
        WflowDepartments dept = departmentsMapper.selectById(deptId);
        if (dept != null && StringUtils.hasText(dept.getParentId())) {
            parents.add(dept.getParentId());
            findParents(dept.getParentId(), parents);
        }
    }
} 
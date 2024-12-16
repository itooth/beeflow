package com.beeflow.workflow.backend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beeflow.workflow.backend.model.organization.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
} 
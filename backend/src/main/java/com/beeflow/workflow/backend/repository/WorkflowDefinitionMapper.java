package com.beeflow.workflow.backend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beeflow.workflow.backend.model.WorkflowDefinition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkflowDefinitionMapper extends BaseMapper<WorkflowDefinition> {
} 
package com.beeflow.workflow.backend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("workflow_definition")
public class WorkflowDefinition {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String icon;
    private String groupId;
    private Integer cancelable;
    private String nodeConfig;
    private String flowWidgets;
    private String flowPermission;
    private String flowAdminIds;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String tenantId;
} 
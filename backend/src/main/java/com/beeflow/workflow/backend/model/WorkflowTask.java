package com.beeflow.workflow.backend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("workflow_task")
public class WorkflowTask {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String instanceId;
    private String taskId;
    private String taskName;
    private String assignee;
    private String comment;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String tenantId;
} 
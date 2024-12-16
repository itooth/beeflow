package com.beeflow.workflow.backend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("workflow_instance")
public class WorkflowInstance {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String definitionId;
    private String processInstanceId;
    private String formData;
    private String initiator;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String tenantId;
} 
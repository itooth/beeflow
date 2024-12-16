package com.beeflow.workflow.backend.model.organization;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("org_role")
public class Role {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String tenantId;
} 
package com.beeflow.workflow.backend.model.organization;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("org_dept")
public class Dept {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String pid;
    private String name;
    private String tenantId;
} 
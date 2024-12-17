package com.wflow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("wflow_departments")
public class WflowDepartments {
    @TableId
    private String deptId;
    private String parentId;
    private String deptName;
    private String leader;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 
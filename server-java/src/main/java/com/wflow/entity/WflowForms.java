package com.wflow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("wflow_models")
public class WflowForms {
    @TableId
    private String id;
    private String groupId;
    private String name;
    private String icon;
    private String logo;
    private String formItems;
    private String process;
    private Integer version;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 
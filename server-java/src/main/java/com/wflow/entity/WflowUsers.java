package com.wflow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("wflow_users")
public class WflowUsers {
    @TableId
    private String userId;
    private String userName;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 
package com.wflow.vo;

import lombok.Data;

@Data
public class OrgTreeVo {
    private String id;
    private String name;
    private String avatar;
    private String type; // user/dept/role
} 
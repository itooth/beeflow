package com.wflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wflow.entity.WflowModelGroups;
import com.wflow.mapper.WflowModelGroupsMapper;
import com.wflow.service.ModelGroupService;
import org.springframework.stereotype.Service;

@Service
public class ModelGroupServiceImpl extends ServiceImpl<WflowModelGroupsMapper, WflowModelGroups> implements ModelGroupService {
} 
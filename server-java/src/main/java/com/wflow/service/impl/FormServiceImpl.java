package com.wflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wflow.entity.WflowForms;
import com.wflow.mapper.WflowFormsMapper;
import com.wflow.service.FormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class FormServiceImpl extends ServiceImpl<WflowFormsMapper, WflowForms> implements FormService {

    public List<WflowForms> list(QueryWrapper<WflowForms> queryWrapper) {
        log.info("Starting form query with wrapper: {}", queryWrapper.getSqlSegment());
        try {
            log.info("Executing SQL query on table: wflow_models");
            List<WflowForms> forms = super.list(queryWrapper);
            
            // Set default logo for forms that don't have one
            forms.forEach(form -> {
                if (form.getLogo() == null || form.getLogo().trim().isEmpty()) {
                    form.setLogo("{\"icon\":\"el-icon-document\",\"background\":\"#409EFF\"}");
                }
            });
            
            log.info("Query returned {} forms with logos: {}", forms.size(), forms);
            return forms;
        } catch (Exception e) {
            log.error("Error executing form query: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean save(WflowForms entity) {
        try {
            // Set default logo if not provided
            if (entity.getLogo() == null || entity.getLogo().trim().isEmpty()) {
                entity.setLogo("{\"icon\":\"el-icon-document\",\"background\":\"#409EFF\"}");
            }
            
            log.info("Attempting to save form entity to wflow_models: {}", entity);
            boolean result = super.save(entity);
            log.info("Form save result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error saving form entity: {}", entity, e);
            throw new RuntimeException("Failed to save form: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean updateById(WflowForms entity) {
        try {
            // Set default logo if not provided
            if (entity.getLogo() == null || entity.getLogo().trim().isEmpty()) {
                entity.setLogo("{\"icon\":\"el-icon-document\",\"background\":\"#409EFF\"}");
            }
            
            log.info("Attempting to update form entity in wflow_models: {}", entity);
            boolean result = super.updateById(entity);
            log.info("Form update result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error updating form entity: {}", entity, e);
            throw new RuntimeException("Failed to update form: " + e.getMessage(), e);
        }
    }
} 
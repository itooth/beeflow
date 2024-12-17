package com.wflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wflow.entity.WflowForms;
import com.wflow.service.FormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @GetMapping("/list")
    public ResponseEntity<?> listForms(@RequestParam(required = false) String groupId) {
        log.info("Fetching forms for groupId: {}", groupId);
        try {
            QueryWrapper<WflowForms> queryWrapper = new QueryWrapper<>();
            if (groupId != null && !groupId.trim().isEmpty()) {
                queryWrapper.eq("group_id", groupId);
                log.info("Added group_id filter to query: {}", groupId);
            }
            log.info("Executing query with wrapper: {}", queryWrapper.getSqlSegment());
            List<WflowForms> forms = formService.list(queryWrapper);
            log.info("Found {} forms for groupId {}: {}", forms.size(), groupId, forms);
            return ResponseEntity.ok(forms);
        } catch (Exception e) {
            log.error("Error fetching forms for groupId {}: {}", groupId, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "获取表单列表失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createForm(@RequestBody WflowForms form) {
        log.info("Creating new form: {}", form);
        try {
            // Set default values if not provided
            if (form.getId() == null) {
                form.setId(UUID.randomUUID().toString().replace("-", ""));
            }
            if (form.getName() == null || form.getName().trim().isEmpty()) {
                form.setName("未命名表单");
            }
            if (form.getVersion() == null) {
                form.setVersion(1);
            }
            
            LocalDateTime now = LocalDateTime.now();
            form.setCreateTime(now);
            form.setUpdateTime(now);

            // Save the form
            formService.save(form);
            log.info("Successfully created form with id: {}", form.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "创建表单成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating form: {}", form, e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "创建表单失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/detail")
    public ResponseEntity<?> updateForm(@RequestBody WflowForms form) {
        log.info("Updating form: {}", form);
        try {
            if (form.getName() == null || form.getName().trim().isEmpty()) {
                form.setName("未命名表单");
            }
            form.setUpdateTime(LocalDateTime.now());
            formService.updateById(form);
            log.info("Successfully updated form with id: {}", form.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "更新表单成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating form: {}", form, e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "更新表单失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getForm(@PathVariable String id) {
        log.info("Fetching form with id: {}", id);
        try {
            WflowForms form = formService.getById(id);
            if (form == null) {
                log.warn("Form not found with id: {}", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Successfully fetched form: {}", form);
            return ResponseEntity.ok(form);
        } catch (Exception e) {
            log.error("Error fetching form with id: {}", id, e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "获取表单失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
} 
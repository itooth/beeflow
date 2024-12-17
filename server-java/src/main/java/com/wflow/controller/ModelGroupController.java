package com.wflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wflow.entity.WflowForms;
import com.wflow.entity.WflowModelGroups;
import com.wflow.service.FormService;
import com.wflow.service.ModelGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/form/group")
@RequiredArgsConstructor
public class ModelGroupController {

    private final ModelGroupService modelGroupService;
    private final FormService formService;

    @GetMapping("")
    public ResponseEntity<?> list(HttpServletRequest request) {
        log.info("GET /admin/form/group - Fetching all model groups with forms");
        try {
            // Get all groups
            List<WflowModelGroups> groups = modelGroupService.list();
            log.info("Found {} groups", groups.size());
            
            // Convert to response format
            List<Map<String, Object>> response = groups.stream().map(group -> {
                Map<String, Object> groupMap = new HashMap<>();
                groupMap.put("id", group.getId());
                groupMap.put("name", group.getName());
                groupMap.put("sort", group.getSort());
                groupMap.put("createTime", group.getCreateTime());
                groupMap.put("updateTime", group.getUpdateTime());
                
                // Get forms for this group
                QueryWrapper<WflowForms> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("group_id", group.getId());
                List<WflowForms> forms = formService.list(queryWrapper);
                
                // Convert forms to response format
                List<Map<String, Object>> items = forms.stream().map(form -> {
                    Map<String, Object> formMap = new HashMap<>();
                    formMap.put("id", form.getId());
                    formMap.put("formId", form.getId());
                    formMap.put("formName", form.getName());
                    formMap.put("name", form.getName());
                    formMap.put("remark", form.getRemark());
                    formMap.put("updated", form.getUpdateTime());
                    formMap.put("isStop", false);
                    formMap.put("logo", form.getLogo() != null ? form.getLogo() : 
                               "{\"icon\":\"el-icon-document\",\"background\":\"#409EFF\"}");
                    return formMap;
                }).collect(Collectors.toList());
                
                groupMap.put("items", items);
                return groupMap;
            }).collect(Collectors.toList());
            
            log.info("Returning response with {} groups", response.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching model groups: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "获取分组异常: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAlternative(HttpServletRequest request) {
        return list(request);  // Reuse the same logic
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody WflowModelGroups group) {
        log.info("Creating new model group: {}", group);
        try {
            modelGroupService.save(group);
            List<WflowModelGroups> groups = modelGroupService.list();
            log.info("Successfully created group. Total groups: {}", groups.size());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "创建分组成功");
            response.put("groups", groups);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating model group: {}", group, e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "创建分组失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody WflowModelGroups group) {
        log.info("Updating model group: {}", group);
        try {
            modelGroupService.updateById(group);
            log.info("Successfully updated group");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating model group: {}", group, e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "更新分组失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        log.info("Deleting model group with id: {}", id);
        try {
            modelGroupService.removeById(id);
            log.info("Successfully deleted group");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting model group with id: {}", id, e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "删除分组失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
} 
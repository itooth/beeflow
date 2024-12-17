package com.wflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wflow.entity.WflowForms;
import com.wflow.service.FormService;
import com.wflow.service.ProcessService;
import com.wflow.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;
    private final FormService formService;
    private final ObjectMapper objectMapper;

    @PostMapping("/start/{formId}")
    public ResponseEntity<?> startProcess(@PathVariable String formId, @RequestBody Map<String, Object> formData) {
        log.info("Starting approval process for form: {} with data: {}", formId, formData);
        try {
            // Log current user
            String currentUser = UserUtil.getLoginUserId();
            log.info("Current user from UserUtil: {}", currentUser);
            
            // Get form details
            WflowForms form = formService.getById(formId);
            if (form == null) {
                log.error("Form not found with id: {}", formId);
                return ResponseEntity.notFound().build();
            }

            // Log form details
            log.info("Form details - Name: {}, Process Definition: {}", form.getName(), form.getProcess());
            
            // Validate form data
            if (formData == null) {
                log.error("Form data is null");
                Map<String, String> error = new HashMap<>();
                error.put("message", "表单数据不能为空");
                return ResponseEntity.badRequest().body(error);
            }

            // Log form data structure
            log.info("Form data structure: {}", objectMapper.writeValueAsString(formData));

            // Start the process
            String processInstanceId = processService.startProcess(form, formData);
            log.info("Successfully started process with ID: {} for form: {}", processInstanceId, formId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "流程启动成功");
            response.put("processInstanceId", processInstanceId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error starting process for form {}: {}", formId, e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("message", "启动流程失败: " + e.getMessage());
            error.put("stackTrace", e.getStackTrace());
            error.put("cause", e.getCause() != null ? e.getCause().getMessage() : null);
            error.put("currentUser", UserUtil.getLoginUserId());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/tasks/{processInstanceId}")
    public ResponseEntity<?> getProcessTasks(@PathVariable String processInstanceId) {
        log.info("Fetching tasks for process instance: {}", processInstanceId);
        try {
            Map<String, Object> tasks = processService.getProcessTasks(processInstanceId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            log.error("Error fetching tasks for process {}: {}", processInstanceId, e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "获取任务列表失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/my")
    public ResponseEntity<?> getInitiatedProcesses() {
        log.info("Fetching processes initiated by current user");
        try {
            String userId = UserUtil.getLoginUserId();
            log.info("Current user: {}", userId);
            
            // URL encode the userId if it's not already encoded
            if (!userId.contains("%")) {
                userId = java.net.URLEncoder.encode(userId, "UTF-8");
                log.info("Encoded user ID: {}", userId);
            }
            
            Map<String, Object> processes = processService.getInitiatedProcesses(userId);
            return ResponseEntity.ok(processes);
        } catch (Exception e) {
            log.error("Error fetching initiated processes: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "获取发起的流程失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
} 
package com.beeflow.workflow.backend.controller;

import com.beeflow.workflow.backend.model.organization.Dept;
import com.beeflow.workflow.backend.model.organization.Role;
import com.beeflow.workflow.backend.model.organization.User;
import com.beeflow.workflow.backend.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/organ")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/getLoginUserDetail")
    public ResponseEntity<?> getLoginUserDetail() {
        log.info("Getting login user detail");
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("id", "1");
        data.put("name", "Admin");
        data.put("email", "admin@example.com");
        data.put("mobile", null);
        data.put("avatar", null);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<?> listUsers(@RequestParam(required = false) String tenantId) {
        log.info("Listing users for tenantId: {}", tenantId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", organizationService.listUsers(tenantId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listRoles")
    public ResponseEntity<?> listRoles(@RequestParam(required = false) String tenantId) {
        log.info("Listing roles for tenantId: {}", tenantId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", organizationService.listRoles(tenantId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listDepts")
    public ResponseEntity<?> listDepts(@RequestParam(required = false) String tenantId) {
        log.info("Listing departments for tenantId: {}", tenantId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", organizationService.listDepts(tenantId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/superior")
    public ResponseEntity<?> getSuperior(
            @RequestParam String tenantId,
            @PathVariable String userId,
            @RequestParam int layerType,
            @RequestParam int level) {
        log.info("Getting superior for userId: {}, layerType: {}, level: {}", userId, layerType, level);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", organizationService.getSuperior(tenantId, userId, layerType, level));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/deptLeader")
    public ResponseEntity<?> getDeptLeader(
            @RequestParam String tenantId,
            @PathVariable String userId,
            @RequestParam int layerType,
            @RequestParam int level) {
        log.info("Getting dept leader for userId: {}, layerType: {}, level: {}", userId, layerType, level);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", organizationService.getDeptLeader(tenantId, userId, layerType, level));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUserDetail")
    public ResponseEntity<?> getUserDetail(@RequestParam String userId) {
        log.info("Getting user detail for userId: {}", userId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        Map<String, Object> data = new HashMap<>();
        data.put("id", userId);
        data.put("name", "User " + userId);
        data.put("email", "user" + userId + "@example.com");
        data.put("mobile", null);
        data.put("avatar", null);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
} 
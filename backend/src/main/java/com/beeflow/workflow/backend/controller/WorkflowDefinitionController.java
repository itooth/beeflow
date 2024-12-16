package com.beeflow.workflow.backend.controller;

import com.beeflow.workflow.backend.model.WorkflowDefinition;
import com.beeflow.workflow.backend.service.WorkflowDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flowdefinition")
@RequiredArgsConstructor
public class WorkflowDefinitionController {

    private final WorkflowDefinitionService definitionService;

    @GetMapping("/listGroups")
    public ResponseEntity<?> listGroups() {
        log.info("Received request to list groups");
        Object result = definitionService.listGroups();
        log.info("Returning groups response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listGroupsWithFlowDefinition")
    public ResponseEntity<?> listGroupsWithFlowDefinition() {
        log.info("Received request to list groups with flow definition");
        Object result = definitionService.listGroupsWithFlowDefinition();
        log.info("Returning groups with flow definition response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listGroupsWithEnabledFlowDefinition")
    public ResponseEntity<?> listGroupsWithEnabledFlowDefinition() {
        log.info("Received request to list groups with enabled flow definition");
        Object result = definitionService.listGroupsWithEnabledFlowDefinition();
        log.info("Returning groups with enabled flow definition response: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveOrUpdateGroup")
    public ResponseEntity<?> saveOrUpdateGroup(@RequestBody Map<String, Object> data) {
        log.info("Received request to save or update group with data: {}", data);
        Object result = definitionService.saveOrUpdateGroup(data);
        log.info("Returning save or update group response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/deleteGroup")
    public ResponseEntity<?> deleteGroup(@RequestParam String id) {
        log.info("Received request to delete group with id: {}", id);
        Object result = definitionService.deleteGroup(id);
        log.info("Returning delete group response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listFlowDefinitions")
    public ResponseEntity<?> listFlowDefinitions(@RequestParam(required = false) String groupId) {
        log.info("Received request to list flow definitions for groupId: {}", groupId);
        Object result = definitionService.listFlowDefinitions(groupId);
        log.info("Returning flow definitions response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getFlowConfig")
    public ResponseEntity<?> getFlowConfig(@RequestParam String id) {
        log.info("Received request to get flow config for id: {}", id);
        Object result = definitionService.getFlowConfig(id);
        log.info("Returning flow config response: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Map<String, Object> data) {
        log.info("Received request to save or update flow with data: {}", data);
        Object result = definitionService.saveOrUpdate(data);
        log.info("Returning save or update response: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/copy")
    public ResponseEntity<?> copy(@RequestBody Map<String, Object> data) {
        log.info("Received request to copy flow with data: {}", data);
        Object result = definitionService.copy(data);
        log.info("Returning copy response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/removeById")
    public ResponseEntity<?> removeById(@RequestParam String id) {
        log.info("Received request to remove flow by id: {}", id);
        Object result = definitionService.removeById(id);
        log.info("Returning remove response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/freezeById")
    public ResponseEntity<?> freezeById(@RequestParam String id) {
        log.info("Received request to freeze flow by id: {}", id);
        Object result = definitionService.freezeById(id);
        log.info("Returning freeze response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/enableById")
    public ResponseEntity<?> enableById(@RequestParam String id) {
        log.info("Received request to enable flow by id: {}", id);
        Object result = definitionService.enableById(id);
        log.info("Returning enable response: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getFlowFormWidget")
    public ResponseEntity<?> getFlowFormWidget(@RequestParam String id) {
        log.info("Received request to get flow form widget for id: {}", id);
        Object result = definitionService.getFlowFormWidget(id);
        log.info("Returning flow form widget response: {}", result);
        return ResponseEntity.ok(result);
    }
} 
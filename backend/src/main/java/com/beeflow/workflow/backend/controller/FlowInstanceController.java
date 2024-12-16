package com.beeflow.workflow.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flowinstance")
@RequiredArgsConstructor
public class FlowInstanceController {

    @PostMapping("/listFlowInsts")
    public ResponseEntity<?> listFlowInsts(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size,
                                         @RequestBody(required = false) Map<String, Object> query) {
        log.info("Listing flow instances with page: {}, size: {}, query: {}", page, size, query);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", new ArrayList<>());
        response.put("total", 0);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/listTasks")
    public ResponseEntity<?> listTasks(@RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size,
                                     @RequestBody(required = false) Map<String, Object> query) {
        log.info("Listing tasks with page: {}, size: {}, query: {}", page, size, query);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", new ArrayList<>());
        response.put("total", 0);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam String id) {
        log.info("Getting flow instance by id: {}", id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", new HashMap<>());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getForm")
    public ResponseEntity<?> getForm(@RequestParam String id) {
        log.info("Getting form for flow instance: {}", id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", new HashMap<>());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getDetail")
    public ResponseEntity<?> getDetail(@RequestParam String id) {
        log.info("Getting detail for flow instance: {}", id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 1);
        response.put("success", true);
        response.put("data", new HashMap<>());
        return ResponseEntity.ok(response);
    }
} 
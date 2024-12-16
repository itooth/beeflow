package com.beeflow.controller;

import com.beeflow.model.ApiResponse;
import com.beeflow.model.FlowDefinition;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class FlowDefinitionController {

    @GetMapping("/flowdefinition/listG-ithFlowDefi")
    public ApiResponse<List<FlowDefinition>> listFlowDefinitions() {
        List<FlowDefinition> definitions = new ArrayList<>();
        FlowDefinition def = new FlowDefinition();
        
        FlowDefinition.WorkFlowDef workFlowDef = new FlowDefinition.WorkFlowDef();
        workFlowDef.setName("示例流程");
        workFlowDef.setIcon("approval");
        workFlowDef.setFlowAdminIds(Arrays.asList("1", "2"));
        workFlowDef.setCancelable(1);
        def.setWorkFlowDef(workFlowDef);

        FlowDefinition.NodeConfig nodeConfig = new FlowDefinition.NodeConfig();
        nodeConfig.setName("开始");
        nodeConfig.setType(0);
        def.setNodeConfig(nodeConfig);

        FlowDefinition.FlowPermission flowPermission = new FlowDefinition.FlowPermission();
        flowPermission.setType(0);
        def.setFlowPermission(flowPermission);

        definitions.add(def);
        return ApiResponse.success(definitions);
    }

    @PostMapping("/flowdefinition/saveFlowDef")
    public ApiResponse<FlowDefinition> saveFlowDefinition(@RequestBody FlowDefinition flowDefinition) {
        return ApiResponse.success(flowDefinition);
    }

    @GetMapping("/flowdefinition/getFlowDefById")
    public ApiResponse<FlowDefinition> getFlowDefinitionById(@RequestParam String id) {
        FlowDefinition def = new FlowDefinition();
        
        FlowDefinition.WorkFlowDef workFlowDef = new FlowDefinition.WorkFlowDef();
        workFlowDef.setName("示例流程");
        workFlowDef.setIcon("approval");
        workFlowDef.setFlowAdminIds(Arrays.asList("1", "2"));
        workFlowDef.setCancelable(1);
        def.setWorkFlowDef(workFlowDef);

        FlowDefinition.NodeConfig nodeConfig = new FlowDefinition.NodeConfig();
        nodeConfig.setName("开始");
        nodeConfig.setType(0);
        def.setNodeConfig(nodeConfig);

        FlowDefinition.FlowPermission flowPermission = new FlowDefinition.FlowPermission();
        flowPermission.setType(0);
        def.setFlowPermission(flowPermission);

        return ApiResponse.success(def);
    }

    @DeleteMapping("/flowdefinition/deleteFlowDef")
    public ApiResponse<Boolean> deleteFlowDefinition(@RequestParam String id) {
        return ApiResponse.success(true);
    }

    @GetMapping("/flowdefinition/listGroupsWithFlowDefinition")
    public ApiResponse<List<Map<String, Object>>> listGroupsWithFlowDefinition() {
        List<Map<String, Object>> groups = new ArrayList<>();
        
        Map<String, Object> group = new HashMap<>();
        group.put("id", "1");
        group.put("name", "默认分组");
        group.put("flowDefinitions", Arrays.asList());
        
        groups.add(group);
        
        return ApiResponse.success(groups);
    }
} 
package com.beeflow.workflow.backend.service;

import java.util.Map;

public interface WorkflowDefinitionService {
    Object listGroups();
    Object listGroupsWithFlowDefinition();
    Object listGroupsWithEnabledFlowDefinition();
    Object saveOrUpdateGroup(Map<String, Object> data);
    Object deleteGroup(String id);
    Object listFlowDefinitions(String groupId);
    Object getFlowConfig(String id);
    Object saveOrUpdate(Map<String, Object> data);
    Object copy(Map<String, Object> data);
    Object removeById(String id);
    Object freezeById(String id);
    Object enableById(String id);
    Object getFlowFormWidget(String id);
} 
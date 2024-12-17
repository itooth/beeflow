package com.wflow.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wflow.entity.WflowForms;
import com.wflow.service.ProcessService;
import com.wflow.utils.UserUtil;
import com.wflow.utils.WFlowToBpmnCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final WFlowToBpmnCreator bpmnCreator;
    private final ObjectMapper objectMapper;
    private final IdentityService identityService;
    private final HistoryService historyService;

    @Override
    @Transactional
    public String startProcess(WflowForms form, Map<String, Object> formData) {
        log.info("Starting process for form {} with data: {}", form.getId(), formData);
        
        try {
            // Validate form process definition
            if (form.getProcess() == null || form.getProcess().trim().isEmpty()) {
                throw new IllegalArgumentException("流程定义不能为空");
            }

            // Log process definition
            log.info("Process definition: {}", form.getProcess());
            
            // Create process variables
            Map<String, Object> variables = new HashMap<>(formData);
            variables.put("formId", form.getId());
            variables.put("formName", form.getName());
            
            // Get initiator from UserUtil (keep it encoded)
            String initiator = UserUtil.getLoginUserId();
            log.info("Setting process initiator (encoded): {}", initiator);
            
            // Set initiator in variables (keep encoded value)
            variables.put("initiator", initiator);
            variables.put("startUser", initiator);
            
            // Log variables
            log.info("Process variables: {}", objectMapper.writeValueAsString(variables));
            
            // Set authenticated user for process start (decode for this)
            String decodedInitiator = URLDecoder.decode(initiator, "UTF-8");
            identityService.setAuthenticatedUserId(decodedInitiator);
            log.info("Set authenticated user ID to: {}", decodedInitiator);
            
            try {
                // Deploy process definition
                String processDefinitionId = deployProcessDefinition(form);
                log.info("Deployed process definition with ID: {}", processDefinitionId);
                
                // Start process instance with business key and initiator
                ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                    .processDefinitionId(processDefinitionId)
                    .businessKey(form.getId())
                    .variables(variables)
                    .start();
                
                log.info("Started process instance: {} with initiator: {}", processInstance.getId(), initiator);
                
                // Verify process instance details
                ProcessInstance verifyInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstance.getId())
                    .startedBy(decodedInitiator)  // Use decoded value for query
                    .singleResult();
                    
                if (verifyInstance == null) {
                    log.warn("Process instance not found with initiator {}, trying without filter", decodedInitiator);
                    verifyInstance = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .singleResult();
                }
                    
                log.info("Verified process instance - ID: {}, StartUserId: {}, Variables: {}", 
                    verifyInstance.getId(),
                    verifyInstance.getStartUserId(),
                    runtimeService.getVariables(processInstance.getId()));
                
                return processInstance.getId();
            } finally {
                // Clear authenticated user
                identityService.setAuthenticatedUserId(null);
            }
            
        } catch (Exception e) {
            log.error("Error starting process: {}", e.getMessage(), e);
            throw new RuntimeException("启动流程失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getProcessTasks(String processInstanceId) {
        List<Task> tasks = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .list();
            
        Map<String, Object> result = new HashMap<>();
        result.put("tasks", tasks.stream().map(task -> {
            Map<String, Object> taskInfo = new HashMap<>();
            taskInfo.put("id", task.getId());
            taskInfo.put("name", task.getName());
            taskInfo.put("assignee", task.getAssignee());
            taskInfo.put("createTime", task.getCreateTime());
            return taskInfo;
        }).collect(Collectors.toList()));
        
        return result;
    }
    
    @Override
    public Map<String, Object> getInitiatedProcesses(String userId) {
        try {
            log.info("Getting processes initiated by user (encoded): {}", userId);
            
            // First check all historic variables to debug
            List<String> allVariables = historyService.createHistoricVariableInstanceQuery()
                .variableName("initiator")
                .list()
                .stream()
                .map(var -> "ProcessId: " + var.getProcessInstanceId() + ", Value: " + var.getValue())
                .collect(Collectors.toList());
            log.info("All initiator variables in history: {}", allVariables);
            
            // Get process instance IDs from variables with original query
            List<String> processInstanceIds = historyService.createHistoricVariableInstanceQuery()
                .variableName("initiator")
                .variableValueEquals("initiator", userId)  // Use encoded userId
                .list()
                .stream()
                .map(var -> var.getProcessInstanceId())
                .collect(Collectors.toList());
                
            log.info("Found process IDs with initiator={}: {}", userId, processInstanceIds);
            
            // Try alternative query with startUserId (decode for this query)
            String decodedUserId = URLDecoder.decode(userId, "UTF-8");
            List<String> processInstanceIds2 = historyService.createHistoricProcessInstanceQuery()
                .startedBy(decodedUserId)
                .list()
                .stream()
                .map(HistoricProcessInstance::getId)
                .collect(Collectors.toList());
                
            log.info("Found process IDs by startedBy={}: {}", decodedUserId, processInstanceIds2);
            
            // Combine both results
            Set<String> combinedIds = new HashSet<>(processInstanceIds);
            combinedIds.addAll(processInstanceIds2);
            processInstanceIds = new ArrayList<>(combinedIds);
            
            log.info("Combined process IDs: {}", processInstanceIds);
            
            if (processInstanceIds.isEmpty()) {
                log.info("No processes found for user {}", decodedUserId);
                Map<String, Object> result = new HashMap<>();
                result.put("processes", Collections.emptyList());
                return result;
            }
            
            // Query historic process instances by IDs
            List<HistoricProcessInstance> processes = historyService.createHistoricProcessInstanceQuery()
                .processInstanceIds(new HashSet<>(processInstanceIds))
                .orderByProcessInstanceStartTime()
                .desc()
                .list();
                
            log.info("Found {} processes initiated by user {}", processes.size(), decodedUserId);
            
            // Convert to response format
            Map<String, Object> result = new HashMap<>();
            result.put("processes", processes.stream().map(process -> {
                Map<String, Object> processInfo = new HashMap<>();
                processInfo.put("id", process.getId());
                processInfo.put("name", process.getName());
                processInfo.put("startTime", process.getStartTime());
                processInfo.put("endTime", process.getEndTime());
                processInfo.put("businessKey", process.getBusinessKey());
                
                // Get process variables
                Map<String, Object> variables = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(process.getId())
                    .list()
                    .stream()
                    .collect(Collectors.toMap(
                        var -> var.getVariableName(),
                        var -> var.getValue()
                    ));
                    
                processInfo.put("variables", variables);
                return processInfo;
            }).collect(Collectors.toList()));
            
            return result;
        } catch (Exception e) {
            log.error("Error getting initiated processes for user {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("获取发起的流程失败: " + e.getMessage(), e);
        }
    }
    
    private String deployProcessDefinition(WflowForms form) {
        try {
            log.info("Deploying process definition for form: {}", form.getId());
            
            // Convert JSON process to BPMN model
            BpmnModel bpmnModel = bpmnCreator.createBpmnModel(form.getProcess());
            log.info("Created BPMN model from process definition");
            
            // Deploy to Flowable
            Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel(form.getId() + ".bpmn20.xml", bpmnModel)
                .name(form.getName())
                .deploy();
            log.info("Deployed BPMN model with deployment ID: {}", deployment.getId());
                
            // Get process definition ID
            String processDefinitionId = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult()
                .getId();
            
            log.info("Retrieved process definition ID: {}", processDefinitionId);
            return processDefinitionId;
                
        } catch (Exception e) {
            log.error("Error deploying process definition for form {}: {}", form.getId(), e.getMessage(), e);
            throw new RuntimeException("Failed to deploy process definition: " + e.getMessage(), e);
        }
    }
} 
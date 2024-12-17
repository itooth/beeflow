package com.wflow.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.bpmn.model.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Component
public class WFlowToBpmnCreator {
    
    private final ObjectMapper objectMapper;
    
    public WFlowToBpmnCreator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public BpmnModel createBpmnModel(String processDefinition) {
        try {
            log.info("Creating BPMN model from process definition: {}", processDefinition);
            JsonNode processNode = objectMapper.readTree(processDefinition);
            BpmnModel bpmnModel = new BpmnModel();
            org.flowable.bpmn.model.Process process = new org.flowable.bpmn.model.Process();
            
            // Set process attributes using root node
            process.setId("process_" + processNode.path("id").asText());
            process.setName(processNode.path("name").asText());
            
            // Set process initiator
            ExtensionElement initiatorElement = new ExtensionElement();
            initiatorElement.setName("initiator");
            initiatorElement.setNamespace("http://flowable.org/bpmn");
            initiatorElement.setNamespacePrefix("flowable");
            initiatorElement.setElementText("${initiator}");
            process.addExtensionElement(initiatorElement);
            
            // Create start event with initiator
            StartEvent startEvent = new StartEvent();
            startEvent.setId("start");
            
            // Set initiator variable
            startEvent.setInitiator("initiator");
            
            // Add flowable:initiator extension element to start event
            ExtensionElement initiatorExtension = new ExtensionElement();
            initiatorExtension.setName("initiator");
            initiatorExtension.setNamespace("http://flowable.org/bpmn");
            initiatorExtension.setNamespacePrefix("flowable");
            initiatorExtension.setElementText("${initiator}");
            startEvent.addExtensionElement(initiatorExtension);
            
            // Add flowable:assignee extension element to start event
            ExtensionElement assigneeExtension = new ExtensionElement();
            assigneeExtension.setName("assignee");
            assigneeExtension.setNamespace("http://flowable.org/bpmn");
            assigneeExtension.setNamespacePrefix("flowable");
            assigneeExtension.setElementText("${initiator}");
            startEvent.addExtensionElement(assigneeExtension);
            
            // Add flowable:formKey extension element
            ExtensionElement formKeyExtension = new ExtensionElement();
            formKeyExtension.setName("formKey");
            formKeyExtension.setNamespace("http://flowable.org/bpmn");
            formKeyExtension.setNamespacePrefix("flowable");
            formKeyExtension.setElementText("${formId}");
            startEvent.addExtensionElement(formKeyExtension);
            
            process.addFlowElement(startEvent);
            
            // Create user task from the first approval node
            JsonNode approvalNode = processNode.path("children").path("id").isMissingNode() ? 
                processNode.path("children") : 
                processNode.path("children").path(processNode.path("children").fieldNames().next());
            
            UserTask userTask = new UserTask();
            userTask.setId(approvalNode.path("id").asText("task_1"));
            userTask.setName(approvalNode.path("name").asText("审批人"));
            
            // Get assignee from assignedUser array
            JsonNode assignedUsers = approvalNode.path("props").path("assignedUser");
            if (assignedUsers.isArray() && assignedUsers.size() > 0) {
                String assignee = assignedUsers.get(0).path("name").asText("张总");
                userTask.setAssignee(assignee);
                log.info("Setting task assignee to: {}", assignee);
            } else {
                userTask.setAssignee("张总");
                log.info("No assignee found in process definition, using default: 张总");
            }
            
            // Add form key to user task
            userTask.setFormKey("${formId}");
            
            process.addFlowElement(userTask);
            
            // Create end event
            EndEvent endEvent = new EndEvent();
            endEvent.setId("end");
            process.addFlowElement(endEvent);
            
            // Create sequence flows
            SequenceFlow flow1 = new SequenceFlow();
            flow1.setId("flow1");
            flow1.setSourceRef(startEvent.getId());
            flow1.setTargetRef(userTask.getId());
            
            SequenceFlow flow2 = new SequenceFlow();
            flow2.setId("flow2");
            flow2.setSourceRef(userTask.getId());
            flow2.setTargetRef(endEvent.getId());
            
            process.addFlowElement(flow1);
            process.addFlowElement(flow2);
            
            bpmnModel.addProcess(process);
            log.info("Successfully created BPMN model");
            return bpmnModel;
            
        } catch (Exception e) {
            log.error("Error creating BPMN model: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating BPMN model: " + e.getMessage(), e);
        }
    }
} 
package com.wflow.service;

import com.wflow.entity.WflowForms;
import java.util.Map;

public interface ProcessService {
    /**
     * Start a new process instance for a form
     * @param form The form definition
     * @param formData The submitted form data
     * @return The process instance ID
     */
    String startProcess(WflowForms form, Map<String, Object> formData);

    /**
     * Get tasks for a process instance
     * @param processInstanceId The process instance ID
     * @return Map containing task information
     */
    Map<String, Object> getProcessTasks(String processInstanceId);

    /**
     * Get processes initiated by a user
     * @param userId The user ID
     * @return Map containing process information
     */
    Map<String, Object> getInitiatedProcesses(String userId);
} 
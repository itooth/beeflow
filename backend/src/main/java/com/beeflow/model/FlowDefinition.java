package com.beeflow.model;

import lombok.Data;
import java.util.List;

@Data
public class FlowDefinition {
    private WorkFlowDef workFlowDef;
    private NodeConfig nodeConfig;
    private FlowPermission flowPermission;

    @Data
    public static class WorkFlowDef {
        private String name;
        private String icon;
        private List<String> flowAdminIds;
        private Integer cancelable;
    }

    @Data
    public static class NodeConfig {
        private String name;
        private Integer type;
    }

    @Data
    public static class FlowPermission {
        private Integer type;
    }
} 
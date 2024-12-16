package com.beeflow.workflow.backend.model.organization.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LayerType {
    DOWN_TO_UP(0, "自下而上"),
    UP_TO_DOWN(1, "自上而下");

    private final int code;
    private final String desc;

    public static LayerType fromCode(int code) {
        for (LayerType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid layer type code: " + code);
    }
} 
package com.beeflow.workflow.backend.model.organization.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Layer {
    LEVEL_1(0, "直属"),
    LEVEL_2(1, "第二级"),
    LEVEL_3(2, "第三级"),
    LEVEL_4(3, "第四级"),
    LEVEL_5(4, "第五级"),
    LEVEL_6(5, "第六级"),
    LEVEL_7(6, "第七级"),
    LEVEL_8(7, "第八级"),
    LEVEL_9(8, "第九级"),
    LEVEL_10(9, "第十级"),
    LEVEL_11(10, "第十一级"),
    LEVEL_12(11, "第十二级"),
    LEVEL_13(12, "第十三级"),
    LEVEL_14(13, "第十四级"),
    LEVEL_15(14, "第十五级"),
    LEVEL_16(15, "第十六级"),
    LEVEL_17(16, "第十七级"),
    LEVEL_18(17, "第十八级"),
    LEVEL_19(18, "第十九级"),
    LEVEL_20(19, "第二十级");

    private final int code;
    private final String desc;

    public static Layer fromCode(int code) {
        for (Layer layer : values()) {
            if (layer.getCode() == code) {
                return layer;
            }
        }
        throw new IllegalArgumentException("Invalid layer code: " + code);
    }
} 
package com.sxlinks.modules.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author rickliu
 * @desc 描述信息
 */
@Getter
@AllArgsConstructor
public enum ProjectStateEnum {
    /**
     * 未开始
     */
    NotStarted(0),

    /**
     * 评估中
     */
    Evaluating(1),

    /**
     * 评估完成/结束
     */
    Completed(2),

    ;

    private final Integer code;

    public static ProjectStateEnum parse(Integer code) {
        return Arrays.stream(ProjectStateEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }
}

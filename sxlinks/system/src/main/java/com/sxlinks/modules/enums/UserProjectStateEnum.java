package com.sxlinks.modules.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author rickliu
 * @desc 用户当前评估任务状态(0:待审核/待分组 1:已通过 2:已拒绝 3:已结束)
 */
@Getter
@AllArgsConstructor
public enum UserProjectStateEnum {

    /**
     * 待审核/待分组
     */
    WaitGrouped(0),

    /**
     * 评估中
     */
    Evaluating(1),

    /**
     * 已拒绝
     */
    Refused(2),

    /**
     * 评估完成/结束
     */
    Completed(3),

    ;

    private final Integer code;

    public static UserProjectStateEnum parse(Integer code) {
        return Arrays.stream(UserProjectStateEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }
}

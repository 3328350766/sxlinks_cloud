package com.sxlinks.modules.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author sxlinks
 * @desc 描述信息
 */
@Getter
@AllArgsConstructor
public enum ExpertStatusEnum {
    /**
     * 超管
     */
    Wait("0"),

    /**
     * 已分配
     */
    Allocated("1"),

    /**
     * 已删除
     */
    Removed("2"),
    ;

    private final String code;

    public static ExpertStatusEnum parse(Integer code) {
        return Arrays.stream(ExpertStatusEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }
}

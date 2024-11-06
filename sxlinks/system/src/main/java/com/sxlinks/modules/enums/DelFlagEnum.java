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
public enum DelFlagEnum {
    /**
     * 未删除
     */
    NotDelete(0),

    /**
     * 已删除
     */
    Deleted(1),

    ;

    private final Integer code;

    public static DelFlagEnum parse(Integer code) {
        return Arrays.stream(DelFlagEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }
}

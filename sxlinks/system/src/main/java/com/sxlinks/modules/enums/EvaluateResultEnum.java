package com.sxlinks.modules.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author rickliu
 * @desc 评估结果选项(0:禁止使用 1:可以使用 2:限制使用)
 * @date 2022/5/29
 */
@Getter
@AllArgsConstructor
public enum EvaluateResultEnum {

    /**
     * 禁止使用
     */
    Prohibited(0),

    /**
     * 可以使用
     */
    Used(1),

    /**
     * 限制使用
     */
    Limited(2),


    ;



    private final Integer code;

    public static EvaluateResultEnum parse(Integer code) {
        return Arrays.stream(EvaluateResultEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }

}

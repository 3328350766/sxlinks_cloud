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
public enum UserIdentityEnum {
    /**
     * 普通用户
     */
    User(1),

    /**
     * 管理员
     */
    Admin(2),

    ;

    private final Integer code;

    public static UserIdentityEnum parse(Integer code) {
        return Arrays.stream(UserIdentityEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }
}

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
public enum UserTypeEnum {
    /**
     * 超管
     */
    SuperUser(0),

    /**
     * 负责人
     */
    Principal(1),

    /**
     * 当地住建联系人
     */
    LocalHousingContact(2),

    /**
     * 当地联系人
     */
    LocalContact(3),

    /**
     * 组长
     */
    GroupLeader(4),

    /**
     * 专家
     */
    Expert(5),

    ;

    private final Integer code;

    public static UserTypeEnum parse(Integer code) {
        return Arrays.stream(UserTypeEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }
}

package com.sxlinks.modules.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author rickliu
 * @desc 评估对象类型(1:单户 2:多户 3:公共建筑 4:填埋场 5:焚烧场 6:垃圾中转站 7:城市公厕 8:排水设施 9:供水设施 10:燃气设施 11:市政基础设施)
 * @date 2022/5/29
 */
@Getter
@AllArgsConstructor
public enum EvaluateObjEnum {

    /**
     * 单户
     */
    SingleHouse(1),

    /**
     * 多户
     */
    MultiHouse(2),

    /**
     * 公共建筑
     */
    Building(3),

    /**
     * 填埋场
     */
    Tmc(4),

    /**
     * 焚烧场
     */
    Fsc(5),

    /**
     * 垃圾中转站
     */
    Ljz(6),

    /**
     * 城市公厕
     */
    Gc(7),

    /**
     * 排水设施
     */
    Ps(8),

    /**
     * 供水设施
     */
    Gs(9),

    /**
     * 燃气设施
     */
    Rq(10),

    /**
     * 市政基础设施
     */
    Sz(11),



    ;



    private final Integer code;

    public static EvaluateObjEnum parse(Integer code) {
        return Arrays.stream(EvaluateObjEnum.values()).filter(o -> Objects.equals(o.getCode(), code)).findFirst().orElse(null);
    }

}

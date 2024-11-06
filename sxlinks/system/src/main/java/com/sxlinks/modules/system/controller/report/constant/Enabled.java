package com.sxlinks.modules.system.controller.report.constant;


import com.sxlinks.modules.system.controller.report.annotation.valid.em.EnumInterface;

/**
 * 是否标识即0,1
 * @author lr
 * @since 2021-01-12
 */
public enum Enabled implements EnumInterface<Integer> {

    YES(1),NO(0);

    private Integer value;

    Enabled(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


    @Override
    public Boolean exist(Integer value) {
        return this.getValue().equals(value);
    }}

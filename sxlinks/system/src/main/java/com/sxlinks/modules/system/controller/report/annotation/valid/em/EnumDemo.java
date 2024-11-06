package com.sxlinks.modules.system.controller.report.annotation.valid.em;

/**
 * @author lr
 * @since 2021-05-12
 */
public enum EnumDemo implements EnumInterface<Integer>{
    RED(1),GREEN(2);

    private Integer value;

    EnumDemo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }


    @Override
    public Boolean exist(Integer value) {
        return this.value.equals(value);
    }}

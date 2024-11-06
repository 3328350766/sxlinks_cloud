package com.sxlinks.modules.system.controller.report.annotation.valid.em;

/**
 * @author lr
 * @since 2021-05-12
 */
public interface EnumInterface<T> {

    /**
     * 判断是否存在
     * @param value
     * @return
     */
    Boolean exist(T value);
}

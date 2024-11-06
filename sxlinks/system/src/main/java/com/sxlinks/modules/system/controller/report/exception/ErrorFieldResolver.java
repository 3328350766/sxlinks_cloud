package com.sxlinks.modules.system.controller.report.exception;

import org.springframework.core.MethodParameter;

/**
 * @author wongbin
 */
public interface ErrorFieldResolver {

    /**
     * 异常信息中 字段名 翻译
     * @param fieldName 字段名编码
     * @param parameter
     * @return 字段名对应 国际化文字
     */
    default String getFieldName(String fieldName, MethodParameter parameter) {
        return fieldName;
    };
}

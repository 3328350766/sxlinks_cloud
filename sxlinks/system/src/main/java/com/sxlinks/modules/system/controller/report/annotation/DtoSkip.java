package com.sxlinks.modules.system.controller.report.annotation;

import java.lang.annotation.*;

/**
 * DTO跳过指定字段
 * @author lr
 * @since 2021-01-19
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface DtoSkip {
}

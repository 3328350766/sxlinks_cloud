package com.sxlinks.modules.system.controller.report.annotation;

import com.sxlinks.modules.system.controller.report.annotation.resolver.format.FormatterEnum;

import java.lang.annotation.*;

/**
 * 字段
 * @author lr
 * @since 2021-01-12
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormatterType {

    FormatterEnum type() default FormatterEnum.OBJECT;

    Class target() default Object.class;
}

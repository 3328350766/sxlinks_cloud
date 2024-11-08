package com.sxlinks.modules.system.controller.report.annotation;

import java.lang.annotation.*;

/**
 * 翻译
 * @author lr
 * @since 2021-01-12
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Formatter {

    /**
     * 对应的字典项，用对应值取该字典中取值
     * @return
     */
    String dictCode() default "";

    /**
     * 替换字符串
     * @return
     */
    String[] replace() default {};


    /**
     * 默认是修改当前字段的，可以不修改，直接赋值到目标字段
     * @return
     */
    String targetField() default "";

    /**
     * 当有值时，不从数据字典取，直接从对应的可以取值
     */
    String key() default "";

    /**
     * 是否追加到当前字段
     */
    boolean append() default false;
    /**
     * 追加的方式 name(code)
     */
    String format() default "%s(%s)";
}

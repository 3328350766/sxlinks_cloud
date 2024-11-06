package com.sxlinks.modules.system.controller.report.annotation.valid.keyvalue;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 限定缓存注解
 * @author lr
 * @since 2021-05-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = {AssertKeyValueValidator.class})
public @interface AssertKeyValue {

    String message() default "key.validation.not.match";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * 对应的字典项，用对应值取该字典中取值
     * @return
     */
    String dictCode() default "";

    /**
     * 当有值时，不从数据字典取，直接从对应的可以取值，支持动态参数${key},默认从UserContentHolder.getContext().getParams()中取
     */
    String key() default "";
}

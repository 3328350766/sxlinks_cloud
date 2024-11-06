package com.sxlinks.common.metadata;

import com.sxlinks.plugin.es.config.enums.BcEsMetaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: MdDesc.java, v 0.1 2020-12-07  Exp $$
  */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaDesc {
    String code() default "";
    String name() default "";
    String desc() default "";
    BcEsMetaType type() default BcEsMetaType.STRING;
}

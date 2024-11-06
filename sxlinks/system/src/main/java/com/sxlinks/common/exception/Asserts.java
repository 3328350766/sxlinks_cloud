package com.sxlinks.common.exception;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/26
 */
public final class Asserts {

    public static void hasLength(String text, Integer code, String message) {
        if (StringUtils.isBlank(text)) {
            throw new MiniException(code, message);
        }
    }


    public static void isTrue(boolean expression, Integer code, String message) {
        if (!expression) {
            throw new MiniException(code, message);
        }
    }

    public static void notNull(Object object, Integer code, String message) {
        if (object == null) {
            throw new MiniException(code, message);
        }
    }

    public static void isNull(Object object, Integer code, String message) {
        if (object != null) {
            throw new MiniException(code, message);
        }
    }

    public static void notEmpty(Collection<?> collection, Integer code, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new MiniException(code, message);
        }
    }


}

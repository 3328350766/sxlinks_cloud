package com.sxlinks.modules.system.controller.report.holder;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * 用户上下文操作
 * @author lr
 * @since 2021-01-12
 */
public class UserContentHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 清空
     */
    public static void clearContext() {
        userContextThreadLocal.remove();
    }

    /**
     * 获取用户信息
     * @return
     */
    public static  UserContext getContext() {
        UserContext ctx = userContextThreadLocal.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            userContextThreadLocal.set(ctx);
        }

        return ctx;
    }

    /**
     * 设置用户信息
     * @param context
     */
    public static  void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        userContextThreadLocal.set(context);
    }


    /**
     * 获取用户名
     * @return
     */
    public static String getUsername() {
        return getContext().getUsername();
    }


    /**
     * 获取用户类型
     * @return
     */
    public static Integer getUserType() {
        return getContext().getType();
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getOrgCode() {
        return getContext().getOrgCode();
    }


    /**
     * 获取租户
     * @return
     */
    public static String getTenantCode() {
        return getContext().getTenantCode();
    }


    /**
     * 标识用户登录的终端
     * @return
     */
    public static String getUuid() {
        return getContext().getUuid();
    }
    /**
     * 创建空的用户信息
     * @return
     */
    public static  UserContext createEmptyContext() {
        return new UserContext();
    }

    /**
     * 获取动态参数
     * @param key
     * @return
     */
    public static Object getParam(String key) {
        return getContext().getParams().get(key);
    }

    /**
     * 添加参数
     * @param key
     * @param value
     */
    public static void putParam(String key, Object value) {
        getContext().getParams().put(key, value);
    }

    /**
     * 获取语言标识
     * @return
     */
    public static Locale getLocale() {
        return getContext().getLocale();
    }

    /**
     * 获取时区
     * @return
     */
    public static String getTimeZone() {
        return getContext().getTimeZone();
    }
}

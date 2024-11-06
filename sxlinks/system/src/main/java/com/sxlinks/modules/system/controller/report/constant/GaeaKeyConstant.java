package com.sxlinks.modules.system.controller.report.constant;

/**
 * 缓存key
 * @author lr
 * @since 2021-03-03
 */
public interface GaeaKeyConstant {

    /**
     * 数据字典前缀
     */
    String DICT_PREFIX = "gaea:dict:prefix:";

    /**
     * 系统登录token
     */
    String USER_LOGIN_TOKEN = "system:login:token:";

    /**
     * 用户名与真实姓名的对应关系
     */
    String USER_NICKNAME_KEY = "gaea:user:nickname:${tenantCode}";

    /**
     * 机构、路由与角色的缓存
     */
    String HASH_URL_ROLE_KEY = "gaea:security:url:roles:";


    /**
     * 用户名与角色对应关系的前缀
     */
    String USER_ROLE_SET_PREFIX = "gaea:security:user:roles:";

    /**
     * 所有权限的映射的前缀，带应用名称的即区分不同应用
     */
    String AUTHORITY_ALL_MAP_PREFIX_WITH_APP = "gaea:security:authorities:all:";

    /**
     * 所有权限的映射的前缀，不区分应用，所有放一起，可以用重复
     */
    String AUTHORITY_ALL_MAP_PREFIX = "gaea:security:authorities:all";

    /**
     * 微信相关的缓存前缀
     */
    String WX_PERFIX = "system:wechat:miniapp";

    /**
     * 微信小程序openid的缓存前缀
     */
    String WX_OPENID_PERFIX = "system:wechat:miniapp:openid";
}

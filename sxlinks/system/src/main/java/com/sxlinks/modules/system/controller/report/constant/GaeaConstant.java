package com.sxlinks.modules.system.controller.report.constant;

/**
 * 全局变量
 * @author lr
 * @since 2021-01-12
 */
public interface GaeaConstant {

    /**
     * 用户
     */
    String USER_NAME = "loginName";

    /**
     * 超管用户
     */
    String SUPER_USER_NAME = "admin";


    /**
     * 超级管理员角色
     */
    String SUPER_ADMIN_ROLE = "superAdmin";

    /**
     * 盖亚属性前缀
     */
    String COMPONENT_PREFIX = "spring.gaea.subscribes.";

    /**
     * 系统分隔符
     */
    String SPLIT = ",";

    /**
     * 下划线
     */
    String UNDERLINE = "_";

    /**
     * 请求头
     */
    String Authorization = "Authorization";

    /**
     * 机构编码
     */
    String ORG_CODE = "orgCode";


    /**
     * 租户
     */
    String TENANT_CODE = "tenantCode";

    /**
     * 终端类型，web还是移动端
     */
    String SYS_CODE = "systemCode";


    /**
     * redis分割
     */
    String REDIS_SPLIT = ":";

    /**
     * 国际化
     */
    String LOCALE = "locale";

    /**
     * 时区
     */
    String TIME_ZONE = "timeZone";

    /**
     * 字符集.
     */
    String CHARSET_UTF8 = "UTF-8";

    /**
     * 表ID
     */
    String ID = "id";

    /**
     * 年月日期格式
     */
    String MOUTH_PATTERN = "yyyy-MM";


    /**
     * 日期格式
     */
    String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式
     */
    String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 降序
     */
    String DESC = "DESC";

    /**
     * 升序
     */
    String ASC = "ASC";

    /**
     * 空格匹配符
     */
    String BLANK_REPLACE = "\\s+";

    /**
     * 空格符
     */
    String BLANK = "";


    /**
     * 分隔符
     */
    String URL_SPLIT = "#";

    /**
     * 正则
     */
    String URL_REGEX = "\\{\\w+\\}";


    /**
     * 时间戳正则
     */
    String TIME_MILLIS_REGEX = "^\\d{1,13}";


    /**
     * 需要替换的请求标识
     */
    String URL_MARK = "{";


    /**
     * 需要替换的请求标识
     */
    String URL_PATTERN_MARK = "${";


    /**
     * 替换的元素
     */
    String URL_REPLACEMENT = "**";

    /**
     * 请求头
     */
    String SOURCE_IP="sourceIp";


    /**
     * 百分比符号
     */
    String PERCENT_SIGN = "%";

    /**
     * 星号
     */
    String PATTERN_SIGN = "*";

}

package com.sxlinks.modules.system.controller.report.holder;

import java.util.*;

/**
 * 用户上下文信息，用户名和角色等
 * @author lr
 * @since 2021-01-12
 */
public class UserContext {

    /**
     * 标识用户登录的终端
     */
    private String uuid;
    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private Set<String> Authorities;

    /**
     * 菜单
     */
    private List<String> menus;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 菜单类型，web还是终端
     */
    private String sysCode;

    /**
     * 语言
     */
    private Locale locale = Locale.getDefault();

    /**
     * 用户类型 0：平台，1：租户，2：普通'
     */
    private Integer type;

    /**
     * 租户
     */
    private String tenantCode;

    /**
     * 时区
     */
    private String timeZone;
    /**
     * 动态参数
     */
    private Map<String,Object> params = new HashMap<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<String> getAuthorities() {
        return Authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        Authorities = authorities;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}

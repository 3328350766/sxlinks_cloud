package com.sxlinks.bean.entity.spider;

import lombok.Data;
import com.sxlinks.bean.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "spider_entity")
@Data
@EntityListeners(AuditingEntityListener.class)
public class SpiderEntity extends BaseEntity {
    @Column
    private Long id;    //编号
    @Column(name="name")
    private String name;    //名称
    @Column
    private String description; //描述

    @Column(name="spider_rule_id")
    private Long spiderRuleId; //采集规则id
    @Column(name="replace_text")
    private String replaceText; //替换文本
    @Column(name="need_login")
    private String needLogin;   //是否需要登录
    @Column(name="http_header")
    private String httpHeader;    //http头
    @Column
    private String state; //状态
    @Column
    private String type; //类型

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSpiderRuleId() {
        return spiderRuleId;
    }

    public void setSpiderRuleId(Long spiderRuleId) {
        this.spiderRuleId = spiderRuleId;
    }

    public String getReplaceText() {
        return replaceText;
    }

    public void setReplaceText(String replaceText) {
        this.replaceText = replaceText;
    }

    public String getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(String needLogin) {
        this.needLogin = needLogin;
    }

    public String getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(String httpHeader) {
        this.httpHeader = httpHeader;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

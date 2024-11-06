package com.sxlinks.bean.entity.spider;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "exporter")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Exporter extends BaseEntity {


    @Column
    private Long id;    //编号
    @Column(name="name")
    private String name;    //名称
    @Column
    private String description; //描述
    @Column
    private String type;    //采集类型
    @Column(name="data_source_id")
    private Long dataSourceId; //识别类型
    @Column(name="params")
    private String params; //是否需要分页
    @Column(name="spider_rule_id")
    private Long spiderRuleId; //识别类型

    @Column(name="state")
    private String state; //状态

    @Column(name="clocking_time")
    private String clockingTime;    //定时时间
    @Column(name="is_clocking")
    private String isClocking; //是否定时
    @Column(name="clocking_interval")
    private String clockingInterval; //定时间隔
    @Column(name="last_clocking_time")
    private String lastClockingTime; //最后一次定时时间

    @Column(name="tag")
    private String tag; //标签
    @Column(name="keyword")
    private String keyword; //关键词

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getSpiderRuleId() {
        return spiderRuleId;
    }

    public void setSpiderRuleId(Long spiderRuleId) {
        this.spiderRuleId = spiderRuleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClockingTime() {
        return clockingTime;
    }

    public void setClockingTime(String clockingTime) {
        this.clockingTime = clockingTime;
    }

    public String getIsClocking() {
        return isClocking;
    }

    public void setIsClocking(String isClocking) {
        this.isClocking = isClocking;
    }

    public String getClockingInterval() {
        return clockingInterval;
    }

    public void setClockingInterval(String clockingInterval) {
        this.clockingInterval = clockingInterval;
    }

    public String getLastClockingTime() {
        return lastClockingTime;
    }

    public void setLastClockingTime(String lastClockingTime) {
        this.lastClockingTime = lastClockingTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

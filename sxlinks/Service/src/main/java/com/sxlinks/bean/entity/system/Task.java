package com.sxlinks.bean.entity.system;


import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

@Table(name="t_sys_task")
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Task extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(50) COMMENT '任务名'")
    private String name;

    @Column(name="job_group", columnDefinition = "VARCHAR(50) COMMENT '任务组名'")
    private String jobGroup;

    @Column(name="job_class", columnDefinition = "VARCHAR(255) COMMENT '执行类'")
    private String jobClass;

    @Column(name="note", columnDefinition = "VARCHAR(255) COMMENT '任务说明'")
    private String note;

    @Column(name="cron", columnDefinition = "VARCHAR(50) COMMENT '定时规则'")
    private String cron;

    @Column(name="concurrent", columnDefinition = "TINYINT COMMENT '是否允许并发'")
    private boolean concurrent;

    @Column(name="data", columnDefinition = "TEXT COMMENT '执行参数'")
    private String data;

    @Column(name="exec_at", columnDefinition = "DateTime COMMENT '执行时间'")
    private Date execAt;

    @Column(name="exec_result", columnDefinition = "text COMMENT '执行结果'")
    private String execResult;

    @Column(name="disabled", columnDefinition = "TINYINT COMMENT '是否禁用'")
    private boolean disabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getExecAt() {
        return execAt;
    }

    public void setExecAt(Date execAt) {
        this.execAt = execAt;
    }

    public String getExecResult() {
        return execResult;
    }

    public void setExecResult(String execResult) {
        this.execResult = execResult;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}

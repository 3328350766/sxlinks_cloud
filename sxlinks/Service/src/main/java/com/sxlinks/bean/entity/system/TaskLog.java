package com.sxlinks.bean.entity.system;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * User: Yao
 * Date: 2017-06-22 11:12:48
 */
@Table(name="t_sys_task_log")
@Entity
@Data
public class TaskLog{
    public static final int EXE_FAILURE_RESULT = 0;
    public static final int EXE_SUCCESS_RESULT = 1;

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long idTask;
    @Column(columnDefinition = "VARCHAR(50) COMMENT '任务名'")
    private String name;

    @Column(columnDefinition = "DATETime COMMENT '执行时间'")
    private Date execAt;

    @Column(columnDefinition = "INTEGER COMMENT '执行结果（成功:1、失败:0)'")
    private int execSuccess;

    @Column(columnDefinition = "VARCHAR(500) COMMENT '抛出异常'")
    private String jobException;

    public static int getExeFailureResult() {
        return EXE_FAILURE_RESULT;
    }

    public static int getExeSuccessResult() {
        return EXE_SUCCESS_RESULT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExecAt() {
        return execAt;
    }

    public void setExecAt(Date execAt) {
        this.execAt = execAt;
    }

    public int getExecSuccess() {
        return execSuccess;
    }

    public void setExecSuccess(int execSuccess) {
        this.execSuccess = execSuccess;
    }

    public String getJobException() {
        return jobException;
    }

    public void setJobException(String jobException) {
        this.jobException = jobException;
    }
}
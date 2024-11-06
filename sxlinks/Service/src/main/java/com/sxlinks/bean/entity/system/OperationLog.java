package com.sxlinks.bean.entity.system;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 2018/4/2 0002.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "t_sys_operation_log")
@Data
public class OperationLog {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String logtype;
    @Column
    private String logname;
    @Column
    private Integer userid;
    @Column
    private String classname;
    @Column
    private String method;
    @Column(name="create_time")
    private Date createTime;
    @Column
    private String succeed;
    @Column
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogname() {
        return logname;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

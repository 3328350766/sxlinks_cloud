package com.sxlinks.bean.entity.system;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * Created  on 2018/4/2 0002.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "t_sys_dept")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Dept extends BaseEntity {
    @Column
    private Integer num;
    @Column
    private Long pid;
    @Column
    private String pids;
    @Column
    private String simplename;
    @Column
    private String fullname;
    @Column
    private String tips;
    @Column
    private Integer version;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getSimplename() {
        return simplename;
    }

    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

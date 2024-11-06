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
@Table(name = "t_sys_role")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntity {
    @Column
    private Integer num;
    @Column
    private Long pid;
    @Column
    private String name;
    @Column
    private Long deptid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
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

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
@Table(name = "t_sys_dict")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Dict extends BaseEntity {
    @Column
    private String num;
    @Column
    private Long pid;
    @Column
    private String name;
    @Column
    private String tips;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
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

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}

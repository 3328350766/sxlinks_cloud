package com.sxlinks.bean.entity.system;

import lombok.Data;

import javax.persistence.*;

/**
 * Created  on 2018/4/2 0002.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "t_sys_relation")
@Data
public class Relation {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long menuid;
    @Column
    private Long roleid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }
}

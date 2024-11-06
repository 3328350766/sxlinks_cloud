package com.sxlinks.bean.entity.datasource;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 2019/5/28 0001.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "weibo_account")
@Data
public class WeiboAccount {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String username;
    @Column
    private String url;
    @Column
    private Long attention;
    @Column
    private Long fans;
    @Column
    private String type;
    @Column
    private Long publish;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_by")
    private Long createBy;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "modify_by")
    private Long modifyBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getAttention() {
        return attention;
    }

    public void setAttention(Long attention) {
        this.attention = attention;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPublish() {
        return publish;
    }

    public void setPublish(Long publish) {
        this.publish = publish;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }
}

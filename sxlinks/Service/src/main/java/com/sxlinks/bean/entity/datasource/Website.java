package com.sxlinks.bean.entity.datasource;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 2019/5/20 0001.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "website")
@Data
public class Website {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String url;
    @Column
    private String domain;
    @Column
    private String state;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="create_by")
    private Long createBy;
    @Column(name="modify_time")
    private Date modifyTime;
    @Column(name="modify_by")
    private Long modifyBy;
    @Column(name="pr_spider")
    private Double prSpider;
    @Column
    private String type;
    @Column
    private String country;
    @Column
    private String province;
    @Column
    private String city;
    @Column
    private String beian;

    public Long getId() {
        return id;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Double getPrSpider() {
        return prSpider;
    }

    public void setPrSpider(Double prSpider) {
        this.prSpider = prSpider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBeian() {
        return beian;
    }

    public void setBeian(String beian) {
        this.beian = beian;
    }
}

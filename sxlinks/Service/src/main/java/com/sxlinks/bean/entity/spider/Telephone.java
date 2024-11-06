package com.sxlinks.bean.entity.spider;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "telephone")
@Data
public class Telephone {
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    private String id;    //编号

    //基本信息
    @Column
    private String name;    //名称
    @Column
    private String num;    //号码
    @Column
    private String address;     //地址
    @Column
    private String description; //描述
    @Column
    private String category;    //行业分类
    @Column(name="data_source")
    private String dataSource;      //电话来源
    @Column
    private String province;    //省
    @Column
    private String city;    //市
    @Column
    private String county;    //县
    @Column(name="refer_url")
    private String referUrl;    //引用url

    //附属字段
    @Column(name="create_time")
    private Date createTime;    //创建时间
    @Column(name="create_by")
    private Long createBy;  //创建人
    @Column(name="modify_time")
    private Date modifyTime;    //创建时间
    @Column(name="modify_by")
    private Long modifyBy;  //创建人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }
}

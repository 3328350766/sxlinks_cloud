package com.sxlinks.bean.entity.spider;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company")
@Data
@EntityListeners(AuditingEntityListener.class)
@Proxy(lazy = false)
public class Company {
    @Id
    @GeneratedValue
    private Long id;    //编号
    @Column
    private String name;    //名称

    @Column(name="prip_id")
    private String pripId;  //主pripId
    @Column(name="node_num")
    private String nodeNum;  //地域编号
    @Column(name="ent_type")
    private String entType;  //企业类型 10-企业 16-个体户
    //最近一份年报
    @Column(name="an_che_id")
    private String anCheId;  //年报安册id
    @Column(name="an_che_year")
    private String anCheYear;  //年报安册year
    @Column(name="an_che_date")
    private String anCheDate;  //年册时间

    @Column
    private String address;  //内容
    @Column
    private String website;
    @Column
    private String telephone;
    @Column
    private String mobilephone;

    @Column(name="info_url")
    private String infoUrl;     //信息地址

    @Column
    private String website2;
    @Column
    private String fax;
    @Column
    private String country;
    @Column
    private String province;
    @Column
    private String city;
    @Column
    private String email;
    @Column(name="spider_rule_id")
    private Long spiderRuleId;
    @Column
    private String code;    //社会代码
    @Column
    private String type;    //公司类型
    @Column
    private String faren;   //法人
    @Column
    private String ziben;   //注册资本
    @Column(name="kaiye_starttime")
    private String kaiyeStarttime; //营业开始时间
    @Column(name="kaiye_endtime")
    private String kaiyeEndtime;   //营业结束时间
    @Column(name="dengji_jiguan")
    private String dengjiJiguan;   //登记机关
    @Column(name="hezun_time")
    private String hezunTime;  //核准时间
    @Column(name="dengji_state")
    private String dengjiState;    //登记状态
    @Column
    private String scope;   //经营范围
    @Column
    private String category;   //行业分类
    @Column(name = "create_time")
    private Date createTime;    //创建时间
    @Column(name = "create_by")
    private Long createBy;  //创建人
    @Column(name = "modify_time")
    private Date modifyTime;    //修改时间
    @Column(name = "modify_by")
    private Long modifyBy;  //修改人

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getWebsite2() {
        return website2;
    }

    public void setWebsite2(String website2) {
        this.website2 = website2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSpiderRuleId() {
        return spiderRuleId;
    }

    public void setSpiderRuleId(Long spiderRuleId) {
        this.spiderRuleId = spiderRuleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFaren() {
        return faren;
    }

    public void setFaren(String faren) {
        this.faren = faren;
    }

    public String getZiben() {
        return ziben;
    }

    public void setZiben(String ziben) {
        this.ziben = ziben;
    }

    public String getKaiyeStarttime() {
        return kaiyeStarttime;
    }

    public void setKaiyeStarttime(String kaiyeStarttime) {
        this.kaiyeStarttime = kaiyeStarttime;
    }

    public String getKaiyeEndtime() {
        return kaiyeEndtime;
    }

    public void setKaiyeEndtime(String kaiyeEndtime) {
        this.kaiyeEndtime = kaiyeEndtime;
    }

    public String getDengjiJiguan() {
        return dengjiJiguan;
    }

    public void setDengjiJiguan(String dengjiJiguan) {
        this.dengjiJiguan = dengjiJiguan;
    }

    public String getHezunTime() {
        return hezunTime;
    }

    public void setHezunTime(String hezunTime) {
        this.hezunTime = hezunTime;
    }

    public String getDengjiState() {
        return dengjiState;
    }

    public void setDengjiState(String dengjiState) {
        this.dengjiState = dengjiState;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public String getPripId() {
        return pripId;
    }

    public void setPripId(String pripId) {
        this.pripId = pripId;
    }

    public String getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(String nodeNum) {
        this.nodeNum = nodeNum;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getAnCheId() {
        return anCheId;
    }

    public void setAnCheId(String anCheId) {
        this.anCheId = anCheId;
    }

    public String getAnCheYear() {
        return anCheYear;
    }

    public void setAnCheYear(String anCheYear) {
        this.anCheYear = anCheYear;
    }

    public String getAnCheDate() {
        return anCheDate;
    }

    public void setAnCheDate(String anCheDate) {
        this.anCheDate = anCheDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

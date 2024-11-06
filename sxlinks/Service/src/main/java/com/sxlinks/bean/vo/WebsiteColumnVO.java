package com.sxlinks.bean.vo;

import java.util.Date;

public class WebsiteColumnVO {

    private Long id;

    private String name;

    private String url;

    private String domain;

    private Long websiteId;

    private String list_url;

    private String type;

    private Double prSpider;

    private Date createTime;

    private Long createBy;

    private Date modifyTime;

    private Long modifyBy;



    private String description;

    private String state;

    private String country;

    private String province;

    private String city;

    private String beian;


    /**附加属性**/
    String websiteName;

    /*关联属性**/

    String websiteid;

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

    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public String getList_url() {
        return list_url;
    }

    public void setList_url(String list_url) {
        this.list_url = list_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrSpider() {
        return prSpider;
    }

    public void setPrSpider(Double prSpider) {
        this.prSpider = prSpider;
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

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteid() {
        return websiteid;
    }

    public void setWebsiteid(String websiteid) {
        this.websiteid = websiteid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

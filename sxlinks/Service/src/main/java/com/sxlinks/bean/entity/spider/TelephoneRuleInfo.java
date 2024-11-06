package com.sxlinks.bean.entity.spider;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 2019/5/30 0002.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "telephone_rule_info")
@Data
public class TelephoneRuleInfo {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="name")
    private String infoName;
    @Column(name="name_regular")
    private String nameRegular;    //名称
    @Column(name="num_regular")
    private String numRegular;    //号码
    @Column(name="num_attachment_regular")
    private String numAttachmentRegular;    //号码附加规则
    @Column(name="num_attr")
    private String numAttr;    //号码属性
    @Column(name="num_attachment_attr")
    private String numAttachmentAttr;    //号码附加规则属性
    @Column(name="num_is_replace")
    private String numIsReplace;    //号码是否替换
    @Column(name="address_regular")
    private String addressRegular;     //地址
    @Column(name="description_regular")
    private String descriptionRegular; //描述
    @Column(name="category_regular")
    private String categoryRegular;    //行业分类
    @Column(name="time_regular")
    private String timeRegular;    //时间规则
    @Column(name="province_regular")
    private String provinceRegular;    //省
    @Column(name="city_regular")
    private String cityRegular;    //市
    @Column(name="info_need_paging")
    private String infoNeedPaging;//是否需要分页
    @Column(name="info_paging_regular")
    private String infoPagingRegular;//分页规则
    @Column(name="encoding")
    private String infoEncoding;
    @Column(name="info_downloader")
    private String infoDownloader;  //下载引擎

    @Column(name="page_type")
    private String infoPageType;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="create_by")
    private Long createBy;
    @Column(name="modify_time")
    private Date modifyTime;
    @Column(name="modify_by")
    private Long modifyBy;

    @Column(name="generate_by")
    private String generateBy;
    @Column
    private String belong;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getNameRegular() {
        return nameRegular;
    }

    public void setNameRegular(String nameRegular) {
        this.nameRegular = nameRegular;
    }

    public String getNumRegular() {
        return numRegular;
    }

    public void setNumRegular(String numRegular) {
        this.numRegular = numRegular;
    }

    public String getNumAttachmentRegular() {
        return numAttachmentRegular;
    }

    public void setNumAttachmentRegular(String numAttachmentRegular) {
        this.numAttachmentRegular = numAttachmentRegular;
    }

    public String getNumAttr() {
        return numAttr;
    }

    public void setNumAttr(String numAttr) {
        this.numAttr = numAttr;
    }

    public String getNumAttachmentAttr() {
        return numAttachmentAttr;
    }

    public void setNumAttachmentAttr(String numAttachmentAttr) {
        this.numAttachmentAttr = numAttachmentAttr;
    }

    public String getNumIsReplace() {
        return numIsReplace;
    }

    public void setNumIsReplace(String numIsReplace) {
        this.numIsReplace = numIsReplace;
    }

    public String getAddressRegular() {
        return addressRegular;
    }

    public void setAddressRegular(String addressRegular) {
        this.addressRegular = addressRegular;
    }

    public String getDescriptionRegular() {
        return descriptionRegular;
    }

    public void setDescriptionRegular(String descriptionRegular) {
        this.descriptionRegular = descriptionRegular;
    }

    public String getCategoryRegular() {
        return categoryRegular;
    }

    public void setCategoryRegular(String categoryRegular) {
        this.categoryRegular = categoryRegular;
    }

    public String getTimeRegular() {
        return timeRegular;
    }

    public void setTimeRegular(String timeRegular) {
        this.timeRegular = timeRegular;
    }

    public String getProvinceRegular() {
        return provinceRegular;
    }

    public void setProvinceRegular(String provinceRegular) {
        this.provinceRegular = provinceRegular;
    }

    public String getCityRegular() {
        return cityRegular;
    }

    public void setCityRegular(String cityRegular) {
        this.cityRegular = cityRegular;
    }

    public String getInfoNeedPaging() {
        return infoNeedPaging;
    }

    public void setInfoNeedPaging(String infoNeedPaging) {
        this.infoNeedPaging = infoNeedPaging;
    }

    public String getInfoPagingRegular() {
        return infoPagingRegular;
    }

    public void setInfoPagingRegular(String infoPagingRegular) {
        this.infoPagingRegular = infoPagingRegular;
    }

    public String getInfoEncoding() {
        return infoEncoding;
    }

    public void setInfoEncoding(String infoEncoding) {
        this.infoEncoding = infoEncoding;
    }

    public String getInfoDownloader() {
        return infoDownloader;
    }

    public void setInfoDownloader(String infoDownloader) {
        this.infoDownloader = infoDownloader;
    }

    public String getInfoPageType() {
        return infoPageType;
    }

    public void setInfoPageType(String infoPageType) {
        this.infoPageType = infoPageType;
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

    public String getGenerateBy() {
        return generateBy;
    }

    public void setGenerateBy(String generateBy) {
        this.generateBy = generateBy;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }
}

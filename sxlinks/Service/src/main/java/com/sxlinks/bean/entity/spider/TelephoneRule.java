package com.sxlinks.bean.entity.spider;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "telephone_rule")
@Data
public class TelephoneRule {

    //规则基本信息
    @Id
    @GeneratedValue
    private Long id;    //编号
    private String name;    //名称
    @Column(name="website_id")
    private Long websiteId;
    @Column(name="website_column_id")
    private Long websiteColumnId;
    private String description; //描述
    private String type;    //采集类型
    @Column(name="discern_type")
    private String discernType; //识别类型
    private String state;
    @Column(name="need_download_image")
    private String needDownloadImage;   //是否需要下载远程图片
    private String islive;      //是否存活
    @Column(name="last_check_live_time")
    private Date lastCheckLiveTime;     //检测存活时间
    private Double prSpider;
    @Column(name="rule_list_id")
    private Long ruleListId;
    @Column(name="rule_info_id")
    private Long ruleInfoId;
    @Column(name="pool_tactic_id")
    private Long poolTacticId;  //池策略ID
    @Column(name="generate_by")
    private String generateBy;  //生成方式
    private String belong;      //归属
    @Column(name="list_url")
    private String listUrl; //列表地址
    @Column(name="info_url")
    private String infoUrl;     //信息地址
    @Column(name="ls_page_start")
    private Integer lsPageStart;    //启始数
    @Column(name="ls_page_end")
    private Integer lsPageEnd;  //结束数

    //附属字段
    @Column(name="create_time")
    private Date createTime;    //创建时间
    @Column(name="create_by")
    private Long createBy;  //创建人
    @Column(name="modify_time")
    private Date modifyTime;    //创建时间
    @Column(name="modify_by")
    private Long modifyBy;  //创建人

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

    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public Long getWebsiteColumnId() {
        return websiteColumnId;
    }

    public void setWebsiteColumnId(Long websiteColumnId) {
        this.websiteColumnId = websiteColumnId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscernType() {
        return discernType;
    }

    public void setDiscernType(String discernType) {
        this.discernType = discernType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeedDownloadImage() {
        return needDownloadImage;
    }

    public void setNeedDownloadImage(String needDownloadImage) {
        this.needDownloadImage = needDownloadImage;
    }

    public String getIslive() {
        return islive;
    }

    public void setIslive(String islive) {
        this.islive = islive;
    }

    public Date getLastCheckLiveTime() {
        return lastCheckLiveTime;
    }

    public void setLastCheckLiveTime(Date lastCheckLiveTime) {
        this.lastCheckLiveTime = lastCheckLiveTime;
    }

    public Double getPrSpider() {
        return prSpider;
    }

    public void setPrSpider(Double prSpider) {
        this.prSpider = prSpider;
    }

    public Long getRuleListId() {
        return ruleListId;
    }

    public void setRuleListId(Long ruleListId) {
        this.ruleListId = ruleListId;
    }

    public Long getRuleInfoId() {
        return ruleInfoId;
    }

    public void setRuleInfoId(Long ruleInfoId) {
        this.ruleInfoId = ruleInfoId;
    }

    public Long getPoolTacticId() {
        return poolTacticId;
    }

    public void setPoolTacticId(Long poolTacticId) {
        this.poolTacticId = poolTacticId;
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

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public Integer getLsPageStart() {
        return lsPageStart;
    }

    public void setLsPageStart(Integer lsPageStart) {
        this.lsPageStart = lsPageStart;
    }

    public Integer getLsPageEnd() {
        return lsPageEnd;
    }

    public void setLsPageEnd(Integer lsPageEnd) {
        this.lsPageEnd = lsPageEnd;
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

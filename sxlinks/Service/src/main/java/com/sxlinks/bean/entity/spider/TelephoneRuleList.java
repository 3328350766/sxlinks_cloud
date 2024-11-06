package com.sxlinks.bean.entity.spider;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created  on 2019/5/29 0002.
 *
 * @author gaoliang
 */
@Entity
@Table(name = "telephone_rule_list")
@Data
public class TelephoneRuleList {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="name")
    private String listName;
    @Column(name="list_regular")
    private String listRegular;
    @Column(name="page_type")
    private String pageType;
    @Column(name="list_need_paging")
    private String listNeedPaging;
    @Column(name="list_paging_regular")
    private String listPagingRegular;
    @Column
    private String encoding;

    @Column(name="generate_by")
    private String generateBy;
    @Column
    private String belong;

    @Column(name="create_time")
    private Date createTime;
    @Column(name="create_by")
    private Long createBy;
    @Column(name="modify_time")
    private Date modifyTime;
    @Column(name="modify_by")
    private Long modifyBy;

    @Column(name="list_downloader")
    private String listDownloader;  //下载引擎

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListRegular() {
        return listRegular;
    }

    public void setListRegular(String listRegular) {
        this.listRegular = listRegular;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getListNeedPaging() {
        return listNeedPaging;
    }

    public void setListNeedPaging(String listNeedPaging) {
        this.listNeedPaging = listNeedPaging;
    }

    public String getListPagingRegular() {
        return listPagingRegular;
    }

    public void setListPagingRegular(String listPagingRegular) {
        this.listPagingRegular = listPagingRegular;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
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

    public String getListDownloader() {
        return listDownloader;
    }

    public void setListDownloader(String listDownloader) {
        this.listDownloader = listDownloader;
    }
}

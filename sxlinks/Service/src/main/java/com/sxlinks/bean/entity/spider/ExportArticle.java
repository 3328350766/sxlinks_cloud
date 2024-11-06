package com.sxlinks.bean.entity.spider;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "export_article")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ExportArticle{
    @Id
    @GeneratedValue
    private Long id;    //编号
    @Column
    private String title; //标题
    @Column
    private String content;    //内容
    @Column(name="image_list")
    private String imageList; //图片列表
    @Column(name="author")
    private String author; //作者
    @Column(name="exporter_id")
    private String exporterId;   //导出器
    @Column(name="data_source_id")
    private String dataSourceId;    //数据源
    @Column(name="article_id")
    private String articleId;  //采集文章ID
    @Column(name="keyword")
    private String keyword; //关键
    @Column(name="tag")
    private String tag; //tag标签
    @Column(name="clocking_time")
    private String clockingTime; //定时时间
    @Column(name="is_clocking")
    private String isClocking; //是否定时 0-直接发出 1-定时
    @Column(name="state")
    private String state; //状态
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExporterId() {
        return exporterId;
    }

    public void setExporterId(String exporterId) {
        this.exporterId = exporterId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getClockingTime() {
        return clockingTime;
    }

    public void setClockingTime(String clockingTime) {
        this.clockingTime = clockingTime;
    }

    public String getIsClocking() {
        return isClocking;
    }

    public void setIsClocking(String isClocking) {
        this.isClocking = isClocking;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

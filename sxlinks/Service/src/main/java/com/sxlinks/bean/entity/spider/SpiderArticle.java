package com.sxlinks.bean.entity.spider;


import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "spider_article")
@Data
@Proxy(lazy = false)
@EntityListeners(AuditingEntityListener.class)
public class SpiderArticle{
    @Id
    @GeneratedValue
    private Long id;    //编号
    @Column
    private String title;    //名称
    @Column
    private String content; //描述
    @Column(name="content_no_pic")
    private String contentNoPic;    //不带图片的内容（含HTML标记）
    @Column(name="content_text")
    private String contentText;     //纯文本内容
    @Column(name="content_text_split")
    private String contentTextSplit;    //分隔的纯文本
    @Column
    private String author;    //作者
    @Column(name = "spider_node_id")
    private Long spiderNodeId; //采集节点id
    @Column(name = "spider_id")
    private Long spiderId; //采集器id
    @Column(name = "spider_rule_id")
    private Long spiderRuleId;   //采集规则id
    @Column(name = "info_url")
    private String infoUrl;   //信息连接
    @Column
    private Date time;    //时间
    @Column
    private String click;   //阅读数
    @Column
    private String forward; //转发数
    @Column
    private String comment; //评论数
    @Column(name="info_encoding")
    private String infoEncoding;    //详细页编码
    @Column
    private String dataSource;		//数据来源
    @Column(name="collect_process")
    private String collectProcess; //采集端名称
    @Column(name="server_ip")
    private String serverIp;
    @Column(name="keyword")
    private String keyword; //关键
    @Column(name="tag")
    private String tag; //tag标签
    @Column(name = "create_time")
    private Date createTime;    //创建时间
    @Column(name = "create_by")
    private Long createBy;  //创建人
    @Column(name = "modify_time")
    private Date modifyTime;    //修改时间
    @Column(name = "modify_by")
    private Long modifyBy;  //修改人

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getSpiderNodeId() {
        return spiderNodeId;
    }

    public void setSpiderNodeId(Long spiderNodeId) {
        this.spiderNodeId = spiderNodeId;
    }

    public Long getSpiderId() {
        return spiderId;
    }

    public void setSpiderId(Long spiderId) {
        this.spiderId = spiderId;
    }

    public Long getSpiderRuleId() {
        return spiderRuleId;
    }

    public void setSpiderRuleId(Long spiderRuleId) {
        this.spiderRuleId = spiderRuleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getContentNoPic() {
        return contentNoPic;
    }

    public void setContentNoPic(String contentNoPic) {
        this.contentNoPic = contentNoPic;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getContentTextSplit() {
        return contentTextSplit;
    }

    public void setContentTextSplit(String contentTextSplit) {
        this.contentTextSplit = contentTextSplit;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInfoEncoding() {
        return infoEncoding;
    }

    public void setInfoEncoding(String infoEncoding) {
        this.infoEncoding = infoEncoding;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCollectProcess() {
        return collectProcess;
    }

    public void setCollectProcess(String collectProcess) {
        this.collectProcess = collectProcess;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
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
}

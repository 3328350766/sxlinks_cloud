package com.sxlinks.bean.entity.spider;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "spider_rule")
@Data
@EntityListeners(AuditingEntityListener.class)
@Proxy(lazy = false)
public class SpiderRule{
    @Id
    @GeneratedValue
    private Long id;    //编号
    @Column
    private String name;    //名称
    @Column
    private String description; //描述
    @Column
    private String type;    //采集类型
    @Column(name="discern_type")
    private String discernType; //识别类型
    @Column(name="need_paging")
    private Integer needPaging; //是否需要分页
    @Column(name="paging_regular")
    private String pagingRegular;   //分页规则
    @Column(name="title_regular")
    private String titleRegular;    //标题规则
    @Column(name="content_regular")
    private String contentRegular;  //内容规则
    @Column(name="time_regular")
    private String timeRegular;     //时间规则
    @Column(name="author_regular")
    private String authorRegular;   //作者规则
    @Column(name="attachment_regular")
    private String attachmentRegular;   //附件规则
    @Column(name="list_url")
    private String listUrl; //列表地址
    @Column(name="list_url_type")
    private String listUrlType; //列表类型
    @Column(name="list_discern_type")
    private String listDiscernType; //列表识别类型
    @Column(name="list_regular")
    private String listRegular; //列表规则
    @Column(name="list_need_paging")
    private String listNeedPaging;  //列表是否需要分页
    @Column(name="ls_page_start")
    private String lsPageStart;  //分页起始页
    @Column(name="ls_page_end")
    private String lsPageEnd;  //分页结束页
    @Column(name="list_paging_regular")
    private String listPagingRegular;   //列表分页规则
    @Column(name="state")
    private String state;
    @Column(name="info_url")
    private String infoUrl;     //信息地址
    @Column(name="need_download_image")
    private String needDownloadImage;   //是否需要下载远程图片
    @Column(name="local_image_path")
    private String localImagePath;       //本地图片保存路径
    @Column(name="url_image_path")
    private String urlImagePath;    //图片URL访问地址
    @Column(name="info_replace_regular")
    private String infoReplaceRegular;  //信息替换规则
    @Column(name="list_downloader")
    private String listDownloader;    //列表下载器
    @Column(name="info_downloader")
    private String infoDownloader;  //信息下载器
    @Column(name="list_encoding")
    private String listEncoding;    //列表编码
    @Column(name="info_encoding")
    private String infoEncoding;  //信息编码
    @Column(name="islive")
    private String islive;     //存活状态
    @Column(name="last_check_live_time")
    private Date lastCheckLiveTime;     //检测存活时间

    @Column(name="click_regular")
    private String clickRegular;
    @Column(name="forward_regular")
    private String forwardRegular;
    @Column(name="comment_regular")
    private String commentRegular;
    @Column(name = "create_time")
    private Date createTime;    //创建时间
    @Column(name = "create_by")
    private Long createBy;  //创建人
    @Column(name = "modify_time")
    private Date modifyTime;    //修改时间
    @Column(name = "modify_by")
    private Long modifyBy;  //修改人

    public String getInfoUrl() {
        return infoUrl;
    }

    public String getNeedDownloadImage() {
        return needDownloadImage;
    }

    public void setNeedDownloadImage(String needDownloadImage) {
        this.needDownloadImage = needDownloadImage;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public String getUrlImagePath() {
        return urlImagePath;
    }

    public void setUrlImagePath(String urlImagePath) {
        this.urlImagePath = urlImagePath;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public Integer getNeedPaging() {
        return needPaging;
    }

    public void setNeedPaging(Integer needPaging) {
        this.needPaging = needPaging;
    }

    public String getPagingRegular() {
        return pagingRegular;
    }

    public void setPagingRegular(String pagingRegular) {
        this.pagingRegular = pagingRegular;
    }

    public String getTitleRegular() {
        return titleRegular;
    }

    public void setTitleRegular(String titleRegular) {
        this.titleRegular = titleRegular;
    }

    public String getContentRegular() {
        return contentRegular;
    }

    public void setContentRegular(String contentRegular) {
        this.contentRegular = contentRegular;
    }

    public String getTimeRegular() {
        return timeRegular;
    }

    public void setTimeRegular(String timeRegular) {
        this.timeRegular = timeRegular;
    }

    public String getAuthorRegular() {
        return authorRegular;
    }

    public void setAuthorRegular(String authorRegular) {
        this.authorRegular = authorRegular;
    }

    public String getAttachmentRegular() {
        return attachmentRegular;
    }

    public void setAttachmentRegular(String attachmentRegular) {
        this.attachmentRegular = attachmentRegular;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getListUrlType() {
        return listUrlType;
    }

    public void setListUrlType(String listUrlType) {
        this.listUrlType = listUrlType;
    }

    public String getListDiscernType() {
        return listDiscernType;
    }

    public void setListDiscernType(String listDiscernType) {
        this.listDiscernType = listDiscernType;
    }

    public String getListRegular() {
        return listRegular;
    }

    public void setListRegular(String listRegular) {
        this.listRegular = listRegular;
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

    public String getInfoReplaceRegular() {
        return infoReplaceRegular;
    }

    public void setInfoReplaceRegular(String infoReplaceRegular) {
        this.infoReplaceRegular = infoReplaceRegular;
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

    public String getInfoDownloader() {
        return infoDownloader;
    }

    public void setInfoDownloader(String infoDownloader) {
        this.infoDownloader = infoDownloader;
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

    public String getListEncoding() {
        return listEncoding;
    }

    public void setListEncoding(String listEncoding) {
        this.listEncoding = listEncoding;
    }

    public String getInfoEncoding() {
        return infoEncoding;
    }

    public void setInfoEncoding(String infoEncoding) {
        this.infoEncoding = infoEncoding;
    }

    public String getClickRegular() {
        return clickRegular;
    }

    public void setClickRegular(String clickRegular) {
        this.clickRegular = clickRegular;
    }

    public String getForwardRegular() {
        return forwardRegular;
    }

    public void setForwardRegular(String forwardRegular) {
        this.forwardRegular = forwardRegular;
    }

    public String getCommentRegular() {
        return commentRegular;
    }

    public void setCommentRegular(String commentRegular) {
        this.commentRegular = commentRegular;
    }

    public String getLsPageStart() {
        return lsPageStart;
    }

    public void setLsPageStart(String lsPageStart) {
        this.lsPageStart = lsPageStart;
    }

    public String getLsPageEnd() {
        return lsPageEnd;
    }

    public void setLsPageEnd(String lsPageEnd) {
        this.lsPageEnd = lsPageEnd;
    }
}

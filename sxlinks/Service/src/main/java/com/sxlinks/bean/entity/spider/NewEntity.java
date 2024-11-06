package com.sxlinks.bean.entity.spider;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "spider_rule")
@Data
@EntityListeners(AuditingEntityListener.class)
public class NewEntity extends BaseEntity {
    @Column
    private Long id;    //编号
    @Column(name="name")
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
    @Column(name="list_paging_Regular")
    private String listPagingRegular;   //列表分页规则

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
}

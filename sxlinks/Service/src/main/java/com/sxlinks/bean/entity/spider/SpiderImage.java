package com.sxlinks.bean.entity.spider;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "spider_image")
@Data
@EntityListeners(AuditingEntityListener.class)
public class SpiderImage extends BaseEntity {
    @Column
    private Long id;    //编号
    @Column(name="title")
    private String title;    //名称
    @Column
    private String name;    //名称
    @Column
    private String content; //描述
    @Column
    private String type;    //类型
    @Column(name="image_list")
    private String imageList; //图片列表
    @Column(name="description_list")
    private String descriptionList; //描述列表
    @Column(name="filesize_list")
    private String filesizeList;   //文件大小列表
    @Column(name="spider_id")
    private Long spiderId;    //导出器ID
    @Column(name="spider_rule_id")
    private Long spiderRuleId;  //数据源
    @Column(name="spider_node_id")
    private Long spiderNodeId;     //采集图片ID

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public String getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(String descriptionList) {
        this.descriptionList = descriptionList;
    }

    public String getFilesizeList() {
        return filesizeList;
    }

    public void setFilesizeList(String filesizeList) {
        this.filesizeList = filesizeList;
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

    public Long getSpiderNodeId() {
        return spiderNodeId;
    }

    public void setSpiderNodeId(Long spiderNodeId) {
        this.spiderNodeId = spiderNodeId;
    }
}

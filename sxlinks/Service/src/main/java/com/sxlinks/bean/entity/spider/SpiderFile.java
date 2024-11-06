package com.sxlinks.bean.entity.spider;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "spider_file")
@Data
@EntityListeners(AuditingEntityListener.class)
public class SpiderFile extends BaseEntity {
    @Column
    private Long id;    //编号
    @Column(name="type")
    private String type;    //名称
    @Column
    private String title; //描述
    @Column
    private String name;    //名称
    @Column
    private String content;    //采集内容
    @Column(name="filepath")
    private String filepath; //文件路径
    @Column(name="url_source")
    private String urlSource; //url源路径
    @Column(name="filesize")
    private String filesize;   //文件大小
    @Column(name="spider_node_id")
    private Long spiderNodeId;    //采集节点
    @Column(name="spider_id")
    private Long spiderId;  //采集器
    @Column(name="spider_rule_id")
    private Long spiderRuleId;     //采集规则

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getUrlSource() {
        return urlSource;
    }

    public void setUrlSource(String urlSource) {
        this.urlSource = urlSource;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
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
}

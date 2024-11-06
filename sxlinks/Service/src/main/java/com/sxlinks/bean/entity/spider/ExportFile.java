package com.sxlinks.bean.entity.spider;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "export_file")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ExportFile extends BaseEntity {
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
    @Column(name="filepath")
    private String filepath; //文件路径
    @Column(name="filesize")
    private String filesize; //文件大小
    @Column(name="exporter_id")
    private String exporterId;   //导出器
    @Column(name="data_source_id")
    private String dataSourceId;    //数据源
    @Column(name="file_id")
    private String fileId;  //采集文件

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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}

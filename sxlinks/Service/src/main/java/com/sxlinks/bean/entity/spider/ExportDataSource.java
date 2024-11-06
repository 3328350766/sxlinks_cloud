package com.sxlinks.bean.entity.spider;

import com.sxlinks.bean.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;


@Entity
@Table(name = "export_data_source")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ExportDataSource extends BaseEntity {
    @Column
    private Long id;    //编号
    @Column(name="name")
    private String name;    //名称
    @Column
    private String description; //描述
    @Column(name="data_type")
    private String dataType;    //数据源类型
    @Column(name="db_type")
    private String dbType; //数据库类型
    @Column(name="url")
    private String url; //连接字符串
    @Column(name="username")
    private String username;   //用户
    @Column(name="password")
    private String password;    //密码
    @Column(name="state")
    private String state;    //状态
    @Column(name="website_db_type")
    private String websiteDbType;   //网站数据类型

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWebsiteDbType() {
        return websiteDbType;
    }

    public void setWebsiteDbType(String websiteDbType) {
        this.websiteDbType = websiteDbType;
    }
}

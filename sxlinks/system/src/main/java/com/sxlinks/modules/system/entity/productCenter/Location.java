package com.sxlinks.modules.system.entity.productCenter;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name="location")
public class Location {
    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
    private Long id;    //编号
    @ApiModelProperty(value = "名称")
    private String name;    //名称
    @ApiModelProperty(value = "编号")
    private String code;    //编号
    @ApiModelProperty(value = "详细地址")
    private String address;    //详细地址
    @ApiModelProperty(value = "坐标类型(wgs84:gps标准坐标系-wgs84 gcj-02:火星坐标系 other:其它)")
    private String type;    //坐标类型(wgs84:gps标准坐标系-wgs84 gcj-02:火星坐标系 other:其它)

    @ApiModelProperty(value = "绑定设备id")
    private Long deviceId;    //绑定设备id
    @ApiModelProperty(value = "绑定监控id")
    private Long cameraId;    //绑定监控id
    @ApiModelProperty(value = "经度")
    private String lng; //经度
    @ApiModelProperty(value = "纬度")
    private String lat; //纬度

    @ApiModelProperty(value = "状态")
    private String state;    //状态
    @ApiModelProperty(value = "删除标志")
    private Integer delFlag;//删除标志 0:未删除 1 删除
    /**创建人*/
    private String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**更新人*/
    private String modifyBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}

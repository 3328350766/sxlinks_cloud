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
@Table(name="device_camera")
public class DeviceCamera {
    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
    private Long id;    //编号
    @ApiModelProperty(value = "编码")
    private String code; //编码
    @ApiModelProperty(value = "序列号")
    private String serial;  //序列号
    @ApiModelProperty(value = "名称")
    private String name;    //名称
    @ApiModelProperty(value = "排序")
    private String num;    //排序
    @ApiModelProperty(value = "类型(1-摄像头 2-卡口相机 3-消防图探 4-可视对讲机)")
    private String type;    //类型(1-摄像头 2-卡口相机 3-消防图探 4-可视对讲机)
    @ApiModelProperty(value = "状态")
    private String state;    //状态
    @ApiModelProperty(value = "ip地址")
    private String ip;  //ip地址
    @ApiModelProperty(value = "端口")
    private String port;    //端口
    @ApiModelProperty(value = "用户")
    private String username;    //用户
    @ApiModelProperty(value = "密码")
    private String password;    //密码
    @ApiModelProperty(value = "协议0-rtsp 1-hls")
    private String protocol;    //协议0-rtsp 1-hls
    @ApiModelProperty(value = "流媒体地址")
    private String rtspUrl;     //流媒体地址
    @ApiModelProperty(value = "上线时间")
    private String lastTime; //上线时间
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

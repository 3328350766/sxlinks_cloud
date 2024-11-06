package com.sxlinks.modules.system.entity.productCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="product")
public class Product {
    @TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
    private Long id;
    @ApiModelProperty(value = "产品编码")
    private String productCode; //产品编码
    @ApiModelProperty(value = "产品名称")
    private String productName; //产品名称
    @ApiModelProperty(value = "传输协议")
    private String transportList;//支持的网络传输协议列表 MQTT
    @ApiModelProperty(value = "协议编码")
    private String protocolCode;//关联的消息协议编码
    @ApiModelProperty(value = "协议类型")
    private String protocolType;//关联的消息协议编码
    @ApiModelProperty(value = "节点类型")
    private String nodeType;//节点类型 直连设备DIRECT/子设备SUB/网关设备GATEWAY
    @ApiModelProperty(value = "网络类型")
    private String networkType; //网络类型(wifi-wifi无线,2/3/4/5G-蜂窝,NB-Iot:窄带,Rj-45:以太网)
    @ApiModelProperty(value = "描述")
    private String productDesc; //描述
    @ApiModelProperty(value = "logo地址")
    private String logUrl; //logo地址

    private Date updateTime;

    private Integer delFlag;//0:未删除 1 删除
    @ApiModelProperty(value = "发布状态")
    private Integer status;//发布状态 0:草稿 1:发布 2:暂停


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

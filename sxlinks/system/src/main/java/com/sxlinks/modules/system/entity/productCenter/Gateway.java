package com.sxlinks.modules.system.entity.productCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
@TableName("gateway")
public class Gateway implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**机构/部门名称*/
	@Excel(name="名称",width=256)
	private String name;
	/**英文名*/
	@Excel(name="编码",width=64)
	private String code;
	/**英文名*/
	private String description;

	private String server;
	private String port;
	private String type; //网关类型
	private String nodeType; //节点类型(device-设备 gateway-网关 repeater-中继器)
	private String network; //网络(wifi-wifi无线,2/3/4/5G-蜂窝,NB-Iot:窄带,Rj-45:以太网)
	private String protocol; //协议(modbus-tcp,opc-ua,http,mqtt,coap,lwm2m)
	private String onlineState;

	/**缩写*/
	@Excel(name="状态",width=64)
	@ApiModelProperty(value = "状态")
	private String state;
	/**排序*/
	@Excel(name="分类id")
	@ApiModelProperty(value = "分类id")
	private Long typeId;
	/**描述*/

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

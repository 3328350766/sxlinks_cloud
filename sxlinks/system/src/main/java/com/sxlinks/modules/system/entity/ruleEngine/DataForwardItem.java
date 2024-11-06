package com.sxlinks.modules.system.entity.ruleEngine;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.sxlinks.common.aspect.annotation.Dict;
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
@TableName("data_forward_item")
public class DataForwardItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**机构/部门名称*/
	@ApiModelProperty(value = "名称")
	private String name;    //名称
	@ApiModelProperty(name="转发id")
	private Long dataForwardId; //转发id
	@ApiModelProperty(name="转发编码")
	private String dataForwardCode; //转发编码
	@ApiModelProperty(value = "产品id")
	private Long productId;
	@ApiModelProperty(name="产品编码")
	private String productCode; //产品编码
	@ApiModelProperty(name="产品名称")
	private String productName; //产品名称
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	@ApiModelProperty(name="设备编码")
	private String deviceCode; //设备编码
	@ApiModelProperty(name="设备名称")
	private String deviceName; //设备名称

	@ApiModelProperty(value = "类型")
	private String type;    //类型(http:http转发)
	@ApiModelProperty(value = "httpUrl")
	private String httpUrl;    //httpUrl
	@ApiModelProperty(value = "数据值")
	private String httpMethod;    //请求方式:get,post
	@ApiModelProperty(value = "数据条件")
	private String httpHeader;    //header
	@ApiModelProperty(value = "数据值")
	private String httpBody;    //body

	@ApiModelProperty(value = "状态")
	private String state;    //状态

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

package com.sxlinks.modules.system.entity.dataCenter;

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
@TableName("down_data_history")
public class DownDataHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@ApiModelProperty(value = "产品id")
	private Long productId;
	@ApiModelProperty(value = "产品编码")
	private String productCode;
	@ApiModelProperty(value = "产品名称")
	private String productName;
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	@ApiModelProperty(value = "长度")
	private String length;
	@ApiModelProperty(value = "内容")
	private String content;
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

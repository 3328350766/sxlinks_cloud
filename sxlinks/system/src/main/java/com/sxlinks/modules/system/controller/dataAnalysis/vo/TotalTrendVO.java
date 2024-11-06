package com.sxlinks.modules.system.controller.dataAnalysis.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
public class TotalTrendVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@ApiModelProperty(value = "产品编码")
	private String productCode;//设备编码
	@ApiModelProperty(value = "产品名称")
	private String productName;//设备名称
	@ApiModelProperty(value = "启用状态 0:未启用 1:启用")
	private Integer enableStatus;//启用状态 0:未启用 1:启用
	@ApiModelProperty(value = "设备数量")
	private Integer deviceCount;
	@ApiModelProperty(value = "设备激活数量")
	private Integer deviceEnableCount;
	@ApiModelProperty(value = "设备在线数量")
	private Integer deviceOnlineCount;
	@ApiModelProperty(value = "设备离线数量")
	private Integer deviceOfflineCount;
}

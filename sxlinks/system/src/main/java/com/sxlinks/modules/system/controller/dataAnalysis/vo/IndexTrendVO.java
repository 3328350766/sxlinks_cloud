package com.sxlinks.modules.system.controller.dataAnalysis.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 指标趋势表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
public class IndexTrendVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;//设备编码
	@ApiModelProperty(value = "设备名称")
	private String deviceName;//设备名称
	@ApiModelProperty(value = "启用状态 0:未启用 1:启用")
	private Integer enableStatus;//启用状态 0:未启用 1:启用
	@ApiModelProperty(value = "在线状态 0:离线 1:在线")
	private Integer activeStatus;
	@ApiModelProperty(value = "属性数量")
	private Integer propertyCount;
	@ApiModelProperty(value = "属性激活数量")
	private Integer propertyEnableCount;
	@ApiModelProperty(value = "最后一次上线时间")
	private Date lastOnlineTime;
}

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
 * 项目表
 * <p>
 * 
 * @Author baba
 * @Since  2022-12-30
 */
@Data
@TableName("project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@Excel(name="名称",width=256)
	private String name;
	@Excel(name="编码",width=64)
	private String code;
	@Excel(name="描述",width=64)
	@ApiModelProperty(value = "描述")
	private String description;
	@Excel(name="省",width=64)
	@ApiModelProperty(value = "省")
	private String province;
	@ApiModelProperty(value = "省(中文)")
	private String provinceName;
	@Excel(name="市",width=64)
	@ApiModelProperty(value = "市")
	private String city;
	@ApiModelProperty(value = "市(中文)")
	private String cityName;
	@Excel(name="区/县",width=64)
	@ApiModelProperty(value = "区/县")
	private String county;
	@ApiModelProperty(value = "区/县(中文)")
	private String countyName;
	@Excel(name="坐标类型",width=64)
	@ApiModelProperty(value = "坐标类型")
	private String locationType;
	@Excel(name="经度",width=64)
	@ApiModelProperty(value = "经度")
	private String lng;
	@Excel(name="纬度",width=64)
	@ApiModelProperty(value = "纬度")
	private String lat;
	@Excel(name="纬度")
	@ApiModelProperty(value = "序号")
	private Long sortNo;
	/**缩写*/
	@Excel(name="状态",width=64)
	@ApiModelProperty(value = "状态")
	private String state;
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

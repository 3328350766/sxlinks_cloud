package com.sxlinks.modules.system.entity.energyCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@TableName("safe_divide_data")
public class SafeDivideData implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	@ApiModelProperty(value = "id")
	private String id;
	@ApiModelProperty(value = "分组id")
	private String divideId;
	@ApiModelProperty(value = "断路器")
	private String duanluqi;
	@ApiModelProperty(value = "烟雾")
	private String yanwu;
	@ApiModelProperty(value = "燃气")
	private String ranqi;
	@ApiModelProperty(value = "水漫")
	private Double shuiman;
	@ApiModelProperty(value = "声光")
	private Long shengguang;
	@ApiModelProperty(value = "门窗")
	private Long menchuang;

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

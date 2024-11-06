package com.sxlinks.modules.system.entity.productCenter.broadcast;

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
@TableName("broadcast_gateway")
public class BroadcastGateway implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	private String name;

	private String code;

	private String description;


	private String isWebsocket;

	private String websocketServer;

	private String websocketPort;


	private String isHttp;

	private String httpServer;

	private String httpPort;

	private String httpMethod;

	private String httpNeedLogin;

	private String httpUsername;

	private String httpPassword;

	private Long firmId;

	private Long projectId;

	/**缩写*/
	@Excel(name="状态",width=64)
	@ApiModelProperty(value = "状态")
	private String state;

	private Integer delFlag;

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

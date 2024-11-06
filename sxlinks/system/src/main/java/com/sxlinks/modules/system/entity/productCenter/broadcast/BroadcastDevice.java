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
@TableName("broadcast_device")
public class BroadcastDevice implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type= IdType.ASSIGN_ID) //采用string非自增，非mybatis雪花巨长自增
	private String id;

	private Integer indexId;    //终端id
	private String ip;    //ip地址
	private String mask; //掩码
	private String mac;  //mac地址
	private String code;    //
	private String name;  //设备名称

	private Integer type;   //类型
	private String typeName;    //类型名称

	private String version;    //版本
	private Integer timeMode;   //时间模式
	private Integer volume; //音量
	private String onlineState;  //在线状态

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

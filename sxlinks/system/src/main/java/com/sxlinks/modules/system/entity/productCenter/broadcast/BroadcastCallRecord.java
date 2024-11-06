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
@TableName("broadcast_call_record")
public class BroadcastCallRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type= IdType.ASSIGN_ID) //采用string非自增，非mybatis雪花巨长自增
	private String id;

	private String deviceId;
	private String deviceName;

	private String caller;    //主叫
	private String callie; //被叫
	private Long duration;  //时长
	private String hangupCause;    //挂机原因
	private String ip;  //ip地址

	//呼叫记录文件
	private String fileUrl; //文件url-绝对
	private String filePath; //文件路径-物理

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

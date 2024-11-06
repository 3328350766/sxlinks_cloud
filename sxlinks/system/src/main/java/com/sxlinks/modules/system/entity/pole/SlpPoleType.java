package com.sxlinks.modules.system.entity.pole;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 灯杆类型对象 slp_pole_type
 *
 * @author wll
 * @date 2022-12-13
 */
@Data
@ApiModel(value = "灯柱类型")
public class SlpPoleType {
    private static final long serialVersionUID = 1L;

    /**
     * 灯杆类型id
     */
    @TableId
    @ApiModelProperty(value = "灯杆类型id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String poleTypeId;

    /**
     * 灯杆类型名称
     */
    //@Excel(name = "灯杆类型名称")
    @ApiModelProperty(value = "灯杆类型名称")
    private String poleTypeName;

    /**
     * 灯杆类型图片
     */
    //@Excel(name = "灯杆类型图片")
    @ApiModelProperty(value = "灯杆类型图片")
    private String poleTypeImgUrl;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}

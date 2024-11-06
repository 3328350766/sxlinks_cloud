package com.sxlinks.modules.system.entity.pole;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 灯杆信息对象 slp_pole
 *
 * @author wll
 * @date 2022-12-13
 */
@Data
@ApiModel(value = "灯柱")
public class SlpPole {
    private static final long serialVersionUID = 1L;

    /**
     * 灯杆主键id
     */
    @ApiModelProperty(value = "灯杆主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String poleId;

    /**
     * 灯杆名称
     */
    @ApiModelProperty(value = "灯杆名称")
    //@Excel(name = "灯杆名称")
    private String poleName;

    /**
     * 灯杆类型id
     */
    @ApiModelProperty(value = "灯杆类型id")
    //@Excel(name = "灯杆类型id")
    private String poleTypeId;

    /**
     * 灯杆所属项目id
     */
    @ApiModelProperty(value = "灯杆所属项目id")
    //@Excel(name = "灯杆所属项目id")
    private Long poleProjectId;

    /**
     * 灯杆尺寸
     */
    @ApiModelProperty(value = "灯杆尺寸")
    //@Excel(name = "灯杆尺寸")
    private String poleSize;

    /**
     * 灯杆图片
     */
    @ApiModelProperty(value = "灯杆图片")
    //@Excel(name = "灯杆图片")
    private String poleImg;

    /**
     * 灯杆所在地址
     */
    @ApiModelProperty(value = "灯杆所在地址")
    //@Excel(name = "灯杆所在地址")
    private String poleAddress;

    /**
     * 灯杆所在经度
     */
    @ApiModelProperty(value = "灯杆所在经度")
    //@Excel(name = "灯杆所在经度")
    private String poleLng;

    /**
     * 灯杆所在维度
     */
    @ApiModelProperty(value = "灯杆所在维度")
    //@Excel(name = "灯杆所在维度")
    private String poleLat;

    /**
     * 是否启用 0启用 1停用
     */
    @ApiModelProperty(value = "是否启用 0启用 1停用")
    //@Excel(name = "是否启用 0启用 1停用")
    private String poleEnable;

    /**
     * 删除标记 0未删除 1已删除
     */
    @ApiModelProperty(value = "删除标记 0未删除 1已删除")
    private String delFlag;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /**
     * 灯杆类型名称
     */
    @ApiModelProperty(value = "灯杆类型名称")
    private String poleTypeName;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

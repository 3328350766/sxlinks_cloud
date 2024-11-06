package com.sxlinks.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysScope implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父级行政代码
     */
    @ApiModelProperty(value = "父级行政代码")
    private String parentCode;

    /**
     * 行政代码
     */
    @ApiModelProperty(value = "行政代码")
    private String scopeCode;

    /**
     * 邮政编码
     */
    @ApiModelProperty(value = "邮政编码")
    private String zipCode;

    /**
     * 区号
     */
    @ApiModelProperty(value = "区号")
    private String cityCode;

    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称")
    private String scopeName;

    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    private String shortName;

    /**
     * 组合名
     */
    @ApiModelProperty(value = "组合名")
    private String mergerName;

    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音")
    private String pinyin;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal lng;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;
}

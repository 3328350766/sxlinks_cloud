package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@ApiModel(value = "小程序评估记录基础信息")
public class MiniProjectEvaluateRecordRespVo implements Serializable {

    @ApiModelProperty(value = "记录ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "评估日期", example = "2021-09-23")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "地址", example = "汶川县映秀镇")
    private String address;

    @ApiModelProperty(value = "详细地址", example = "北湖路")
    private String detailAddress;

    @ApiModelProperty(value = "评估人", example = "张晓刚")
    private String evaluateUserName;

    @ApiModelProperty(value = "评估人所在小组名称", example = "第一组")
    private String evaluateUserGroupName;

    @ApiModelProperty(value = "评估结果选项(0:禁止使用 1:可以使用 2:限制使用)", example = "1")
    private Integer evaluateResult;

}

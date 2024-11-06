package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/21
 */
@Data
@ApiModel(value = "小程序地震项目专家组任务信息")
public class MiniProjectExpertGroupTaskRespVo implements Serializable {

    @ApiModelProperty(value = "任务Id", example = "摸底排查")
    private Long id;

    @ApiModelProperty(value = "任务名称", example = "摸底排查")
    private String taskName;

    @ApiModelProperty(value = "当地联系人名称", example = "张三")
    private String localContact;
    @ApiModelProperty(value = "当地联系人职务", example = "技术员")
    private String localContactPost;
    @ApiModelProperty(value = "当地联系人电话", example = "13000800900")
    private String localContactPhone;

    @ApiModelProperty(value = "当地住建联系人名称", example = "王武")
    private String localHousingContact;
    @ApiModelProperty(value = "当地住建联系人职务", example = "王武")
    private String localHousingContactPost;
    @ApiModelProperty(value = "当地住建联系人电话", example = "13000800901")
    private String localHousingContactPhone;

    @ApiModelProperty(value = "评估范围", example = "[]")
    private List<MiniProjectExpertGroupTaskStatusRespVo> scopeList;


}

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
@ApiModel(value = "小程序地震项目专家组基础信息")
public class MiniProjectExpertGroupRespVo implements Serializable {

    @ApiModelProperty(value = "专家组ID", example = "1")
    private Long groupId;

    @ApiModelProperty(value = "专家组名称", example = "飞虎大部队")
    private String groupName;

    @ApiModelProperty(value = "专家组组长名称", example = "王武")
    private String groupLeaderName;

    @ApiModelProperty(value = "专家组组长手机号", example = "13000900900")
    private String groupLeaderPhone;

    @ApiModelProperty(value = "评估范围", example = "[]")
    private List<MiniProjectExpertGroupTaskStatusRespVo> scopeList;

    @ApiModelProperty(value = "专家组类别(0:住房 1:建筑 2：设施)", example = "市政设施组")
    private Integer type;

    @ApiModelProperty(value = "今日工作完成状态(0:未完成 1:已完成)", example = "1")
    private int workStatus;
}

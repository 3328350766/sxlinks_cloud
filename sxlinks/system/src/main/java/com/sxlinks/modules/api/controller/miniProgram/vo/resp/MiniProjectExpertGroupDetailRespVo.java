package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "小程序地震项目专家组详情信息")
public class MiniProjectExpertGroupDetailRespVo extends MiniProjectExpertGroupRespVo implements Serializable {

    @ApiModelProperty(value = "项目名称", example = "汶川大地震")
    private String projectName;

    @ApiModelProperty(value = "专家组任务信息列表", example = "[]")
    private List<MiniProjectExpertGroupTaskRespVo> groupTaskList;

    @ApiModelProperty(value = "专家组成员信息列表", example = "[]")
    private List<MiniProjectExpertUserRespVo> groupUserList;
}

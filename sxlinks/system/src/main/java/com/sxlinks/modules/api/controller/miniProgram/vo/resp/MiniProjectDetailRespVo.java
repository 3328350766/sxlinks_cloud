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
@ApiModel(value = "小程序地震项目详情信息")
public class MiniProjectDetailRespVo extends MiniProjectRespVo implements Serializable {

    @ApiModelProperty(value = "负责人名称", example = "张三")
    private String principalName;

    @ApiModelProperty(value = "负责人电话", example = "13000800900")
    private String principalPhone;

    @ApiModelProperty(value = "住房总数", example = "100")
    private int totalHousesNum;

    @ApiModelProperty(value = "已评估住房数", example = "26")
    private int evaluatedHousesNum;

    @ApiModelProperty(value = "已评估建筑数", example = "26")
    private int evaluatedBuildingsNum;

    @ApiModelProperty(value = "已评估设施数", example = "26")
    private int evaluatedFacilitiesNum;

    @ApiModelProperty(value = "专家组信息", example = "[]")
    private List<MiniProjectExpertGroupRespVo> projectExpertGroupList;

}

package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "小程序住房评估记录信息")
public class MiniProjectEvaluateHouseRecordRespVo extends MiniProjectEvaluateRecordRespVo implements Serializable {

    @ApiModelProperty(value = "户主", example = "张三")
    private String ownerName;

    @ApiModelProperty(value = "户数", example = "100")
    private Integer num;

    @ApiModelProperty(value = "住房类型(1:单户 2:多户)", example = "1")
    private Integer type;

    @ApiModelProperty(value = "多户建筑名称", example = "楼房")
    private String name;

}

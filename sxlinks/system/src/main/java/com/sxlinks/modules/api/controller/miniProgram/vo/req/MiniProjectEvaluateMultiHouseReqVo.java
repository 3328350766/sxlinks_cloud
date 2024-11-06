package com.sxlinks.modules.api.controller.miniProgram.vo.req;

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
@ApiModel(value = "小程序多户评估参数信息")
public class MiniProjectEvaluateMultiHouseReqVo extends MiniProjectEvaluateReqVo implements Serializable {

    @ApiModelProperty(value = "建筑名称", example = "映秀小区A栋")
    private String name;

    @ApiModelProperty(value = "户数", example = "100")
    private Integer num;

}

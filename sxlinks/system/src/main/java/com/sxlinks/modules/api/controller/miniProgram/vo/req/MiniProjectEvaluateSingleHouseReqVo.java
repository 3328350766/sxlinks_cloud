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
@ApiModel(value = "小程序单户评估参数信息")
public class MiniProjectEvaluateSingleHouseReqVo extends MiniProjectEvaluateReqVo implements Serializable {

    @ApiModelProperty(value = "业主姓名", example = "北湖路")
    private String ownerName;

    @ApiModelProperty(value = "业主身份证", example = "北湖路")
    private String ownerIdCard;

}

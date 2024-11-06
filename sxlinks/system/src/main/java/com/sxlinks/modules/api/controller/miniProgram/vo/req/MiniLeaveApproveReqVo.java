package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@ApiModel(value = "小程序请假审批参数信息")
public class MiniLeaveApproveReqVo implements Serializable {

    @ApiModelProperty(value = "审批类型(0:拒绝 1:通过)", example = "1")
    private Integer ops;

    @ApiModelProperty(value = "备注信息", example = "注意身体")
    private String remark;

}

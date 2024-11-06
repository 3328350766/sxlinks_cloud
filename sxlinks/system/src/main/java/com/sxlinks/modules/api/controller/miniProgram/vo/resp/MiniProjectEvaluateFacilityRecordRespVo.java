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
@ApiModel(value = "小程序市政设施评估记录信息")
public class MiniProjectEvaluateFacilityRecordRespVo extends MiniProjectEvaluateRecordRespVo implements Serializable {

    @ApiModelProperty(value = "设施类型(4:填埋场 5:焚烧场 6:垃圾中转站 7:城市公厕 8:排水设施 9:供水设施 10:燃气设施 11:市政基础设施)", example = "1")
    private Integer type;

}

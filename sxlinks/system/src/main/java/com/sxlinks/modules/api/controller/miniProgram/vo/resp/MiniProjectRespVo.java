package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/20
 */
@Data
@ApiModel(value = "小程序地震项目基础信息")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniProjectRespVo implements Serializable {

    @ApiModelProperty(value = "地震项目ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "地震项目名称", example = "汶川大地震")
    private String name;

    @ApiModelProperty(value = "地震范围", example = "[]")
    private List<MiniScopeInfoRespVo> scopeList;

}

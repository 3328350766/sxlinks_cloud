package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@ApiModel(value = "小程序设施评估基础参数信息")
public class MiniProjectEvaluateFacilityReqVo implements Serializable {

    @ApiModelProperty(value = "设施类型(3:公共建筑 4:填埋场 5:焚烧场 6:垃圾中转站 7:城市公厕 8:排水设施 9:供水设施 10:燃气设施 11:市政基础设施)", example = "1")
    private Integer type;

    @ApiModelProperty(value = "日期", example = "2021-09-23")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "地址", example = "汶川县映秀镇")
    private String address;

    @ApiModelProperty(value = "详细地址", example = "北湖路")
    private String detailAddress;

    @ApiModelProperty(value = "基本概况", example = "楼房3层,3x3结构,已居住5年")
    private String info;

    @ApiModelProperty(value = "评估结果选项(0:禁止使用 1:可以使用 2:限制使用)", example = "1")
    private Integer evaluateResult;

    @ApiModelProperty(value = "评估意见", example = "大梁加固后可以继续居住")
    private String evaluateOpinion;

    @ApiModelProperty(value = "手写签名", example = "xxx")
    private String autograph;

}

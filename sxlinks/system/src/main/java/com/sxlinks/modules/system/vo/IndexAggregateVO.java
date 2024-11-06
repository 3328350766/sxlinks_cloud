package com.sxlinks.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;
import java.util.List;
@Data
public class IndexAggregateVO  implements Serializable {
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "指定日期(格式:2022-6-14)")
    String startDate;
//    @ApiModelProperty(value = "聚合条件列表-必传")
//    private String endTime;

    @ApiModelProperty(value = "聚合条件列表-必传")
    List<AggregateConditionVO> condition;
}

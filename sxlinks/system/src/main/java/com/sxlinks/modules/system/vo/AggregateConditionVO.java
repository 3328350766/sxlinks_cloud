package com.sxlinks.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AggregateConditionVO  implements Serializable {
    @ApiModelProperty(value = "产品编码-必传")
    String productCode;
    @ApiModelProperty(value = "设备编码-必传")
    String deviceCode;
    @ApiModelProperty(value = "属性编码-必传")
    String propertyCode;
    @ApiModelProperty(value = "聚合类型(平均值-avg 累计值-total)")
    String type;//平均值-avg 累计值-total
    @ApiModelProperty(value = "聚合间隔(时段数据-hour 日段数据-day)")
    String splitType;//时段数据-hour 日段数据-day
}
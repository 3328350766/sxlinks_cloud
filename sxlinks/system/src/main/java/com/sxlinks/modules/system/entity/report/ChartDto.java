
package com.sxlinks.modules.system.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
*
* @description 大屏设计 dto
* @author Raod
* @date 2021-04-12 14:52:21.761
**/
@Data
@ApiModel(value = "大屏设计dto")
public class ChartDto implements Serializable {

    private String chartType;

    /**数据集编码*/
    @ApiModelProperty(value = "数据集编码")
    private String setCode;

    /** 传入的自定义参数*/
    @ApiModelProperty(value = "传入的自定义参数")
    private Map<String, Object> contextData;

    /**图表属性*/
    @ApiModelProperty(value = "图表属性")
    private Map<String, String> chartProperties;

    /**时间字段*/
    private String timeLineFiled;

    /**时间颗粒度*/
    private String particles;

    /**时间格式化*/
    private String dataTimeFormat;

    /**时间展示层*/
    private String timeLineFormat;

    private int timeUnit;

    /**时间区间*/
    private String startTime;

    /**时间区间*/
    private String endTime;


}

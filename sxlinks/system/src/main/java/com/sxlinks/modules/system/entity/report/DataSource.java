package com.sxlinks.modules.system.entity.report;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sxlinks.modules.system.entity.ReportBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @description 数据源 entity
* @author Raod
* @date 2021-03-18 12:09:57.728203200
**/
@TableName(keepGlobalPrefix=true, value="report_data_source")
@Data
public class DataSource extends ReportBaseEntity {

    private String sourceCode;

    @ApiModelProperty(value = "数据源名称")
    private String sourceName;

    @ApiModelProperty(value = "数据源描述")
    private String sourceDesc;

    @ApiModelProperty(value = "数据源类型 DIC_NAME=SOURCE_TYPE; mysql，orace，sqlserver，elasticsearch，接口，javaBean，数据源类型字典中item-extend动态生成表单")
    private String sourceType;

    @ApiModelProperty(value = "数据源连接配置json：关系库{ jdbcUrl:'', username:'', password:'','driverName':''}ES-sql{ apiUrl:'http://127.0.0.1:9092/_xpack/sql?format=json','method':'POST','body':'{\"query\":\"select 1\"}' }  接口{ apiUrl:'http://ip:port/url', method:'' } javaBean{ beanNamw:'xxx' }")
    private String sourceConfig;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;


}

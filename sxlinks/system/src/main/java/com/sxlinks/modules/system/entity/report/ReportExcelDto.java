package com.sxlinks.modules.system.entity.report;

import com.sxlinks.modules.system.entity.ReportBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**

 * @date 2021/4/13 15:12
 */
@Data
public class ReportExcelDto extends ReportBaseEntity implements Serializable {
    private Long id;


    @ApiModelProperty(value = "报表编码")
    private String reportCode;

    @ApiModelProperty(value = "数据集编码，以|分割")
    private String setCodes;

    @ApiModelProperty(value = "数据集查询参数")
    private String setParam;

    @ApiModelProperty(value = "报表json字符串")
    private String jsonStr;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;

}

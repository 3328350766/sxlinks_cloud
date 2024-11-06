package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@ApiModel(value = "小程序地震项目通知信息")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniProjectNoticeRespVo implements Serializable {

    @ApiModelProperty(value = "通知ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "通知内容", example = "请全体专家注意：地震局预测将会在今日晚八点爆发余震，请各位做好防护注意安全")
    private String content;

    @ApiModelProperty(value = "创建/发布时间", example = "2022-01-02 10:20:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "读取状态(0:未读 1:已读)", example = "1")
    private int status;

}

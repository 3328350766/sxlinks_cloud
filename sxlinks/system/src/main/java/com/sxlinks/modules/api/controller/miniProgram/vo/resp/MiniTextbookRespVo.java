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
@ApiModel(value = "小程序教材基本信息")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniTextbookRespVo implements Serializable {

    @ApiModelProperty(value = "教材ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "教材名称", example = "评估指南")
    private String name;

    @ApiModelProperty(value = "面向用户(2:当地住建联系人、3:当地联系人、5:专家)", example = "1")
    private Integer userType;

    @ApiModelProperty(value = "创建时间", example = "2022-01-02 10:20:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "附件链接地址", example = "https://www.baidu.com/a.mp4")
    private String url;

}

package com.sxlinks.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文件信息
 *
 * @author ruoyi
 */
@Data
@ApiModel(value = "附件信息")
public class SysFile {

    /**ID*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "文件ID")
    private String id;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称(新增必传)")
    private String fileName;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型 1图片 2视频 3音频 4其他(新增必传)")
    private String fileType;

    @ApiModelProperty(value = "文件拓展名(新增必传)")
    private String extendType;

    /**
     * 文件类型
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "文件类型(中文)")
    private String fileTypeName;
    /**
     * 文件地址
     */
    @ApiModelProperty(value = "文件地址(新增必传)")
    private String fileUrl;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小(新增必传,单位kb)")
    private Long fileSize;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fileName", getFileName())
                .append("fileUrl", getFileUrl())
                .toString();
    }
}

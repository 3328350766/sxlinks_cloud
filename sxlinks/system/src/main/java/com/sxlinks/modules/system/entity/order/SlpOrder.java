package com.sxlinks.modules.system.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工单对象 slp_order
 *
 * @author ruoyi
 * @date 2022-11-14
 */

@Data
@ApiModel(value = "工单")
public class SlpOrder {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "工单id")
    private String orderId;

    /**
     * 工单状态
     */
    //@Excel(name = "工单状态")
    @ApiModelProperty(value = "工单状态")
    private Integer orderState;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String stateName;
    /**
     * 工单所属项目
     */
    //@Excel(name = "工单所属项目")
    @ApiModelProperty(value = "工单所属项目")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderProject;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    /**
     * 是否开单
     */
    @ApiModelProperty(value = "是否开单")
    private String invoice;

    private String orderManagementId;

    @ApiModelProperty(value = "工单状态:派单(0),接单(1),解决(2),修改(3)")
    private String ifOrderState;

    /**
     * 工单编号
     */
    //@Excel(name = "工单编号")
    @ApiModelProperty(value = "工单编号")
    private String orderNumber;

    /**
     * 工单费用
     */
    //@Excel(name = "工单费用")
    @ApiModelProperty(value = "工单费用")
    private BigDecimal orderCost;

    /**
     * 工单是否开票
     */
    //@Excel(name = "工单是否开票")
    @ApiModelProperty(value = "工单是否开票")
    private Integer orderInvoice;

    /**
     * 工单创建人
     */
    //@Excel(name = "工单创建人")
    @ApiModelProperty(value = "工单创建人")
    private String orderCreator;

    /**
     * 工单派单人
     */
    @ApiModelProperty(value = "工单派单人(派单必填)")
    private String orderDistributeLeaflets;

    /**
     * 工单被派单人
     */
    @ApiModelProperty(value = "工单被派单人(派单必传)")
    private String orderDispatched;

    /**
     * 工单处理人(接单人,应该与工单被派单人保持一致)
     */
    //@Excel(name = "工单处理人")
    @ApiModelProperty(value = "工单处理人")
    private String orderHandler;

    /**
     * 工单审核人(工单解决人)
     */
    //@Excel(name = "工单审核人")
    @ApiModelProperty(value = "工单审核人")
    private String orderAuditor;

    /**
     * 工单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工单创建时间")
    //@Excel(name = "工单创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderCreateTime;

    /**
     * 工单派送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工单派送时间")
    //@Excel(name = "工单派送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderSendTime;

    /**
     * 工单接收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工单接收时间")
    //@Excel(name = "工单接收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderAcceptTime;

    /**
     * 工单处理完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工单处理完成时间")
    //@Excel(name = "工单处理完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderHandleTime;

    /**
     * 工单处理现场图片
     */
    //@Excel(name = "工单处理现场图片")
    @ApiModelProperty(value = "工单处理现场图片")
    private String orderHandleSrc;

    /**
     * 工单备注信息
     */
    //@Excel(name = "工单备注信息")
    @ApiModelProperty(value = "工单备注信息")
    private String orderHandleRemark;

    /**
     * 工单确认处理完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工单确认处理完成时间")
    //@Excel(name = "工单确认处理完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderAuditTime;

    /**
     * 工单确认完成备注
     */
    //@Excel(name = "工单确认完成备注")
    @ApiModelProperty(value = "工单确认完成备注")
    private String orderAuditRemark;

    /**
     * 工单要处理的灯杆
     */
    //@Excel(name = "工单要处理的灯杆")
    @ApiModelProperty(value = "工单要处理的灯杆")
    private String orderPoles;

    /**
     * 工单是否删除
     */
    //@Excel(name = "工单是否删除")
    @ApiModelProperty(value = "工单是否删除")
    private String orderIsDelete;

    /**
     * 工单更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工单更新时间")
    //@Excel(name = "工单更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderUpdateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    private String startbirth;

    private String endbirth;
}

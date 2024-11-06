package com.sxlinks.modules.system.entity.productCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="product_property")
public class ProductProperty {
    @TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
    private Long id;
    @ApiModelProperty(value = "产品编码")
    private String productCode; //产品编码
    @ApiModelProperty(value = "名称")
    private String name; //名称
    @ApiModelProperty(value = "标识符")
    private String identifier; //标识符
    @ApiModelProperty(value = "物模型类型")
    private String funcType; //物模型类型 功能：PROP 功能：FUNC 事件：EVENT 服务：SERVICE
    @ApiModelProperty(value = "数据类型")
    private String dataType; //数据类型 -字典里面选

    private String dataDefine; //数据定义-无需输入
    @ApiModelProperty(value = "读写类型")
    private Integer wrType; //读写类型 0:只读 1:读写
    @ApiModelProperty(value = "发布状态")
    private Integer status; //发布状态-0:草稿 1:启用

    private Integer delFlag = 0; //删除状态 0:正常 1：已删除

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    private Date updateTime;

    @ApiModelProperty(value = "描述")
    private String funcDesc; //描述
    @ApiModelProperty(value = "计量单位(CODE)")
    private String unit; //计量单位(CODE)
    @ApiModelProperty(value = "计量单位名称")
    private String unitName; //计量单位名称
    private String attr; //属性 - 无需输入
    //@ApiModelProperty(value = "事件类型")
    private String eventType; //事件类型 -无需输入

    private Integer async; //同步方式 默认异步-1 无需输入

    private String inputParam; //输入参数

    private String outputParam; //输出参数

    /**创建人*/
    private String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**更新人*/
    private String modifyBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
}

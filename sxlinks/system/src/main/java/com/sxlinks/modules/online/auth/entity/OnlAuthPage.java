//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_auth_page")
@ApiModel(
        value = "onl_auth_page对象",
        description = "onl_auth_page"
)
public class OnlAuthPage implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.ASSIGN_ID
  )
  @ApiModelProperty(" 主键")
  private String id;
  @Excel(
          name = "online表id",
          width = 15.0D
  )
  @ApiModelProperty("online表id")
  private String cgformId;
  @Excel(
          name = "字段名/按钮编码",
          width = 15.0D
  )
  @ApiModelProperty("字段名/按钮编码")
  private String code;
  @Excel(
          name = "1字段 2按钮",
          width = 15.0D
  )
  @ApiModelProperty("1字段 2按钮")
  private Integer type;
  @Excel(
          name = "3可编辑 5可见",
          width = 15.0D
  )
  @ApiModelProperty("3可编辑 5可见")
  private Integer control;
  @Excel(
          name = "3列表 5表单",
          width = 15.0D
  )
  @ApiModelProperty("3列表 5表单")
  private Integer page;
  @Excel(
          name = "1有效 0无效",
          width = 15.0D
  )
  @ApiModelProperty("1有效 0无效")
  private Integer status;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd"
  )
  @ApiModelProperty("创建时间")
  @JsonIgnore
  private Date createTime;
  @ApiModelProperty("创建人")
  @JsonIgnore
  private String createBy;
  @ApiModelProperty("更新人")
  @JsonIgnore
  private String updateBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd"
  )
  @ApiModelProperty("更新日期")
  @JsonIgnore
  private Date updateTime;

  public OnlAuthPage() {
  }

  public OnlAuthPage(String cgformId, String code, int page, int control) {
    this.type = 1;
    this.cgformId = cgformId;
    this.code = code;
    this.control = control;
    this.page = page;
    this.status = 1;
  }

  public String getId() {
    return this.id;
  }

  public String getCgformId() {
    return this.cgformId;
  }

  public String getCode() {
    return this.code;
  }

  public Integer getType() {
    return this.type;
  }

  public Integer getControl() {
    return this.control;
  }

  public Integer getPage() {
    return this.page;
  }

  public Integer getStatus() {
    return this.status;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public String getCreateBy() {
    return this.createBy;
  }

  public String getUpdateBy() {
    return this.updateBy;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public OnlAuthPage setId(String id) {
    this.id = id;
    return this;
  }

  public OnlAuthPage setCgformId(String cgformId) {
    this.cgformId = cgformId;
    return this;
  }

  public OnlAuthPage setCode(String code) {
    this.code = code;
    return this;
  }

  public OnlAuthPage setType(Integer type) {
    this.type = type;
    return this;
  }

  public OnlAuthPage setControl(Integer control) {
    this.control = control;
    return this;
  }

  public OnlAuthPage setPage(Integer page) {
    this.page = page;
    return this;
  }

  public OnlAuthPage setStatus(Integer status) {
    this.status = status;
    return this;
  }

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  @JsonIgnore
  public OnlAuthPage setCreateTime(Date createTime) {
    this.createTime = createTime;
    return this;
  }

  @JsonIgnore
  public OnlAuthPage setCreateBy(String createBy) {
    this.createBy = createBy;
    return this;
  }

  @JsonIgnore
  public OnlAuthPage setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
    return this;
  }

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  @JsonIgnore
  public OnlAuthPage setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  public String toString() {
    return "OnlAuthPage(id=" + this.getId() + ", cgformId=" + this.getCgformId() + ", code=" + this.getCode() + ", type=" + this.getType() + ", control=" + this.getControl() + ", page=" + this.getPage() + ", status=" + this.getStatus() + ", createTime=" + this.getCreateTime() + ", createBy=" + this.getCreateBy() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlAuthPage)) {
      return false;
    } else {
      OnlAuthPage var2 = (OnlAuthPage)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label143: {
          Integer var3 = this.getType();
          Integer var4 = var2.getType();
          if (var3 == null) {
            if (var4 == null) {
              break label143;
            }
          } else if (var3.equals(var4)) {
            break label143;
          }

          return false;
        }

        Integer var5 = this.getControl();
        Integer var6 = var2.getControl();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        Integer var7 = this.getPage();
        Integer var8 = var2.getPage();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label122: {
          Integer var9 = this.getStatus();
          Integer var10 = var2.getStatus();
          if (var9 == null) {
            if (var10 == null) {
              break label122;
            }
          } else if (var9.equals(var10)) {
            break label122;
          }

          return false;
        }

        label115: {
          String var11 = this.getId();
          String var12 = var2.getId();
          if (var11 == null) {
            if (var12 == null) {
              break label115;
            }
          } else if (var11.equals(var12)) {
            break label115;
          }

          return false;
        }

        String var13 = this.getCgformId();
        String var14 = var2.getCgformId();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        String var15 = this.getCode();
        String var16 = var2.getCode();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        label94: {
          Date var17 = this.getCreateTime();
          Date var18 = var2.getCreateTime();
          if (var17 == null) {
            if (var18 == null) {
              break label94;
            }
          } else if (var17.equals(var18)) {
            break label94;
          }

          return false;
        }

        label87: {
          String var19 = this.getCreateBy();
          String var20 = var2.getCreateBy();
          if (var19 == null) {
            if (var20 == null) {
              break label87;
            }
          } else if (var19.equals(var20)) {
            break label87;
          }

          return false;
        }

        String var21 = this.getUpdateBy();
        String var22 = var2.getUpdateBy();
        if (var21 == null) {
          if (var22 != null) {
            return false;
          }
        } else if (!var21.equals(var22)) {
          return false;
        }

        Date var23 = this.getUpdateTime();
        Date var24 = var2.getUpdateTime();
        if (var23 == null) {
          if (var24 != null) {
            return false;
          }
        } else if (!var23.equals(var24)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlAuthPage;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getType();
    int var14 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    Integer var4 = this.getControl();
    var14 = var14 * 59 + (var4 == null ? 43 : var4.hashCode());
    Integer var5 = this.getPage();
    var14 = var14 * 59 + (var5 == null ? 43 : var5.hashCode());
    Integer var6 = this.getStatus();
    var14 = var14 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getId();
    var14 = var14 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getCgformId();
    var14 = var14 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getCode();
    var14 = var14 * 59 + (var9 == null ? 43 : var9.hashCode());
    Date var10 = this.getCreateTime();
    var14 = var14 * 59 + (var10 == null ? 43 : var10.hashCode());
    String var11 = this.getCreateBy();
    var14 = var14 * 59 + (var11 == null ? 43 : var11.hashCode());
    String var12 = this.getUpdateBy();
    var14 = var14 * 59 + (var12 == null ? 43 : var12.hashCode());
    Date var13 = this.getUpdateTime();
    var14 = var14 * 59 + (var13 == null ? 43 : var13.hashCode());
    return var14;
  }
}

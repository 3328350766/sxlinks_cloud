//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_auth_data")
@ApiModel(
        value = "onl_auth_data对象",
        description = "onl_auth_data"
)
public class OnlAuthData implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.ASSIGN_ID
  )
  @ApiModelProperty("主键")
  private String id;
  @Excel(
          name = "online表ID",
          width = 15.0D
  )
  @ApiModelProperty("online表ID")
  private String cgformId;
  @Excel(
          name = "规则名",
          width = 15.0D
  )
  @ApiModelProperty("规则名")
  private String ruleName;
  @Excel(
          name = "规则列",
          width = 15.0D
  )
  @ApiModelProperty("规则列")
  private String ruleColumn;
  @Excel(
          name = "规则条件 大于小于like",
          width = 15.0D
  )
  @ApiModelProperty("规则条件 大于小于like")
  private String ruleOperator;
  @Excel(
          name = "规则值",
          width = 15.0D
  )
  @ApiModelProperty("规则值")
  private String ruleValue;
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
  private Date createTime;
  @ApiModelProperty("创建人")
  private String createBy;
  @ApiModelProperty("更新人")
  private String updateBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd"
  )
  @ApiModelProperty("更新日期")
  private Date updateTime;

  public OnlAuthData() {
  }

  public String getId() {
    return this.id;
  }

  public String getCgformId() {
    return this.cgformId;
  }

  public String getRuleName() {
    return this.ruleName;
  }

  public String getRuleColumn() {
    return this.ruleColumn;
  }

  public String getRuleOperator() {
    return this.ruleOperator;
  }

  public String getRuleValue() {
    return this.ruleValue;
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

  public OnlAuthData setId(String id) {
    this.id = id;
    return this;
  }

  public OnlAuthData setCgformId(String cgformId) {
    this.cgformId = cgformId;
    return this;
  }

  public OnlAuthData setRuleName(String ruleName) {
    this.ruleName = ruleName;
    return this;
  }

  public OnlAuthData setRuleColumn(String ruleColumn) {
    this.ruleColumn = ruleColumn;
    return this;
  }

  public OnlAuthData setRuleOperator(String ruleOperator) {
    this.ruleOperator = ruleOperator;
    return this;
  }

  public OnlAuthData setRuleValue(String ruleValue) {
    this.ruleValue = ruleValue;
    return this;
  }

  public OnlAuthData setStatus(Integer status) {
    this.status = status;
    return this;
  }

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  public OnlAuthData setCreateTime(Date createTime) {
    this.createTime = createTime;
    return this;
  }

  public OnlAuthData setCreateBy(String createBy) {
    this.createBy = createBy;
    return this;
  }

  public OnlAuthData setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
    return this;
  }

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd"
  )
  public OnlAuthData setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  public String toString() {
    return "OnlAuthData(id=" + this.getId() + ", cgformId=" + this.getCgformId() + ", ruleName=" + this.getRuleName() + ", ruleColumn=" + this.getRuleColumn() + ", ruleOperator=" + this.getRuleOperator() + ", ruleValue=" + this.getRuleValue() + ", status=" + this.getStatus() + ", createTime=" + this.getCreateTime() + ", createBy=" + this.getCreateBy() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlAuthData)) {
      return false;
    } else {
      OnlAuthData var2 = (OnlAuthData)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label143: {
          Integer var3 = this.getStatus();
          Integer var4 = var2.getStatus();
          if (var3 == null) {
            if (var4 == null) {
              break label143;
            }
          } else if (var3.equals(var4)) {
            break label143;
          }

          return false;
        }

        String var5 = this.getId();
        String var6 = var2.getId();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        String var7 = this.getCgformId();
        String var8 = var2.getCgformId();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label122: {
          String var9 = this.getRuleName();
          String var10 = var2.getRuleName();
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
          String var11 = this.getRuleColumn();
          String var12 = var2.getRuleColumn();
          if (var11 == null) {
            if (var12 == null) {
              break label115;
            }
          } else if (var11.equals(var12)) {
            break label115;
          }

          return false;
        }

        String var13 = this.getRuleOperator();
        String var14 = var2.getRuleOperator();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        String var15 = this.getRuleValue();
        String var16 = var2.getRuleValue();
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
    return other instanceof OnlAuthData;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getStatus();
    int var14 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getId();
    var14 = var14 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getCgformId();
    var14 = var14 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getRuleName();
    var14 = var14 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getRuleColumn();
    var14 = var14 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getRuleOperator();
    var14 = var14 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getRuleValue();
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

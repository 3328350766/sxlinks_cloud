

package com.sxlinks.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.jeecgframework.poi.excel.annotation.Excel;

@TableName("onl_auth_relation")
@ApiModel(
        value = "onl_auth_relation对象",
        description = "onl_auth_relation"
)
public class OnlAuthRelation implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.ASSIGN_ID
  )
  @ApiModelProperty("id")
  private String id;
  @Excel(
          name = "角色id",
          width = 15.0D
  )
  @ApiModelProperty("角色id")
  private String roleId;
  @Excel(
          name = "权限id",
          width = 15.0D
  )
  @ApiModelProperty("权限id")
  private String authId;
  @Excel(
          name = "1字段 2按钮 3数据权限",
          width = 15.0D
  )
  @ApiModelProperty("1字段 2按钮 3数据权限")
  private Integer type;
  private String cgformId;
  private String authMode;

  public OnlAuthRelation() {
  }

  public String getId() {
    return this.id;
  }

  public String getRoleId() {
    return this.roleId;
  }

  public String getAuthId() {
    return this.authId;
  }

  public Integer getType() {
    return this.type;
  }

  public String getCgformId() {
    return this.cgformId;
  }

  public String getAuthMode() {
    return this.authMode;
  }

  public OnlAuthRelation setId(String id) {
    this.id = id;
    return this;
  }

  public OnlAuthRelation setRoleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  public OnlAuthRelation setAuthId(String authId) {
    this.authId = authId;
    return this;
  }

  public OnlAuthRelation setType(Integer type) {
    this.type = type;
    return this;
  }

  public OnlAuthRelation setCgformId(String cgformId) {
    this.cgformId = cgformId;
    return this;
  }

  public OnlAuthRelation setAuthMode(String authMode) {
    this.authMode = authMode;
    return this;
  }

  public String toString() {
    return "OnlAuthRelation(id=" + this.getId() + ", roleId=" + this.getRoleId() + ", authId=" + this.getAuthId() + ", type=" + this.getType() + ", cgformId=" + this.getCgformId() + ", authMode=" + this.getAuthMode() + ")";
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlAuthRelation)) {
      return false;
    } else {
      OnlAuthRelation var2 = (OnlAuthRelation)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        Integer var3 = this.getType();
        Integer var4 = var2.getType();
        if (var3 == null) {
          if (var4 != null) {
            return false;
          }
        } else if (!var3.equals(var4)) {
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

        String var7 = this.getRoleId();
        String var8 = var2.getRoleId();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label62: {
          String var9 = this.getAuthId();
          String var10 = var2.getAuthId();
          if (var9 == null) {
            if (var10 == null) {
              break label62;
            }
          } else if (var9.equals(var10)) {
            break label62;
          }

          return false;
        }

        label55: {
          String var11 = this.getCgformId();
          String var12 = var2.getCgformId();
          if (var11 == null) {
            if (var12 == null) {
              break label55;
            }
          } else if (var11.equals(var12)) {
            break label55;
          }

          return false;
        }

        String var13 = this.getAuthMode();
        String var14 = var2.getAuthMode();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlAuthRelation;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getType();
    int var9 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getId();
    var9 = var9 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getRoleId();
    var9 = var9 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getAuthId();
    var9 = var9 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getCgformId();
    var9 = var9 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getAuthMode();
    var9 = var9 * 59 + (var8 == null ? 43 : var8.hashCode());
    return var9;
  }
}

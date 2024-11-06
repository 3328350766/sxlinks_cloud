//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_button")
public class OnlCgformButton implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String cgformHeadId;
  private String buttonCode;
  private String buttonName;
  private String buttonStyle;
  private String optType;
  private String exp;
  private String buttonStatus;
  private Integer orderNum;
  private String buttonIcon;
  private String optPosition;

  public OnlCgformButton() {
  }

  public String getId() {
    return this.id;
  }

  public String getCgformHeadId() {
    return this.cgformHeadId;
  }

  public String getButtonCode() {
    return this.buttonCode;
  }

  public String getButtonName() {
    return this.buttonName;
  }

  public String getButtonStyle() {
    return this.buttonStyle;
  }

  public String getOptType() {
    return this.optType;
  }

  public String getExp() {
    return this.exp;
  }

  public String getButtonStatus() {
    return this.buttonStatus;
  }

  public Integer getOrderNum() {
    return this.orderNum;
  }

  public String getButtonIcon() {
    return this.buttonIcon;
  }

  public String getOptPosition() {
    return this.optPosition;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCgformHeadId(String cgformHeadId) {
    this.cgformHeadId = cgformHeadId;
  }

  public void setButtonCode(String buttonCode) {
    this.buttonCode = buttonCode;
  }

  public void setButtonName(String buttonName) {
    this.buttonName = buttonName;
  }

  public void setButtonStyle(String buttonStyle) {
    this.buttonStyle = buttonStyle;
  }

  public void setOptType(String optType) {
    this.optType = optType;
  }

  public void setExp(String exp) {
    this.exp = exp;
  }

  public void setButtonStatus(String buttonStatus) {
    this.buttonStatus = buttonStatus;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
  }

  public void setButtonIcon(String buttonIcon) {
    this.buttonIcon = buttonIcon;
  }

  public void setOptPosition(String optPosition) {
    this.optPosition = optPosition;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformButton)) {
      return false;
    } else {
      OnlCgformButton var2 = (OnlCgformButton)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label143: {
          Integer var3 = this.getOrderNum();
          Integer var4 = var2.getOrderNum();
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

        String var7 = this.getCgformHeadId();
        String var8 = var2.getCgformHeadId();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label122: {
          String var9 = this.getButtonCode();
          String var10 = var2.getButtonCode();
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
          String var11 = this.getButtonName();
          String var12 = var2.getButtonName();
          if (var11 == null) {
            if (var12 == null) {
              break label115;
            }
          } else if (var11.equals(var12)) {
            break label115;
          }

          return false;
        }

        String var13 = this.getButtonStyle();
        String var14 = var2.getButtonStyle();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        String var15 = this.getOptType();
        String var16 = var2.getOptType();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        label94: {
          String var17 = this.getExp();
          String var18 = var2.getExp();
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
          String var19 = this.getButtonStatus();
          String var20 = var2.getButtonStatus();
          if (var19 == null) {
            if (var20 == null) {
              break label87;
            }
          } else if (var19.equals(var20)) {
            break label87;
          }

          return false;
        }

        String var21 = this.getButtonIcon();
        String var22 = var2.getButtonIcon();
        if (var21 == null) {
          if (var22 != null) {
            return false;
          }
        } else if (!var21.equals(var22)) {
          return false;
        }

        String var23 = this.getOptPosition();
        String var24 = var2.getOptPosition();
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
    return other instanceof OnlCgformButton;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getOrderNum();
    int var14 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getId();
    var14 = var14 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getCgformHeadId();
    var14 = var14 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getButtonCode();
    var14 = var14 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getButtonName();
    var14 = var14 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getButtonStyle();
    var14 = var14 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getOptType();
    var14 = var14 * 59 + (var9 == null ? 43 : var9.hashCode());
    String var10 = this.getExp();
    var14 = var14 * 59 + (var10 == null ? 43 : var10.hashCode());
    String var11 = this.getButtonStatus();
    var14 = var14 * 59 + (var11 == null ? 43 : var11.hashCode());
    String var12 = this.getButtonIcon();
    var14 = var14 * 59 + (var12 == null ? 43 : var12.hashCode());
    String var13 = this.getOptPosition();
    var14 = var14 * 59 + (var13 == null ? 43 : var13.hashCode());
    return var14;
  }

  public String toString() {
    return "OnlCgformButton(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", buttonCode=" + this.getButtonCode() + ", buttonName=" + this.getButtonName() + ", buttonStyle=" + this.getButtonStyle() + ", optType=" + this.getOptType() + ", exp=" + this.getExp() + ", buttonStatus=" + this.getButtonStatus() + ", orderNum=" + this.getOrderNum() + ", buttonIcon=" + this.getButtonIcon() + ", optPosition=" + this.getOptPosition() + ")";
  }
}

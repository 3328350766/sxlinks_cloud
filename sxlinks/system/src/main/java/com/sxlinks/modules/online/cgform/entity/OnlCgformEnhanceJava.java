//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_enhance_java")
public class OnlCgformEnhanceJava implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String cgformHeadId;
  private String buttonCode;
  private String cgJavaType;
  private String cgJavaValue;
  private String activeStatus;
  private String event;

  public OnlCgformEnhanceJava() {
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

  public String getCgJavaType() {
    return this.cgJavaType;
  }

  public String getCgJavaValue() {
    return this.cgJavaValue;
  }

  public String getActiveStatus() {
    return this.activeStatus;
  }

  public String getEvent() {
    return this.event;
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

  public void setCgJavaType(String cgJavaType) {
    this.cgJavaType = cgJavaType;
  }

  public void setCgJavaValue(String cgJavaValue) {
    this.cgJavaValue = cgJavaValue;
  }

  public void setActiveStatus(String activeStatus) {
    this.activeStatus = activeStatus;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformEnhanceJava)) {
      return false;
    } else {
      OnlCgformEnhanceJava var2 = (OnlCgformEnhanceJava)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label95: {
          String var3 = this.getId();
          String var4 = var2.getId();
          if (var3 == null) {
            if (var4 == null) {
              break label95;
            }
          } else if (var3.equals(var4)) {
            break label95;
          }

          return false;
        }

        String var5 = this.getCgformHeadId();
        String var6 = var2.getCgformHeadId();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        String var7 = this.getButtonCode();
        String var8 = var2.getButtonCode();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label74: {
          String var9 = this.getCgJavaType();
          String var10 = var2.getCgJavaType();
          if (var9 == null) {
            if (var10 == null) {
              break label74;
            }
          } else if (var9.equals(var10)) {
            break label74;
          }

          return false;
        }

        label67: {
          String var11 = this.getCgJavaValue();
          String var12 = var2.getCgJavaValue();
          if (var11 == null) {
            if (var12 == null) {
              break label67;
            }
          } else if (var11.equals(var12)) {
            break label67;
          }

          return false;
        }

        String var13 = this.getActiveStatus();
        String var14 = var2.getActiveStatus();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        String var15 = this.getEvent();
        String var16 = var2.getEvent();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlCgformEnhanceJava;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getId();
    int var10 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getCgformHeadId();
    var10 = var10 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getButtonCode();
    var10 = var10 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getCgJavaType();
    var10 = var10 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getCgJavaValue();
    var10 = var10 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getActiveStatus();
    var10 = var10 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getEvent();
    var10 = var10 * 59 + (var9 == null ? 43 : var9.hashCode());
    return var10;
  }

  public String toString() {
    return "OnlCgformEnhanceJava(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", buttonCode=" + this.getButtonCode() + ", cgJavaType=" + this.getCgJavaType() + ", cgJavaValue=" + this.getCgJavaValue() + ", activeStatus=" + this.getActiveStatus() + ", event=" + this.getEvent() + ")";
  }
}

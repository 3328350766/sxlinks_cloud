//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_enhance_js")
public class OnlCgformEnhanceJs implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String cgformHeadId;
  private String cgJsType;
  private String cgJs;
  private String content;

  public OnlCgformEnhanceJs() {
  }

  public String getId() {
    return this.id;
  }

  public String getCgformHeadId() {
    return this.cgformHeadId;
  }

  public String getCgJsType() {
    return this.cgJsType;
  }

  public String getCgJs() {
    return this.cgJs;
  }

  public String getContent() {
    return this.content;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCgformHeadId(String cgformHeadId) {
    this.cgformHeadId = cgformHeadId;
  }

  public void setCgJsType(String cgJsType) {
    this.cgJsType = cgJsType;
  }

  public void setCgJs(String cgJs) {
    this.cgJs = cgJs;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformEnhanceJs)) {
      return false;
    } else {
      OnlCgformEnhanceJs var2 = (OnlCgformEnhanceJs)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label71: {
          String var3 = this.getId();
          String var4 = var2.getId();
          if (var3 == null) {
            if (var4 == null) {
              break label71;
            }
          } else if (var3.equals(var4)) {
            break label71;
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

        label57: {
          String var7 = this.getCgJsType();
          String var8 = var2.getCgJsType();
          if (var7 == null) {
            if (var8 == null) {
              break label57;
            }
          } else if (var7.equals(var8)) {
            break label57;
          }

          return false;
        }

        String var9 = this.getCgJs();
        String var10 = var2.getCgJs();
        if (var9 == null) {
          if (var10 != null) {
            return false;
          }
        } else if (!var9.equals(var10)) {
          return false;
        }

        String var11 = this.getContent();
        String var12 = var2.getContent();
        if (var11 == null) {
          if (var12 == null) {
            return true;
          }
        } else if (var11.equals(var12)) {
          return true;
        }

        return false;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlCgformEnhanceJs;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getId();
    int var8 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getCgformHeadId();
    var8 = var8 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getCgJsType();
    var8 = var8 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getCgJs();
    var8 = var8 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getContent();
    var8 = var8 * 59 + (var7 == null ? 43 : var7.hashCode());
    return var8;
  }

  public String toString() {
    return "OnlCgformEnhanceJs(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", cgJsType=" + this.getCgJsType() + ", cgJs=" + this.getCgJs() + ", content=" + this.getContent() + ")";
  }
}

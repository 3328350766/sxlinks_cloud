//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("onl_cgform_enhance_sql")
public class OnlCgformEnhanceSql implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String cgformHeadId;
  private String buttonCode;
  private String cgbSql;
  private String cgbSqlName;
  private String content;

  public OnlCgformEnhanceSql() {
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

  public String getCgbSql() {
    return this.cgbSql;
  }

  public String getCgbSqlName() {
    return this.cgbSqlName;
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

  public void setButtonCode(String buttonCode) {
    this.buttonCode = buttonCode;
  }

  public void setCgbSql(String cgbSql) {
    this.cgbSql = cgbSql;
  }

  public void setCgbSqlName(String cgbSqlName) {
    this.cgbSqlName = cgbSqlName;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformEnhanceSql)) {
      return false;
    } else {
      OnlCgformEnhanceSql var2 = (OnlCgformEnhanceSql)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        String var3 = this.getId();
        String var4 = var2.getId();
        if (var3 == null) {
          if (var4 != null) {
            return false;
          }
        } else if (!var3.equals(var4)) {
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

        label62: {
          String var9 = this.getCgbSql();
          String var10 = var2.getCgbSql();
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
          String var11 = this.getCgbSqlName();
          String var12 = var2.getCgbSqlName();
          if (var11 == null) {
            if (var12 == null) {
              break label55;
            }
          } else if (var11.equals(var12)) {
            break label55;
          }

          return false;
        }

        String var13 = this.getContent();
        String var14 = var2.getContent();
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
    return other instanceof OnlCgformEnhanceSql;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getId();
    int var9 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getCgformHeadId();
    var9 = var9 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getButtonCode();
    var9 = var9 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getCgbSql();
    var9 = var9 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getCgbSqlName();
    var9 = var9 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getContent();
    var9 = var9 * 59 + (var8 == null ? 43 : var8.hashCode());
    return var9;
  }

  public String toString() {
    return "OnlCgformEnhanceSql(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", buttonCode=" + this.getButtonCode() + ", cgbSql=" + this.getCgbSql() + ", cgbSqlName=" + this.getCgbSqlName() + ", content=" + this.getContent() + ")";
  }
}

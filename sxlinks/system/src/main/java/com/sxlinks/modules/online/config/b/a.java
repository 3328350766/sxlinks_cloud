//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.config.b;

import org.apache.commons.lang.StringUtils;

public class a {
  private String a;
  private String b;
  private String c;
  private int d;
  private String e;
  private String f;
  private String g;
  private int h;
  private String i;
  private String j;
  private String k;
  private String l;

  public a() {
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (!(obj instanceof a)) {
      return false;
    } else {
      a var2 = (a)obj;
      if (!this.e.contains("date") && !this.e.contains("blob") && !this.e.contains("text")) {
        return this.e.equals(var2.getColunmType()) && this.i.equals(var2.i) && this.d == var2.getColumnSize() && this.a(this.f, var2.getComment()) && this.a(this.g, var2.getFieldDefault());
      } else {
        return this.c.equals(var2.getColumnName()) && this.i.equals(var2.i) && this.a(this.f, var2.getComment()) && this.a(this.g, var2.getFieldDefault());
      }
    }
  }

  public boolean a(Object var1, String var2) {
    if (var1 == this) {
      return true;
    } else if (!(var1 instanceof a)) {
      return false;
    } else {
      a var3 = (a)var1;
      if ("SQLSERVER".equals(var2)) {
        if (!this.e.contains("date") && !this.e.contains("blob") && !this.e.contains("text")) {
          return this.e.equals(var3.getColunmType()) && this.i.equals(var3.i) && this.d == var3.getColumnSize() && this.a(this.g, var3.getFieldDefault());
        } else {
          return this.c.equals(var3.getColumnName()) && this.i.equals(var3.i);
        }
      } else if ("POSTGRESQL".equals(var2)) {
        if (!this.e.contains("date") && !this.e.contains("blob") && !this.e.contains("text")) {
          return this.e.equals(var3.getColunmType()) && this.i.equals(var3.i) && this.d == var3.getColumnSize() && this.a(this.g, var3.getFieldDefault());
        } else {
          return this.c.equals(var3.getColumnName()) && this.i.equals(var3.i);
        }
      } else if (!"ORACLE".equals(var2) && !"DM".equals(var2)) {
        if (!this.e.contains("date") && !this.e.contains("blob") && !this.e.contains("text")) {
          return this.e.equals(var3.getColunmType()) && this.i.equals(var3.i) && this.d == var3.getColumnSize() && this.a(this.f, var3.getComment()) && this.a(this.g, var3.getFieldDefault());
        } else {
          return this.e.equals(var3.getColunmType()) && this.c.equals(var3.getColumnName()) && this.i.equals(var3.i) && this.a(this.f, var3.getComment()) && this.a(this.g, var3.getFieldDefault());
        }
      } else if (!this.e.contains("date") && !this.e.contains("blob") && !this.e.contains("text")) {
        return this.e.equals(var3.getColunmType()) && this.i.equals(var3.i) && this.d == var3.getColumnSize() && this.a(this.g, var3.getFieldDefault());
      } else {
        return this.c.equals(var3.getColumnName()) && this.i.equals(var3.i);
      }
    }
  }

  public boolean a(a var1) {
    return var1 == this ? true : this.a(this.f, var1.getComment());
  }

  public boolean b(a var1) {
    return var1 == this ? true : this.a(this.f, var1.getComment());
  }

  private boolean a(String var1, String var2) {
    boolean var3 = StringUtils.isNotEmpty(var1);
    boolean var4 = StringUtils.isNotEmpty(var2);
    if (var3 != var4) {
      return false;
    } else {
      return var3 ? var1.equals(var2) : true;
    }
  }

  public String getColumnName() {
    return this.c;
  }

  public int getColumnSize() {
    return this.d;
  }

  public String getColunmType() {
    return this.e;
  }

  public String getComment() {
    return this.f;
  }

  public int getDecimalDigits() {
    return this.h;
  }

  public String getIsNullable() {
    return this.i;
  }

  public String getOldColumnName() {
    return this.k;
  }

  public int hashCode() {
    return this.d + this.e.hashCode() * this.c.hashCode();
  }

  public void setColumnName(String columnName) {
    this.c = columnName;
  }

  public void setColumnSize(int columnSize) {
    this.d = columnSize;
  }

  public void setColunmType(String colunmType) {
    this.e = colunmType;
  }

  public void setComment(String comment) {
    this.f = comment;
  }

  public void setDecimalDigits(int decimalDigits) {
    this.h = decimalDigits;
  }

  public void setIsNullable(String isNullable) {
    this.i = isNullable;
  }

  public void setOldColumnName(String oldColumnName) {
    this.k = oldColumnName;
  }

  public String toString() {
    return this.c + "," + this.e + "," + this.i + "," + this.d;
  }

  public String getColumnId() {
    return this.b;
  }

  public void setColumnId(String columnId) {
    this.b = columnId;
  }

  public String getTableName() {
    return this.a;
  }

  public void setTableName(String tableName) {
    this.a = tableName;
  }

  public String getFieldDefault() {
    return this.g;
  }

  public void setFieldDefault(String fieldDefault) {
    this.g = fieldDefault;
  }

  public String getPkType() {
    return this.j;
  }

  public void setPkType(String pkType) {
    this.j = pkType;
  }

  public String getRealDbType() {
    return this.l;
  }

  public void setRealDbType(String realDbType) {
    this.l = realDbType;
  }
}

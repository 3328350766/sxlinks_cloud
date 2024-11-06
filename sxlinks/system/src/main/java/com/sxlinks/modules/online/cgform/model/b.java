//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

public class b {
  private String a;
  private String b;
  private String c;

  public b() {
  }

  public b(String var1, String var2) {
    this.c = var2;
    this.a = var1;
  }

  public String getField() {
    return this.a;
  }

  public String getTable() {
    return this.b;
  }

  public String getKey() {
    return this.c;
  }

  public void setField(String field) {
    this.a = field;
  }

  public void setTable(String table) {
    this.b = table;
  }

  public void setKey(String key) {
    this.c = key;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof b)) {
      return false;
    } else {
      b var2 = (b)o;
      if (!var2.a(this)) {
        return false;
      } else {
        label47: {
          String var3 = this.getField();
          String var4 = var2.getField();
          if (var3 == null) {
            if (var4 == null) {
              break label47;
            }
          } else if (var3.equals(var4)) {
            break label47;
          }

          return false;
        }

        String var5 = this.getTable();
        String var6 = var2.getTable();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        String var7 = this.getKey();
        String var8 = var2.getKey();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean a(Object var1) {
    return var1 instanceof b;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getField();
    int var6 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getTable();
    var6 = var6 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getKey();
    var6 = var6 * 59 + (var5 == null ? 43 : var5.hashCode());
    return var6;
  }

  public String toString() {
    return "OnlForeignKey(field=" + this.getField() + ", table=" + this.getTable() + ", key=" + this.getKey() + ")";
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.a;

public class a {
  private String a;
  private String b;
  private String c;
  private String d;
  private String e;
  private String f;
  private String g;
  private String h;

  private String getQuerySql() {
    StringBuffer var1 = new StringBuffer();
    String var2 = " ";
    var1.append("SELECT ");
    return null;
  }

  public a() {
  }

  public String getTable() {
    return this.a;
  }

  public String getTxt() {
    return this.b;
  }

  public String getKey() {
    return this.c;
  }

  public String getLinkField() {
    return this.d;
  }

  public String getIdField() {
    return this.e;
  }

  public String getPidField() {
    return this.f;
  }

  public String getPidValue() {
    return this.g;
  }

  public String getCondition() {
    return this.h;
  }

  public void setTable(String table) {
    this.a = table;
  }

  public void setTxt(String txt) {
    this.b = txt;
  }

  public void setKey(String key) {
    this.c = key;
  }

  public void setLinkField(String linkField) {
    this.d = linkField;
  }

  public void setIdField(String idField) {
    this.e = idField;
  }

  public void setPidField(String pidField) {
    this.f = pidField;
  }

  public void setPidValue(String pidValue) {
    this.g = pidValue;
  }

  public void setCondition(String condition) {
    this.h = condition;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof a)) {
      return false;
    } else {
      a var2 = (a)o;
      if (!var2.a(this)) {
        return false;
      } else {
        label107: {
          String var3 = this.getTable();
          String var4 = var2.getTable();
          if (var3 == null) {
            if (var4 == null) {
              break label107;
            }
          } else if (var3.equals(var4)) {
            break label107;
          }

          return false;
        }

        String var5 = this.getTxt();
        String var6 = var2.getTxt();
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

        label86: {
          String var9 = this.getLinkField();
          String var10 = var2.getLinkField();
          if (var9 == null) {
            if (var10 == null) {
              break label86;
            }
          } else if (var9.equals(var10)) {
            break label86;
          }

          return false;
        }

        label79: {
          String var11 = this.getIdField();
          String var12 = var2.getIdField();
          if (var11 == null) {
            if (var12 == null) {
              break label79;
            }
          } else if (var11.equals(var12)) {
            break label79;
          }

          return false;
        }

        label72: {
          String var13 = this.getPidField();
          String var14 = var2.getPidField();
          if (var13 == null) {
            if (var14 == null) {
              break label72;
            }
          } else if (var13.equals(var14)) {
            break label72;
          }

          return false;
        }

        String var15 = this.getPidValue();
        String var16 = var2.getPidValue();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        String var17 = this.getCondition();
        String var18 = var2.getCondition();
        if (var17 == null) {
          if (var18 != null) {
            return false;
          }
        } else if (!var17.equals(var18)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean a(Object var1) {
    return var1 instanceof a;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getTable();
    int var11 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getTxt();
    var11 = var11 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getKey();
    var11 = var11 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getLinkField();
    var11 = var11 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getIdField();
    var11 = var11 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getPidField();
    var11 = var11 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getPidValue();
    var11 = var11 * 59 + (var9 == null ? 43 : var9.hashCode());
    String var10 = this.getCondition();
    var11 = var11 * 59 + (var10 == null ? 43 : var10.hashCode());
    return var11;
  }

  public String toString() {
    return "LinkDown(table=" + this.getTable() + ", txt=" + this.getTxt() + ", key=" + this.getKey() + ", linkField=" + this.getLinkField() + ", idField=" + this.getIdField() + ", pidField=" + this.getPidField() + ", pidValue=" + this.getPidValue() + ", condition=" + this.getCondition() + ")";
  }
}

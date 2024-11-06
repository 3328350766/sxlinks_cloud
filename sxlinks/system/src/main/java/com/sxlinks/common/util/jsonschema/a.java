//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema;

public class a {
  private String a;
  private String b;

  public a() {
  }

  public a(String var1, String var2) {
    this.a = var1;
    this.b = var2;
  }

  public String getTitle() {
    return this.a;
  }

  public String getField() {
    return this.b;
  }

  public void setTitle(String title) {
    this.a = title;
  }

  public void setField(String field) {
    this.b = field;
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
        String var3 = this.getTitle();
        String var4 = var2.getTitle();
        if (var3 == null) {
          if (var4 != null) {
            return false;
          }
        } else if (!var3.equals(var4)) {
          return false;
        }

        String var5 = this.getField();
        String var6 = var2.getField();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
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
    String var3 = this.getTitle();
    int var5 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getField();
    var5 = var5 * 59 + (var4 == null ? 43 : var4.hashCode());
    return var5;
  }

  public String toString() {
    return "BaseColumn(title=" + this.getTitle() + ", field=" + this.getField() + ")";
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

public class c {
  private String a;

  public c() {
  }

  public c(String var1) {
    this.a = var1;
  }

  public String getCustomRender() {
    return this.a;
  }

  public void setCustomRender(String customRender) {
    this.a = customRender;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof c)) {
      return false;
    } else {
      c var2 = (c)o;
      if (!var2.a(this)) {
        return false;
      } else {
        String var3 = this.getCustomRender();
        String var4 = var2.getCustomRender();
        if (var3 == null) {
          if (var4 != null) {
            return false;
          }
        } else if (!var3.equals(var4)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean a(Object var1) {
    return var1 instanceof c;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getCustomRender();
    int var4 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    return var4;
  }

  public String toString() {
    return "ScopedSlots(customRender=" + this.getCustomRender() + ")";
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

public class HrefSlots {
  private String slotName;
  private String href;

  public HrefSlots() {
  }

  public HrefSlots(String slotName, String href) {
    this.slotName = slotName;
    this.href = href;
  }

  public String getSlotName() {
    return this.slotName;
  }

  public String getHref() {
    return this.href;
  }

  public void setSlotName(String slotName) {
    this.slotName = slotName;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof HrefSlots)) {
      return false;
    } else {
      HrefSlots var2 = (HrefSlots)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        String var3 = this.getSlotName();
        String var4 = var2.getSlotName();
        if (var3 == null) {
          if (var4 != null) {
            return false;
          }
        } else if (!var3.equals(var4)) {
          return false;
        }

        String var5 = this.getHref();
        String var6 = var2.getHref();
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

  protected boolean canEqual(Object other) {
    return other instanceof HrefSlots;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getSlotName();
    int var5 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getHref();
    var5 = var5 * 59 + (var4 == null ? 43 : var4.hashCode());
    return var5;
  }

  public String toString() {
    return "HrefSlots(slotName=" + this.getSlotName() + ", href=" + this.getHref() + ")";
  }
}

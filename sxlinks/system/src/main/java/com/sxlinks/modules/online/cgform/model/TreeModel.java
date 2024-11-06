//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

public class TreeModel {
  private String label;
  private String store;
  private String id;
  private String pid;

  public TreeModel() {
  }

  public String getLabel() {
    return this.label;
  }

  public String getStore() {
    return this.store;
  }

  public String getId() {
    return this.id;
  }

  public String getPid() {
    return this.pid;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof TreeModel)) {
      return false;
    } else {
      TreeModel var2 = (TreeModel)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label59: {
          String var3 = this.getLabel();
          String var4 = var2.getLabel();
          if (var3 == null) {
            if (var4 == null) {
              break label59;
            }
          } else if (var3.equals(var4)) {
            break label59;
          }

          return false;
        }

        String var5 = this.getStore();
        String var6 = var2.getStore();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        String var7 = this.getId();
        String var8 = var2.getId();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        String var9 = this.getPid();
        String var10 = var2.getPid();
        if (var9 == null) {
          if (var10 != null) {
            return false;
          }
        } else if (!var9.equals(var10)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof TreeModel;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    String var3 = this.getLabel();
    int var7 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getStore();
    var7 = var7 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getId();
    var7 = var7 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getPid();
    var7 = var7 * 59 + (var6 == null ? 43 : var6.hashCode());
    return var7;
  }

  public String toString() {
    return "TreeModel(label=" + this.getLabel() + ", store=" + this.getStore() + ", id=" + this.getId() + ", pid=" + this.getPid() + ")";
  }
}

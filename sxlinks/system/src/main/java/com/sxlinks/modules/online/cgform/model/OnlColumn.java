//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

public class OnlColumn {
  private String title;
  private String dataIndex;
  private String align;
  private String customRender;
  private com.sxlinks.modules.online.cgform.model.c scopedSlots;
  private String hrefSlotName;
  private int showLength;
  private boolean sorter = false;

  public OnlColumn() {
  }

  public OnlColumn(String title, String dataIndex) {
    this.align = "center";
    this.title = title;
    this.dataIndex = dataIndex;
  }

  public String getTitle() {
    return this.title;
  }

  public String getDataIndex() {
    return this.dataIndex;
  }

  public String getAlign() {
    return this.align;
  }

  public String getCustomRender() {
    return this.customRender;
  }

  public com.sxlinks.modules.online.cgform.model.c getScopedSlots() {
    return this.scopedSlots;
  }

  public String getHrefSlotName() {
    return this.hrefSlotName;
  }

  public int getShowLength() {
    return this.showLength;
  }

  public boolean isSorter() {
    return this.sorter;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDataIndex(String dataIndex) {
    this.dataIndex = dataIndex;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public void setCustomRender(String customRender) {
    this.customRender = customRender;
  }

  public void setScopedSlots(com.sxlinks.modules.online.cgform.model.c scopedSlots) {
    this.scopedSlots = scopedSlots;
  }

  public void setHrefSlotName(String hrefSlotName) {
    this.hrefSlotName = hrefSlotName;
  }

  public void setShowLength(int showLength) {
    this.showLength = showLength;
  }

  public void setSorter(boolean sorter) {
    this.sorter = sorter;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlColumn)) {
      return false;
    } else {
      OnlColumn var2 = (OnlColumn)o;
      if (!var2.canEqual(this)) {
        return false;
      } else if (this.getShowLength() != var2.getShowLength()) {
        return false;
      } else if (this.isSorter() != var2.isSorter()) {
        return false;
      } else {
        label88: {
          String var3 = this.getTitle();
          String var4 = var2.getTitle();
          if (var3 == null) {
            if (var4 == null) {
              break label88;
            }
          } else if (var3.equals(var4)) {
            break label88;
          }

          return false;
        }

        String var5 = this.getDataIndex();
        String var6 = var2.getDataIndex();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        label74: {
          String var7 = this.getAlign();
          String var8 = var2.getAlign();
          if (var7 == null) {
            if (var8 == null) {
              break label74;
            }
          } else if (var7.equals(var8)) {
            break label74;
          }

          return false;
        }

        label67: {
          String var9 = this.getCustomRender();
          String var10 = var2.getCustomRender();
          if (var9 == null) {
            if (var10 == null) {
              break label67;
            }
          } else if (var9.equals(var10)) {
            break label67;
          }

          return false;
        }

        com.sxlinks.modules.online.cgform.model.c var11 = this.getScopedSlots();
        com.sxlinks.modules.online.cgform.model.c var12 = var2.getScopedSlots();
        if (var11 == null) {
          if (var12 != null) {
            return false;
          }
        } else if (!var11.equals(var12)) {
          return false;
        }

        String var13 = this.getHrefSlotName();
        String var14 = var2.getHrefSlotName();
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
    return other instanceof OnlColumn;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    int var9 = var2 * 59 + this.getShowLength();
    var9 = var9 * 59 + (this.isSorter() ? 79 : 97);
    String var3 = this.getTitle();
    var9 = var9 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getDataIndex();
    var9 = var9 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getAlign();
    var9 = var9 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getCustomRender();
    var9 = var9 * 59 + (var6 == null ? 43 : var6.hashCode());
    com.sxlinks.modules.online.cgform.model.c var7 = this.getScopedSlots();
    var9 = var9 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getHrefSlotName();
    var9 = var9 * 59 + (var8 == null ? 43 : var8.hashCode());
    return var9;
  }

  public String toString() {
    return "OnlColumn(title=" + this.getTitle() + ", dataIndex=" + this.getDataIndex() + ", align=" + this.getAlign() + ", customRender=" + this.getCustomRender() + ", scopedSlots=" + this.getScopedSlots() + ", hrefSlotName=" + this.getHrefSlotName() + ", showLength=" + this.getShowLength() + ", sorter=" + this.isSorter() + ")";
  }
}

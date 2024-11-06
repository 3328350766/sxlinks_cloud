//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.vo;

import java.io.Serializable;

public class AuthPageVO implements Serializable {
  private static final long serialVersionUID = 724713901683956568L;
  private String id;
  private String code;
  private String title;
  private Integer page;
  private Integer control;
  private String relId;
  private Boolean checked;

  public Boolean isChecked() {
    return this.relId != null && this.relId.length() > 0;
  }

  public AuthPageVO() {
  }

  public String getId() {
    return this.id;
  }

  public String getCode() {
    return this.code;
  }

  public String getTitle() {
    return this.title;
  }

  public Integer getPage() {
    return this.page;
  }

  public Integer getControl() {
    return this.control;
  }

  public String getRelId() {
    return this.relId;
  }

  public Boolean getChecked() {
    return this.checked;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public void setControl(Integer control) {
    this.control = control;
  }

  public void setRelId(String relId) {
    this.relId = relId;
  }

  public void setChecked(Boolean checked) {
    this.checked = checked;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof AuthPageVO)) {
      return false;
    } else {
      AuthPageVO var2 = (AuthPageVO)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label95: {
          Integer var3 = this.getPage();
          Integer var4 = var2.getPage();
          if (var3 == null) {
            if (var4 == null) {
              break label95;
            }
          } else if (var3.equals(var4)) {
            break label95;
          }

          return false;
        }

        Integer var5 = this.getControl();
        Integer var6 = var2.getControl();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        Boolean var7 = this.getChecked();
        Boolean var8 = var2.getChecked();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label74: {
          String var9 = this.getId();
          String var10 = var2.getId();
          if (var9 == null) {
            if (var10 == null) {
              break label74;
            }
          } else if (var9.equals(var10)) {
            break label74;
          }

          return false;
        }

        label67: {
          String var11 = this.getCode();
          String var12 = var2.getCode();
          if (var11 == null) {
            if (var12 == null) {
              break label67;
            }
          } else if (var11.equals(var12)) {
            break label67;
          }

          return false;
        }

        String var13 = this.getTitle();
        String var14 = var2.getTitle();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        String var15 = this.getRelId();
        String var16 = var2.getRelId();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof AuthPageVO;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getPage();
    int var10 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    Integer var4 = this.getControl();
    var10 = var10 * 59 + (var4 == null ? 43 : var4.hashCode());
    Boolean var5 = this.getChecked();
    var10 = var10 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getId();
    var10 = var10 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getCode();
    var10 = var10 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getTitle();
    var10 = var10 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getRelId();
    var10 = var10 * 59 + (var9 == null ? 43 : var9.hashCode());
    return var10;
  }

  public String toString() {
    return "AuthPageVO(id=" + this.getId() + ", code=" + this.getCode() + ", title=" + this.getTitle() + ", page=" + this.getPage() + ", control=" + this.getControl() + ", relId=" + this.getRelId() + ", checked=" + this.getChecked() + ")";
  }
}

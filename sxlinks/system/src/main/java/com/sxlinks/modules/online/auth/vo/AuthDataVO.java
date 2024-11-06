//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.vo;

import java.io.Serializable;

public class AuthDataVO implements Serializable {
  private static final long serialVersionUID = 1057819436991228603L;
  private String id;
  private String title;
  private String relId;
  private Boolean checked;

  public Boolean isChecked() {
    return this.relId != null && this.relId.length() > 0;
  }

  public AuthDataVO() {
  }

  public String getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
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

  public void setTitle(String title) {
    this.title = title;
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
    } else if (!(o instanceof AuthDataVO)) {
      return false;
    } else {
      AuthDataVO var2 = (AuthDataVO)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label59: {
          Boolean var3 = this.getChecked();
          Boolean var4 = var2.getChecked();
          if (var3 == null) {
            if (var4 == null) {
              break label59;
            }
          } else if (var3.equals(var4)) {
            break label59;
          }

          return false;
        }

        String var5 = this.getId();
        String var6 = var2.getId();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        String var7 = this.getTitle();
        String var8 = var2.getTitle();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        String var9 = this.getRelId();
        String var10 = var2.getRelId();
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
    return other instanceof AuthDataVO;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Boolean var3 = this.getChecked();
    int var7 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getId();
    var7 = var7 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getTitle();
    var7 = var7 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getRelId();
    var7 = var7 * 59 + (var6 == null ? 43 : var6.hashCode());
    return var7;
  }

  public String toString() {
    return "AuthDataVO(id=" + this.getId() + ", title=" + this.getTitle() + ", relId=" + this.getRelId() + ", checked=" + this.getChecked() + ")";
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.vo;

import java.io.Serializable;
import com.sxlinks.modules.online.cgform.entity.OnlCgformField;

public class AuthColumnVO implements Serializable {
  private static final long serialVersionUID = 5445993027926933917L;
  private String id;
  private String cgformId;
  private Integer type = 1;
  private String code;
  private String title;
  private Integer status;
  private boolean listShow;
  private boolean formShow;
  private boolean formEditable;
  private Integer isShowForm;
  private Integer isShowList;
  private String tableName;
  private String tableNameTxt;
  private int switchFlag;

  public AuthColumnVO() {
  }

  public AuthColumnVO(OnlCgformField field) {
    this.id = field.getId();
    this.cgformId = field.getCgformHeadId();
    this.code = field.getDbFieldName();
    this.title = field.getDbFieldTxt();
    this.type = 1;
    this.isShowForm = field.getIsShowForm();
    this.isShowList = field.getIsShowList();
  }

  public String getId() {
    return this.id;
  }

  public String getCgformId() {
    return this.cgformId;
  }

  public Integer getType() {
    return this.type;
  }

  public String getCode() {
    return this.code;
  }

  public String getTitle() {
    return this.title;
  }

  public Integer getStatus() {
    return this.status;
  }

  public boolean isListShow() {
    return this.listShow;
  }

  public boolean isFormShow() {
    return this.formShow;
  }

  public boolean isFormEditable() {
    return this.formEditable;
  }

  public Integer getIsShowForm() {
    return this.isShowForm;
  }

  public Integer getIsShowList() {
    return this.isShowList;
  }

  public String getTableName() {
    return this.tableName;
  }

  public String getTableNameTxt() {
    return this.tableNameTxt;
  }

  public int getSwitchFlag() {
    return this.switchFlag;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCgformId(String cgformId) {
    this.cgformId = cgformId;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public void setListShow(boolean listShow) {
    this.listShow = listShow;
  }

  public void setFormShow(boolean formShow) {
    this.formShow = formShow;
  }

  public void setFormEditable(boolean formEditable) {
    this.formEditable = formEditable;
  }

  public void setIsShowForm(Integer isShowForm) {
    this.isShowForm = isShowForm;
  }

  public void setIsShowList(Integer isShowList) {
    this.isShowList = isShowList;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setTableNameTxt(String tableNameTxt) {
    this.tableNameTxt = tableNameTxt;
  }

  public void setSwitchFlag(int switchFlag) {
    this.switchFlag = switchFlag;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof AuthColumnVO)) {
      return false;
    } else {
      AuthColumnVO var2 = (AuthColumnVO)o;
      if (!var2.canEqual(this)) {
        return false;
      } else if (this.isListShow() != var2.isListShow()) {
        return false;
      } else if (this.isFormShow() != var2.isFormShow()) {
        return false;
      } else if (this.isFormEditable() != var2.isFormEditable()) {
        return false;
      } else if (this.getSwitchFlag() != var2.getSwitchFlag()) {
        return false;
      } else {
        Integer var3 = this.getType();
        Integer var4 = var2.getType();
        if (var3 == null) {
          if (var4 != null) {
            return false;
          }
        } else if (!var3.equals(var4)) {
          return false;
        }

        label134: {
          Integer var5 = this.getStatus();
          Integer var6 = var2.getStatus();
          if (var5 == null) {
            if (var6 == null) {
              break label134;
            }
          } else if (var5.equals(var6)) {
            break label134;
          }

          return false;
        }

        label127: {
          Integer var7 = this.getIsShowForm();
          Integer var8 = var2.getIsShowForm();
          if (var7 == null) {
            if (var8 == null) {
              break label127;
            }
          } else if (var7.equals(var8)) {
            break label127;
          }

          return false;
        }

        label120: {
          Integer var9 = this.getIsShowList();
          Integer var10 = var2.getIsShowList();
          if (var9 == null) {
            if (var10 == null) {
              break label120;
            }
          } else if (var9.equals(var10)) {
            break label120;
          }

          return false;
        }

        String var11 = this.getId();
        String var12 = var2.getId();
        if (var11 == null) {
          if (var12 != null) {
            return false;
          }
        } else if (!var11.equals(var12)) {
          return false;
        }

        label106: {
          String var13 = this.getCgformId();
          String var14 = var2.getCgformId();
          if (var13 == null) {
            if (var14 == null) {
              break label106;
            }
          } else if (var13.equals(var14)) {
            break label106;
          }

          return false;
        }

        String var15 = this.getCode();
        String var16 = var2.getCode();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        String var17 = this.getTitle();
        String var18 = var2.getTitle();
        if (var17 == null) {
          if (var18 != null) {
            return false;
          }
        } else if (!var17.equals(var18)) {
          return false;
        }

        String var19 = this.getTableName();
        String var20 = var2.getTableName();
        if (var19 == null) {
          if (var20 != null) {
            return false;
          }
        } else if (!var19.equals(var20)) {
          return false;
        }

        String var21 = this.getTableNameTxt();
        String var22 = var2.getTableNameTxt();
        if (var21 == null) {
          if (var22 != null) {
            return false;
          }
        } else if (!var21.equals(var22)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof AuthColumnVO;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    int var13 = var2 * 59 + (this.isListShow() ? 79 : 97);
    var13 = var13 * 59 + (this.isFormShow() ? 79 : 97);
    var13 = var13 * 59 + (this.isFormEditable() ? 79 : 97);
    var13 = var13 * 59 + this.getSwitchFlag();
    Integer var3 = this.getType();
    var13 = var13 * 59 + (var3 == null ? 43 : var3.hashCode());
    Integer var4 = this.getStatus();
    var13 = var13 * 59 + (var4 == null ? 43 : var4.hashCode());
    Integer var5 = this.getIsShowForm();
    var13 = var13 * 59 + (var5 == null ? 43 : var5.hashCode());
    Integer var6 = this.getIsShowList();
    var13 = var13 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getId();
    var13 = var13 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getCgformId();
    var13 = var13 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getCode();
    var13 = var13 * 59 + (var9 == null ? 43 : var9.hashCode());
    String var10 = this.getTitle();
    var13 = var13 * 59 + (var10 == null ? 43 : var10.hashCode());
    String var11 = this.getTableName();
    var13 = var13 * 59 + (var11 == null ? 43 : var11.hashCode());
    String var12 = this.getTableNameTxt();
    var13 = var13 * 59 + (var12 == null ? 43 : var12.hashCode());
    return var13;
  }

  public String toString() {
    return "AuthColumnVO(id=" + this.getId() + ", cgformId=" + this.getCgformId() + ", type=" + this.getType() + ", code=" + this.getCode() + ", title=" + this.getTitle() + ", status=" + this.getStatus() + ", listShow=" + this.isListShow() + ", formShow=" + this.isFormShow() + ", formEditable=" + this.isFormEditable() + ", isShowForm=" + this.getIsShowForm() + ", isShowList=" + this.getIsShowList() + ", tableName=" + this.getTableName() + ", tableNameTxt=" + this.getTableNameTxt() + ", switchFlag=" + this.getSwitchFlag() + ")";
  }
}

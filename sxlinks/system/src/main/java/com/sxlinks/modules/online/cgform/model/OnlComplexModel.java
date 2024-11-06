//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sxlinks.common.system.vo.DictModel;
import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;

public class OnlComplexModel implements Serializable {
  private static final long b = 1L;
  private String c;
  private String d;
  private String e;
  private String f;
  private Integer g;
  private String h;
  private String i;
  private Integer j;
  private List<com.sxlinks.modules.online.cgform.model.OnlColumn> k;
  private List<String> l;
  private Map<String, List<DictModel>> m = new HashMap();
  private List<OnlCgformButton> n;
  List<com.sxlinks.modules.online.cgform.model.HrefSlots> a;
  private String o;
  private List<com.sxlinks.modules.online.cgform.model.b> p;
  private String q;
  private String r;
  private String s;
  private String t;
  private String u;
  private Integer v;

  public OnlComplexModel() {
  }

  public String getCode() {
    return this.c;
  }

  public String getFormTemplate() {
    return this.d;
  }

  public String getDescription() {
    return this.e;
  }

  public String getCurrentTableName() {
    return this.f;
  }

  public Integer getTableType() {
    return this.g;
  }

  public String getPaginationFlag() {
    return this.h;
  }

  public String getCheckboxFlag() {
    return this.i;
  }

  public Integer getScrollFlag() {
    return this.j;
  }

  public List<com.sxlinks.modules.online.cgform.model.OnlColumn> getColumns() {
    return this.k;
  }

  public List<String> getHideColumns() {
    return this.l;
  }

  public Map<String, List<DictModel>> getDictOptions() {
    return this.m;
  }

  public List<OnlCgformButton> getCgButtonList() {
    return this.n;
  }

  public List<com.sxlinks.modules.online.cgform.model.HrefSlots> getFieldHrefSlots() {
    return this.a;
  }

  public String getEnhanceJs() {
    return this.o;
  }

  public List<com.sxlinks.modules.online.cgform.model.b> getForeignKeys() {
    return this.p;
  }

  public String getPidField() {
    return this.q;
  }

  public String getHasChildrenField() {
    return this.r;
  }

  public String getTextField() {
    return this.s;
  }

  public String getIsDesForm() {
    return this.t;
  }

  public String getDesFormCode() {
    return this.u;
  }

  public Integer getRelationType() {
    return this.v;
  }

  public void setCode(String code) {
    this.c = code;
  }

  public void setFormTemplate(String formTemplate) {
    this.d = formTemplate;
  }

  public void setDescription(String description) {
    this.e = description;
  }

  public void setCurrentTableName(String currentTableName) {
    this.f = currentTableName;
  }

  public void setTableType(Integer tableType) {
    this.g = tableType;
  }

  public void setPaginationFlag(String paginationFlag) {
    this.h = paginationFlag;
  }

  public void setCheckboxFlag(String checkboxFlag) {
    this.i = checkboxFlag;
  }

  public void setScrollFlag(Integer scrollFlag) {
    this.j = scrollFlag;
  }

  public void setColumns(List<com.sxlinks.modules.online.cgform.model.OnlColumn> columns) {
    this.k = columns;
  }

  public void setHideColumns(List<String> hideColumns) {
    this.l = hideColumns;
  }

  public void setDictOptions(Map<String, List<DictModel>> dictOptions) {
    this.m = dictOptions;
  }

  public void setCgButtonList(List<OnlCgformButton> cgButtonList) {
    this.n = cgButtonList;
  }

  public void setFieldHrefSlots(List<com.sxlinks.modules.online.cgform.model.HrefSlots> fieldHrefSlots) {
    this.a = fieldHrefSlots;
  }

  public void setEnhanceJs(String enhanceJs) {
    this.o = enhanceJs;
  }

  public void setForeignKeys(List<com.sxlinks.modules.online.cgform.model.b> foreignKeys) {
    this.p = foreignKeys;
  }

  public void setPidField(String pidField) {
    this.q = pidField;
  }

  public void setHasChildrenField(String hasChildrenField) {
    this.r = hasChildrenField;
  }

  public void setTextField(String textField) {
    this.s = textField;
  }

  public void setIsDesForm(String isDesForm) {
    this.t = isDesForm;
  }

  public void setDesFormCode(String desFormCode) {
    this.u = desFormCode;
  }

  public void setRelationType(Integer relationType) {
    this.v = relationType;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlComplexModel)) {
      return false;
    } else {
      OnlComplexModel var2 = (OnlComplexModel)o;
      if (!var2.a(this)) {
        return false;
      } else {
        label263: {
          Integer var3 = this.getTableType();
          Integer var4 = var2.getTableType();
          if (var3 == null) {
            if (var4 == null) {
              break label263;
            }
          } else if (var3.equals(var4)) {
            break label263;
          }

          return false;
        }

        Integer var5 = this.getScrollFlag();
        Integer var6 = var2.getScrollFlag();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        label249: {
          Integer var7 = this.getRelationType();
          Integer var8 = var2.getRelationType();
          if (var7 == null) {
            if (var8 == null) {
              break label249;
            }
          } else if (var7.equals(var8)) {
            break label249;
          }

          return false;
        }

        String var9 = this.getCode();
        String var10 = var2.getCode();
        if (var9 == null) {
          if (var10 != null) {
            return false;
          }
        } else if (!var9.equals(var10)) {
          return false;
        }

        label235: {
          String var11 = this.getFormTemplate();
          String var12 = var2.getFormTemplate();
          if (var11 == null) {
            if (var12 == null) {
              break label235;
            }
          } else if (var11.equals(var12)) {
            break label235;
          }

          return false;
        }

        String var13 = this.getDescription();
        String var14 = var2.getDescription();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        label221: {
          String var15 = this.getCurrentTableName();
          String var16 = var2.getCurrentTableName();
          if (var15 == null) {
            if (var16 == null) {
              break label221;
            }
          } else if (var15.equals(var16)) {
            break label221;
          }

          return false;
        }

        label214: {
          String var17 = this.getPaginationFlag();
          String var18 = var2.getPaginationFlag();
          if (var17 == null) {
            if (var18 == null) {
              break label214;
            }
          } else if (var17.equals(var18)) {
            break label214;
          }

          return false;
        }

        String var19 = this.getCheckboxFlag();
        String var20 = var2.getCheckboxFlag();
        if (var19 == null) {
          if (var20 != null) {
            return false;
          }
        } else if (!var19.equals(var20)) {
          return false;
        }

        label200: {
          List var21 = this.getColumns();
          List var22 = var2.getColumns();
          if (var21 == null) {
            if (var22 == null) {
              break label200;
            }
          } else if (var21.equals(var22)) {
            break label200;
          }

          return false;
        }

        label193: {
          List var23 = this.getHideColumns();
          List var24 = var2.getHideColumns();
          if (var23 == null) {
            if (var24 == null) {
              break label193;
            }
          } else if (var23.equals(var24)) {
            break label193;
          }

          return false;
        }

        Map var25 = this.getDictOptions();
        Map var26 = var2.getDictOptions();
        if (var25 == null) {
          if (var26 != null) {
            return false;
          }
        } else if (!var25.equals(var26)) {
          return false;
        }

        List var27 = this.getCgButtonList();
        List var28 = var2.getCgButtonList();
        if (var27 == null) {
          if (var28 != null) {
            return false;
          }
        } else if (!var27.equals(var28)) {
          return false;
        }

        label172: {
          List var29 = this.getFieldHrefSlots();
          List var30 = var2.getFieldHrefSlots();
          if (var29 == null) {
            if (var30 == null) {
              break label172;
            }
          } else if (var29.equals(var30)) {
            break label172;
          }

          return false;
        }

        String var31 = this.getEnhanceJs();
        String var32 = var2.getEnhanceJs();
        if (var31 == null) {
          if (var32 != null) {
            return false;
          }
        } else if (!var31.equals(var32)) {
          return false;
        }

        List var33 = this.getForeignKeys();
        List var34 = var2.getForeignKeys();
        if (var33 == null) {
          if (var34 != null) {
            return false;
          }
        } else if (!var33.equals(var34)) {
          return false;
        }

        label151: {
          String var35 = this.getPidField();
          String var36 = var2.getPidField();
          if (var35 == null) {
            if (var36 == null) {
              break label151;
            }
          } else if (var35.equals(var36)) {
            break label151;
          }

          return false;
        }

        String var37 = this.getHasChildrenField();
        String var38 = var2.getHasChildrenField();
        if (var37 == null) {
          if (var38 != null) {
            return false;
          }
        } else if (!var37.equals(var38)) {
          return false;
        }

        label137: {
          String var39 = this.getTextField();
          String var40 = var2.getTextField();
          if (var39 == null) {
            if (var40 == null) {
              break label137;
            }
          } else if (var39.equals(var40)) {
            break label137;
          }

          return false;
        }

        String var41 = this.getIsDesForm();
        String var42 = var2.getIsDesForm();
        if (var41 == null) {
          if (var42 != null) {
            return false;
          }
        } else if (!var41.equals(var42)) {
          return false;
        }

        String var43 = this.getDesFormCode();
        String var44 = var2.getDesFormCode();
        if (var43 == null) {
          if (var44 == null) {
            return true;
          }
        } else if (var43.equals(var44)) {
          return true;
        }

        return false;
      }
    }
  }

  protected boolean a(Object var1) {
    return var1 instanceof OnlComplexModel;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getTableType();
    int var24 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    Integer var4 = this.getScrollFlag();
    var24 = var24 * 59 + (var4 == null ? 43 : var4.hashCode());
    Integer var5 = this.getRelationType();
    var24 = var24 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getCode();
    var24 = var24 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getFormTemplate();
    var24 = var24 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getDescription();
    var24 = var24 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getCurrentTableName();
    var24 = var24 * 59 + (var9 == null ? 43 : var9.hashCode());
    String var10 = this.getPaginationFlag();
    var24 = var24 * 59 + (var10 == null ? 43 : var10.hashCode());
    String var11 = this.getCheckboxFlag();
    var24 = var24 * 59 + (var11 == null ? 43 : var11.hashCode());
    List var12 = this.getColumns();
    var24 = var24 * 59 + (var12 == null ? 43 : var12.hashCode());
    List var13 = this.getHideColumns();
    var24 = var24 * 59 + (var13 == null ? 43 : var13.hashCode());
    Map var14 = this.getDictOptions();
    var24 = var24 * 59 + (var14 == null ? 43 : var14.hashCode());
    List var15 = this.getCgButtonList();
    var24 = var24 * 59 + (var15 == null ? 43 : var15.hashCode());
    List var16 = this.getFieldHrefSlots();
    var24 = var24 * 59 + (var16 == null ? 43 : var16.hashCode());
    String var17 = this.getEnhanceJs();
    var24 = var24 * 59 + (var17 == null ? 43 : var17.hashCode());
    List var18 = this.getForeignKeys();
    var24 = var24 * 59 + (var18 == null ? 43 : var18.hashCode());
    String var19 = this.getPidField();
    var24 = var24 * 59 + (var19 == null ? 43 : var19.hashCode());
    String var20 = this.getHasChildrenField();
    var24 = var24 * 59 + (var20 == null ? 43 : var20.hashCode());
    String var21 = this.getTextField();
    var24 = var24 * 59 + (var21 == null ? 43 : var21.hashCode());
    String var22 = this.getIsDesForm();
    var24 = var24 * 59 + (var22 == null ? 43 : var22.hashCode());
    String var23 = this.getDesFormCode();
    var24 = var24 * 59 + (var23 == null ? 43 : var23.hashCode());
    return var24;
  }

  public String toString() {
    return "OnlComplexModel(code=" + this.getCode() + ", formTemplate=" + this.getFormTemplate() + ", description=" + this.getDescription() + ", currentTableName=" + this.getCurrentTableName() + ", tableType=" + this.getTableType() + ", paginationFlag=" + this.getPaginationFlag() + ", checkboxFlag=" + this.getCheckboxFlag() + ", scrollFlag=" + this.getScrollFlag() + ", columns=" + this.getColumns() + ", hideColumns=" + this.getHideColumns() + ", dictOptions=" + this.getDictOptions() + ", cgButtonList=" + this.getCgButtonList() + ", fieldHrefSlots=" + this.getFieldHrefSlots() + ", enhanceJs=" + this.getEnhanceJs() + ", foreignKeys=" + this.getForeignKeys() + ", pidField=" + this.getPidField() + ", hasChildrenField=" + this.getHasChildrenField() + ", textField=" + this.getTextField() + ", isDesForm=" + this.getIsDesForm() + ", desFormCode=" + this.getDesFormCode() + ", relationType=" + this.getRelationType() + ")";
  }
}

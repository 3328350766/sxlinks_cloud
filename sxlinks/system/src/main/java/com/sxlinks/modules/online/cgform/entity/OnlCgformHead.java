//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgform_head")
public class OnlCgformHead implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String tableName;
  private Integer tableType;
  private Integer tableVersion;
  private String tableTxt;
  private String isCheckbox;
  private String isDbSynch;
  private String isPage;
  private String isTree;
  private String idSequence;
  private String idType;
  private String queryMode;
  private Integer relationType;
  private String subTableStr;
  private Integer tabOrderNum;
  private String treeParentIdField;
  private String treeIdField;
  private String treeFieldname;
  private String formCategory;
  private String formTemplate;
  private String themeTemplate;
  private String formTemplateMobile;
  private String updateBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  private Date updateTime;
  private String createBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  private Date createTime;
  private Integer copyType;
  private Integer copyVersion;
  private String physicId;
  private transient Integer hascopy;
  private Integer scroll;
  private transient String taskId;
  private String isDesForm;
  private String desFormCode;

  public OnlCgformHead() {
  }

  public String getId() {
    return this.id;
  }

  public String getTableName() {
    return this.tableName;
  }

  public Integer getTableType() {
    return this.tableType;
  }

  public Integer getTableVersion() {
    return this.tableVersion;
  }

  public String getTableTxt() {
    return this.tableTxt;
  }

  public String getIsCheckbox() {
    return this.isCheckbox;
  }

  public String getIsDbSynch() {
    return this.isDbSynch;
  }

  public String getIsPage() {
    return this.isPage;
  }

  public String getIsTree() {
    return this.isTree;
  }

  public String getIdSequence() {
    return this.idSequence;
  }

  public String getIdType() {
    return this.idType;
  }

  public String getQueryMode() {
    return this.queryMode;
  }

  public Integer getRelationType() {
    return this.relationType;
  }

  public String getSubTableStr() {
    return this.subTableStr;
  }

  public Integer getTabOrderNum() {
    return this.tabOrderNum;
  }

  public String getTreeParentIdField() {
    return this.treeParentIdField;
  }

  public String getTreeIdField() {
    return this.treeIdField;
  }

  public String getTreeFieldname() {
    return this.treeFieldname;
  }

  public String getFormCategory() {
    return this.formCategory;
  }

  public String getFormTemplate() {
    return this.formTemplate;
  }

  public String getThemeTemplate() {
    return this.themeTemplate;
  }

  public String getFormTemplateMobile() {
    return this.formTemplateMobile;
  }

  public String getUpdateBy() {
    return this.updateBy;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public String getCreateBy() {
    return this.createBy;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public Integer getCopyType() {
    return this.copyType;
  }

  public Integer getCopyVersion() {
    return this.copyVersion;
  }

  public String getPhysicId() {
    return this.physicId;
  }

  public Integer getHascopy() {
    return this.hascopy;
  }

  public Integer getScroll() {
    return this.scroll;
  }

  public String getTaskId() {
    return this.taskId;
  }

  public String getIsDesForm() {
    return this.isDesForm;
  }

  public String getDesFormCode() {
    return this.desFormCode;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setTableType(Integer tableType) {
    this.tableType = tableType;
  }

  public void setTableVersion(Integer tableVersion) {
    this.tableVersion = tableVersion;
  }

  public void setTableTxt(String tableTxt) {
    this.tableTxt = tableTxt;
  }

  public void setIsCheckbox(String isCheckbox) {
    this.isCheckbox = isCheckbox;
  }

  public void setIsDbSynch(String isDbSynch) {
    this.isDbSynch = isDbSynch;
  }

  public void setIsPage(String isPage) {
    this.isPage = isPage;
  }

  public void setIsTree(String isTree) {
    this.isTree = isTree;
  }

  public void setIdSequence(String idSequence) {
    this.idSequence = idSequence;
  }

  public void setIdType(String idType) {
    this.idType = idType;
  }

  public void setQueryMode(String queryMode) {
    this.queryMode = queryMode;
  }

  public void setRelationType(Integer relationType) {
    this.relationType = relationType;
  }

  public void setSubTableStr(String subTableStr) {
    this.subTableStr = subTableStr;
  }

  public void setTabOrderNum(Integer tabOrderNum) {
    this.tabOrderNum = tabOrderNum;
  }

  public void setTreeParentIdField(String treeParentIdField) {
    this.treeParentIdField = treeParentIdField;
  }

  public void setTreeIdField(String treeIdField) {
    this.treeIdField = treeIdField;
  }

  public void setTreeFieldname(String treeFieldname) {
    this.treeFieldname = treeFieldname;
  }

  public void setFormCategory(String formCategory) {
    this.formCategory = formCategory;
  }

  public void setFormTemplate(String formTemplate) {
    this.formTemplate = formTemplate;
  }

  public void setThemeTemplate(String themeTemplate) {
    this.themeTemplate = themeTemplate;
  }

  public void setFormTemplateMobile(String formTemplateMobile) {
    this.formTemplateMobile = formTemplateMobile;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public void setCopyType(Integer copyType) {
    this.copyType = copyType;
  }

  public void setCopyVersion(Integer copyVersion) {
    this.copyVersion = copyVersion;
  }

  public void setPhysicId(String physicId) {
    this.physicId = physicId;
  }

  public void setHascopy(Integer hascopy) {
    this.hascopy = hascopy;
  }

  public void setScroll(Integer scroll) {
    this.scroll = scroll;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public void setIsDesForm(String isDesForm) {
    this.isDesForm = isDesForm;
  }

  public void setDesFormCode(String desFormCode) {
    this.desFormCode = desFormCode;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformHead)) {
      return false;
    } else {
      OnlCgformHead var2 = (OnlCgformHead)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label395: {
          Integer var3 = this.getTableType();
          Integer var4 = var2.getTableType();
          if (var3 == null) {
            if (var4 == null) {
              break label395;
            }
          } else if (var3.equals(var4)) {
            break label395;
          }

          return false;
        }

        Integer var5 = this.getTableVersion();
        Integer var6 = var2.getTableVersion();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        Integer var7 = this.getRelationType();
        Integer var8 = var2.getRelationType();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label374: {
          Integer var9 = this.getTabOrderNum();
          Integer var10 = var2.getTabOrderNum();
          if (var9 == null) {
            if (var10 == null) {
              break label374;
            }
          } else if (var9.equals(var10)) {
            break label374;
          }

          return false;
        }

        label367: {
          Integer var11 = this.getCopyType();
          Integer var12 = var2.getCopyType();
          if (var11 == null) {
            if (var12 == null) {
              break label367;
            }
          } else if (var11.equals(var12)) {
            break label367;
          }

          return false;
        }

        label360: {
          Integer var13 = this.getCopyVersion();
          Integer var14 = var2.getCopyVersion();
          if (var13 == null) {
            if (var14 == null) {
              break label360;
            }
          } else if (var13.equals(var14)) {
            break label360;
          }

          return false;
        }

        Integer var15 = this.getScroll();
        Integer var16 = var2.getScroll();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        label346: {
          String var17 = this.getId();
          String var18 = var2.getId();
          if (var17 == null) {
            if (var18 == null) {
              break label346;
            }
          } else if (var17.equals(var18)) {
            break label346;
          }

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

        label332: {
          String var21 = this.getTableTxt();
          String var22 = var2.getTableTxt();
          if (var21 == null) {
            if (var22 == null) {
              break label332;
            }
          } else if (var21.equals(var22)) {
            break label332;
          }

          return false;
        }

        String var23 = this.getIsCheckbox();
        String var24 = var2.getIsCheckbox();
        if (var23 == null) {
          if (var24 != null) {
            return false;
          }
        } else if (!var23.equals(var24)) {
          return false;
        }

        String var25 = this.getIsDbSynch();
        String var26 = var2.getIsDbSynch();
        if (var25 == null) {
          if (var26 != null) {
            return false;
          }
        } else if (!var25.equals(var26)) {
          return false;
        }

        label311: {
          String var27 = this.getIsPage();
          String var28 = var2.getIsPage();
          if (var27 == null) {
            if (var28 == null) {
              break label311;
            }
          } else if (var27.equals(var28)) {
            break label311;
          }

          return false;
        }

        label304: {
          String var29 = this.getIsTree();
          String var30 = var2.getIsTree();
          if (var29 == null) {
            if (var30 == null) {
              break label304;
            }
          } else if (var29.equals(var30)) {
            break label304;
          }

          return false;
        }

        String var31 = this.getIdSequence();
        String var32 = var2.getIdSequence();
        if (var31 == null) {
          if (var32 != null) {
            return false;
          }
        } else if (!var31.equals(var32)) {
          return false;
        }

        String var33 = this.getIdType();
        String var34 = var2.getIdType();
        if (var33 == null) {
          if (var34 != null) {
            return false;
          }
        } else if (!var33.equals(var34)) {
          return false;
        }

        label283: {
          String var35 = this.getQueryMode();
          String var36 = var2.getQueryMode();
          if (var35 == null) {
            if (var36 == null) {
              break label283;
            }
          } else if (var35.equals(var36)) {
            break label283;
          }

          return false;
        }

        String var37 = this.getSubTableStr();
        String var38 = var2.getSubTableStr();
        if (var37 == null) {
          if (var38 != null) {
            return false;
          }
        } else if (!var37.equals(var38)) {
          return false;
        }

        String var39 = this.getTreeParentIdField();
        String var40 = var2.getTreeParentIdField();
        if (var39 == null) {
          if (var40 != null) {
            return false;
          }
        } else if (!var39.equals(var40)) {
          return false;
        }

        label262: {
          String var41 = this.getTreeIdField();
          String var42 = var2.getTreeIdField();
          if (var41 == null) {
            if (var42 == null) {
              break label262;
            }
          } else if (var41.equals(var42)) {
            break label262;
          }

          return false;
        }

        label255: {
          String var43 = this.getTreeFieldname();
          String var44 = var2.getTreeFieldname();
          if (var43 == null) {
            if (var44 == null) {
              break label255;
            }
          } else if (var43.equals(var44)) {
            break label255;
          }

          return false;
        }

        label248: {
          String var45 = this.getFormCategory();
          String var46 = var2.getFormCategory();
          if (var45 == null) {
            if (var46 == null) {
              break label248;
            }
          } else if (var45.equals(var46)) {
            break label248;
          }

          return false;
        }

        String var47 = this.getFormTemplate();
        String var48 = var2.getFormTemplate();
        if (var47 == null) {
          if (var48 != null) {
            return false;
          }
        } else if (!var47.equals(var48)) {
          return false;
        }

        label234: {
          String var49 = this.getThemeTemplate();
          String var50 = var2.getThemeTemplate();
          if (var49 == null) {
            if (var50 == null) {
              break label234;
            }
          } else if (var49.equals(var50)) {
            break label234;
          }

          return false;
        }

        String var51 = this.getFormTemplateMobile();
        String var52 = var2.getFormTemplateMobile();
        if (var51 == null) {
          if (var52 != null) {
            return false;
          }
        } else if (!var51.equals(var52)) {
          return false;
        }

        label220: {
          String var53 = this.getUpdateBy();
          String var54 = var2.getUpdateBy();
          if (var53 == null) {
            if (var54 == null) {
              break label220;
            }
          } else if (var53.equals(var54)) {
            break label220;
          }

          return false;
        }

        Date var55 = this.getUpdateTime();
        Date var56 = var2.getUpdateTime();
        if (var55 == null) {
          if (var56 != null) {
            return false;
          }
        } else if (!var55.equals(var56)) {
          return false;
        }

        String var57 = this.getCreateBy();
        String var58 = var2.getCreateBy();
        if (var57 == null) {
          if (var58 != null) {
            return false;
          }
        } else if (!var57.equals(var58)) {
          return false;
        }

        label199: {
          Date var59 = this.getCreateTime();
          Date var60 = var2.getCreateTime();
          if (var59 == null) {
            if (var60 == null) {
              break label199;
            }
          } else if (var59.equals(var60)) {
            break label199;
          }

          return false;
        }

        label192: {
          String var61 = this.getPhysicId();
          String var62 = var2.getPhysicId();
          if (var61 == null) {
            if (var62 == null) {
              break label192;
            }
          } else if (var61.equals(var62)) {
            break label192;
          }

          return false;
        }

        String var63 = this.getIsDesForm();
        String var64 = var2.getIsDesForm();
        if (var63 == null) {
          if (var64 != null) {
            return false;
          }
        } else if (!var63.equals(var64)) {
          return false;
        }

        String var65 = this.getDesFormCode();
        String var66 = var2.getDesFormCode();
        if (var65 == null) {
          if (var66 != null) {
            return false;
          }
        } else if (!var65.equals(var66)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlCgformHead;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getTableType();
    int var35 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    Integer var4 = this.getTableVersion();
    var35 = var35 * 59 + (var4 == null ? 43 : var4.hashCode());
    Integer var5 = this.getRelationType();
    var35 = var35 * 59 + (var5 == null ? 43 : var5.hashCode());
    Integer var6 = this.getTabOrderNum();
    var35 = var35 * 59 + (var6 == null ? 43 : var6.hashCode());
    Integer var7 = this.getCopyType();
    var35 = var35 * 59 + (var7 == null ? 43 : var7.hashCode());
    Integer var8 = this.getCopyVersion();
    var35 = var35 * 59 + (var8 == null ? 43 : var8.hashCode());
    Integer var9 = this.getScroll();
    var35 = var35 * 59 + (var9 == null ? 43 : var9.hashCode());
    String var10 = this.getId();
    var35 = var35 * 59 + (var10 == null ? 43 : var10.hashCode());
    String var11 = this.getTableName();
    var35 = var35 * 59 + (var11 == null ? 43 : var11.hashCode());
    String var12 = this.getTableTxt();
    var35 = var35 * 59 + (var12 == null ? 43 : var12.hashCode());
    String var13 = this.getIsCheckbox();
    var35 = var35 * 59 + (var13 == null ? 43 : var13.hashCode());
    String var14 = this.getIsDbSynch();
    var35 = var35 * 59 + (var14 == null ? 43 : var14.hashCode());
    String var15 = this.getIsPage();
    var35 = var35 * 59 + (var15 == null ? 43 : var15.hashCode());
    String var16 = this.getIsTree();
    var35 = var35 * 59 + (var16 == null ? 43 : var16.hashCode());
    String var17 = this.getIdSequence();
    var35 = var35 * 59 + (var17 == null ? 43 : var17.hashCode());
    String var18 = this.getIdType();
    var35 = var35 * 59 + (var18 == null ? 43 : var18.hashCode());
    String var19 = this.getQueryMode();
    var35 = var35 * 59 + (var19 == null ? 43 : var19.hashCode());
    String var20 = this.getSubTableStr();
    var35 = var35 * 59 + (var20 == null ? 43 : var20.hashCode());
    String var21 = this.getTreeParentIdField();
    var35 = var35 * 59 + (var21 == null ? 43 : var21.hashCode());
    String var22 = this.getTreeIdField();
    var35 = var35 * 59 + (var22 == null ? 43 : var22.hashCode());
    String var23 = this.getTreeFieldname();
    var35 = var35 * 59 + (var23 == null ? 43 : var23.hashCode());
    String var24 = this.getFormCategory();
    var35 = var35 * 59 + (var24 == null ? 43 : var24.hashCode());
    String var25 = this.getFormTemplate();
    var35 = var35 * 59 + (var25 == null ? 43 : var25.hashCode());
    String var26 = this.getThemeTemplate();
    var35 = var35 * 59 + (var26 == null ? 43 : var26.hashCode());
    String var27 = this.getFormTemplateMobile();
    var35 = var35 * 59 + (var27 == null ? 43 : var27.hashCode());
    String var28 = this.getUpdateBy();
    var35 = var35 * 59 + (var28 == null ? 43 : var28.hashCode());
    Date var29 = this.getUpdateTime();
    var35 = var35 * 59 + (var29 == null ? 43 : var29.hashCode());
    String var30 = this.getCreateBy();
    var35 = var35 * 59 + (var30 == null ? 43 : var30.hashCode());
    Date var31 = this.getCreateTime();
    var35 = var35 * 59 + (var31 == null ? 43 : var31.hashCode());
    String var32 = this.getPhysicId();
    var35 = var35 * 59 + (var32 == null ? 43 : var32.hashCode());
    String var33 = this.getIsDesForm();
    var35 = var35 * 59 + (var33 == null ? 43 : var33.hashCode());
    String var34 = this.getDesFormCode();
    var35 = var35 * 59 + (var34 == null ? 43 : var34.hashCode());
    return var35;
  }

  public String toString() {
    return "OnlCgformHead(id=" + this.getId() + ", tableName=" + this.getTableName() + ", tableType=" + this.getTableType() + ", tableVersion=" + this.getTableVersion() + ", tableTxt=" + this.getTableTxt() + ", isCheckbox=" + this.getIsCheckbox() + ", isDbSynch=" + this.getIsDbSynch() + ", isPage=" + this.getIsPage() + ", isTree=" + this.getIsTree() + ", idSequence=" + this.getIdSequence() + ", idType=" + this.getIdType() + ", queryMode=" + this.getQueryMode() + ", relationType=" + this.getRelationType() + ", subTableStr=" + this.getSubTableStr() + ", tabOrderNum=" + this.getTabOrderNum() + ", treeParentIdField=" + this.getTreeParentIdField() + ", treeIdField=" + this.getTreeIdField() + ", treeFieldname=" + this.getTreeFieldname() + ", formCategory=" + this.getFormCategory() + ", formTemplate=" + this.getFormTemplate() + ", themeTemplate=" + this.getThemeTemplate() + ", formTemplateMobile=" + this.getFormTemplateMobile() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", copyType=" + this.getCopyType() + ", copyVersion=" + this.getCopyVersion() + ", physicId=" + this.getPhysicId() + ", hascopy=" + this.getHascopy() + ", scroll=" + this.getScroll() + ", taskId=" + this.getTaskId() + ", isDesForm=" + this.getIsDesForm() + ", desFormCode=" + this.getDesFormCode() + ")";
  }
}

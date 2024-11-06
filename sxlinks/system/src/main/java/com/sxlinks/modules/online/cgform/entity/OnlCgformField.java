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

@TableName("onl_cgform_field")
public class OnlCgformField implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String cgformHeadId;
  private String dbFieldName;
  private String dbFieldTxt;
  private String dbFieldNameOld;
  private Integer dbIsKey;
  private Integer dbIsNull;
  private String dbType;
  private Integer dbLength;
  private Integer dbPointLength;
  private String dbDefaultVal;
  private String dictField;
  private String dictTable;
  private String dictText;
  private String fieldShowType;
  private String fieldHref;
  private Integer fieldLength;
  private String fieldValidType;
  private String fieldMustInput;
  private String fieldExtendJson;
  private String fieldDefaultValue;
  private Integer isQuery;
  private Integer isShowForm;
  private Integer isShowList;
  private Integer isReadOnly;
  private String queryMode;
  private String mainTable;
  private String mainField;
  private Integer orderNum;
  private String updateBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  private Date updateTime;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  private Date createTime;
  private String createBy;
  private String converter;
  private String queryConfigFlag;
  private String queryDefVal;
  private String queryDictText;
  private String queryDictField;
  private String queryDictTable;
  private String queryShowType;
  private String queryValidType;
  private String queryMustInput;
  private String sortFlag;

  public OnlCgformField() {
  }

  public String getId() {
    return this.id;
  }

  public String getCgformHeadId() {
    return this.cgformHeadId;
  }

  public String getDbFieldName() {
    return this.dbFieldName;
  }

  public String getDbFieldTxt() {
    return this.dbFieldTxt;
  }

  public String getDbFieldNameOld() {
    return this.dbFieldNameOld;
  }

  public Integer getDbIsKey() {
    return this.dbIsKey;
  }

  public Integer getDbIsNull() {
    return this.dbIsNull;
  }

  public String getDbType() {
    return this.dbType;
  }

  public Integer getDbLength() {
    return this.dbLength;
  }

  public Integer getDbPointLength() {
    return this.dbPointLength;
  }

  public String getDbDefaultVal() {
    return this.dbDefaultVal;
  }

  public String getDictField() {
    return this.dictField;
  }

  public String getDictTable() {
    return this.dictTable;
  }

  public String getDictText() {
    return this.dictText;
  }

  public String getFieldShowType() {
    return this.fieldShowType;
  }

  public String getFieldHref() {
    return this.fieldHref;
  }

  public Integer getFieldLength() {
    return this.fieldLength;
  }

  public String getFieldValidType() {
    return this.fieldValidType;
  }

  public String getFieldMustInput() {
    return this.fieldMustInput;
  }

  public String getFieldExtendJson() {
    return this.fieldExtendJson;
  }

  public String getFieldDefaultValue() {
    return this.fieldDefaultValue;
  }

  public Integer getIsQuery() {
    return this.isQuery;
  }

  public Integer getIsShowForm() {
    return this.isShowForm;
  }

  public Integer getIsShowList() {
    return this.isShowList;
  }

  public Integer getIsReadOnly() {
    return this.isReadOnly;
  }

  public String getQueryMode() {
    return this.queryMode;
  }

  public String getMainTable() {
    return this.mainTable;
  }

  public String getMainField() {
    return this.mainField;
  }

  public Integer getOrderNum() {
    return this.orderNum;
  }

  public String getUpdateBy() {
    return this.updateBy;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public String getCreateBy() {
    return this.createBy;
  }

  public String getConverter() {
    return this.converter;
  }

  public String getQueryConfigFlag() {
    return this.queryConfigFlag;
  }

  public String getQueryDefVal() {
    return this.queryDefVal;
  }

  public String getQueryDictText() {
    return this.queryDictText;
  }

  public String getQueryDictField() {
    return this.queryDictField;
  }

  public String getQueryDictTable() {
    return this.queryDictTable;
  }

  public String getQueryShowType() {
    return this.queryShowType;
  }

  public String getQueryValidType() {
    return this.queryValidType;
  }

  public String getQueryMustInput() {
    return this.queryMustInput;
  }

  public String getSortFlag() {
    return this.sortFlag;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCgformHeadId(String cgformHeadId) {
    this.cgformHeadId = cgformHeadId;
  }

  public void setDbFieldName(String dbFieldName) {
    this.dbFieldName = dbFieldName;
  }

  public void setDbFieldTxt(String dbFieldTxt) {
    this.dbFieldTxt = dbFieldTxt;
  }

  public void setDbFieldNameOld(String dbFieldNameOld) {
    this.dbFieldNameOld = dbFieldNameOld;
  }

  public void setDbIsKey(Integer dbIsKey) {
    this.dbIsKey = dbIsKey;
  }

  public void setDbIsNull(Integer dbIsNull) {
    this.dbIsNull = dbIsNull;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  public void setDbLength(Integer dbLength) {
    this.dbLength = dbLength;
  }

  public void setDbPointLength(Integer dbPointLength) {
    this.dbPointLength = dbPointLength;
  }

  public void setDbDefaultVal(String dbDefaultVal) {
    this.dbDefaultVal = dbDefaultVal;
  }

  public void setDictField(String dictField) {
    this.dictField = dictField;
  }

  public void setDictTable(String dictTable) {
    this.dictTable = dictTable;
  }

  public void setDictText(String dictText) {
    this.dictText = dictText;
  }

  public void setFieldShowType(String fieldShowType) {
    this.fieldShowType = fieldShowType;
  }

  public void setFieldHref(String fieldHref) {
    this.fieldHref = fieldHref;
  }

  public void setFieldLength(Integer fieldLength) {
    this.fieldLength = fieldLength;
  }

  public void setFieldValidType(String fieldValidType) {
    this.fieldValidType = fieldValidType;
  }

  public void setFieldMustInput(String fieldMustInput) {
    this.fieldMustInput = fieldMustInput;
  }

  public void setFieldExtendJson(String fieldExtendJson) {
    this.fieldExtendJson = fieldExtendJson;
  }

  public void setFieldDefaultValue(String fieldDefaultValue) {
    this.fieldDefaultValue = fieldDefaultValue;
  }

  public void setIsQuery(Integer isQuery) {
    this.isQuery = isQuery;
  }

  public void setIsShowForm(Integer isShowForm) {
    this.isShowForm = isShowForm;
  }

  public void setIsShowList(Integer isShowList) {
    this.isShowList = isShowList;
  }

  public void setIsReadOnly(Integer isReadOnly) {
    this.isReadOnly = isReadOnly;
  }

  public void setQueryMode(String queryMode) {
    this.queryMode = queryMode;
  }

  public void setMainTable(String mainTable) {
    this.mainTable = mainTable;
  }

  public void setMainField(String mainField) {
    this.mainField = mainField;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
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

  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public void setConverter(String converter) {
    this.converter = converter;
  }

  public void setQueryConfigFlag(String queryConfigFlag) {
    this.queryConfigFlag = queryConfigFlag;
  }

  public void setQueryDefVal(String queryDefVal) {
    this.queryDefVal = queryDefVal;
  }

  public void setQueryDictText(String queryDictText) {
    this.queryDictText = queryDictText;
  }

  public void setQueryDictField(String queryDictField) {
    this.queryDictField = queryDictField;
  }

  public void setQueryDictTable(String queryDictTable) {
    this.queryDictTable = queryDictTable;
  }

  public void setQueryShowType(String queryShowType) {
    this.queryShowType = queryShowType;
  }

  public void setQueryValidType(String queryValidType) {
    this.queryValidType = queryValidType;
  }

  public void setQueryMustInput(String queryMustInput) {
    this.queryMustInput = queryMustInput;
  }

  public void setSortFlag(String sortFlag) {
    this.sortFlag = sortFlag;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformField)) {
      return false;
    } else {
      OnlCgformField var2 = (OnlCgformField)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label527: {
          Integer var3 = this.getDbIsKey();
          Integer var4 = var2.getDbIsKey();
          if (var3 == null) {
            if (var4 == null) {
              break label527;
            }
          } else if (var3.equals(var4)) {
            break label527;
          }

          return false;
        }

        Integer var5 = this.getDbIsNull();
        Integer var6 = var2.getDbIsNull();
        if (var5 == null) {
          if (var6 != null) {
            return false;
          }
        } else if (!var5.equals(var6)) {
          return false;
        }

        Integer var7 = this.getDbLength();
        Integer var8 = var2.getDbLength();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label506: {
          Integer var9 = this.getDbPointLength();
          Integer var10 = var2.getDbPointLength();
          if (var9 == null) {
            if (var10 == null) {
              break label506;
            }
          } else if (var9.equals(var10)) {
            break label506;
          }

          return false;
        }

        label499: {
          Integer var11 = this.getFieldLength();
          Integer var12 = var2.getFieldLength();
          if (var11 == null) {
            if (var12 == null) {
              break label499;
            }
          } else if (var11.equals(var12)) {
            break label499;
          }

          return false;
        }

        Integer var13 = this.getIsQuery();
        Integer var14 = var2.getIsQuery();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        Integer var15 = this.getIsShowForm();
        Integer var16 = var2.getIsShowForm();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        label478: {
          Integer var17 = this.getIsShowList();
          Integer var18 = var2.getIsShowList();
          if (var17 == null) {
            if (var18 == null) {
              break label478;
            }
          } else if (var17.equals(var18)) {
            break label478;
          }

          return false;
        }

        label471: {
          Integer var19 = this.getIsReadOnly();
          Integer var20 = var2.getIsReadOnly();
          if (var19 == null) {
            if (var20 == null) {
              break label471;
            }
          } else if (var19.equals(var20)) {
            break label471;
          }

          return false;
        }

        Integer var21 = this.getOrderNum();
        Integer var22 = var2.getOrderNum();
        if (var21 == null) {
          if (var22 != null) {
            return false;
          }
        } else if (!var21.equals(var22)) {
          return false;
        }

        label457: {
          String var23 = this.getId();
          String var24 = var2.getId();
          if (var23 == null) {
            if (var24 == null) {
              break label457;
            }
          } else if (var23.equals(var24)) {
            break label457;
          }

          return false;
        }

        String var25 = this.getCgformHeadId();
        String var26 = var2.getCgformHeadId();
        if (var25 == null) {
          if (var26 != null) {
            return false;
          }
        } else if (!var25.equals(var26)) {
          return false;
        }

        label443: {
          String var27 = this.getDbFieldName();
          String var28 = var2.getDbFieldName();
          if (var27 == null) {
            if (var28 == null) {
              break label443;
            }
          } else if (var27.equals(var28)) {
            break label443;
          }

          return false;
        }

        String var29 = this.getDbFieldTxt();
        String var30 = var2.getDbFieldTxt();
        if (var29 == null) {
          if (var30 != null) {
            return false;
          }
        } else if (!var29.equals(var30)) {
          return false;
        }

        String var31 = this.getDbFieldNameOld();
        String var32 = var2.getDbFieldNameOld();
        if (var31 == null) {
          if (var32 != null) {
            return false;
          }
        } else if (!var31.equals(var32)) {
          return false;
        }

        label422: {
          String var33 = this.getDbType();
          String var34 = var2.getDbType();
          if (var33 == null) {
            if (var34 == null) {
              break label422;
            }
          } else if (var33.equals(var34)) {
            break label422;
          }

          return false;
        }

        label415: {
          String var35 = this.getDbDefaultVal();
          String var36 = var2.getDbDefaultVal();
          if (var35 == null) {
            if (var36 == null) {
              break label415;
            }
          } else if (var35.equals(var36)) {
            break label415;
          }

          return false;
        }

        String var37 = this.getDictField();
        String var38 = var2.getDictField();
        if (var37 == null) {
          if (var38 != null) {
            return false;
          }
        } else if (!var37.equals(var38)) {
          return false;
        }

        String var39 = this.getDictTable();
        String var40 = var2.getDictTable();
        if (var39 == null) {
          if (var40 != null) {
            return false;
          }
        } else if (!var39.equals(var40)) {
          return false;
        }

        label394: {
          String var41 = this.getDictText();
          String var42 = var2.getDictText();
          if (var41 == null) {
            if (var42 == null) {
              break label394;
            }
          } else if (var41.equals(var42)) {
            break label394;
          }

          return false;
        }

        label387: {
          String var43 = this.getFieldShowType();
          String var44 = var2.getFieldShowType();
          if (var43 == null) {
            if (var44 == null) {
              break label387;
            }
          } else if (var43.equals(var44)) {
            break label387;
          }

          return false;
        }

        String var45 = this.getFieldHref();
        String var46 = var2.getFieldHref();
        if (var45 == null) {
          if (var46 != null) {
            return false;
          }
        } else if (!var45.equals(var46)) {
          return false;
        }

        String var47 = this.getFieldValidType();
        String var48 = var2.getFieldValidType();
        if (var47 == null) {
          if (var48 != null) {
            return false;
          }
        } else if (!var47.equals(var48)) {
          return false;
        }

        label366: {
          String var49 = this.getFieldMustInput();
          String var50 = var2.getFieldMustInput();
          if (var49 == null) {
            if (var50 == null) {
              break label366;
            }
          } else if (var49.equals(var50)) {
            break label366;
          }

          return false;
        }

        label359: {
          String var51 = this.getFieldExtendJson();
          String var52 = var2.getFieldExtendJson();
          if (var51 == null) {
            if (var52 == null) {
              break label359;
            }
          } else if (var51.equals(var52)) {
            break label359;
          }

          return false;
        }

        String var53 = this.getFieldDefaultValue();
        String var54 = var2.getFieldDefaultValue();
        if (var53 == null) {
          if (var54 != null) {
            return false;
          }
        } else if (!var53.equals(var54)) {
          return false;
        }

        label345: {
          String var55 = this.getQueryMode();
          String var56 = var2.getQueryMode();
          if (var55 == null) {
            if (var56 == null) {
              break label345;
            }
          } else if (var55.equals(var56)) {
            break label345;
          }

          return false;
        }

        String var57 = this.getMainTable();
        String var58 = var2.getMainTable();
        if (var57 == null) {
          if (var58 != null) {
            return false;
          }
        } else if (!var57.equals(var58)) {
          return false;
        }

        label331: {
          String var59 = this.getMainField();
          String var60 = var2.getMainField();
          if (var59 == null) {
            if (var60 == null) {
              break label331;
            }
          } else if (var59.equals(var60)) {
            break label331;
          }

          return false;
        }

        String var61 = this.getUpdateBy();
        String var62 = var2.getUpdateBy();
        if (var61 == null) {
          if (var62 != null) {
            return false;
          }
        } else if (!var61.equals(var62)) {
          return false;
        }

        Date var63 = this.getUpdateTime();
        Date var64 = var2.getUpdateTime();
        if (var63 == null) {
          if (var64 != null) {
            return false;
          }
        } else if (!var63.equals(var64)) {
          return false;
        }

        label310: {
          Date var65 = this.getCreateTime();
          Date var66 = var2.getCreateTime();
          if (var65 == null) {
            if (var66 == null) {
              break label310;
            }
          } else if (var65.equals(var66)) {
            break label310;
          }

          return false;
        }

        label303: {
          String var67 = this.getCreateBy();
          String var68 = var2.getCreateBy();
          if (var67 == null) {
            if (var68 == null) {
              break label303;
            }
          } else if (var67.equals(var68)) {
            break label303;
          }

          return false;
        }

        String var69 = this.getConverter();
        String var70 = var2.getConverter();
        if (var69 == null) {
          if (var70 != null) {
            return false;
          }
        } else if (!var69.equals(var70)) {
          return false;
        }

        String var71 = this.getQueryConfigFlag();
        String var72 = var2.getQueryConfigFlag();
        if (var71 == null) {
          if (var72 != null) {
            return false;
          }
        } else if (!var71.equals(var72)) {
          return false;
        }

        label282: {
          String var73 = this.getQueryDefVal();
          String var74 = var2.getQueryDefVal();
          if (var73 == null) {
            if (var74 == null) {
              break label282;
            }
          } else if (var73.equals(var74)) {
            break label282;
          }

          return false;
        }

        label275: {
          String var75 = this.getQueryDictText();
          String var76 = var2.getQueryDictText();
          if (var75 == null) {
            if (var76 == null) {
              break label275;
            }
          } else if (var75.equals(var76)) {
            break label275;
          }

          return false;
        }

        String var77 = this.getQueryDictField();
        String var78 = var2.getQueryDictField();
        if (var77 == null) {
          if (var78 != null) {
            return false;
          }
        } else if (!var77.equals(var78)) {
          return false;
        }

        String var79 = this.getQueryDictTable();
        String var80 = var2.getQueryDictTable();
        if (var79 == null) {
          if (var80 != null) {
            return false;
          }
        } else if (!var79.equals(var80)) {
          return false;
        }

        label254: {
          String var81 = this.getQueryShowType();
          String var82 = var2.getQueryShowType();
          if (var81 == null) {
            if (var82 == null) {
              break label254;
            }
          } else if (var81.equals(var82)) {
            break label254;
          }

          return false;
        }

        label247: {
          String var83 = this.getQueryValidType();
          String var84 = var2.getQueryValidType();
          if (var83 == null) {
            if (var84 == null) {
              break label247;
            }
          } else if (var83.equals(var84)) {
            break label247;
          }

          return false;
        }

        String var85 = this.getQueryMustInput();
        String var86 = var2.getQueryMustInput();
        if (var85 == null) {
          if (var86 != null) {
            return false;
          }
        } else if (!var85.equals(var86)) {
          return false;
        }

        String var87 = this.getSortFlag();
        String var88 = var2.getSortFlag();
        if (var87 == null) {
          if (var88 != null) {
            return false;
          }
        } else if (!var87.equals(var88)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlCgformField;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getDbIsKey();
    int var46 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    Integer var4 = this.getDbIsNull();
    var46 = var46 * 59 + (var4 == null ? 43 : var4.hashCode());
    Integer var5 = this.getDbLength();
    var46 = var46 * 59 + (var5 == null ? 43 : var5.hashCode());
    Integer var6 = this.getDbPointLength();
    var46 = var46 * 59 + (var6 == null ? 43 : var6.hashCode());
    Integer var7 = this.getFieldLength();
    var46 = var46 * 59 + (var7 == null ? 43 : var7.hashCode());
    Integer var8 = this.getIsQuery();
    var46 = var46 * 59 + (var8 == null ? 43 : var8.hashCode());
    Integer var9 = this.getIsShowForm();
    var46 = var46 * 59 + (var9 == null ? 43 : var9.hashCode());
    Integer var10 = this.getIsShowList();
    var46 = var46 * 59 + (var10 == null ? 43 : var10.hashCode());
    Integer var11 = this.getIsReadOnly();
    var46 = var46 * 59 + (var11 == null ? 43 : var11.hashCode());
    Integer var12 = this.getOrderNum();
    var46 = var46 * 59 + (var12 == null ? 43 : var12.hashCode());
    String var13 = this.getId();
    var46 = var46 * 59 + (var13 == null ? 43 : var13.hashCode());
    String var14 = this.getCgformHeadId();
    var46 = var46 * 59 + (var14 == null ? 43 : var14.hashCode());
    String var15 = this.getDbFieldName();
    var46 = var46 * 59 + (var15 == null ? 43 : var15.hashCode());
    String var16 = this.getDbFieldTxt();
    var46 = var46 * 59 + (var16 == null ? 43 : var16.hashCode());
    String var17 = this.getDbFieldNameOld();
    var46 = var46 * 59 + (var17 == null ? 43 : var17.hashCode());
    String var18 = this.getDbType();
    var46 = var46 * 59 + (var18 == null ? 43 : var18.hashCode());
    String var19 = this.getDbDefaultVal();
    var46 = var46 * 59 + (var19 == null ? 43 : var19.hashCode());
    String var20 = this.getDictField();
    var46 = var46 * 59 + (var20 == null ? 43 : var20.hashCode());
    String var21 = this.getDictTable();
    var46 = var46 * 59 + (var21 == null ? 43 : var21.hashCode());
    String var22 = this.getDictText();
    var46 = var46 * 59 + (var22 == null ? 43 : var22.hashCode());
    String var23 = this.getFieldShowType();
    var46 = var46 * 59 + (var23 == null ? 43 : var23.hashCode());
    String var24 = this.getFieldHref();
    var46 = var46 * 59 + (var24 == null ? 43 : var24.hashCode());
    String var25 = this.getFieldValidType();
    var46 = var46 * 59 + (var25 == null ? 43 : var25.hashCode());
    String var26 = this.getFieldMustInput();
    var46 = var46 * 59 + (var26 == null ? 43 : var26.hashCode());
    String var27 = this.getFieldExtendJson();
    var46 = var46 * 59 + (var27 == null ? 43 : var27.hashCode());
    String var28 = this.getFieldDefaultValue();
    var46 = var46 * 59 + (var28 == null ? 43 : var28.hashCode());
    String var29 = this.getQueryMode();
    var46 = var46 * 59 + (var29 == null ? 43 : var29.hashCode());
    String var30 = this.getMainTable();
    var46 = var46 * 59 + (var30 == null ? 43 : var30.hashCode());
    String var31 = this.getMainField();
    var46 = var46 * 59 + (var31 == null ? 43 : var31.hashCode());
    String var32 = this.getUpdateBy();
    var46 = var46 * 59 + (var32 == null ? 43 : var32.hashCode());
    Date var33 = this.getUpdateTime();
    var46 = var46 * 59 + (var33 == null ? 43 : var33.hashCode());
    Date var34 = this.getCreateTime();
    var46 = var46 * 59 + (var34 == null ? 43 : var34.hashCode());
    String var35 = this.getCreateBy();
    var46 = var46 * 59 + (var35 == null ? 43 : var35.hashCode());
    String var36 = this.getConverter();
    var46 = var46 * 59 + (var36 == null ? 43 : var36.hashCode());
    String var37 = this.getQueryConfigFlag();
    var46 = var46 * 59 + (var37 == null ? 43 : var37.hashCode());
    String var38 = this.getQueryDefVal();
    var46 = var46 * 59 + (var38 == null ? 43 : var38.hashCode());
    String var39 = this.getQueryDictText();
    var46 = var46 * 59 + (var39 == null ? 43 : var39.hashCode());
    String var40 = this.getQueryDictField();
    var46 = var46 * 59 + (var40 == null ? 43 : var40.hashCode());
    String var41 = this.getQueryDictTable();
    var46 = var46 * 59 + (var41 == null ? 43 : var41.hashCode());
    String var42 = this.getQueryShowType();
    var46 = var46 * 59 + (var42 == null ? 43 : var42.hashCode());
    String var43 = this.getQueryValidType();
    var46 = var46 * 59 + (var43 == null ? 43 : var43.hashCode());
    String var44 = this.getQueryMustInput();
    var46 = var46 * 59 + (var44 == null ? 43 : var44.hashCode());
    String var45 = this.getSortFlag();
    var46 = var46 * 59 + (var45 == null ? 43 : var45.hashCode());
    return var46;
  }

  public String toString() {
    return "OnlCgformField(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", dbFieldName=" + this.getDbFieldName() + ", dbFieldTxt=" + this.getDbFieldTxt() + ", dbFieldNameOld=" + this.getDbFieldNameOld() + ", dbIsKey=" + this.getDbIsKey() + ", dbIsNull=" + this.getDbIsNull() + ", dbType=" + this.getDbType() + ", dbLength=" + this.getDbLength() + ", dbPointLength=" + this.getDbPointLength() + ", dbDefaultVal=" + this.getDbDefaultVal() + ", dictField=" + this.getDictField() + ", dictTable=" + this.getDictTable() + ", dictText=" + this.getDictText() + ", fieldShowType=" + this.getFieldShowType() + ", fieldHref=" + this.getFieldHref() + ", fieldLength=" + this.getFieldLength() + ", fieldValidType=" + this.getFieldValidType() + ", fieldMustInput=" + this.getFieldMustInput() + ", fieldExtendJson=" + this.getFieldExtendJson() + ", fieldDefaultValue=" + this.getFieldDefaultValue() + ", isQuery=" + this.getIsQuery() + ", isShowForm=" + this.getIsShowForm() + ", isShowList=" + this.getIsShowList() + ", isReadOnly=" + this.getIsReadOnly() + ", queryMode=" + this.getQueryMode() + ", mainTable=" + this.getMainTable() + ", mainField=" + this.getMainField() + ", orderNum=" + this.getOrderNum() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ", createTime=" + this.getCreateTime() + ", createBy=" + this.getCreateBy() + ", converter=" + this.getConverter() + ", queryConfigFlag=" + this.getQueryConfigFlag() + ", queryDefVal=" + this.getQueryDefVal() + ", queryDictText=" + this.getQueryDictText() + ", queryDictField=" + this.getQueryDictField() + ", queryDictTable=" + this.getQueryDictTable() + ", queryShowType=" + this.getQueryShowType() + ", queryValidType=" + this.getQueryValidType() + ", queryMustInput=" + this.getQueryMustInput() + ", sortFlag=" + this.getSortFlag() + ")";
  }
}

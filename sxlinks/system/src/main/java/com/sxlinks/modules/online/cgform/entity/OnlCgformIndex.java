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

@TableName("onl_cgform_index")
public class OnlCgformIndex implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(
          type = IdType.UUID
  )
  private String id;
  private String cgformHeadId;
  private String indexName;
  private String indexField;
  private String isDbSynch;
  private Integer delFlag;
  private String indexType;
  private String createBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  private Date createTime;
  private String updateBy;
  @JsonFormat(
          timezone = "GMT+8",
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  @DateTimeFormat(
          pattern = "yyyy-MM-dd HH:mm:ss"
  )
  private Date updateTime;

  public OnlCgformIndex() {
  }

  public String getId() {
    return this.id;
  }

  public String getCgformHeadId() {
    return this.cgformHeadId;
  }

  public String getIndexName() {
    return this.indexName;
  }

  public String getIndexField() {
    return this.indexField;
  }

  public String getIsDbSynch() {
    return this.isDbSynch;
  }

  public Integer getDelFlag() {
    return this.delFlag;
  }

  public String getIndexType() {
    return this.indexType;
  }

  public String getCreateBy() {
    return this.createBy;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public String getUpdateBy() {
    return this.updateBy;
  }

  public Date getUpdateTime() {
    return this.updateTime;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCgformHeadId(String cgformHeadId) {
    this.cgformHeadId = cgformHeadId;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public void setIndexField(String indexField) {
    this.indexField = indexField;
  }

  public void setIsDbSynch(String isDbSynch) {
    this.isDbSynch = isDbSynch;
  }

  public void setDelFlag(Integer delFlag) {
    this.delFlag = delFlag;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
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

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof OnlCgformIndex)) {
      return false;
    } else {
      OnlCgformIndex var2 = (OnlCgformIndex)o;
      if (!var2.canEqual(this)) {
        return false;
      } else {
        label143: {
          Integer var3 = this.getDelFlag();
          Integer var4 = var2.getDelFlag();
          if (var3 == null) {
            if (var4 == null) {
              break label143;
            }
          } else if (var3.equals(var4)) {
            break label143;
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

        String var7 = this.getCgformHeadId();
        String var8 = var2.getCgformHeadId();
        if (var7 == null) {
          if (var8 != null) {
            return false;
          }
        } else if (!var7.equals(var8)) {
          return false;
        }

        label122: {
          String var9 = this.getIndexName();
          String var10 = var2.getIndexName();
          if (var9 == null) {
            if (var10 == null) {
              break label122;
            }
          } else if (var9.equals(var10)) {
            break label122;
          }

          return false;
        }

        label115: {
          String var11 = this.getIndexField();
          String var12 = var2.getIndexField();
          if (var11 == null) {
            if (var12 == null) {
              break label115;
            }
          } else if (var11.equals(var12)) {
            break label115;
          }

          return false;
        }

        String var13 = this.getIsDbSynch();
        String var14 = var2.getIsDbSynch();
        if (var13 == null) {
          if (var14 != null) {
            return false;
          }
        } else if (!var13.equals(var14)) {
          return false;
        }

        String var15 = this.getIndexType();
        String var16 = var2.getIndexType();
        if (var15 == null) {
          if (var16 != null) {
            return false;
          }
        } else if (!var15.equals(var16)) {
          return false;
        }

        label94: {
          String var17 = this.getCreateBy();
          String var18 = var2.getCreateBy();
          if (var17 == null) {
            if (var18 == null) {
              break label94;
            }
          } else if (var17.equals(var18)) {
            break label94;
          }

          return false;
        }

        label87: {
          Date var19 = this.getCreateTime();
          Date var20 = var2.getCreateTime();
          if (var19 == null) {
            if (var20 == null) {
              break label87;
            }
          } else if (var19.equals(var20)) {
            break label87;
          }

          return false;
        }

        String var21 = this.getUpdateBy();
        String var22 = var2.getUpdateBy();
        if (var21 == null) {
          if (var22 != null) {
            return false;
          }
        } else if (!var21.equals(var22)) {
          return false;
        }

        Date var23 = this.getUpdateTime();
        Date var24 = var2.getUpdateTime();
        if (var23 == null) {
          if (var24 != null) {
            return false;
          }
        } else if (!var23.equals(var24)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof OnlCgformIndex;
  }

  public int hashCode() {
    boolean var1 = true;
    byte var2 = 1;
    Integer var3 = this.getDelFlag();
    int var14 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
    String var4 = this.getId();
    var14 = var14 * 59 + (var4 == null ? 43 : var4.hashCode());
    String var5 = this.getCgformHeadId();
    var14 = var14 * 59 + (var5 == null ? 43 : var5.hashCode());
    String var6 = this.getIndexName();
    var14 = var14 * 59 + (var6 == null ? 43 : var6.hashCode());
    String var7 = this.getIndexField();
    var14 = var14 * 59 + (var7 == null ? 43 : var7.hashCode());
    String var8 = this.getIsDbSynch();
    var14 = var14 * 59 + (var8 == null ? 43 : var8.hashCode());
    String var9 = this.getIndexType();
    var14 = var14 * 59 + (var9 == null ? 43 : var9.hashCode());
    String var10 = this.getCreateBy();
    var14 = var14 * 59 + (var10 == null ? 43 : var10.hashCode());
    Date var11 = this.getCreateTime();
    var14 = var14 * 59 + (var11 == null ? 43 : var11.hashCode());
    String var12 = this.getUpdateBy();
    var14 = var14 * 59 + (var12 == null ? 43 : var12.hashCode());
    Date var13 = this.getUpdateTime();
    var14 = var14 * 59 + (var13 == null ? 43 : var13.hashCode());
    return var14;
  }

  public String toString() {
    return "OnlCgformIndex(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", indexName=" + this.getIndexName() + ", indexField=" + this.getIndexField() + ", isDbSynch=" + this.getIsDbSynch() + ", delFlag=" + this.getDelFlag() + ", indexType=" + this.getIndexType() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
  }
}

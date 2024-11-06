//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.config.a;

import java.util.List;
import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
import com.sxlinks.modules.online.cgform.entity.OnlCgformIndex;

public class a {
  private String a;
  private String b;
  private String c;
  private String d;
  private Integer e;
  private String f;
  private String g;
  private Integer h;
  private String i;
  private Integer j;
  private List<OnlCgformField> k;
  private List<OnlCgformIndex> l;
  private String m;
  private String n;
  private String o;
  private b p;

  public a() {
  }

  public String getTableName() {
    return this.a;
  }

  public void setTableName(String tableName) {
    this.a = tableName;
  }

  public String getIsDbSynch() {
    return this.b;
  }

  public void setIsDbSynch(String isDbSynch) {
    this.b = isDbSynch;
  }

  public String getContent() {
    return this.c;
  }

  public void setContent(String content) {
    this.c = content;
  }

  public String getJformVersion() {
    return this.d;
  }

  public void setJformVersion(String jformVersion) {
    this.d = jformVersion;
  }

  public Integer getJformType() {
    return this.e;
  }

  public void setJformType(Integer jformType) {
    this.e = jformType;
  }

  public String getJformPkType() {
    return this.f;
  }

  public void setJformPkType(String jformPkType) {
    this.f = jformPkType;
  }

  public String getJformPkSequence() {
    return this.g;
  }

  public void setJformPkSequence(String jformPkSequence) {
    this.g = jformPkSequence;
  }

  public Integer getRelationType() {
    return this.h;
  }

  public void setRelationType(Integer relationType) {
    this.h = relationType;
  }

  public String getSubTableStr() {
    return this.i;
  }

  public void setSubTableStr(String subTableStr) {
    this.i = subTableStr;
  }

  public Integer getTabOrder() {
    return this.j;
  }

  public void setTabOrder(Integer tabOrder) {
    this.j = tabOrder;
  }

  public List<OnlCgformField> getColumns() {
    return this.k;
  }

  public void setColumns(List<OnlCgformField> columns) {
    this.k = columns;
  }

  public List<OnlCgformIndex> getIndexes() {
    return this.l;
  }

  public void setIndexes(List<OnlCgformIndex> indexes) {
    this.l = indexes;
  }

  public String getTreeParentIdFieldName() {
    return this.m;
  }

  public void setTreeParentIdFieldName(String treeParentIdFieldName) {
    this.m = treeParentIdFieldName;
  }

  public String getTreeIdFieldname() {
    return this.n;
  }

  public void setTreeIdFieldname(String treeIdFieldname) {
    this.n = treeIdFieldname;
  }

  public String getTreeFieldname() {
    return this.o;
  }

  public void setTreeFieldname(String treeFieldname) {
    this.o = treeFieldname;
  }

  public b getDbConfig() {
    return this.p;
  }

  public void setDbConfig(b dbConfig) {
    this.p = dbConfig;
  }
}

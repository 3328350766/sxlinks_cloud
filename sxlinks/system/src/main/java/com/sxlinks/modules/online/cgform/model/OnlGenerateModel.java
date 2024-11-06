//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OnlGenerateModel implements Serializable {
  private static final long b = 684098897071177558L;
  private String c;
  private String d;
  private String e;
  private String f;
  private String g;
  private String h;
  private String i;
  private String j;
  private String k;
  List<OnlGenerateModel> a = new ArrayList();

  public OnlGenerateModel() {
  }

  public String getProjectPath() {
    return this.d;
  }

  public void setProjectPath(String projectPath) {
    this.d = projectPath;
  }

  public String getPackageStyle() {
    return this.e;
  }

  public void setPackageStyle(String packageStyle) {
    this.e = packageStyle;
  }

  public String getFtlDescription() {
    return this.f;
  }

  public void setFtlDescription(String ftlDescription) {
    this.f = ftlDescription;
  }

  public String getJformType() {
    return this.g;
  }

  public void setJformType(String jformType) {
    this.g = jformType;
  }

  public String getTableName() {
    return this.h;
  }

  public void setTableName(String tableName) {
    this.h = tableName;
  }

  public String getEntityPackage() {
    return this.i;
  }

  public void setEntityPackage(String entityPackage) {
    this.i = entityPackage;
  }

  public String getEntityName() {
    return this.j;
  }

  public void setEntityName(String entityName) {
    this.j = entityName;
  }

  public String getJspMode() {
    return this.k;
  }

  public void setJspMode(String jspMode) {
    this.k = jspMode;
  }

  public String getCode() {
    return this.c;
  }

  public void setCode(String code) {
    this.c = code;
  }

  public List<OnlGenerateModel> getSubList() {
    return this.a;
  }

  public void setSubList(List<OnlGenerateModel> subList) {
    this.a = subList;
  }
}

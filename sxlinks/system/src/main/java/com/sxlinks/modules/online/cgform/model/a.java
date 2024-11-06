//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.model;

import java.util.List;
import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
import com.sxlinks.modules.online.cgform.entity.OnlCgformIndex;

public class a {
  private OnlCgformHead a;
  private List<OnlCgformField> b;
  private List<String> c;
  private List<OnlCgformIndex> d;
  private List<String> e;

  public a() {
  }

  public OnlCgformHead getHead() {
    return this.a;
  }

  public void setHead(OnlCgformHead head) {
    this.a = head;
  }

  public List<OnlCgformField> getFields() {
    return this.b;
  }

  public void setFields(List<OnlCgformField> fields) {
    this.b = fields;
  }

  public List<OnlCgformIndex> getIndexs() {
    return this.d;
  }

  public void setIndexs(List<OnlCgformIndex> indexs) {
    this.d = indexs;
  }

  public List<String> getDeleteFieldIds() {
    return this.c;
  }

  public void setDeleteFieldIds(List<String> deleteFieldIds) {
    this.c = deleteFieldIds;
  }

  public List<String> getDeleteIndexIds() {
    return this.e;
  }

  public void setDeleteIndexIds(List<String> deleteIndexIds) {
    this.e = deleteIndexIds;
  }

  public String toString() {
    return "OnlCgformModel [head=" + this.a + ", fields=" + this.b + ", deleteFieldIds=" + this.c + ", indexs=" + this.d + ", deleteIndexIds=" + this.e + "]";
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.sxlinks.common.system.vo.DictModel;

public abstract class CommonProperty implements Serializable {
  private static final long l = -426159949502493187L;
  protected String a;
  protected String b;
  protected List<DictModel> c;
  protected Object d;
  protected String e;
  protected String f;
  protected Integer g;
  protected boolean h;
  protected String i;
  protected String j;
  protected Integer k;

  public CommonProperty() {
  }

  public String getDefVal() {
    return this.i;
  }

  public void setDefVal(String defVal) {
    this.i = defVal;
  }

  public boolean a() {
    return this.h;
  }

  public void setDisabled(boolean disabled) {
    this.h = disabled;
  }

  public String getView() {
    return this.e;
  }

  public void setView(String view) {
    this.e = view;
  }

  public String getKey() {
    return this.a;
  }

  public void setKey(String key) {
    this.a = key;
  }

  public String getType() {
    return this.b;
  }

  public void setType(String type) {
    this.b = type;
  }

  public List<DictModel> getInclude() {
    return this.c;
  }

  public void setInclude(List<DictModel> include) {
    this.c = include;
  }

  public Object getConstant() {
    return this.d;
  }

  public void setConstant(Object constant) {
    this.d = constant;
  }

  public String getTitle() {
    return this.f;
  }

  public void setTitle(String title) {
    this.f = title;
  }

  public Integer getOrder() {
    return this.g;
  }

  public void setOrder(Integer order) {
    this.g = order;
  }

  public String getFieldExtendJson() {
    return this.j;
  }

  public void setFieldExtendJson(String fieldExtendJson) {
    this.j = fieldExtendJson;
  }

  public Integer getDbPointLength() {
    return this.k;
  }

  public void setDbPointLength(Integer dbPointLength) {
    this.k = dbPointLength;
  }

  public abstract Map<String, Object> getPropertyJson();

  public JSONObject getCommonJson() {
    JSONObject var1 = new JSONObject();
    var1.put("type", this.b);
    if (this.c != null && this.c.size() > 0) {
      var1.put("enum", this.c);
    }

    if (this.d != null) {
      var1.put("const", this.d);
    }

    if (this.f != null) {
      var1.put("title", this.f);
    }

    if (this.g != null) {
      var1.put("order", this.g);
    }

    if (this.e == null) {
      var1.put("view", "input");
    } else {
      var1.put("view", this.e);
    }

    if (this.h) {
      String var2 = "{\"widgetattrs\":{\"disabled\":true}}";
      JSONObject var3 = JSONObject.parseObject(var2);
      var1.put("ui", var3);
    }

    if (this.i != null && this.i.length() > 0) {
      var1.put("defVal", this.i);
    }

    if (this.j != null) {
      var1.put("fieldExtendJson", this.j);
    }

    if (this.k != null) {
      var1.put("dbPointLength", this.k);
    }

    return var1;
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.sxlinks.common.util.jsonschema.CommonProperty;

public class TreeSelectProperty extends CommonProperty {
  private static final long l = 3786503639885610767L;
  private String m;
  private String n;
  private String o;
  private String p;
  private String q;
  private Integer r;

  public String getDict() {
    return this.m;
  }

  public void setDict(String dict) {
    this.m = dict;
  }

  public String getPidField() {
    return this.n;
  }

  public void setPidField(String pidField) {
    this.n = pidField;
  }

  public String getPidValue() {
    return this.o;
  }

  public void setPidValue(String pidValue) {
    this.o = pidValue;
  }

  public String getHasChildField() {
    return this.p;
  }

  public void setHasChildField(String hasChildField) {
    this.p = hasChildField;
  }

  public TreeSelectProperty() {
    this.r = 0;
  }

  public String getTextField() {
    return this.q;
  }

  public void setTextField(String textField) {
    this.q = textField;
  }

  public Integer getPidComponent() {
    return this.r;
  }

  public void setPidComponent(Integer pidComponent) {
    this.r = pidComponent;
  }

  public TreeSelectProperty(String key, String title, String dict, String pidField, String pidValue) {
    this.r = 0;
    this.b = "string";
    this.e = "sel_tree";
    this.a = key;
    this.f = title;
    this.m = dict;
    this.n = pidField;
    this.o = pidValue;
  }

  public TreeSelectProperty(String key, String title, String pidValue) {
    this.r = 0;
    this.b = "string";
    this.e = "cat_tree";
    this.a = key;
    this.f = title;
    this.o = pidValue;
  }

  public TreeSelectProperty(String key, String title, String pidValue, String textField) {
    this(key, title, pidValue);
    this.q = textField;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    if (this.m != null) {
      var2.put("dict", this.m);
    }

    if (this.n != null) {
      var2.put("pidField", this.n);
    }

    if (this.o != null) {
      var2.put("pidValue", this.o);
    }

    if (this.q != null) {
      var2.put("textField", this.q);
    }

    if (this.p != null) {
      var2.put("hasChildField", this.p);
    }

    if (this.r != null) {
      var2.put("pidComponent", this.r);
    }

    var1.put("prop", var2);
    return var1;
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sxlinks.common.system.vo.DictModel;
import com.sxlinks.common.util.jsonschema.CommonProperty;

public class NumberProperty extends CommonProperty {
  private static final long l = -558615331436437200L;
  private Integer m;
  private Integer n;
  private Integer o;
  private Integer p;
  private Integer q;
  private String r;

  public Integer getMultipleOf() {
    return this.m;
  }

  public void setMultipleOf(Integer multipleOf) {
    this.m = multipleOf;
  }

  public Integer getMaxinum() {
    return this.n;
  }

  public void setMaxinum(Integer maxinum) {
    this.n = maxinum;
  }

  public Integer getExclusiveMaximum() {
    return this.o;
  }

  public void setExclusiveMaximum(Integer exclusiveMaximum) {
    this.o = exclusiveMaximum;
  }

  public Integer getMinimum() {
    return this.p;
  }

  public void setMinimum(Integer minimum) {
    this.p = minimum;
  }

  public Integer getExclusiveMinimum() {
    return this.q;
  }

  public void setExclusiveMinimum(Integer exclusiveMinimum) {
    this.q = exclusiveMinimum;
  }

  public String getPattern() {
    return this.r;
  }

  public void setPattern(String pattern) {
    this.r = pattern;
  }

  public NumberProperty() {
  }

  public NumberProperty(String key, String title, String type) {
    this.a = key;
    this.b = type;
    this.f = title;
    this.e = "number";
  }

  public NumberProperty(String key, String title, String view, List<DictModel> include) {
    this.b = "integer";
    this.a = key;
    this.e = view;
    this.f = title;
    this.c = include;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    if (this.m != null) {
      var2.put("multipleOf", this.m);
    }

    if (this.n != null) {
      var2.put("maxinum", this.n);
    }

    if (this.o != null) {
      var2.put("exclusiveMaximum", this.o);
    }

    if (this.p != null) {
      var2.put("minimum", this.p);
    }

    if (this.q != null) {
      var2.put("exclusiveMinimum", this.q);
    }

    if (this.r != null) {
      var2.put("pattern", this.r);
    }

    var1.put("prop", var2);
    return var1;
  }
}

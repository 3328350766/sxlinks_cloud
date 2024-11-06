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

public class StringProperty extends CommonProperty {
  private static final long l = -3200493311633999539L;
  private Integer m;
  private Integer n;
  private String o;
  private String p;

  public Integer getMaxLength() {
    return this.m;
  }

  public void setMaxLength(Integer maxLength) {
    this.m = maxLength;
  }

  public Integer getMinLength() {
    return this.n;
  }

  public void setMinLength(Integer minLength) {
    this.n = minLength;
  }

  public String getPattern() {
    return this.o;
  }

  public void setPattern(String pattern) {
    this.o = pattern;
  }

  public String getErrorInfo() {
    return this.p;
  }

  public void setErrorInfo(String errorInfo) {
    this.p = errorInfo;
  }

  public StringProperty() {
  }

  public StringProperty(String key, String title, String view, Integer maxLength) {
    this.m = maxLength;
    this.a = key;
    this.e = view;
    this.f = title;
    this.b = "string";
  }

  public StringProperty(String key, String title, String view, Integer maxLength, List<DictModel> include) {
    this.m = maxLength;
    this.a = key;
    this.e = view;
    this.f = title;
    this.b = "string";
    this.c = include;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    if (this.m != null) {
      var2.put("maxLength", this.m);
    }

    if (this.n != null) {
      var2.put("minLength", this.n);
    }

    if (this.o != null) {
      var2.put("pattern", this.o);
    }

    if (this.p != null) {
      var2.put("errorInfo", this.p);
    }

    var1.put("prop", var2);
    return var1;
  }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.sxlinks.common.util.jsonschema.CommonProperty;

public class DictProperty extends CommonProperty {
  private static final long l = 3786503639885610767L;
  private String m;
  private String n;
  private String o;

  public String getDictCode() {
    return this.m;
  }

  public void setDictCode(String dictCode) {
    this.m = dictCode;
  }

  public String getDictTable() {
    return this.n;
  }

  public void setDictTable(String dictTable) {
    this.n = dictTable;
  }

  public String getDictText() {
    return this.o;
  }

  public void setDictText(String dictText) {
    this.o = dictText;
  }

  public DictProperty() {
  }

  public DictProperty(String key, String title, String dictTable, String dictCode, String dictText) {
    this.b = "string";
    this.e = "sel_search";
    this.a = key;
    this.f = title;
    this.m = dictCode;
    this.n = dictTable;
    this.o = dictText;
  }

  public DictProperty(String key, String view, String title, String dictTable, String dictCode, String dictText) {
    this.b = "string";
    this.e = view;
    this.a = key;
    this.f = title;
    this.m = dictCode;
    this.n = dictTable;
    this.o = dictText;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    if (this.m != null) {
      var2.put("dictCode", this.m);
    }

    if (this.n != null) {
      var2.put("dictTable", this.n);
    }

    if (this.o != null) {
      var2.put("dictText", this.o);
    }

    var1.put("prop", var2);
    return var1;
  }
}

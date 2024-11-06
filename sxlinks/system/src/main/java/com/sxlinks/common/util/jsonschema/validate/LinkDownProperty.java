//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sxlinks.common.util.jsonschema.CommonProperty;
import com.sxlinks.common.util.jsonschema.a;

public class LinkDownProperty extends CommonProperty {
  String l;
  List<a> m;

  public String getDictTable() {
    return this.l;
  }

  public void setDictTable(String dictTable) {
    this.l = dictTable;
  }

  public List<a> getOtherColumns() {
    return this.m;
  }

  public void setOtherColumns(List<a> otherColumns) {
    this.m = otherColumns;
  }

  public LinkDownProperty() {
  }

  public LinkDownProperty(String key, String title, String dictTable) {
    this.b = "string";
    this.e = "link_down";
    this.a = key;
    this.f = title;
    this.l = dictTable;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    JSONObject var3 = JSONObject.parseObject(this.l);
    var2.put("config", var3);
    var2.put("others", this.m);
    var1.put("prop", var2);
    return var1;
  }
}

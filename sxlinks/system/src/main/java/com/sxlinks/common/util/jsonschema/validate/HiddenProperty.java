//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.sxlinks.common.util.jsonschema.CommonProperty;

public class HiddenProperty extends CommonProperty {
  private static final long l = -8939298551502162479L;

  public HiddenProperty() {
  }

  public HiddenProperty(String key, String title) {
    this.b = "string";
    this.e = "hidden";
    this.a = key;
    this.f = title;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    var2.put("hidden", true);
    var1.put("prop", var2);
    return var1;
  }
}

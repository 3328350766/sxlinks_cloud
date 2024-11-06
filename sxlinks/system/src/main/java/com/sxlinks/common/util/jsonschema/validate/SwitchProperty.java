//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.sxlinks.common.util.jsonschema.CommonProperty;

public class SwitchProperty extends CommonProperty {
  private String l;

  public SwitchProperty() {
  }

  public SwitchProperty(String key, String title, String extendStr) {
    this.b = "string";
    this.e = "switch";
    this.a = key;
    this.f = title;
    this.l = extendStr;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    new JSONArray();
    if (this.l != null) {
      JSONArray var3 = JSONArray.parseArray(this.l);
      var2.put("extendOption", var3);
    }

    var1.put("prop", var2);
    return var1;
  }
}

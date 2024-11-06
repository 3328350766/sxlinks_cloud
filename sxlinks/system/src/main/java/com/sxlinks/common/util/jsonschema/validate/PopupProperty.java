//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.common.util.jsonschema.validate;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.sxlinks.common.util.jsonschema.CommonProperty;

public class PopupProperty extends CommonProperty {
  private static final long l = -3200493311633999539L;
  private String m;
  private String n;
  private String o;
  private Boolean p;

  public String getCode() {
    return this.m;
  }

  public void setCode(String code) {
    this.m = code;
  }

  public String getDestFields() {
    return this.n;
  }

  public void setDestFields(String destFields) {
    this.n = destFields;
  }

  public String getOrgFields() {
    return this.o;
  }

  public void setOrgFields(String orgFields) {
    this.o = orgFields;
  }

  public Boolean getPopupMulti() {
    return this.p;
  }

  public void setPopupMulti(Boolean popupMulti) {
    this.p = popupMulti;
  }

  public PopupProperty() {
  }

  public PopupProperty(String key, String title, String code, String destFields, String orgFields) {
    this.e = "popup";
    this.b = "string";
    this.a = key;
    this.f = title;
    this.m = code;
    this.n = destFields;
    this.o = orgFields;
    this.p = true;
  }

  public Map<String, Object> getPropertyJson() {
    HashMap var1 = new HashMap();
    var1.put("key", this.getKey());
    JSONObject var2 = this.getCommonJson();
    if (this.m != null) {
      var2.put("code", this.m);
    }

    if (this.n != null) {
      var2.put("destFields", this.n);
    }

    if (this.o != null) {
      var2.put("orgFields", this.o);
    }

    var2.put("popupMulti", this.p);
    var1.put("prop", var2);
    return var1;
  }
}

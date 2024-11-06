//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.sxlinks.common.system.api.ISysBaseAPI;
import com.sxlinks.common.util.SpringContextUtils;
import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;

public class a extends ExcelDataHandlerDefaultImpl {
  Map<String, OnlCgformField> a;
  ISysBaseAPI b;
  String c;
  String d;
  String e;

  public a(List<OnlCgformField> var1, String var2, String var3) {
    this.a = this.a(var1);
    this.c = var2;
    this.d = "online";
    this.e = var3;
    this.b = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
  }

  private Map<String, OnlCgformField> a(List<OnlCgformField> var1) {
    HashMap var2 = new HashMap();
    Iterator var3 = var1.iterator();

    while(var3.hasNext()) {
      OnlCgformField var4 = (OnlCgformField)var3.next();
      var2.put(var4.getDbFieldTxt(), var4);
    }

    return var2;
  }

  public void setMapValue(Map<String, Object> map, String originKey, Object value) {
    String var4 = this.a(originKey);
    if (value instanceof Double) {
      map.put(var4, PoiPublicUtil.doubleToString((Double)value));
    } else if (value instanceof byte[]) {
      byte[] var5 = (byte[])((byte[])value);
//      String var6 = com.sxlinks.modules.online.cgform.util.b.a(var5, this.c, this.d, this.e);
//      if (var6 != null) {
//        map.put(var4, var6);
//      }
    } else {
      map.put(var4, value == null ? "" : value.toString());
    }

  }

  private String a(String var1) {
    return this.a.containsKey(var1) ? "$mainTable$" + ((OnlCgformField)this.a.get(var1)).getDbFieldName() : "$subTable$" + var1;
  }
}

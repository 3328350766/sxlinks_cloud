//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.cgform.util;

import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.util.Map;
import com.sxlinks.modules.online.cgform.entity.OnlCgformField;

public class i {
  public static final String a = "int";
  public static final String b = "Integer";
  public static final String c = "double";
  public static final String d = "BigDecimal";
  public static final String e = "Blob";
  public static final String f = "Date";
  public static final String g = "datetime";
  public static final String h = "Timestamp";

  public i() {
  }

  public static boolean a(String var0) {
    return "int".equals(var0) || "double".equals(var0) || "BigDecimal".equals(var0) || "Integer".equals(var0);
  }

  public static boolean b(String var0) {
    return "Date".equalsIgnoreCase(var0) || "datetime".equalsIgnoreCase(var0) || "Timestamp".equalsIgnoreCase(var0);
  }

  public static String a(String var0, OnlCgformField var1, JSONObject var2, Map<String, Object> var3) {
    String var4 = var1.getDbType();
    String var5 = var1.getDbFieldName();
    String var6 = var1.getFieldShowType();
    if (var2.get(var5) == null) {
      return "null";
    } else if ("int".equals(var4)) {
      var3.put(var5, var2.getIntValue(var5));
      return "#{" + var5 + ",jdbcType=INTEGER}";
    } else if ("double".equals(var4)) {
      var3.put(var5, var2.getDoubleValue(var5));
      return "#{" + var5 + ",jdbcType=DOUBLE}";
    } else if ("BigDecimal".equals(var4)) {
      var3.put(var5, new BigDecimal(var2.getString(var5)));
      return "#{" + var5 + ",jdbcType=DECIMAL}";
    } else if ("Blob".equals(var4)) {
      var3.put(var5, var2.getString(var5) != null ? var2.getString(var5).getBytes() : null);
      return "#{" + var5 + ",jdbcType=BLOB}";
    } else if ("Date".equals(var4)) {
      String var7 = var2.getString(var5);
      if ("ORACLE".equals(var0)) {
        if ("date".equals(var6)) {
          var3.put(var5, var7.length() > 10 ? var7.substring(0, 10) : var7);
          return "to_date(#{" + var5 + "},'yyyy-MM-dd')";
        } else {
          var3.put(var5, var7.length() == 10 ? var2.getString(var5) + " 00:00:00" : var7);
          return "to_date(#{" + var5 + "},'yyyy-MM-dd HH24:mi:ss')";
        }
      } else if ("POSTGRESQL".equals(var0)) {
        if ("date".equals(var6)) {
          var3.put(var5, var7.length() > 10 ? var7.substring(0, 10) : var7);
          return "CAST(#{" + var5 + "} as TIMESTAMP)";
        } else {
          var3.put(var5, var7.length() == 10 ? var2.getString(var5) + " 00:00:00" : var7);
          return "CAST(#{" + var5 + "} as TIMESTAMP)";
        }
      } else {
        var3.put(var5, var2.getString(var5));
        return "#{" + var5 + "}";
      }
    } else {
      var3.put(var5, var2.getString(var5));
      return "#{" + var5 + ",jdbcType=VARCHAR}";
    }
  }
}

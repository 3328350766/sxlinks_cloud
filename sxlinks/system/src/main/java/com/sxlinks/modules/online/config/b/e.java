//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.config.b;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class e {
  protected static Map<String, String> a = new HashMap();

  public e() {
  }

  private static String a(String var0, int var1) {
    String var2 = var0;
    Iterator var3 = a.keySet().iterator();

    while(var3.hasNext()) {
      String var4 = String.valueOf(var3.next());
      String var5 = String.valueOf(a.get(var4));
      if (var1 == 1) {
        var2 = var0.replaceAll(var4, var5);
      } else if (var1 == 2) {
        var2 = var0.replaceAll(var5, var4);
      }
    }

    return var2;
  }

  static {
    a.put("class", "clazz");
  }
}

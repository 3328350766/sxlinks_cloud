////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.enhance.a;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import com.sxlinks.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
//import com.sxlinks.modules.online.config.exception.BusinessException;
//import org.springframework.stereotype.Component;
//
//@Component("cgformEnhanceQueryDemo")
//public class e implements CgformEnhanceJavaListInter {
//  public e() {
//  }
//
//  public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
//    List var3 = this.a();
//    Iterator var4 = data.iterator();
//
//    while(var4.hasNext()) {
//      Map var5 = (Map)var4.next();
//      Object var6 = var5.get("province");
//      if (var6 != null) {
//        String var7 = (String)var3.stream().filter((var1) -> {
//          return var6.toString().equals(var1.a());
//        }).map(e.a::b).findAny().orElse("");
//        var5.put("province", var7);
//      }
//    }
//
//  }
//
//  private List<e.a> a() {
//    ArrayList var1 = new ArrayList();
//    var1.add(new e.a("bj", "北京"));
//    var1.add(new e.a("sd", "山东"));
//    var1.add(new e.a("ah", "安徽"));
//    return var1;
//  }
//
//  class a {
//    String a;
//    String b;
//
//    public a(String var2, String var3) {
//      this.a = var2;
//      this.b = var3;
//    }
//
//    public String a() {
//      return this.a;
//    }
//
//    public String b() {
//      return this.b;
//    }
//  }
//}

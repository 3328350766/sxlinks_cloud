////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.config.b;
//
//import java.util.Comparator;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//
//public class f implements Comparator<OnlCgformField> {
//  public f() {
//  }
//
//  public int a(OnlCgformField var1, OnlCgformField var2) {
//    if (var1 != null && var1.getOrderNum() != null && var2 != null && var2.getOrderNum() != null) {
//      Integer var3 = var1.getOrderNum();
//      Integer var4 = var2.getOrderNum();
//      return var3 < var4 ? -1 : (var3 == var4 ? 0 : 1);
//    } else {
//      return -1;
//    }
//  }
//}

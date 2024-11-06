////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import java.util.List;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJava;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceSql;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformEnhanceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service("onlCgformEnhanceService")
//public class c implements IOnlCgformEnhanceService {
//  @Autowired
//  private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
//  @Autowired
//  private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;
//
//  public c() {
//  }
//
//  public List<OnlCgformEnhanceJava> queryEnhanceJavaList(String cgformId) {
//    LambdaQueryWrapper var2 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", cgformId);
//    List var3 = this.onlCgformEnhanceJavaMapper.selectList(var2);
//    return var3;
//  }
//
//  public void saveEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
//    this.onlCgformEnhanceJavaMapper.insert(onlCgformEnhanceJava);
//  }
//
//  public void updateEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
//    this.onlCgformEnhanceJavaMapper.updateById(onlCgformEnhanceJava);
//  }
//
//  public void deleteEnhanceJava(String id) {
//    this.onlCgformEnhanceJavaMapper.deleteById(id);
//  }
//
//  public void deleteBatchEnhanceJava(List<String> idList) {
//    this.onlCgformEnhanceJavaMapper.deleteBatchIds(idList);
//  }
//
//  public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
//    LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//    var2.eq("buttonCode", onlCgformEnhanceJava.getButtonCode());
//    var2.eq("cgformHeadId", onlCgformEnhanceJava.getCgformHeadId());
//    var2.eq("event", onlCgformEnhanceJava.getEvent());
//    Integer var3 = this.onlCgformEnhanceJavaMapper.selectCount(var2);
//    if (var3 != null) {
//      if (var3 == 1 && oConvertUtils.isEmpty(onlCgformEnhanceJava.getId())) {
//        return false;
//      }
//
//      if (var3 == 2) {
//        return false;
//      }
//    }
//
//    return true;
//  }
//
//  public boolean checkOnlyEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
//    LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//    var2.eq("buttonCode", onlCgformEnhanceSql.getButtonCode());
//    var2.eq("cgformHeadId", onlCgformEnhanceSql.getCgformHeadId());
//    Integer var3 = this.onlCgformEnhanceSqlMapper.selectCount(var2);
//    if (var3 != null) {
//      if (var3 == 1 && oConvertUtils.isEmpty(onlCgformEnhanceSql.getId())) {
//        return false;
//      }
//
//      if (var3 > 1) {
//        return false;
//      }
//    }
//
//    return true;
//  }
//
//  public List<OnlCgformEnhanceSql> queryEnhanceSqlList(String cgformId) {
//    LambdaQueryWrapper var2 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", cgformId);
//    List var3 = this.onlCgformEnhanceSqlMapper.selectList(var2);
//    return var3;
//  }
//
//  public void saveEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
//    this.onlCgformEnhanceSqlMapper.insert(onlCgformEnhanceSql);
//  }
//
//  public void updateEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
//    this.onlCgformEnhanceSqlMapper.updateById(onlCgformEnhanceSql);
//  }
//
//  public void deleteEnhanceSql(String id) {
//    this.onlCgformEnhanceSqlMapper.deleteById(id);
//  }
//
//  public void deleteBatchEnhanceSql(List<String> idList) {
//    this.onlCgformEnhanceSqlMapper.deleteBatchIds(idList);
//  }
//}

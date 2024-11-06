////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.IService;
//import java.util.List;
//import java.util.Map;
//import org.apache.ibatis.annotations.Param;
//import com.sxlinks.modules.online.cgform.a.a;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.model.TreeModel;
//
//public interface IOnlCgformFieldService extends IService<OnlCgformField> {
//  Map<String, Object> queryAutolistPage(String var1, String var2, Map<String, Object> var3, List<String> var4);
//
//  Map<String, Object> queryAutoTreeNoPage(String var1, String var2, Map<String, Object> var3, List<String> var4, String var5);
//
//  void deleteAutoListMainAndSub(OnlCgformHead var1, String var2);
//
//  void deleteAutoListById(String var1, String var2);
//
//  void deleteAutoList(String var1, String var2, String var3);
//
//  void saveFormData(String var1, String var2, JSONObject var3, boolean var4);
//
//  void saveTreeFormData(String var1, String var2, JSONObject var3, String var4, String var5);
//
//  void saveFormData(List<OnlCgformField> var1, String var2, JSONObject var3);
//
//  List<OnlCgformField> queryFormFields(String var1, boolean var2);
//
//  List<OnlCgformField> queryFormFieldsByTableName(String var1);
//
//  OnlCgformField queryFormFieldByTableNameAndField(String var1, String var2);
//
//  void editTreeFormData(String var1, String var2, JSONObject var3, String var4, String var5);
//
//  void editFormData(String var1, String var2, JSONObject var3, boolean var4);
//
//  Map<String, Object> queryFormData(String var1, String var2, String var3);
//
//  Map<String, Object> queryFormData(List<OnlCgformField> var1, String var2, String var3);
//
//  List<Map<String, Object>> querySubFormData(List<OnlCgformField> var1, String var2, String var3, String var4);
//
//  List<Map<String, String>> getAutoListQueryInfo(String var1);
//
//  IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> var1, @Param("sqlStr") String var2);
//
//  List<String> selectOnlineHideColumns(String var1);
//
//  List<OnlCgformField> queryAvailableFields(String var1, String var2, String var3, boolean var4);
//
//  List<String> queryDisabledFields(String var1);
//
//  List<String> queryDisabledFields(String var1, String var2);
//
//  List<OnlCgformField> queryAvailableFields(String var1, boolean var2, List<OnlCgformField> var3, List<String> var4);
//
//  List<OnlCgformField> queryAvailableFields(String var1, String var2, boolean var3, List<OnlCgformField> var4, List<String> var5);
//
//  void executeInsertSQL(Map<String, Object> var1);
//
//  void executeUpdatetSQL(Map<String, Object> var1);
//
//  List<TreeModel> queryDataListByLinkDown(a var1);
//
//  void updateTreeNodeNoChild(String var1, String var2, String var3);
//
//  String queryTreeChildIds(OnlCgformHead var1, String var2);
//
//  String queryTreePids(OnlCgformHead var1, String var2);
//
//  String queryForeignKey(String var1, String var2);
//}

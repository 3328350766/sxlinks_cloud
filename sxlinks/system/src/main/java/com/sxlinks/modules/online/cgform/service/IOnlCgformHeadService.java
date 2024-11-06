////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.extension.service.IService;
//import freemarker.template.TemplateException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//import org.hibernate.HibernateException;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJava;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJs;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceSql;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.model.OnlGenerateModel;
//import com.sxlinks.modules.online.cgform.model.a;
//import com.sxlinks.modules.online.config.exception.BusinessException;
//import com.sxlinks.modules.online.config.exception.DBException;
//
//public interface IOnlCgformHeadService extends IService<OnlCgformHead> {
//  Result<?> addAll(a var1);
//
//  Result<?> editAll(a var1);
//
//  void doDbSynch(String var1, String var2) throws HibernateException, IOException, TemplateException, SQLException, DBException;
//
//  void deleteRecordAndTable(String var1) throws DBException, SQLException;
//
//  void deleteRecord(String var1) throws DBException, SQLException;
//
//  List<Map<String, Object>> queryListData(String var1);
//
//  OnlCgformEnhanceJs queryEnhance(String var1, String var2);
//
//  void saveEnhance(OnlCgformEnhanceJs var1);
//
//  void editEnhance(OnlCgformEnhanceJs var1);
//
//  OnlCgformEnhanceSql queryEnhanceSql(String var1, String var2);
//
//  OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava var1);
//
//  List<OnlCgformButton> queryButtonList(String var1, boolean var2);
//
//  List<OnlCgformButton> queryButtonList(String var1);
//
//  List<String> queryOnlinetables();
//
//  void saveDbTable2Online(String var1);
//
//  JSONObject queryFormItem(OnlCgformHead var1, String var2);
//
//  String saveManyFormData(String var1, JSONObject var2, String var3) throws DBException, BusinessException;
//
//  Map<String, Object> queryManyFormData(String var1, String var2) throws DBException;
//
//  List<Map<String, Object>> queryManySubFormData(String var1, String var2) throws DBException;
//
//  Map<String, Object> querySubFormData(String var1, String var2) throws DBException;
//
//  String editManyFormData(String var1, JSONObject var2) throws DBException, BusinessException;
//
//  int executeEnhanceJava(String var1, String var2, OnlCgformHead var3, JSONObject var4) throws BusinessException;
//
//  void executeEnhanceExport(OnlCgformHead var1, List<Map<String, Object>> var2) throws BusinessException;
//
//  void executeEnhanceList(OnlCgformHead var1, String var2, List<Map<String, Object>> var3) throws BusinessException;
//
//  void executeEnhanceSql(String var1, String var2, JSONObject var3);
//
//  void executeCustomerButton(String var1, String var2, String var3) throws BusinessException;
//
//  List<OnlCgformButton> queryValidButtonList(String var1);
//
//  OnlCgformEnhanceJs queryEnhanceJs(String var1, String var2);
//
//  void deleteOneTableInfo(String var1, String var2) throws BusinessException;
//
//  List<String> generateCode(OnlGenerateModel var1) throws Exception;
//
//  List<String> generateOneToMany(OnlGenerateModel var1) throws Exception;
//
//  void addCrazyFormData(String var1, JSONObject var2) throws DBException, UnsupportedEncodingException;
//
//  void editCrazyFormData(String var1, JSONObject var2) throws DBException, UnsupportedEncodingException;
//
//  Integer getMaxCopyVersion(String var1);
//
//  void copyOnlineTableConfig(OnlCgformHead var1) throws Exception;
//
//  void initCopyState(List<OnlCgformHead> var1);
//
//  void deleteBatch(String var1, String var2);
//
//  void updateParentNode(OnlCgformHead var1, String var2);
//}

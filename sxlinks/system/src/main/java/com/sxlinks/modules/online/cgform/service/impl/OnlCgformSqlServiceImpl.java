////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.jeecg.weibo.exception.BusinessException;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import com.sxlinks.common.util.SpringContextUtils;
//
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformFieldMapper;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformSqlService;
//import com.sxlinks.modules.online.cgform.util.j;
//
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service("onlCgformSqlServiceImpl")
//public class OnlCgformSqlServiceImpl implements IOnlCgformSqlService {
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @Autowired
//    private IOnlCgformHeadService onlCgformHeadService;
//
//    public OnlCgformSqlServiceImpl() {
//    }
//
//    public void saveBatchOnlineTable(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) throws BusinessException {
//        SqlSession var4 = null;
//
//        try {
//           // b.a(2, dataList, fieldList);
//            var4 = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//            OnlCgformFieldMapper var5 = (OnlCgformFieldMapper)var4.getMapper(OnlCgformFieldMapper.class);
//            short var6 = 1000;
//            int var7;
//            String var8;
//            if (var6 >= dataList.size()) {
//                for(var7 = 0; var7 < dataList.size(); ++var7) {
//                    var8 = JSON.toJSONString(dataList.get(var7));
//                    this.a(var8, head, fieldList, var5);
//                }
//            } else {
//                for(var7 = 0; var7 < dataList.size(); ++var7) {
//                    var8 = JSON.toJSONString(dataList.get(var7));
//                    this.a(var8, head, fieldList, var5);
//                    if (var7 % var6 == 0) {
//                        var4.commit();
//                        var4.clearCache();
//                    }
//                }
//            }
//
//            var4.commit();
//        } catch (Exception var12) {
//            var4.rollback();
//            throw new BusinessException(var12.getMessage());
//        } finally {
//            var4.close();
//        }
//
//    }
//
//    public void saveOrUpdateSubData(String subDataJsonStr, OnlCgformHead head, List<OnlCgformField> subFiledList) throws BusinessException {
//        OnlCgformFieldMapper var4 = (OnlCgformFieldMapper)SpringContextUtils.getBean(OnlCgformFieldMapper.class);
//        this.a(subDataJsonStr, head, subFiledList, var4);
//    }
//
//    public Map<String, String> saveOnlineImportDataWithValidate(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) {
//        StringBuffer var4 = new StringBuffer();
//        j var5 = new j(fieldList);
//        OnlCgformFieldMapper var6 = (OnlCgformFieldMapper)SpringContextUtils.getBean(OnlCgformFieldMapper.class);
//        int var7 = 0;
//        int var8 = 0;
//        int var9 = dataList.size();
//
//        for(int var10 = 0; var10 < var9; ++var10) {
//            String var11 = JSON.toJSONString(dataList.get(var10));
//            ++var7;
//            String var12 = var5.a(var11, var7);
//            if (var12 == null) {
//                try {
//                    this.a(var11, head, fieldList, var6);
//                } catch (Exception var16) {
//                    ++var8;
//                    String var14 = this.a(var16.getCause().getMessage());
//                    String var15 = j.b(var14, var7);
//                    var4.append(var15);
//                }
//            } else {
//                ++var8;
//                var4.append(var12);
//            }
//        }
//
//        HashMap var17 = new HashMap();
//        var17.put("error", var4.toString());
//        var17.put("tip", j.a(var9, var8));
//        return var17;
//    }
//
//    private void a(String var1, OnlCgformHead var2, List<OnlCgformField> var3, OnlCgformFieldMapper var4) throws BusinessException {
//        JSONObject var5 = JSONObject.parseObject(var1);
////        int var6 = this.onlCgformHeadService.executeEnhanceJava("import", "start", var2, var5);
////        String var7 = var2.getTableName();
////        Map var8;
////        if (1 == var6) {
////            var8 = com.sxlinks.modules.online.cgform.util.b.a(var7, var3, var5);
////            var4.executeInsertSQL(var8);
////        } else if (2 == var6) {
////            var8 = com.sxlinks.modules.online.cgform.util.b.b(var7, var3, var5);
////            var4.executeUpdatetSQL(var8);
////        } else if (0 == var6) {
////        }
//
//    }
//
//    private String a(String var1) {
//        String var2 = "^Duplicate entry \\'(.*)\\' for key .*$";
//        Pattern var3 = Pattern.compile(var2);
//        Matcher var4 = var3.matcher(var1);
//        return var4.find() ? "重复数据" + var4.group(1) : var1;
//    }
//}

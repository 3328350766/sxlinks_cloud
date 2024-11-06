////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import org.apache.shiro.SecurityUtils;
//import com.sxlinks.common.system.api.ISysBaseAPI;
//import com.sxlinks.common.system.util.JeecgDataAutorUtils;
//import com.sxlinks.common.system.vo.LoginUser;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.auth.service.IOnlAuthDataService;
//import com.sxlinks.modules.online.auth.service.IOnlAuthPageService;
//import com.sxlinks.modules.online.cgform.a.a;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformFieldMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformHeadMapper;
//import com.sxlinks.modules.online.cgform.model.TreeModel;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.util.b;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service("onlCgformFieldServiceImpl")
//public class OnlCgformFieldService extends ServiceImpl<OnlCgformFieldMapper, OnlCgformField> implements IOnlCgformFieldService {
//    private static final Logger a = LoggerFactory.getLogger(OnlCgformFieldService.class);
//    @Autowired
//    private OnlCgformFieldMapper onlCgformFieldMapper;
//    @Autowired
//    private OnlCgformHeadMapper cgformHeadMapper;
//    @Autowired
//    private IOnlAuthDataService onlAuthDataService;
//    @Autowired
//    private IOnlAuthPageService onlAuthPageService;
//    @Autowired
//    private ISysBaseAPI sysBaseAPI;
//    private static final String b = "0";
//
//    public OnlCgformFieldService() {
//    }
//
//    public Map<String, Object> queryAutolistPage(String tbname, String headId, Map<String, Object> params, List<String> needList) {
//        HashMap var5 = new HashMap();
//        LambdaQueryWrapper var6 = new LambdaQueryWrapper();
//        var6.eq("cgformHeadId", headId);
//        var6.orderByAsc("orderNum");
//        List var7 = this.list(var6);
//        List var8 = this.queryAvailableFields(headId, tbname, true, var7, needList);
//        StringBuffer var9 = new StringBuffer();
//        com.sxlinks.modules.online.cgform.util.b.a(tbname, var8, var9);
//        LoginUser var10 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var11 = var10.getId();
//        List var12 = this.onlAuthDataService.queryUserOnlineAuthData(var11, headId);
//        if (var12 != null && var12.size() > 0) {
//            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(var10.getUsername()));
//        }
//
//        String var13 = com.sxlinks.modules.online.cgform.util.b.a(var7, params, needList, var12);
//        String var14 = com.sxlinks.modules.online.cgform.util.b.a(params);
//        var9.append(" where 1=1  " + var13 + var14);
//        Object var15 = params.get("column");
//        if (var15 != null) {
//            String var16 = var15.toString();
//            String var17 = params.get("order").toString();
//            if (this.a(var16, var7)) {
//                var9.append(" ORDER BY " + oConvertUtils.camelToUnderline(var16));
//                if ("asc".equals(var17)) {
//                    var9.append(" asc");
//                } else {
//                    var9.append(" desc");
//                }
//            }
//        }
//
//        Integer var20 = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
//        if (var20 == -521) {
//            List var21 = this.onlCgformFieldMapper.queryListBySql(var9.toString());
//            a.debug("---Online查询sql 不分页 :>>" + var9.toString());
//            if (var21 != null && var21.size() != 0) {
//                var5.put("total", var21.size());
//                var5.put("fieldList", var8);
//                var5.put("records", com.sxlinks.modules.online.cgform.util.b.d(var21));
//            } else {
//                var5.put("total", 0);
//                var5.put("fieldList", var8);
//            }
//        } else {
//            Integer var22 = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
//            Page var18 = new Page((long)var22, (long)var20);
//            a.debug("---Online查询sql:>>" + var9.toString());
//            IPage var19 = this.onlCgformFieldMapper.selectPageBySql(var18, var9.toString());
//            var5.put("total", var19.getTotal());
//            var5.put("records", com.sxlinks.modules.online.cgform.util.b.d(var19.getRecords()));
//        }
//
//        return var5;
//    }
//
//    public Map<String, Object> queryAutoTreeNoPage(String tbname, String headId, Map<String, Object> params, List<String> needList, String pidField) {
//        HashMap var6 = new HashMap();
//        LambdaQueryWrapper var7 = new LambdaQueryWrapper();
//        var7.eq("cgformHeadId", headId);
//        var7.orderByAsc("orderNum");
//        List var8 = this.list(var7);
//        List var9 = this.queryAvailableFields(headId, tbname, true, var8, needList);
//        StringBuffer var10 = new StringBuffer();
//        com.sxlinks.modules.online.cgform.util.b.a(tbname, var9, var10);
//        LoginUser var11 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var12 = var11.getId();
//        List var13 = this.onlAuthDataService.queryUserOnlineAuthData(var12, headId);
//        if (var13 != null && var13.size() > 0) {
//            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(var11.getUsername()));
//        }
//
//        String var14 = com.sxlinks.modules.online.cgform.util.b.a(var8, params, needList, var13);
//        String var15 = com.sxlinks.modules.online.cgform.util.b.a(params);
//        var10.append(" where 1=1  " + var14 + var15);
//        Object var16 = params.get("column");
//        if (var16 != null) {
//            String var17 = var16.toString();
//            String var18 = params.get("order").toString();
//            if (this.a(var17, var8)) {
//                var10.append(" ORDER BY " + oConvertUtils.camelToUnderline(var17));
//                if ("asc".equals(var18)) {
//                    var10.append(" asc");
//                } else {
//                    var10.append(" desc");
//                }
//            }
//        }
//
//        Integer var24 = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
//        if (var24 == -521) {
//            Object var25 = this.onlCgformFieldMapper.queryListBySql(var10.toString());
//            if ("true".equals(params.get("hasQuery"))) {
//                ArrayList var19 = new ArrayList();
//                Iterator var20 = ((List)var25).iterator();
//
//                while(true) {
//                    while(var20.hasNext()) {
//                        Map var21 = (Map)var20.next();
//                        String var22 = var21.get(pidField).toString();
//                        if (var22 != null && !"0".equals(var22)) {
//                            Map var23 = this.a(var22, tbname, headId, needList, pidField);
//                            if (var23 != null && var23.size() > 0 && !var19.contains(var23)) {
//                                var19.add(var23);
//                            }
//                        } else if (!var19.contains(var21)) {
//                            var19.add(var21);
//                        }
//                    }
//
//                    var25 = var19;
//                    break;
//                }
//            }
//
//            a.debug("---Online查询sql 不分页 :>>" + var10.toString());
//            if (var25 != null && ((List)var25).size() != 0) {
//                var6.put("total", ((List)var25).size());
//                var6.put("fieldList", var9);
//                var6.put("records", com.sxlinks.modules.online.cgform.util.b.d((List)var25));
//            } else {
//                var6.put("total", 0);
//                var6.put("fieldList", var9);
//            }
//        } else {
//            Integer var26 = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
//            Page var27 = new Page((long)var26, (long)var24);
//            a.debug("---Online查询sql:>>" + var10.toString());
//            IPage var28 = this.onlCgformFieldMapper.selectPageBySql(var27, var10.toString());
//            var6.put("total", var28.getTotal());
//            var6.put("records", com.sxlinks.modules.online.cgform.util.b.d(var28.getRecords()));
//        }
//
//        return var6;
//    }
//
//    private Map<String, Object> a(String var1, String var2, String var3, List<String> var4, String var5) {
//        HashMap var6 = new HashMap();
//        var6.put("id", var1);
//        LambdaQueryWrapper var7 = new LambdaQueryWrapper();
//        var7.eq("cgformHeadId", var3);
//        var7.orderByAsc("orderNum");
//        List var8 = this.list(var7);
//        List var9 = this.queryAvailableFields(var3, var2, true, var8, var4);
//        StringBuffer var10 = new StringBuffer();
//        com.sxlinks.modules.online.cgform.util.b.a(var2, var9, var10);
//        LoginUser var11 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var12 = var11.getId();
//        List var13 = this.onlAuthDataService.queryUserOnlineAuthData(var12, var3);
//        if (var13 != null && var13.size() > 0) {
//            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(var11.getUsername()));
//        }
//
//        String var14 = com.sxlinks.modules.online.cgform.util.b.a(var8, var6, var4, var13);
//        var10.append(" where 1=1  " + var14 + "and id='" + var1 + "'");
//        List var15 = this.onlCgformFieldMapper.queryListBySql(var10.toString());
//        if (var15 != null && var15.size() > 0) {
//            Map var16 = (Map)var15.get(0);
//            return var16 != null && var16.get(var5) != null && !"0".equals(var16.get(var5)) ? this.a(var16.get(var5).toString(), var2, var3, var4, var5) : var16;
//        } else {
//            return null;
//        }
//    }
//
//    public void saveFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
//        LambdaQueryWrapper var5 = new LambdaQueryWrapper();
//        var5.eq("cgformHeadId", code);
//        List var6 = this.list(var5);
//        if (isCrazy) {
//            ((OnlCgformFieldMapper)this.baseMapper).executeInsertSQL(com.sxlinks.modules.online.cgform.util.b.c(tbname, var6, json));
//        } else {
//            ((OnlCgformFieldMapper)this.baseMapper).executeInsertSQL(com.sxlinks.modules.online.cgform.util.b.a(tbname, var6, json));
//        }
//
//    }
//
//    public void saveTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
//        LambdaQueryWrapper var6 = new LambdaQueryWrapper();
//        var6.eq("cgformHeadId", code);
//        List var7 = this.list(var6);
//        Iterator var8 = var7.iterator();
//
//        while(true) {
//            while(var8.hasNext()) {
//                OnlCgformField var9 = (OnlCgformField)var8.next();
//                if (hasChildField.equals(var9.getDbFieldName()) && var9.getIsShowForm() != 1) {
//                    var9.setIsShowForm(1);
//                    json.put(hasChildField, "0");
//                } else if (pidField.equals(var9.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
//                    var9.setIsShowForm(1);
//                    json.put(pidField, "0");
//                }
//            }
//
//            Map var10 = com.sxlinks.modules.online.cgform.util.b.a(tbname, var7, json);
//            ((OnlCgformFieldMapper)this.baseMapper).executeInsertSQL(var10);
//            if (!"0".equals(json.getString(pidField))) {
//                ((OnlCgformFieldMapper)this.baseMapper).editFormData("update " + tbname + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + "'");
//            }
//
//            return;
//        }
//    }
//
//    public void saveFormData(List<OnlCgformField> fieldList, String tbname, JSONObject json) {
//        Map var4 = com.sxlinks.modules.online.cgform.util.b.a(tbname, fieldList, json);
//        ((OnlCgformFieldMapper)this.baseMapper).executeInsertSQL(var4);
//    }
//
//    public void editFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
//        LambdaQueryWrapper var5 = new LambdaQueryWrapper();
//        var5.eq("cgformHeadId", code);
//        List var6 = this.list(var5);
//        if (isCrazy) {
//            ((OnlCgformFieldMapper)this.baseMapper).executeUpdatetSQL(com.sxlinks.modules.online.cgform.util.b.d(tbname, var6, json));
//        } else {
//            ((OnlCgformFieldMapper)this.baseMapper).executeUpdatetSQL(com.sxlinks.modules.online.cgform.util.b.b(tbname, var6, json));
//        }
//
//    }
//
//    public void editTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
//        String var6 = com.sxlinks.modules.online.cgform.util.b.f(tbname);
//        String var7 = "select * from " + var6 + " where id = '" + json.getString("id") + "'";
//        Map var8 = ((OnlCgformFieldMapper)this.baseMapper).queryFormData(var7);
//        Map var9 = com.sxlinks.modules.online.cgform.util.b.b(var8);
//        String var10 = var9.get(pidField).toString();
//        LambdaQueryWrapper var11 = new LambdaQueryWrapper();
//        var11.eq("cgformHeadId", code);
//        List var12 = this.list(var11);
//        Iterator var13 = var12.iterator();
//
//        while(var13.hasNext()) {
//            OnlCgformField var14 = (OnlCgformField)var13.next();
//            if (pidField.equals(var14.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
//                var14.setIsShowForm(1);
//                json.put(pidField, "0");
//            }
//        }
//
//        Map var16 = com.sxlinks.modules.online.cgform.util.b.b(tbname, var12, json);
//        ((OnlCgformFieldMapper)this.baseMapper).executeUpdatetSQL(var16);
//        if (!var10.equals(json.getString(pidField))) {
//            if (!"0".equals(var10)) {
//                String var17 = "select count(*) from " + var6 + " where " + pidField + " = '" + var10 + "'";
//                Integer var15 = ((OnlCgformFieldMapper)this.baseMapper).queryCountBySql(var17);
//                if (var15 == null || var15 == 0) {
//                    ((OnlCgformFieldMapper)this.baseMapper).editFormData("update " + var6 + " set " + hasChildField + " = '0' where id = '" + var10 + "'");
//                }
//            }
//
//            if (!"0".equals(json.getString(pidField))) {
//                ((OnlCgformFieldMapper)this.baseMapper).editFormData("update " + var6 + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + "'");
//            }
//        }
//
//    }
//
//    public Map<String, Object> queryFormData(String code, String tbname, String id) {
//        LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//        var4.eq("cgformHeadId", code);
//        var4.eq("isShowForm", 1);
//        List var5 = this.list(var4);
//        String var6 = com.sxlinks.modules.online.cgform.util.b.a(tbname, var5, id);
//        return this.onlCgformFieldMapper.queryFormData(var6);
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public void deleteAutoListMainAndSub(OnlCgformHead head, String ids) {
//        if (head.getTableType() == 2) {
//            String var3 = head.getId();
//            String var4 = head.getTableName();
//            String var5 = "tableName";
//            String var6 = "linkField";
//            String var7 = "linkValueStr";
//            String var8 = "mainField";
//            ArrayList var9 = new ArrayList();
//            if (oConvertUtils.isNotEmpty(head.getSubTableStr())) {
//                String[] var10 = head.getSubTableStr().split(",");
//                int var11 = var10.length;
//
//                for(int var12 = 0; var12 < var11; ++var12) {
//                    String var13 = var10[var12];
//                    OnlCgformHead var14 = (OnlCgformHead)this.cgformHeadMapper.selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var13));
//                    if (var14 != null) {
//                        LambdaQueryWrapper var15 = (LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var14.getId())).eq("mainTable", head.getTableName());
//                        List var16 = this.list(var15);
//                        if (var16 != null && var16.size() != 0) {
//                            OnlCgformField var17 = (OnlCgformField)var16.get(0);
//                            HashMap var18 = new HashMap();
//                            var18.put(var6, var17.getDbFieldName());
//                            var18.put(var8, var17.getMainField());
//                            var18.put(var5, var13);
//                            var18.put(var7, "");
//                            var9.add(var18);
//                        }
//                    }
//                }
//
//                LambdaQueryWrapper var24 = new LambdaQueryWrapper();
//                var24.eq("cgformHeadId", var3);
//                List var25 = this.list(var24);
//                String[] var26 = ids.split(",");
//                String[] var27 = var26;
//                int var29 = var26.length;
//                int var31 = 0;
//
//                label52:
//                while(true) {
//                    if (var31 >= var29) {
//                        Iterator var28 = var9.iterator();
//
//                        while(true) {
//                            if (!var28.hasNext()) {
//                                break label52;
//                            }
//
//                            Map var30 = (Map)var28.next();
//                            this.deleteAutoList((String)var30.get(var5), (String)var30.get(var6), (String)var30.get(var7));
//                        }
//                    }
//
//                    String var32 = var27[var31];
//                    String var33 = com.sxlinks.modules.online.cgform.util.b.a(var4, var25, var32);
//                    Map var34 = this.onlCgformFieldMapper.queryFormData(var33);
//                    new ArrayList();
//                    Iterator var20 = var9.iterator();
//
//                    while(var20.hasNext()) {
//                        Map var21 = (Map)var20.next();
//                        Object var22 = var34.get(((String)var21.get(var8)).toLowerCase());
//                        if (var22 == null) {
//                            var22 = var34.get(((String)var21.get(var8)).toUpperCase());
//                        }
//
//                        if (var22 != null) {
//                            String var23 = (String)var21.get(var7) + String.valueOf(var22) + ",";
//                            var21.put(var7, var23);
//                        }
//                    }
//
//                    ++var31;
//                }
//            }
//
//            this.deleteAutoListById(head.getTableName(), ids);
//        }
//
//    }
//
//    public void deleteAutoListById(String tbname, String ids) {
//        this.deleteAutoList(tbname, "id", ids);
//    }
//
//    public void deleteAutoList(String tbname, String linkField, String linkValue) {
//        if (linkValue != null && !"".equals(linkValue)) {
//            String[] var4 = linkValue.split(",");
//            StringBuffer var5 = new StringBuffer();
//            String[] var6 = var4;
//            int var7 = var4.length;
//
//            for(int var8 = 0; var8 < var7; ++var8) {
//                String var9 = var6[var8];
//                if (var9 != null && !"".equals(var9)) {
//                    var5.append("'" + var9 + "',");
//                }
//            }
//
//            String var10 = var5.toString();
//            String var11 = "DELETE FROM " + com.sxlinks.modules.online.cgform.util.b.f(tbname) + " where " + linkField + " in(" + var10.substring(0, var10.length() - 1) + ")";
//            a.debug("--删除sql-->" + var11);
//            this.onlCgformFieldMapper.deleteAutoList(var11);
//        }
//
//    }
//
//    public List<Map<String, String>> getAutoListQueryInfo(String code) {
//        LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//        var2.eq("cgformHeadId", code);
//        var2.eq("isQuery", 1);
//        var2.orderByAsc("orderNum");
//        List var3 = this.list(var2);
//        ArrayList var4 = new ArrayList();
//        int var5 = 0;
//
//        HashMap var8;
//        for(Iterator var6 = var3.iterator(); var6.hasNext(); var4.add(var8)) {
//            OnlCgformField var7 = (OnlCgformField)var6.next();
//            var8 = new HashMap();
//            var8.put("label", var7.getDbFieldTxt());
//            var8.put("field", var7.getDbFieldName());
//            var8.put("mode", var7.getQueryMode());
//            String[] var9;
//            String var10;
//            if ("1".equals(var7.getQueryConfigFlag())) {
//                var8.put("config", "1");
//                var8.put("view", var7.getQueryShowType());
//                var8.put("defValue", var7.getQueryDefVal());
//                if ("cat_tree".equals(var7.getFieldShowType())) {
//                    var8.put("pcode", var7.getQueryDictField());
//                } else if ("sel_tree".equals(var7.getFieldShowType())) {
//                    var9 = var7.getQueryDictText().split(",");
//                    var10 = var7.getQueryDictTable() + "," + var9[2] + "," + var9[0];
//                    var8.put("dict", var10);
//                    var8.put("pidField", var9[1]);
//                    var8.put("hasChildField", var9[3]);
//                    var8.put("pidValue", var7.getQueryDictField());
//                } else {
//                    var8.put("dictTable", var7.getQueryDictTable());
//                    var8.put("dictCode", var7.getQueryDictField());
//                    var8.put("dictText", var7.getQueryDictText());
//                }
//            } else {
//                var8.put("view", var7.getFieldShowType());
//                if ("cat_tree".equals(var7.getFieldShowType())) {
//                    var8.put("pcode", var7.getDictField());
//                } else if ("sel_tree".equals(var7.getFieldShowType())) {
//                    var9 = var7.getDictText().split(",");
//                    var10 = var7.getDictTable() + "," + var9[2] + "," + var9[0];
//                    var8.put("dict", var10);
//                    var8.put("pidField", var9[1]);
//                    var8.put("hasChildField", var9[3]);
//                    var8.put("pidValue", var7.getDictField());
//                } else if ("popup".equals(var7.getFieldShowType())) {
//                    var8.put("dictTable", var7.getDictTable());
//                    var8.put("dictCode", var7.getDictField());
//                    var8.put("dictText", var7.getDictText());
//                } else if ("sel_search".equals(var7.getFieldShowType())) {
//                    var8.put("dictTable", var7.getDictTable());
//                    var8.put("dictCode", var7.getDictField());
//                    var8.put("dictText", var7.getDictText());
//                }
//
//                var8.put("mode", var7.getQueryMode());
//            }
//
//            ++var5;
//            if (var5 > 2) {
//                var8.put("hidden", "1");
//            }
//        }
//
//        return var4;
//    }
//
//    public List<OnlCgformField> queryFormFields(String code, boolean isform) {
//        LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//        var3.eq("cgformHeadId", code);
//        if (isform) {
//            var3.eq("isShowForm", 1);
//        }
//
//        return this.list(var3);
//    }
//
//    public List<OnlCgformField> queryFormFieldsByTableName(String tableName) {
//        OnlCgformHead var2 = (OnlCgformHead)this.cgformHeadMapper.selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", tableName));
//        if (var2 != null) {
//            LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//            var3.eq("cgformHeadId", var2.getId());
//            return this.list(var3);
//        } else {
//            return null;
//        }
//    }
//
//    public OnlCgformField queryFormFieldByTableNameAndField(String tableName, String fieldName) {
//        OnlCgformHead var3 = (OnlCgformHead)this.cgformHeadMapper.selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", tableName));
//        if (var3 != null) {
//            LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//            var4.eq("cgformHeadId", var3.getId());
//            var4.eq("dbFieldName", fieldName);
//            if (this.list(var4) != null && this.list(var4).size() > 0) {
//                return (OnlCgformField)this.list(var4).get(0);
//            }
//        }
//
//        return null;
//    }
//
//    public Map<String, Object> queryFormData(List<OnlCgformField> fieldList, String tbname, String id) {
//        String var4 = com.sxlinks.modules.online.cgform.util.b.a(tbname, fieldList, id);
//        return this.onlCgformFieldMapper.queryFormData(var4);
//    }
//
//    public List<Map<String, Object>> querySubFormData(List<OnlCgformField> fieldList, String tbname, String linkField, String value) {
//        String var5 = com.sxlinks.modules.online.cgform.util.b.a(tbname, fieldList, linkField, value);
//        return this.onlCgformFieldMapper.queryListData(var5);
//    }
//
//    public IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> page, String sql) {
//        return ((OnlCgformFieldMapper)this.baseMapper).selectPageBySql(page, sql);
//    }
//
//    public List<String> selectOnlineHideColumns(String tbname) {
//        String var2 = "online:" + tbname + ":%";
//        LoginUser var3 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var4 = var3.getId();
//        List var5 = ((OnlCgformFieldMapper)this.baseMapper).selectOnlineHideColumns(var4, var2);
//        return this.a(var5);
//    }
//
//    public List<OnlCgformField> queryAvailableFields(String cgFormId, String tbname, String taskId, boolean isList) {
//        LambdaQueryWrapper var5 = new LambdaQueryWrapper();
//        var5.eq("cgformHeadId", cgFormId);
//        if (isList) {
//            var5.eq("isShowList", 1);
//        } else {
//            var5.eq("isShowForm", 1);
//        }
//
//        var5.orderByAsc("orderNum");
//        List var6 = this.list(var5);
//        String var7 = "online:" + tbname + "%";
//        LoginUser var8 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var9 = var8.getId();
//        ArrayList var10 = new ArrayList();
//        List var11;
//        if (oConvertUtils.isEmpty(taskId)) {
//            var11 = this.onlAuthPageService.queryHideCode(var9, cgFormId, isList);
//            if (var11 != null && var11.size() != 0 && var11.get(0) != null) {
//                var10.addAll(var11);
//            }
//        } else if (oConvertUtils.isNotEmpty(taskId)) {
//            var11 = ((OnlCgformFieldMapper)this.baseMapper).selectFlowAuthColumns(tbname, taskId, "1");
//            if (var11 != null && var11.size() > 0 && var11.get(0) != null) {
//                var10.addAll(var11);
//            }
//        }
//
//        if (var10.size() == 0) {
//            return var6;
//        } else {
//            ArrayList var14 = new ArrayList();
//
//            for(int var12 = 0; var12 < var6.size(); ++var12) {
//                OnlCgformField var13 = (OnlCgformField)var6.get(var12);
//                if (this.b(var13.getDbFieldName(), var10)) {
//                    var14.add(var13);
//                }
//            }
//
//            return var14;
//        }
//    }
//
//    public List<String> queryDisabledFields(String tbname) {
//        String var2 = "online:" + tbname + "%";
//        LoginUser var3 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var4 = var3.getId();
//        List var5 = ((OnlCgformFieldMapper)this.baseMapper).selectOnlineDisabledColumns(var4, var2);
//        return this.a(var5);
//    }
//
//    public List<String> queryDisabledFields(String tbname, String taskId) {
//        if (oConvertUtils.isEmpty(taskId)) {
//            return null;
//        } else {
//            List var3 = ((OnlCgformFieldMapper)this.baseMapper).selectFlowAuthColumns(tbname, taskId, "2");
//            return this.a(var3);
//        }
//    }
//
//    private List<String> a(List<String> var1) {
//        ArrayList var2 = new ArrayList();
//        if (var1 != null && var1.size() != 0 && var1.get(0) != null) {
//            Iterator var3 = var1.iterator();
//
//            while(var3.hasNext()) {
//                String var4 = (String)var3.next();
//                if (!oConvertUtils.isEmpty(var4)) {
//                    String var5 = var4.substring(var4.lastIndexOf(":") + 1);
//                    if (!oConvertUtils.isEmpty(var5)) {
//                        var2.add(var5);
//                    }
//                }
//            }
//
//            return var2;
//        } else {
//            return var2;
//        }
//    }
//
//    public List<OnlCgformField> queryAvailableFields(String tbname, boolean isList, List<OnlCgformField> List, List<String> needList) {
//        String var5 = "online:" + tbname + "%";
//        LoginUser var6 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var7 = var6.getId();
//        List var8 = ((OnlCgformFieldMapper)this.baseMapper).selectOnlineHideColumns(var7, var5);
//        return this.a(var8, isList, List, needList);
//    }
//
//    public List<OnlCgformField> queryAvailableFields(String cgformId, String tbname, boolean isList, List<OnlCgformField> List, List<String> needList) {
//        LoginUser var6 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        String var7 = var6.getId();
//        List var8 = this.onlAuthPageService.queryListHideColumn(var7, cgformId);
//        return this.a(var8, isList, List, needList);
//    }
//
//    private List<OnlCgformField> a(List<String> var1, boolean var2, List<OnlCgformField> var3, List<String> var4) {
//        ArrayList var5 = new ArrayList();
//        boolean var6 = true;
//        if (var1 == null || var1.size() == 0 || var1.get(0) == null) {
//            var6 = false;
//        }
//
//        Iterator var7 = var3.iterator();
//
//        while(true) {
//            while(var7.hasNext()) {
//                OnlCgformField var8 = (OnlCgformField)var7.next();
//                String var9 = var8.getDbFieldName();
//                if (var4 != null && var4.contains(var9)) {
//                    var8.setIsQuery(1);
//                    var5.add(var8);
//                } else {
//                    if (var2) {
//                        if (var8.getIsShowList() != 1) {
//                            if (oConvertUtils.isNotEmpty(var8.getMainTable()) && oConvertUtils.isNotEmpty(var8.getMainField())) {
//                                var5.add(var8);
//                            }
//                            continue;
//                        }
//                    } else if (var8.getIsShowForm() != 1) {
//                        continue;
//                    }
//
//                    if (var6) {
//                        if (this.b(var9, var1)) {
//                            var5.add(var8);
//                        }
//                    } else {
//                        var5.add(var8);
//                    }
//                }
//            }
//
//            return var5;
//        }
//    }
//
//    private boolean b(String var1, List<String> var2) {
//        boolean var3 = true;
//
//        for(int var4 = 0; var4 < var2.size(); ++var4) {
//            String var5 = (String)var2.get(var4);
//            if (!oConvertUtils.isEmpty(var5)) {
//                String var6 = var5.substring(var5.lastIndexOf(":") + 1);
//                if (!oConvertUtils.isEmpty(var6) && var6.equals(var1)) {
//                    var3 = false;
//                }
//            }
//        }
//
//        return var3;
//    }
//
//    public boolean a(String var1, List<OnlCgformField> var2) {
//        boolean var3 = false;
//        Iterator var4 = var2.iterator();
//
//        while(var4.hasNext()) {
//            OnlCgformField var5 = (OnlCgformField)var4.next();
//            if (oConvertUtils.camelToUnderline(var1).equals(var5.getDbFieldName())) {
//                var3 = true;
//                break;
//            }
//        }
//
//        return var3;
//    }
//
//    public void executeInsertSQL(Map<String, Object> params) {
//        ((OnlCgformFieldMapper)this.baseMapper).executeInsertSQL(params);
//    }
//
//    public void executeUpdatetSQL(Map<String, Object> params) {
//        ((OnlCgformFieldMapper)this.baseMapper).executeUpdatetSQL(params);
//    }
//
//    public List<TreeModel> queryDataListByLinkDown(a linkDown) {
//        return ((OnlCgformFieldMapper)this.baseMapper).queryDataListByLinkDown(linkDown);
//    }
//
//    public void updateTreeNodeNoChild(String tableName, String filed, String id) {
//        Map var4 = com.sxlinks.modules.online.cgform.util.b.a(tableName, filed, id);
//        ((OnlCgformFieldMapper)this.baseMapper).executeUpdatetSQL(var4);
//    }
//
//    public String queryTreeChildIds(OnlCgformHead head, String ids) {
//        String var3 = head.getTreeParentIdField();
//        String var4 = head.getTableName();
//        String[] var5 = ids.split(",");
//        StringBuffer var6 = new StringBuffer();
//        String[] var7 = var5;
//        int var8 = var5.length;
//
//        for(int var9 = 0; var9 < var8; ++var9) {
//            String var10 = var7[var9];
//            if (var10 != null && !var6.toString().contains(var10)) {
//                if (var6.toString().length() > 0) {
//                    var6.append(",");
//                }
//
//                var6.append(var10);
//                this.a(var10, var3, var4, var6);
//            }
//        }
//
//        return var6.toString();
//    }
//
//    public String queryTreePids(OnlCgformHead head, String ids) {
//        String var3 = head.getTreeParentIdField();
//        String var4 = head.getTableName();
//        StringBuffer var5 = new StringBuffer();
//        String[] var6 = ids.split(",");
//        String[] var7 = var6;
//        int var8 = var6.length;
//
//        for(int var9 = 0; var9 < var8; ++var9) {
//            String var10 = var7[var9];
//            if (var10 != null) {
//                String var11 = com.sxlinks.modules.online.cgform.util.b.f(var4);
//                String var12 = "select * from " + var11 + " where id = '" + var10 + "'";
//                Map var13 = ((OnlCgformFieldMapper)this.baseMapper).queryFormData(var12);
//                Map var14 = com.sxlinks.modules.online.cgform.util.b.b(var13);
//                String var15 = var14.get(var3).toString();
//                String var16 = "select * from " + com.sxlinks.modules.online.cgform.util.b.f(var4) + " where " + var3 + "= '" + var15 + "' and id not in(" + ids + ")";
//                List var17 = this.onlCgformFieldMapper.queryListBySql(var16);
//                if ((var17 == null || var17.size() == 0) && !Arrays.asList(var6).contains(var15) && !var5.toString().contains(var15)) {
//                    var5.append(var15).append(",");
//                }
//            }
//        }
//
//        return var5.toString();
//    }
//
//    public String queryForeignKey(String cgFormId, String mainTable) {
//        LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//        var3.eq("cgformHeadId", cgFormId);
//        var3.eq("mainTable", mainTable);
//        List var4 = this.list(var3);
//        return var4 != null && var4.size() > 0 ? ((OnlCgformField)var4.get(0)).getMainField() : null;
//    }
//
//    private StringBuffer a(String var1, String var2, String var3, StringBuffer var4) {
//        String var5 = "select * from " + com.sxlinks.modules.online.cgform.util.b.f(var3) + " where " + var2 + "= '" + var1 + "'";
//        List var6 = this.onlCgformFieldMapper.queryListBySql(var5);
//        Map var9;
//        if (var6 != null && var6.size() > 0) {
//            for(Iterator var7 = var6.iterator(); var7.hasNext(); this.a(var9.get("id").toString(), var2, var3, var4)) {
//                Map var8 = (Map)var7.next();
//                var9 = com.sxlinks.modules.online.cgform.util.b.b(var8);
//                if (!var4.toString().contains(var9.get("id").toString())) {
//                    var4.append(",").append(var9.get("id"));
//                }
//            }
//        }
//
//        return var4;
//    }
//}

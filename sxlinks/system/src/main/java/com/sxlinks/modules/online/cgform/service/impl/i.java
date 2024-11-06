////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import com.sxlinks.common.system.api.ISysBaseAPI;
//import com.sxlinks.common.system.vo.LoginUser;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.auth.service.IOnlAuthPageService;
//import com.sxlinks.modules.online.cgform.a.a;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJs;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.model.HrefSlots;
//import com.sxlinks.modules.online.cgform.model.OnlColumn;
//import com.sxlinks.modules.online.cgform.model.OnlComplexModel;
//import com.sxlinks.modules.online.cgform.model.b;
//import com.sxlinks.modules.online.cgform.model.c;
//import com.sxlinks.modules.online.cgform.model.d;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import com.sxlinks.modules.online.cgform.service.IOnlineService;
//import com.sxlinks.modules.online.cgform.util.EnhanceJsUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//@Service("onlineService")
//public class i implements IOnlineService {
//    @Autowired
//    private IOnlCgformFieldService onlCgformFieldService;
//    @Autowired
//    private IOnlCgformHeadService onlCgformHeadService;
//    @Autowired
//    private ISysBaseAPI sysBaseAPI;
//    @Autowired
//    private IOnlAuthPageService onlAuthPageService;
//
//    public i() {
//    }
//
//    public OnlComplexModel queryOnlineConfig(OnlCgformHead head, String username) {
//        String var3 = head.getId();
//        List var4 = this.a(var3);
//        List var5 = this.onlAuthPageService.queryHideCode(var3, true);
//        ArrayList var6 = new ArrayList();
//        HashMap var7 = new HashMap();
//        ArrayList var8 = new ArrayList();
//        ArrayList var9 = new ArrayList();
//        ArrayList var10 = new ArrayList();
//        Iterator var11 = var4.iterator();
//
//        String var15;
//        while(var11.hasNext()) {
//            OnlCgformField var12 = (OnlCgformField)var11.next();
//            String var13 = var12.getDbFieldName();
//            String var14 = var12.getMainTable();
//            var15 = var12.getMainField();
//            if (oConvertUtils.isNotEmpty(var15) && oConvertUtils.isNotEmpty(var14)) {
//                b var16 = new b(var13, var15);
//                var9.add(var16);
//            }
//
//            if (1 == var12.getIsShowList() && !"id".equals(var13) && !var5.contains(var13) && !var10.contains(var13)) {
//                OnlColumn var29 = new OnlColumn(var12.getDbFieldTxt(), var13);
//                String var17 = var12.getDictField();
//                String var18 = var12.getFieldShowType();
//                if (oConvertUtils.isNotEmpty(var17) && !"popup".equals(var18)) {
//                    Object var19 = new ArrayList();
//                    if (oConvertUtils.isNotEmpty(var12.getDictTable())) {
//                        var19 = this.sysBaseAPI.queryTableDictItemsByCode(var12.getDictTable(), var12.getDictText(), var17);
//                    } else if (oConvertUtils.isNotEmpty(var12.getDictField())) {
//                        var19 = this.sysBaseAPI.queryDictItemsByCode(var17);
//                    }
//
//                    var7.put(var13, var19);
//                    var29.setCustomRender(var13);
//                }
//
//                List var30;
//                if ("switch".equals(var18)) {
//                    var30 = com.sxlinks.modules.online.cgform.util.b.a(var12);
//                    var7.put(var13, var30);
//                    var29.setCustomRender(var13);
//                }
//
//                List var21;
//                String var32;
//                if ("link_down".equals(var18)) {
//                    var32 = var12.getDictTable();
//                    a var20 = (a)JSONObject.parseObject(var32, a.class);
//                    var21 = this.sysBaseAPI.queryTableDictItemsByCode(var20.getTable(), var20.getTxt(), var20.getKey());
//                    var7.put(var13, var21);
//                    var29.setCustomRender(var13);
//                    var6.add(var29);
//                    String var22 = var20.getLinkField();
//                    this.a(var4, var10, var6, var13, var22);
//                }
//
//                if ("sel_tree".equals(var18)) {
//                    String[] var34 = var12.getDictText().split(",");
//                    List var31 = this.sysBaseAPI.queryTableDictItemsByCode(var12.getDictTable(), var34[2], var34[0]);
//                    var7.put(var13, var31);
//                    var29.setCustomRender(var13);
//                }
//
//                if ("cat_tree".equals(var18)) {
//                    var32 = var12.getDictText();
//                    if (oConvertUtils.isEmpty(var32)) {
//                        String var33 = com.sxlinks.modules.online.cgform.util.b.e(var12.getDictField());
//                        var21 = this.sysBaseAPI.queryFilterTableDictInfo("SYS_CATEGORY", "NAME", "ID", var33);
//                        var7.put(var13, var21);
//                        var29.setCustomRender(var13);
//                    } else {
//                        var29.setCustomRender("_replace_text_" + var32);
//                    }
//                }
//
//                if ("sel_depart".equals(var18)) {
//                    var30 = this.sysBaseAPI.queryAllDepartBackDictModel();
//                    var7.put(var13, var30);
//                    var29.setCustomRender(var13);
//                }
//
//                if ("sel_user".equals(var12.getFieldShowType())) {
//                    var30 = this.sysBaseAPI.queryTableDictItemsByCode("SYS_USER", "REALNAME", "USERNAME");
//                    var7.put(var13, var30);
//                    var29.setCustomRender(var13);
//                }
//
//                if (var18.indexOf("file") >= 0) {
//                    var29.setScopedSlots(new c("fileSlot"));
//                } else if (var18.indexOf("image") >= 0) {
//                    var29.setScopedSlots(new c("imgSlot"));
//                } else if (var18.indexOf("editor") >= 0) {
//                    var29.setScopedSlots(new c("htmlSlot"));
//                } else if (var18.equals("date")) {
//                    var29.setScopedSlots(new c("dateSlot"));
//                } else if (var18.equals("pca")) {
//                    var29.setScopedSlots(new c("pcaSlot"));
//                }
//
//                if (StringUtils.isNotBlank(var12.getFieldHref())) {
//                    var32 = "fieldHref_" + var13;
//                    var29.setHrefSlotName(var32);
//                    var8.add(new HrefSlots(var32, var12.getFieldHref()));
//                }
//
//                if ("1".equals(var12.getSortFlag())) {
//                    var29.setSorter(true);
//                }
//
//                var32 = var12.getFieldExtendJson();
//                if (oConvertUtils.isNotEmpty(var32) && var32.indexOf("showLength") > 0) {
//                    JSONObject var35 = JSON.parseObject(var32);
//                    if (var35 != null && var35.get("showLength") != null) {
//                        var29.setShowLength(oConvertUtils.getInt(var35.get("showLength")));
//                    }
//                }
//
//                if (!"link_down".equals(var18)) {
//                    var6.add(var29);
//                }
//            }
//        }
//
//        OnlComplexModel var23 = new OnlComplexModel();
//        var23.setCode(var3);
//        var23.setTableType(head.getTableType());
//        var23.setFormTemplate(head.getFormTemplate());
//        var23.setDescription(head.getTableTxt());
//        var23.setCurrentTableName(head.getTableName());
//        var23.setPaginationFlag(head.getIsPage());
//        var23.setCheckboxFlag(head.getIsCheckbox());
//        var23.setScrollFlag(head.getScroll());
//        var23.setRelationType(head.getRelationType());
//        var23.setColumns(var6);
//        var23.setDictOptions(var7);
//        var23.setFieldHrefSlots(var8);
//        var23.setForeignKeys(var9);
//        var23.setHideColumns(var5);
//        List var24 = this.onlCgformHeadService.queryButtonList(var3, true);
//        ArrayList var25 = new ArrayList();
//        Iterator var26 = var24.iterator();
//
//        while(var26.hasNext()) {
//            OnlCgformButton var28 = (OnlCgformButton)var26.next();
//            if (!var5.contains(var28.getButtonCode())) {
//                var25.add(var28);
//            }
//        }
//
//        var23.setCgButtonList(var25);
//        OnlCgformEnhanceJs var27 = this.onlCgformHeadService.queryEnhanceJs(var3, "list");
//        if (var27 != null && oConvertUtils.isNotEmpty(var27.getCgJs())) {
//            var15 = EnhanceJsUtil.b(var27.getCgJs(), var24);
//            var23.setEnhanceJs(var15);
//        }
//
//        if ("Y".equals(head.getIsTree())) {
//            var23.setPidField(head.getTreeParentIdField());
//            var23.setHasChildrenField(head.getTreeIdField());
//            var23.setTextField(head.getTreeFieldname());
//        }
//
//        return var23;
//    }
//
//    private void a(List<OnlCgformField> var1, List<String> var2, List<OnlColumn> var3, String var4, String var5) {
//        if (oConvertUtils.isNotEmpty(var5)) {
//            String[] var6 = var5.split(",");
//            String[] var7 = var6;
//            int var8 = var6.length;
//
//            for(int var9 = 0; var9 < var8; ++var9) {
//                String var10 = var7[var9];
//                Iterator var11 = var1.iterator();
//
//                while(var11.hasNext()) {
//                    OnlCgformField var12 = (OnlCgformField)var11.next();
//                    String var13 = var12.getDbFieldName();
//                    if (1 == var12.getIsShowList() && var10.equals(var13)) {
//                        var2.add(var10);
//                        OnlColumn var14 = new OnlColumn(var12.getDbFieldTxt(), var13);
//                        var14.setCustomRender(var4);
//                        var3.add(var14);
//                        break;
//                    }
//                }
//            }
//        }
//
//    }
//
//    public JSONObject queryOnlineFormObj(OnlCgformHead head, OnlCgformEnhanceJs onlCgformEnhanceJs) {
//        JSONObject var3 = new JSONObject();
//        String var4 = head.getId();
//        String var5 = head.getTaskId();
//        List var6 = this.onlCgformFieldService.queryAvailableFields(var4, head.getTableName(), var5, false);
//        ArrayList var7 = new ArrayList();
//        List var8;
//        if (oConvertUtils.isEmpty(var5)) {
//            var8 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
//            if (var8 != null && var8.size() > 0 && var8.get(0) != null) {
//                var7.addAll(var8);
//            }
//        } else {
//            var8 = this.onlCgformFieldService.queryDisabledFields(head.getTableName(), var5);
//            if (var8 != null && var8.size() > 0 && var8.get(0) != null) {
//                var7.addAll(var8);
//            }
//        }
//
//        EnhanceJsUtil.a(onlCgformEnhanceJs, head.getTableName(), var6);
//        d var12 = null;
//        if ("Y".equals(head.getIsTree())) {
//            var12 = new d();
//            var12.setCodeField("id");
//            var12.setFieldName(head.getTreeParentIdField());
//            var12.setPidField(head.getTreeParentIdField());
//            var12.setPidValue("0");
//            var12.setHsaChildField(head.getTreeIdField());
//            var12.setTableName(com.sxlinks.modules.online.cgform.util.b.f(head.getTableName()));
//            var12.setTextField(head.getTreeFieldname());
//        }
//
//        JSONObject var9 = com.sxlinks.modules.online.cgform.util.b.a(var6, var7, var12);
//        var9.put("table", head.getTableName());
//        var9.put("describe", head.getTableTxt());
//        var3.put("schema", var9);
//        var3.put("head", head);
//        List var10 = this.queryFormValidButton(var4);
//        if (var10 != null && var10.size() > 0) {
//            var3.put("cgButtonList", var10);
//        }
//
//        if (onlCgformEnhanceJs != null && oConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
//            String var11 = EnhanceJsUtil.c(onlCgformEnhanceJs.getCgJs(), var10);
//            onlCgformEnhanceJs.setCgJs(var11);
//            var3.put("enhanceJs", EnhanceJsUtil.a(onlCgformEnhanceJs.getCgJs()));
//        }
//
//        return var3;
//    }
//
//    @Cacheable(
//            value = {"sys:cache:online:form"},
//            key = "'erp'+ #head.id+'-'+#username"
//    )
//    public JSONObject queryOnlineFormObj(OnlCgformHead head, String username) {
//        OnlCgformEnhanceJs var3 = this.onlCgformHeadService.queryEnhanceJs(head.getId(), "form");
//        return this.queryOnlineFormObj(head, var3);
//    }
//
//    public List<OnlCgformButton> queryFormValidButton(String headId) {
//        List var2 = this.onlCgformHeadService.queryButtonList(headId, false);
//        List var3 = null;
//        if (var2 != null && var2.size() > 0) {
//            LoginUser var4 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//            String var5 = var4.getId();
//            List var6 = this.onlAuthPageService.queryFormHideButton(var5, headId);
////            var3 = (List)var2.stream().filter((var1) -> {
////                return var6 == null || var6.indexOf(var1.getButtonCode()) < 0;
////            }).collect(Collectors.toList());
//        }
//
//        return var3;
//    }
//
//    @Cacheable(
//            value = {"sys:cache:online:form"},
//            key = "#head.id+'-'+#username"
//    )
//    public JSONObject queryOnlineFormItem(OnlCgformHead head, String username) {
//        head.setTaskId((String)null);
//        return this.a(head);
//    }
//
//    @Cacheable(
//            value = {"sys:cache:online:form"},
//            key = "'flow' + #head.id + '-' + #username + '-' + #taskId"
//    )
//    public JSONObject queryFlowOnlineFormItem(OnlCgformHead head, String username, String taskId) {
//        head.setTaskId(taskId);
//        return this.a(head);
//    }
//
//    @Cacheable(
//            value = {"sys:cache:online:form"},
//            key = "'enhancejs' + #code + '-' + #type"
//    )
//    public String queryEnahcneJsString(String code, String type) {
//        String var3 = "";
//        OnlCgformEnhanceJs var4 = this.onlCgformHeadService.queryEnhanceJs(code, type);
//        if (var4 != null && oConvertUtils.isNotEmpty(var4.getCgJs())) {
//            var3 = EnhanceJsUtil.b(var4.getCgJs(), (List)null);
//        }
//
//        return var3;
//    }
//
//    private List<OnlCgformField> a(String var1) {
//        LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//        var2.eq("cgformHeadId", var1);
//        var2.orderByAsc("orderNum");
//        return this.onlCgformFieldService.list(var2);
//    }
//
//    private JSONObject a(OnlCgformHead var1) {
//        OnlCgformEnhanceJs var2 = this.onlCgformHeadService.queryEnhanceJs(var1.getId(), "form");
//        JSONObject var3 = this.queryOnlineFormObj(var1, var2);
//        var3.put("formTemplate", var1.getFormTemplate());
//        if (var1.getTableType() == 2) {
//            JSONObject var4 = var3.getJSONObject("schema");
//            String var5 = var1.getSubTableStr();
//            if (oConvertUtils.isNotEmpty(var5)) {
//                ArrayList var6 = new ArrayList();
//                String[] var7 = var5.split(",");
//                int var8 = var7.length;
//
//                for(int var9 = 0; var9 < var8; ++var9) {
//                    String var10 = var7[var9];
//                    OnlCgformHead var11 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var10));
//                    if (var11 != null) {
//                        var6.add(var11);
//                    }
//                }
//
//                if (var6.size() > 0) {
////                    Collections.sort(var6, new Comparator<OnlCgformHead>() {
////                        public int a(OnlCgformHead var1, OnlCgformHead var2) {
////                            Integer var3 = var1.getTabOrderNum();
////                            if (var3 == null) {
////                                var3 = 0;
////                            }
////
////                            Integer var4 = var2.getTabOrderNum();
////                            if (var4 == null) {
////                                var4 = 0;
////                            }
////
////                            return var3.compareTo(var4);
////                        }
////                    });
//                    Iterator var13 = var6.iterator();
//
//                    while(var13.hasNext()) {
//                        OnlCgformHead var14 = (OnlCgformHead)var13.next();
//                        List var15 = this.onlCgformFieldService.queryAvailableFields(var14.getId(), var14.getTableName(), var1.getTaskId(), false);
//                        EnhanceJsUtil.b(var2, var14.getTableName(), var15);
//                        JSONObject var16 = new JSONObject();
//                        new ArrayList();
//                        List var17;
//                        if (oConvertUtils.isNotEmpty(var1.getTaskId())) {
//                            var17 = this.onlCgformFieldService.queryDisabledFields(var14.getTableName(), var1.getTaskId());
//                        } else {
//                            var17 = this.onlAuthPageService.queryFormDisabledCode(var14.getId());
//                        }
//
//                        if (1 == var14.getRelationType()) {
//                            var16 = com.sxlinks.modules.online.cgform.util.b.a(var15, var17, (d)null);
//                        } else {
//                            var16.put("columns", com.sxlinks.modules.online.cgform.util.b.a(var15, var17));
//                            List var12 = this.onlAuthPageService.queryListHideButton((String)null, var14.getId());
//                            var16.put("hideButtons", var12);
//                        }
//
//                        String var18 = this.onlCgformFieldService.queryForeignKey(var14.getId(), var1.getTableName());
//                        var16.put("foreignKey", var18);
//                        var16.put("id", var14.getId());
//                        var16.put("describe", var14.getTableTxt());
//                        var16.put("key", var14.getTableName());
//                        var16.put("view", "tab");
//                        var16.put("order", var14.getTabOrderNum());
//                        var16.put("relationType", var14.getRelationType());
//                        var16.put("formTemplate", var14.getFormTemplate());
//                        var4.getJSONObject("properties").put(var14.getTableName(), var16);
//                    }
//                }
//            }
//
//            if (var2 != null && oConvertUtils.isNotEmpty(var2.getCgJs())) {
//                var3.put("enhanceJs", EnhanceJsUtil.a(var2.getCgJs()));
//            }
//        }
//
//        return var3;
//    }
//}

////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.enhance.a;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import com.sxlinks.common.system.api.ISysBaseAPI;
//import com.sxlinks.common.system.vo.SysCategoryModel;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.config.exception.BusinessException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component("cgformEnhanceExportDemo")
//public class b implements CgformEnhanceJavaListInter {
//    @Autowired
//    ISysBaseAPI sysBaseAPI;
//    @Autowired
//    IOnlCgformFieldService onlCgformFieldService;
//
//    public b() {
//    }
//
//    public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
//        List var3 = this.sysBaseAPI.queryAllDSysCategory();
//        Iterator var4 = data.iterator();
//
//        while(var4.hasNext()) {
//            Map var5 = (Map)var4.next();
//            String var6 = oConvertUtils.getString(var5.get("fen_tree"));
//            if (!oConvertUtils.isEmpty(var6)) {
//                List var7 = (List)var3.stream().filter((var1) -> {
//                    return var1.getId().equals(var6);
//                }).collect(Collectors.toList());
//                if (var7 != null && var7.size() != 0) {
//                    var5.put("fen_tree", ((SysCategoryModel)var7.get(0)).getName());
//                }
//
//                String var8 = oConvertUtils.getString(var5.get("sel_search"));
//                if (!oConvertUtils.isEmpty(var8)) {
//                    OnlCgformField var9 = this.onlCgformFieldService.queryFormFieldByTableNameAndField(tableName, "sel_search");
//                    if (var9 != null && !oConvertUtils.isEmpty(var9.getDictTable())) {
//                        List var10 = this.sysBaseAPI.queryTableDictByKeys(var9.getDictTable(), var9.getDictText(), var9.getDictField(), new String[]{var8});
//                        if (var10 != null && var10.size() > 0) {
//                            var5.put("sel_search", var10.get(0));
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//}

////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import java.util.Iterator;
//import java.util.List;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformFieldMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformHeadMapper;
//import com.sxlinks.modules.online.cgform.service.IOnlineBaseAPI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service("onlineBaseAPI")
//public class h implements IOnlineBaseAPI {
//    @Autowired
//    private OnlCgformHeadMapper onlCgformHeadMapper;
//    @Autowired
//    private OnlCgformFieldMapper onlCgformFieldMapper;
//
//    public h() {
//    }
//
//    public String getOnlineErpCode(String code, String tableType) {
//        if ("3".equals(tableType)) {
//            String var3 = code.substring(1);
//            OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadMapper.selectById(var3);
//            if (var4 != null && var4.getTableType() == 3) {
//                LambdaQueryWrapper var5 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var3);
//                List var6 = this.onlCgformFieldMapper.selectList(var5);
//                if (var6 != null && var6.size() > 0) {
//                    String var7 = null;
//                    Iterator var8 = var6.iterator();
//
//                    while(var8.hasNext()) {
//                        OnlCgformField var9 = (OnlCgformField)var8.next();
//                        if (oConvertUtils.isNotEmpty(var9.getMainTable())) {
//                            var7 = var9.getMainTable();
//                            break;
//                        }
//                    }
//
//                    LambdaQueryWrapper var11 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("tableName", var7);
//                    OnlCgformHead var12 = (OnlCgformHead)this.onlCgformHeadMapper.selectOne(var11);
//                    String var10 = var12.getThemeTemplate();
//                    if (var12 != null && ("innerTable".equals(var10) || "erp".equals(var10))) {
//                        code = "/" + var12.getId();
//                    }
//                }
//            }
//        }
//
//        return code;
//    }
//}

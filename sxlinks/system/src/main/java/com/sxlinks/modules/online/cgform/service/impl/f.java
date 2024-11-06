////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import java.util.Iterator;
//import java.util.List;
//import com.sxlinks.common.constant.CommonConstant;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformIndex;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformHeadMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformIndexMapper;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformIndexService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service("onlCgformIndexServiceImpl")
//public class f extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {
//    private static final Logger a = LoggerFactory.getLogger(f.class);
//    @Autowired
//    private OnlCgformHeadMapper onlCgformHeadMapper;
//
//    public f() {
//    }
//
//    public void createIndex(String code, String databaseType, String tbname) {
//        LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//        var4.eq("cgformHeadId", code);
//        List var5 = this.list(var4);
//        if (var5 != null && var5.size() > 0) {
//            Iterator var6 = var5.iterator();
//
//            while(var6.hasNext()) {
//                OnlCgformIndex var7 = (OnlCgformIndex)var6.next();
//                if (!CommonConstant.DEL_FLAG_1.equals(var7.getDelFlag()) && "N".equals(var7.getIsDbSynch())) {
//                    String var8 = "";
//                    String var9 = var7.getIndexName();
//                    String var10 = var7.getIndexField();
//                    String var11 = "normal".equals(var7.getIndexType()) ? " index " : var7.getIndexType() + " index ";
//                    byte var13 = -1;
//                    switch(databaseType.hashCode()) {
//                        case -1955532418:
//                            if (databaseType.equals("ORACLE")) {
//                                var13 = 1;
//                            }
//                            break;
//                        case -1620389036:
//                            if (databaseType.equals("POSTGRESQL")) {
//                                var13 = 3;
//                            }
//                            break;
//                        case 73844866:
//                            if (databaseType.equals("MYSQL")) {
//                                var13 = 0;
//                            }
//                            break;
//                        case 912124529:
//                            if (databaseType.equals("SQLSERVER")) {
//                                var13 = 2;
//                            }
//                    }
//
//                    switch(var13) {
//                        case 0:
//                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
//                            break;
//                        case 1:
//                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
//                            break;
//                        case 2:
//                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
//                            break;
//                        case 3:
//                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
//                            break;
//                        default:
//                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
//                    }
//
//                    a.info(" 创建索引 executeDDL ：" + var8);
//                    this.onlCgformHeadMapper.executeDDL(var8);
//                    var7.setIsDbSynch("Y");
//                    this.updateById(var7);
//                }
//            }
//        }
//
//    }
//
//    public boolean isExistIndex(String countSql) {
//        if (countSql == null) {
//            return true;
//        } else {
//            Integer var2 = ((OnlCgformIndexMapper)this.baseMapper).queryIndexCount(countSql);
//            return var2 != null && var2 > 0;
//        }
//    }
//
//    public List<OnlCgformIndex> getCgformIndexsByCgformId(String cgformId) {
//        return ((OnlCgformIndexMapper)this.baseMapper).selectList((Wrapper)(new LambdaQueryWrapper()).in("cgformHeadId", new Object[]{cgformId}));
//    }
//}

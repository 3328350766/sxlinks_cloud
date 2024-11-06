////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import freemarker.template.TemplateException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Collectors;
//import org.apache.commons.lang.StringUtils;
//import org.hibernate.HibernateException;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.constant.CommonConstant;
//import com.sxlinks.common.constant.enums.CgformEnum;
//import com.sxlinks.common.util.MyClassLoader;
//import com.sxlinks.common.util.RestUtil;
//import com.sxlinks.common.util.SpringContextUtils;
//import com.sxlinks.common.util.UUIDGenerator;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.auth.service.IOnlAuthPageService;
//import com.sxlinks.modules.online.cgform.enhance.CgformEnhanceJavaInter;
//import com.sxlinks.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJava;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJs;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceSql;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformIndex;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformButtonMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformEnhanceJsMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformHeadMapper;
//import com.sxlinks.modules.online.cgform.model.OnlGenerateModel;
//import com.sxlinks.modules.online.cgform.model.a;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformIndexService;
//import com.sxlinks.modules.online.config.a.b;
//import com.sxlinks.modules.online.config.b.c;
//import com.sxlinks.modules.online.config.b.d;
//import com.sxlinks.modules.online.config.exception.BusinessException;
//import com.sxlinks.modules.online.config.exception.DBException;
//import com.sxlinks.modules.online.config.service.DbTableHandleI;
//import org.jeecgframework.codegenerate.database.DbReadTableUtil;
//import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOne;
//import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOneToMany;
//import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
//import org.jeecgframework.codegenerate.generate.pojo.TableVo;
//import org.jeecgframework.codegenerate.generate.pojo.onetomany.MainTableVo;
//import org.jeecgframework.codegenerate.generate.pojo.onetomany.SubTableVo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service("onlCgformHeadServiceImpl")
//public class e extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {
//    private static final Logger a = LoggerFactory.getLogger(e.class);
//    @Autowired
//    private IOnlCgformFieldService fieldService;
//    @Autowired
//    private IOnlCgformIndexService indexService;
//    @Autowired
//    private OnlCgformEnhanceJsMapper onlCgformEnhanceJsMapper;
//    @Autowired
//    private OnlCgformButtonMapper onlCgformButtonMapper;
//    @Autowired
//    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
//    @Autowired
//    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;
//    @Autowired
//    private b dataBaseConfig;
//    @Autowired
//    private IOnlAuthPageService onlAuthPageService;
//
//    public e() {
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public Result<?> addAll(a model) {
//        String var2 = UUID.randomUUID().toString().replace("-", "");
//        OnlCgformHead var3 = model.getHead();
//        List var4 = model.getFields();
//        List var5 = model.getIndexs();
//        var3.setId(var2);
//
//        for(int var6 = 0; var6 < var4.size(); ++var6) {
//            OnlCgformField var7 = (OnlCgformField)var4.get(var6);
//            var7.setId((String)null);
//            var7.setCgformHeadId(var2);
//            if (var7.getOrderNum() == null) {
//                var7.setOrderNum(var6);
//            }
//
//            this.a(var7);
//        }
//
//        Iterator var8 = var5.iterator();
//
//        while(var8.hasNext()) {
//            OnlCgformIndex var9 = (OnlCgformIndex)var8.next();
//            var9.setId((String)null);
//            var9.setCgformHeadId(var2);
//        }
//
//        var3.setIsDbSynch("N");
//        var3.setQueryMode("single");
//        var3.setTableVersion(1);
//        var3.setCopyType(0);
//        if (var3.getTableType() == 3 && var3.getTabOrderNum() == null) {
//            var3.setTabOrderNum(1);
//        }
//
//        super.save(var3);
//        this.fieldService.saveBatch(var4);
//        this.indexService.saveBatch(var5);
//        this.a(var3, var4);
//        return Result.ok("添加成功");
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public Result<?> editAll(a model) {
//        OnlCgformHead var2 = model.getHead();
//        OnlCgformHead var3 = (OnlCgformHead)super.getById(var2.getId());
//        if (var3 == null) {
//            return Result.error("未找到对应实体");
//        } else {
//            String var4 = var3.getIsDbSynch();
//            if (com.sxlinks.modules.online.cgform.util.b.a(var3, var2)) {
//                var4 = "N";
//            }
//
//            Integer var5 = var3.getTableVersion();
//            if (var5 == null) {
//                var5 = 1;
//            }
//
//            var2.setTableVersion(var5 + 1);
//            List var6 = model.getFields();
//            List var7 = model.getIndexs();
//            ArrayList var8 = new ArrayList();
//            ArrayList var9 = new ArrayList();
//            Iterator var10 = var6.iterator();
//
//            while(var10.hasNext()) {
//                OnlCgformField var11 = (OnlCgformField)var10.next();
//                String var12 = String.valueOf(var11.getId());
//                this.a(var11);
//                if (var12.length() == 32) {
//                    var9.add(var11);
//                } else {
//                    String var13 = "_pk";
//                    if (!var13.equals(var12)) {
//                        var11.setId((String)null);
//                        var11.setCgformHeadId(var2.getId());
//                        var8.add(var11);
//                    }
//                }
//            }
//
//            if (var8.size() > 0) {
//                var4 = "N";
//            }
//
//            int var18 = 0;
//
//            Iterator var19;
//            OnlCgformField var21;
//            for(var19 = var9.iterator(); var19.hasNext(); this.fieldService.updateById(var21)) {
//                var21 = (OnlCgformField)var19.next();
//                OnlCgformField var23 = (OnlCgformField)this.fieldService.getById(var21.getId());
//                this.a(var23.getMainTable(), var2.getTableName());
//                boolean var14 = com.sxlinks.modules.online.cgform.util.b.a(var23, var21);
//                if (var14) {
//                    var4 = "N";
//                }
//
//                if ((var23.getOrderNum() == null ? 0 : var23.getOrderNum()) > var18) {
//                    var18 = var23.getOrderNum();
//                }
//            }
//
//            for(var19 = var8.iterator(); var19.hasNext(); this.fieldService.save(var21)) {
//                var21 = (OnlCgformField)var19.next();
//                if (var21.getOrderNum() == null) {
//                    ++var18;
//                    var21.setOrderNum(var18);
//                }
//            }
//
//            List var20 = this.indexService.getCgformIndexsByCgformId(var2.getId());
//            ArrayList var22 = new ArrayList();
//            ArrayList var24 = new ArrayList();
//            Iterator var25 = var7.iterator();
//
//            OnlCgformIndex var15;
//            String var16;
//            while(var25.hasNext()) {
//                var15 = (OnlCgformIndex)var25.next();
//                var16 = String.valueOf(var15.getId());
//                if (var16.length() == 32) {
//                    var24.add(var15);
//                } else {
//                    var15.setId((String)null);
//                    var15.setIsDbSynch("N");
//                    var15.setDelFlag(CommonConstant.DEL_FLAG_0);
//                    var15.setCgformHeadId(var2.getId());
//                    var22.add(var15);
//                }
//            }
//
//            var25 = var20.iterator();
//
//            while(var25.hasNext()) {
//                var15 = (OnlCgformIndex)var25.next();
//                boolean var28 = var7.stream().anyMatch((var1) -> {
//                    return var15.getId().equals(var1.getId());
//                });
//                if (!var28) {
//                    var15.setDelFlag(CommonConstant.DEL_FLAG_1);
//                    var24.add(var15);
//                    var4 = "N";
//                }
//            }
//
//            if (var22.size() > 0) {
//                var4 = "N";
//                this.indexService.saveBatch(var22);
//            }
//
//            for(var25 = var24.iterator(); var25.hasNext(); this.indexService.updateById(var15)) {
//                var15 = (OnlCgformIndex)var25.next();
//                OnlCgformIndex var29 = (OnlCgformIndex)this.indexService.getById(var15.getId());
//                boolean var17 = com.sxlinks.modules.online.cgform.util.b.a(var29, var15);
//                if (var17) {
//                    var4 = "N";
//                    var15.setIsDbSynch("N");
//                }
//            }
//
//            if (model.getDeleteFieldIds().size() > 0) {
//                var4 = "N";
//                List var26 = model.getDeleteFieldIds();
//                Iterator var27 = var26.iterator();
//
//                while(var27.hasNext()) {
//                    var16 = (String)var27.next();
//                    OnlCgformField var30 = (OnlCgformField)this.fieldService.getById(var16);
//                    if (var30 != null) {
//                        this.a(var30.getMainTable(), var2.getTableName());
//                        this.fieldService.removeById(var16);
//                    }
//                }
//            }
//
//            var2.setIsDbSynch(var4);
//            super.updateById(var2);
//            this.a(var2, var6);
//            this.b(var2, var6);
//            return Result.ok("全部修改成功");
//        }
//    }
//
//    private void a(String var1, String var2) {
//        if (oConvertUtils.isNotEmpty(var1)) {
//            LambdaQueryWrapper var3 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("tableName", var1);
//            OnlCgformHead var4 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne(var3);
//            if (var4 != null) {
//                String var5 = var4.getSubTableStr();
//                String[] var6 = var5.split(",");
//                ArrayList var7 = new ArrayList();
//                String[] var8 = var6;
//                int var9 = var6.length;
//
//                for(int var10 = 0; var10 < var9; ++var10) {
//                    String var11 = var8[var10];
//                    if (!var11.equals(var2)) {
//                        var7.add(var11);
//                    }
//                }
//
//                var4.setSubTableStr(String.join(",", var7));
//                ((OnlCgformHeadMapper)this.baseMapper).updateById(var4);
//            }
//        }
//
//    }
//
//    public void doDbSynch(String code, String synMethod) throws HibernateException, IOException, TemplateException, SQLException, DBException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getById(code);
//        if (var3 == null) {
//            throw new DBException("实体配置不存在");
//        } else {
//            String var4 = var3.getTableName();
//            LambdaQueryWrapper var5 = new LambdaQueryWrapper();
//            var5.eq("cgformHeadId", code);
//            var5.orderByAsc("orderNum");
//            List var6 = this.fieldService.list(var5);
//            com.sxlinks.modules.online.config.a.a var7 = new com.sxlinks.modules.online.config.a.a();
//            var7.setTableName(var4);
//            var7.setJformPkType(var3.getIdType());
//            var7.setJformPkSequence(var3.getIdSequence());
//            var7.setContent(var3.getTableTxt());
//            var7.setColumns(var6);
//            var7.setDbConfig(this.dataBaseConfig);
//            if ("normal".equals(synMethod)) {
//                long var8 = System.currentTimeMillis();
//                boolean var10 = d.a(var4);
//                a.info("==判断表是否存在消耗时间" + (System.currentTimeMillis() - var8) + "毫秒");
//                if (var10) {
//                    c var11 = new c();
//                    List var12 = var11.b(var7);
//                    Iterator var13 = var12.iterator();
//
//                    while(var13.hasNext()) {
//                        String var14 = (String)var13.next();
//                        if (!oConvertUtils.isEmpty(var14) && !oConvertUtils.isEmpty(var14.trim())) {
//                            ((OnlCgformHeadMapper)this.baseMapper).executeDDL(var14);
//                        }
//                    }
//
//                    List var21 = this.indexService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", code));
//                    Iterator var22 = var21.iterator();
//
//                    label58:
//                    while(true) {
//                        OnlCgformIndex var15;
//                        do {
//                            if (!var22.hasNext()) {
//                                break label58;
//                            }
//
//                            var15 = (OnlCgformIndex)var22.next();
//                        } while(!"N".equals(var15.getIsDbSynch()) && !CommonConstant.DEL_FLAG_1.equals(var15.getDelFlag()));
//
//                        String var16 = var11.b(var15.getIndexName(), var4);
//                        if (this.indexService.isExistIndex(var16)) {
//                            String var17 = var11.a(var15.getIndexName(), var4);
//
//                            try {
//                                a.info("删除索引 executeDDL:" + var17);
//                                ((OnlCgformHeadMapper)this.baseMapper).executeDDL(var17);
//                                if (CommonConstant.DEL_FLAG_1.equals(var15.getDelFlag())) {
//                                    this.indexService.removeById(var15.getId());
//                                }
//                            } catch (Exception var19) {
//                                a.error("删除表【" + var4 + "】索引(" + var15.getIndexName() + ")失败!", var19);
//                            }
//                        } else if (CommonConstant.DEL_FLAG_1.equals(var15.getDelFlag())) {
//                            this.indexService.removeById(var15.getId());
//                        }
//                    }
//                } else {
//                    c.a(var7);
//                }
//            } else if ("force".equals(synMethod)) {
//                DbTableHandleI var20 = d.getTableHandle();
//                String var9 = var20.dropTableSQL(var4);
//                ((OnlCgformHeadMapper)this.baseMapper).executeDDL(var9);
//                c.a(var7);
//            }
//
//            this.indexService.createIndex(code, d.getDatabaseType(), var4);
//            var3.setIsDbSynch("Y");
//            if (var3.getTableVersion() == 1) {
//                var3.setTableVersion(2);
//            }
//
//            this.updateById(var3);
//        }
//    }
//
//    public void deleteRecordAndTable(String id) throws DBException, SQLException {
//        OnlCgformHead var2 = (OnlCgformHead)this.getById(id);
//        if (var2 == null) {
//            throw new DBException("实体配置不存在");
//        } else {
//            long var3 = System.currentTimeMillis();
//            boolean var5 = d.a(var2.getTableName());
//            a.info("==判断表是否存在消耗时间 " + (System.currentTimeMillis() - var3) + "毫秒");
//            if (var5) {
//                String var6 = d.getTableHandle().dropTableSQL(var2.getTableName());
//                a.info(" 删除表  executeDDL： " + var6);
//                ((OnlCgformHeadMapper)this.baseMapper).executeDDL(var6);
//            }
//
//            ((OnlCgformHeadMapper)this.baseMapper).deleteById(id);
//            this.a(var2);
//        }
//    }
//
//    public void deleteRecord(String id) throws DBException, SQLException {
//        OnlCgformHead var2 = (OnlCgformHead)this.getById(id);
//        if (var2 == null) {
//            throw new DBException("实体配置不存在");
//        } else {
//            ((OnlCgformHeadMapper)this.baseMapper).deleteById(id);
//            this.a(var2);
//        }
//    }
//
//    private void a(OnlCgformHead var1) {
//        if (var1.getTableType() == 3) {
//            LambdaQueryWrapper var2 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var1.getId());
//            List var3 = this.fieldService.list(var2);
//            String var4 = null;
//            Iterator var5 = var3.iterator();
//
//            while(var5.hasNext()) {
//                OnlCgformField var6 = (OnlCgformField)var5.next();
//                var4 = var6.getMainTable();
//                if (oConvertUtils.isNotEmpty(var4)) {
//                    break;
//                }
//            }
//
//            if (oConvertUtils.isNotEmpty(var4)) {
//                OnlCgformHead var8 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var4));
//                if (var8 != null) {
//                    String var9 = var8.getSubTableStr();
//                    if (oConvertUtils.isNotEmpty(var9)) {
//                        List var7 = (List)Arrays.asList(var9.split(",")).stream().collect(Collectors.toList());
//                        var7.remove(var1.getTableName());
//                        var8.setSubTableStr(String.join(",", var7));
//                        ((OnlCgformHeadMapper)this.baseMapper).updateById(var8);
//                    }
//                }
//            }
//        }
//
//    }
//
//    public List<Map<String, Object>> queryListData(String sql) {
//        return ((OnlCgformHeadMapper)this.baseMapper).queryList(sql);
//    }
//
//    public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
//        this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
//    }
//
//    public OnlCgformEnhanceJs queryEnhance(String code, String type) {
//        return (OnlCgformEnhanceJs)this.onlCgformEnhanceJsMapper.selectOne((Wrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq(OnlCgformEnhanceJs::getCgJsType, type)).eq(OnlCgformEnhanceJs::getCgformHeadId, code));
//    }
//
//    public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
//        this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
//    }
//
//    public OnlCgformEnhanceSql queryEnhanceSql(String formId, String buttonCode) {
//        return (OnlCgformEnhanceSql)this.onlCgformEnhanceSqlMapper.selectOne((Wrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", formId)).eq("buttonCode", buttonCode));
//    }
//
//    public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
//        LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//        var2.eq("buttonCode", onlCgformEnhanceJava.getButtonCode());
//        var2.eq("cgformHeadId", onlCgformEnhanceJava.getCgformHeadId());
//        var2.eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType());
//        var2.eq("event", onlCgformEnhanceJava.getEvent());
//        return (OnlCgformEnhanceJava)this.onlCgformEnhanceJavaMapper.selectOne(var2);
//    }
//
//    public List<OnlCgformButton> queryButtonList(String code, boolean isListButton) {
//        LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//        var3.eq("buttonStatus", "1");
//        var3.eq("cgformHeadId", code);
//        if (isListButton) {
//            var3.in("buttonSytle", new Object[]{"link", "button"});
//        } else {
//            var3.eq("buttonSytle", "form");
//        }
//
//        var3.orderByAsc(OnlCgformButton::getOrderNum);
//        return this.onlCgformButtonMapper.selectList(var3);
//    }
//
//    public List<OnlCgformButton> queryButtonList(String code) {
//        LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//        var2.eq("buttonStatus", "1");
//        var2.eq("cgformHeadId", code);
//        var2.orderByAsc(OnlCgformButton::getOrderNum);
//        return this.onlCgformButtonMapper.selectList(var2);
//    }
//
//    public List<String> queryOnlinetables() {
//        return ((OnlCgformHeadMapper)this.baseMapper).queryOnlinetables();
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public void saveDbTable2Online(String tbname) {
//        OnlCgformHead var2 = new OnlCgformHead();
//        var2.setTableType(1);
//        var2.setIsCheckbox("Y");
//        var2.setIsDbSynch("Y");
//        var2.setIsTree("N");
//        var2.setIsPage("Y");
//        var2.setQueryMode("group");
//        var2.setTableName(tbname.toLowerCase());
//        var2.setTableTxt(tbname);
//        var2.setTableVersion(1);
//        var2.setFormTemplate("1");
//        var2.setCopyType(0);
//        var2.setIsDesForm("N");
//        var2.setScroll(1);
//        var2.setThemeTemplate("normal");
//        String var3 = UUIDGenerator.generate();
//        var2.setId(var3);
//        ArrayList var4 = new ArrayList();
//
//        try {
//            List var5 = DbReadTableUtil.b(tbname);
//
//            for(int var6 = 0; var6 < var5.size(); ++var6) {
//                ColumnVo var7 = (ColumnVo)var5.get(var6);
//                a.info("  columnt : " + var7.toString());
//                String var8 = var7.getFieldDbName();
//                OnlCgformField var9 = new OnlCgformField();
//                var9.setCgformHeadId(var3);
//                var9.setDbFieldNameOld(var7.getFieldDbName().toLowerCase());
//                var9.setDbFieldName(var7.getFieldDbName().toLowerCase());
//                if (oConvertUtils.isNotEmpty(var7.getFiledComment())) {
//                    var9.setDbFieldTxt(var7.getFiledComment());
//                } else {
//                    var9.setDbFieldTxt(var7.getFieldName());
//                }
//
//                var9.setDbIsKey(0);
//                var9.setIsShowForm(1);
//                var9.setIsQuery(0);
//                var9.setFieldMustInput("0");
//                var9.setIsShowList(1);
//                var9.setOrderNum(var6 + 1);
//                var9.setQueryMode("single");
//                var9.setDbLength(oConvertUtils.getInt(var7.getPrecision()));
//                var9.setFieldLength(120);
//                var9.setDbPointLength(oConvertUtils.getInt(var7.getScale()));
//                var9.setFieldShowType("text");
//                var9.setDbIsNull("Y".equals(var7.getNullable()) ? 1 : 0);
//                var9.setIsReadOnly(0);
//                if ("id".equalsIgnoreCase(var8)) {
//                    String[] var10 = new String[]{"java.lang.Integer", "java.lang.Long"};
//                    String var11 = var7.getFieldType();
//                    if (Arrays.asList(var10).contains(var11)) {
//                        var2.setIdType("NATIVE");
//                    } else {
//                        var2.setIdType("UUID");
//                    }
//
//                    var9.setDbIsKey(1);
//                    var9.setIsShowForm(0);
//                    var9.setIsShowList(0);
//                    var9.setIsReadOnly(1);
//                }
//
//                if ("create_by".equalsIgnoreCase(var8) || "create_time".equalsIgnoreCase(var8) || "update_by".equalsIgnoreCase(var8) || "update_time".equalsIgnoreCase(var8) || "sys_org_code".equalsIgnoreCase(var8)) {
//                    var9.setIsShowForm(0);
//                    var9.setIsShowList(0);
//                }
//
//                if ("java.lang.Integer".equalsIgnoreCase(var7.getFieldType())) {
//                    var9.setDbType("int");
//                } else if ("java.lang.Long".equalsIgnoreCase(var7.getFieldType())) {
//                    var9.setDbType("int");
//                } else if ("java.util.Date".equalsIgnoreCase(var7.getFieldType())) {
//                    var9.setDbType("Date");
//                    var9.setFieldShowType("date");
//                } else if (!"java.lang.Double".equalsIgnoreCase(var7.getFieldType()) && !"java.lang.Float".equalsIgnoreCase(var7.getFieldType())) {
//                    if (!"java.math.BigDecimal".equalsIgnoreCase(var7.getFieldType()) && !"BigDecimal".equalsIgnoreCase(var7.getFieldType())) {
//                        if (!"byte[]".equalsIgnoreCase(var7.getFieldType()) && !var7.getFieldType().contains("blob")) {
//                            if (!"java.lang.Object".equals(var7.getFieldType()) || !"text".equalsIgnoreCase(var7.getFieldDbType()) && !"ntext".equalsIgnoreCase(var7.getFieldDbType())) {
//                                if ("java.lang.Object".equals(var7.getFieldType()) && "image".equalsIgnoreCase(var7.getFieldDbType())) {
//                                    var9.setDbType("Blob");
//                                } else {
//                                    var9.setDbType("string");
//                                }
//                            } else {
//                                var9.setDbType("Text");
//                                var9.setFieldShowType("textarea");
//                            }
//                        } else {
//                            var9.setDbType("Blob");
//                            var7.setCharmaxLength((String)null);
//                        }
//                    } else {
//                        var9.setDbType("BigDecimal");
//                    }
//                } else {
//                    var9.setDbType("double");
//                }
//
//                if (oConvertUtils.isEmpty(var7.getPrecision()) && oConvertUtils.isNotEmpty(var7.getCharmaxLength())) {
//                    if (Long.valueOf(var7.getCharmaxLength()) >= 3000L) {
//                        var9.setDbType("Text");
//                        var9.setFieldShowType("textarea");
//
//                        try {
//                            var9.setDbLength(Integer.valueOf(var7.getCharmaxLength()));
//                        } catch (Exception var12) {
//                            a.error(var12.getMessage(), var12);
//                        }
//                    } else {
//                        var9.setDbLength(Integer.valueOf(var7.getCharmaxLength()));
//                    }
//                } else {
//                    if (oConvertUtils.isNotEmpty(var7.getPrecision())) {
//                        var9.setDbLength(Integer.valueOf(var7.getPrecision()));
//                    } else if (var9.getDbType().equals("int")) {
//                        var9.setDbLength(10);
//                    }
//
//                    if (oConvertUtils.isNotEmpty(var7.getScale())) {
//                        var9.setDbPointLength(Integer.valueOf(var7.getScale()));
//                    }
//                }
//
//                if (oConvertUtils.getInt(var7.getPrecision()) == -1 && oConvertUtils.getInt(var7.getScale()) == 0) {
//                    var9.setDbType("Text");
//                }
//
//                if ("Blob".equals(var9.getDbType()) || "Text".equals(var9.getDbType()) || "Date".equals(var9.getDbType())) {
//                    var9.setDbLength(0);
//                    var9.setDbPointLength(0);
//                }
//
//                var4.add(var9);
//            }
//        } catch (Exception var13) {
//            a.error(var13.getMessage(), var13);
//        }
//
//        if (oConvertUtils.isEmpty(var2.getFormCategory())) {
//            var2.setFormCategory("bdfl_include");
//        }
//
//        this.save(var2);
//        this.fieldService.saveBatch(var4);
//    }
//
//    private void a(OnlCgformHead var1, List<OnlCgformField> var2) {
//        if (var1.getTableType() == 3) {
//            var1 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectById(var1.getId());
//
//            for(int var3 = 0; var3 < var2.size(); ++var3) {
//                OnlCgformField var4 = (OnlCgformField)var2.get(var3);
//                String var5 = var4.getMainTable();
//                if (!oConvertUtils.isEmpty(var5)) {
//                    OnlCgformHead var6 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var5));
//                    if (var6 != null) {
//                        String var7 = var6.getSubTableStr();
//                        if (oConvertUtils.isEmpty(var7)) {
//                            var7 = var1.getTableName();
//                        } else if (var7.indexOf(var1.getTableName()) < 0) {
//                            ArrayList var8 = new ArrayList(Arrays.asList(var7.split(",")));
//
//                            for(int var9 = 0; var9 < var8.size(); ++var9) {
//                                String var10 = (String)var8.get(var9);
//                                OnlCgformHead var11 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var10));
//                                if (var11 != null && var1.getTabOrderNum() < oConvertUtils.getInt(var11.getTabOrderNum(), 0)) {
//                                    var8.add(var9, var1.getTableName());
//                                    break;
//                                }
//                            }
//
//                            if (var8.indexOf(var1.getTableName()) < 0) {
//                                var8.add(var1.getTableName());
//                            }
//
//                            var7 = String.join(",", var8);
//                        }
//
//                        var6.setSubTableStr(var7);
//                        ((OnlCgformHeadMapper)this.baseMapper).updateById(var6);
//                        break;
//                    }
//                }
//            }
//        } else {
//            List var12 = ((OnlCgformHeadMapper)this.baseMapper).selectList((Wrapper)(new LambdaQueryWrapper()).like(OnlCgformHead::getSubTableStr, var1.getTableName()));
//            if (var12 != null && var12.size() > 0) {
//                Iterator var13 = var12.iterator();
//
//                while(var13.hasNext()) {
//                    OnlCgformHead var14 = (OnlCgformHead)var13.next();
//                    String var15 = var14.getSubTableStr();
//                    if (var14.getSubTableStr().equals(var1.getTableName())) {
//                        var15 = "";
//                    } else if (var14.getSubTableStr().startsWith(var1.getTableName() + ",")) {
//                        var15 = var15.replace(var1.getTableName() + ",", "");
//                    } else if (var14.getSubTableStr().endsWith("," + var1.getTableName())) {
//                        var15 = var15.replace("," + var1.getTableName(), "");
//                    } else if (var14.getSubTableStr().indexOf("," + var1.getTableName() + ",") != -1) {
//                        var15 = var15.replace("," + var1.getTableName() + ",", ",");
//                    }
//
//                    var14.setSubTableStr(var15);
//                    ((OnlCgformHeadMapper)this.baseMapper).updateById(var14);
//                }
//            }
//        }
//
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public String saveManyFormData(String code, JSONObject json, String xAccessToken) throws DBException, BusinessException {
//        OnlCgformHead var4 = (OnlCgformHead)this.getById(code);
//        if (var4 == null) {
//            throw new DBException("数据库主表ID[" + code + "]不存在");
//        } else {
//            String var5 = "add";
//            this.executeEnhanceJava(var5, "start", var4, json);
//            String var6 = com.sxlinks.modules.online.cgform.util.b.f(var4.getTableName());
//            if (var4.getTableType() == 2) {
//                String var7 = var4.getSubTableStr();
//                if (oConvertUtils.isNotEmpty(var7)) {
//                    String[] var8 = var7.split(",");
//                    String[] var9 = var8;
//                    int var10 = var8.length;
//
//                    for(int var11 = 0; var11 < var10; ++var11) {
//                        String var12 = var9[var11];
//                        JSONArray var13 = json.getJSONArray(var12);
//                        if (var13 != null && var13.size() != 0) {
//                            OnlCgformHead var14 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var12));
//                            if (var14 != null) {
//                                List var15 = this.fieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var14.getId()));
//                                String var16 = "";
//                                String var17 = null;
//                                Iterator var18 = var15.iterator();
//
//                                while(var18.hasNext()) {
//                                    OnlCgformField var19 = (OnlCgformField)var18.next();
//                                    if (!oConvertUtils.isEmpty(var19.getMainField())) {
//                                        var16 = var19.getDbFieldName();
//                                        String var20 = var19.getMainField();
//                                        if (json.get(var20.toLowerCase()) != null) {
//                                            var17 = json.getString(var20.toLowerCase());
//                                        }
//
//                                        if (json.get(var20.toUpperCase()) != null) {
//                                            var17 = json.getString(var20.toUpperCase());
//                                        }
//                                    }
//                                }
//
//                                for(int var27 = 0; var27 < var13.size(); ++var27) {
//                                    JSONObject var28 = var13.getJSONObject(var27);
//                                    if (var17 != null) {
//                                        var28.put(var16, var17);
//                                    }
//
//                                    this.fieldService.saveFormData(var15, var12, var28);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            if ("Y".equals(var4.getIsTree())) {
//                this.fieldService.saveTreeFormData(code, var6, json, var4.getTreeIdField(), var4.getTreeParentIdField());
//            } else {
//                this.fieldService.saveFormData(code, var6, json, false);
//            }
//
//            this.executeEnhanceSql(var5, code, json);
//            this.executeEnhanceJava(var5, "end", var4, json);
//            if (oConvertUtils.isNotEmpty(json.get("bpm_status")) || oConvertUtils.isNotEmpty(json.get("bpm_status".toUpperCase()))) {
//                try {
//                    HttpHeaders var22 = new HttpHeaders();
//                    var22.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
//                    var22.set("Accept", "application/json;charset=UTF-8");
//                    var22.set("X-Access-Token", xAccessToken);
//                    JSONObject var23 = new JSONObject();
//                    var23.put("flowCode", "onl_" + var4.getTableName());
//                    var23.put("id", json.get("id"));
//                    var23.put("formUrl", "modules/bpm/task/form/OnlineFormDetail");
//                    var23.put("formUrlMobile", "online/OnlineDetailForm");
//                    String var24 = RestUtil.getBaseUrl() + "/act/process/extActProcess/saveMutilProcessDraft";
//                    JSONObject var25 = (JSONObject)RestUtil.request(var24, HttpMethod.POST, var22, (JSONObject)null, var23, JSONObject.class).getBody();
//                    if (var25 != null) {
//                        String var26 = var25.getString("result");
//                        a.info("保存流程草稿 dataId : " + var26);
//                    }
//                } catch (Exception var21) {
//                    a.error("保存流程草稿异常, " + var21.getMessage(), var21);
//                }
//            }
//
//            return var4.getTableName();
//        }
//    }
//
//    public Map<String, Object> querySubFormData(String table, String mainId) throws DBException {
//        new HashMap();
//        OnlCgformHead var4 = (OnlCgformHead)this.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", table));
//        if (var4 == null) {
//            throw new DBException("数据库子表[" + table + "]不存在");
//        } else {
//            List var5 = this.fieldService.queryFormFields(var4.getId(), false);
//            String var6 = null;
//            Iterator var7 = var5.iterator();
//
//            while(var7.hasNext()) {
//                OnlCgformField var8 = (OnlCgformField)var7.next();
//                if (oConvertUtils.isNotEmpty(var8.getMainField())) {
//                    var6 = var8.getDbFieldName();
//                    break;
//                }
//            }
//
//            List var9 = this.fieldService.querySubFormData(var5, table, var6, mainId);
//            if (var9 != null && var9.size() == 0) {
//                throw new DBException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
//            } else if (var9.size() > 1) {
//                throw new DBException("数据库子表[" + table + "]存在多条记录,主表ID为" + mainId);
//            } else {
//                Map var3 = (Map)var9.get(0);
//                return var3;
//            }
//        }
//    }
//
//    public List<Map<String, Object>> queryManySubFormData(String table, String mainId) throws DBException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", table));
//        if (var3 == null) {
//            throw new DBException("数据库子表[" + table + "]不存在");
//        } else {
//            List var4 = this.fieldService.queryFormFields(var3.getId(), false);
//            if (var4 != null && var4.size() != 0) {
//                String var5 = null;
//                String var6 = null;
//                String var7 = null;
//                Iterator var8 = var4.iterator();
//
//                OnlCgformField var9;
//                while(var8.hasNext()) {
//                    var9 = (OnlCgformField)var8.next();
//                    if (oConvertUtils.isNotEmpty(var9.getMainField())) {
//                        var5 = var9.getDbFieldName();
//                        var6 = var9.getMainTable();
//                        var7 = var9.getMainField();
//                        break;
//                    }
//                }
//
//                ArrayList var16 = new ArrayList();
//                var9 = new OnlCgformField();
//                var9.setDbFieldName(var7);
//                var16.add(var9);
//                Map var10 = this.fieldService.queryFormData(var16, var6, mainId);
//                String var11 = oConvertUtils.getString(oConvertUtils.getString(var10.get(var7)), oConvertUtils.getString(var10.get(var7.toUpperCase())));
//                List var12 = this.fieldService.querySubFormData(var4, table, var5, var11);
//                if (var12 != null && var12.size() == 0) {
//                    throw new DBException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
//                } else {
//                    ArrayList var13 = new ArrayList(var12.size());
//                    Iterator var14 = var12.iterator();
//
//                    while(var14.hasNext()) {
//                        Map var15 = (Map)var14.next();
//                        var13.add(com.sxlinks.modules.online.cgform.util.b.b(var15));
//                    }
//
//                    return var13;
//                }
//            } else {
//                throw new DBException("找不到子表字段，请确认配置是否正确!");
//            }
//        }
//    }
//
//    public Map<String, Object> queryManyFormData(String code, String id) throws DBException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getById(code);
//        if (var3 == null) {
//            throw new DBException("数据库主表ID[" + code + "]不存在");
//        } else {
//            List var4 = this.fieldService.queryFormFields(code, true);
//            if (var4 != null && var4.size() != 0) {
//                Map var5 = this.fieldService.queryFormData(var4, var3.getTableName(), id);
//                if (var3.getTableType() == 2) {
//                    String var6 = var3.getSubTableStr();
//                    if (oConvertUtils.isNotEmpty(var6)) {
//                        String[] var7 = var6.split(",");
//                        String[] var8 = var7;
//                        int var9 = var7.length;
//
//                        for(int var10 = 0; var10 < var9; ++var10) {
//                            String var11 = var8[var10];
//                            OnlCgformHead var12 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var11));
//                            if (var12 != null) {
//                                List var13 = this.fieldService.queryFormFields(var12.getId(), false);
//                                String var14 = "";
//                                String var15 = null;
//                                Iterator var16 = var13.iterator();
//
//                                while(var16.hasNext()) {
//                                    OnlCgformField var17 = (OnlCgformField)var16.next();
//                                    if (!oConvertUtils.isEmpty(var17.getMainField())) {
//                                        var14 = var17.getDbFieldName();
//                                        String var18 = var17.getMainField();
//                                        if (null == var5.get(var18)) {
//                                            var15 = var5.get(var18.toUpperCase()).toString();
//                                        } else {
//                                            var15 = var5.get(var18).toString();
//                                        }
//                                    }
//                                }
//
//                                List var19 = this.fieldService.querySubFormData(var13, var11, var14, var15);
//                                if (var19 != null && var19.size() != 0) {
//                                    var5.put(var11, com.sxlinks.modules.online.cgform.util.b.d(var19));
//                                } else {
//                                    var5.put(var11, new String[0]);
//                                }
//                            }
//                        }
//                    }
//                }
//
//                return var5;
//            } else {
//                throw new DBException("找不到字段，请确认配置是否正确!");
//            }
//        }
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public String editManyFormData(String code, JSONObject json) throws DBException, BusinessException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getById(code);
//        if (var3 == null) {
//            throw new DBException("数据库主表ID[" + code + "]不存在");
//        } else {
//            String var4 = "edit";
//            this.executeEnhanceJava(var4, "start", var3, json);
//            String var5 = var3.getTableName();
//            if ("Y".equals(var3.getIsTree())) {
//                this.fieldService.editTreeFormData(code, var5, json, var3.getTreeIdField(), var3.getTreeParentIdField());
//            } else {
//                this.fieldService.editFormData(code, var5, json, false);
//            }
//
//            if (var3.getTableType() == 2) {
//                String var6 = var3.getSubTableStr();
//                if (oConvertUtils.isNotEmpty(var6)) {
//                    String[] var7 = var6.split(",");
//                    String[] var8 = var7;
//                    int var9 = var7.length;
//
//                    for(int var10 = 0; var10 < var9; ++var10) {
//                        String var11 = var8[var10];
//                        OnlCgformHead var12 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var11));
//                        if (var12 != null) {
//                            List var13 = this.fieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var12.getId()));
//                            String var14 = "";
//                            String var15 = null;
//                            Iterator var16 = var13.iterator();
//
//                            while(var16.hasNext()) {
//                                OnlCgformField var17 = (OnlCgformField)var16.next();
//                                if (!oConvertUtils.isEmpty(var17.getMainField())) {
//                                    var14 = var17.getDbFieldName();
//                                    String var18 = var17.getMainField();
//                                    if (json.get(var18.toLowerCase()) != null) {
//                                        var15 = json.getString(var18.toLowerCase());
//                                    }
//
//                                    if (json.get(var18.toUpperCase()) != null) {
//                                        var15 = json.getString(var18.toUpperCase());
//                                    }
//                                }
//                            }
//
//                            if (!oConvertUtils.isEmpty(var15)) {
//                                this.fieldService.deleteAutoList(var11, var14, var15);
//                                JSONArray var19 = json.getJSONArray(var11);
//                                if (var19 != null && var19.size() != 0) {
//                                    for(int var20 = 0; var20 < var19.size(); ++var20) {
//                                        JSONObject var21 = var19.getJSONObject(var20);
//                                        if (var15 != null) {
//                                            var21.put(var14, var15);
//                                        }
//
//                                        this.fieldService.saveFormData(var13, var11, var21);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            this.executeEnhanceJava(var4, "end", var3, json);
//            this.executeEnhanceSql(var4, code, json);
//            return var5;
//        }
//    }
//
//    public int executeEnhanceJava(String buttonCode, String eventType, OnlCgformHead head, JSONObject json) throws BusinessException {
//        LambdaQueryWrapper var5 = new LambdaQueryWrapper();
//        var5.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
//        var5.eq("buttonCode", buttonCode);
//        var5.eq("cgformHeadId", head.getId());
//        var5.eq("event", eventType);
//        OnlCgformEnhanceJava var6 = (OnlCgformEnhanceJava)this.onlCgformEnhanceJavaMapper.selectOne(var5);
//        Object var7 = this.a(var6);
//        if (var7 != null && var7 instanceof CgformEnhanceJavaInter) {
//            CgformEnhanceJavaInter var8 = (CgformEnhanceJavaInter)var7;
//            return var8.execute(head.getTableName(), json);
//        } else {
//            return 1;
//        }
//    }
//
//    public void executeEnhanceExport(OnlCgformHead head, List<Map<String, Object>> dataList) throws BusinessException {
//        this.executeEnhanceList(head, "export", dataList);
//    }
//
//    public void executeEnhanceList(OnlCgformHead head, String buttonCode, List<Map<String, Object>> dataList) throws BusinessException {
//        LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//        var4.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
//        var4.eq("buttonCode", buttonCode);
//        var4.eq("cgformHeadId", head.getId());
//        List var5 = this.onlCgformEnhanceJavaMapper.selectList(var4);
//        if (var5 != null && var5.size() > 0) {
//            Object var6 = this.a((OnlCgformEnhanceJava)var5.get(0));
//            if (var6 != null && var6 instanceof CgformEnhanceJavaListInter) {
//                CgformEnhanceJavaListInter var7 = (CgformEnhanceJavaListInter)var6;
//                var7.execute(head.getTableName(), dataList);
//            }
//        }
//
//    }
//
//    private Object a(OnlCgformEnhanceJava var1) {
//        if (var1 != null) {
//            String var2 = var1.getCgJavaType();
//            String var3 = var1.getCgJavaValue();
//            if (oConvertUtils.isNotEmpty(var3)) {
//                Object var4 = null;
//                if ("class".equals(var2)) {
//                    try {
//                        var4 = MyClassLoader.getClassByScn(var3).newInstance();
//                    } catch (InstantiationException var6) {
//                        a.error(var6.getMessage(), var6);
//                    } catch (IllegalAccessException var7) {
//                        a.error(var7.getMessage(), var7);
//                    }
//                } else if ("spring".equals(var2)) {
//                    var4 = SpringContextUtils.getBean(var3);
//                }
//
//                return var4;
//            }
//        }
//
//        return null;
//    }
//
//    public void executeEnhanceSql(String buttonCode, String formId, JSONObject json) {
//        LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//        var4.eq("buttonCode", buttonCode);
//        var4.eq("cgformHeadId", formId);
//        OnlCgformEnhanceSql var5 = (OnlCgformEnhanceSql)this.onlCgformEnhanceSqlMapper.selectOne(var4);
//        if (var5 != null && oConvertUtils.isNotEmpty(var5.getCgbSql())) {
//            String var6 = com.sxlinks.modules.online.cgform.util.b.a(var5.getCgbSql(), json);
//            String[] var7 = var6.split(";");
//            String[] var8 = var7;
//            int var9 = var7.length;
//
//            for(int var10 = 0; var10 < var9; ++var10) {
//                String var11 = var8[var10];
//                if (var11 != null && !var11.toLowerCase().trim().equals("")) {
//                    a.info(" online sql 增强： " + var11);
//                    ((OnlCgformHeadMapper)this.baseMapper).executeDDL(var11);
//                }
//            }
//        }
//
//    }
//
//    public void executeCustomerButton(String buttonCode, String formId, String dataId) throws BusinessException {
//        OnlCgformHead var4 = (OnlCgformHead)this.getById(formId);
//        if (var4 == null) {
//            throw new BusinessException("未找到表配置信息");
//        } else {
//            Map var5 = ((OnlCgformHeadMapper)this.baseMapper).queryOneByTableNameAndId(com.sxlinks.modules.online.cgform.util.b.f(var4.getTableName()), dataId);
//            JSONObject var6 = JSONObject.parseObject(JSON.toJSONString(var5));
//            this.executeEnhanceJava(buttonCode, "start", var4, var6);
//            this.executeEnhanceSql(buttonCode, formId, var6);
//            this.executeEnhanceJava(buttonCode, "end", var4, var6);
//        }
//    }
//
//    public List<OnlCgformButton> queryValidButtonList(String headId) {
//        LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//        var2.eq("cgformHeadId", headId);
//        var2.eq("buttonStatus", "1");
//        var2.orderByAsc(OnlCgformButton::getOrderNum);
//        return this.onlCgformButtonMapper.selectList(var2);
//    }
//
//    public OnlCgformEnhanceJs queryEnhanceJs(String formId, String cgJsType) {
//        LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//        var3.eq(OnlCgformEnhanceJs::getCgformHeadId, formId);
//        var3.eq(OnlCgformEnhanceJs::getCgJsType, cgJsType);
//        return (OnlCgformEnhanceJs)this.onlCgformEnhanceJsMapper.selectOne(var3);
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public void deleteOneTableInfo(String formId, String dataId) throws BusinessException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getById(formId);
//        if (var3 == null) {
//            throw new BusinessException("未找到表配置信息");
//        } else {
//            String var4 = com.sxlinks.modules.online.cgform.util.b.f(var3.getTableName());
//            Map var5 = ((OnlCgformHeadMapper)this.baseMapper).queryOneByTableNameAndId(var4, dataId);
//            if (var5 == null) {
//                throw new BusinessException("未找到数据信息");
//            } else {
//                Map var6 = com.sxlinks.modules.online.cgform.util.b.b(var5);
//                String var7 = "delete";
//                JSONObject var8 = JSONObject.parseObject(JSON.toJSONString(var6));
//                this.executeEnhanceJava(var7, "start", var3, var8);
//                this.updateParentNode(var3, dataId);
//                if (var3.getTableType() == 2) {
//                    this.fieldService.deleteAutoListMainAndSub(var3, dataId);
//                } else {
//                    String var9 = "delete from " + var4 + " where id = '" + dataId + "'";
//                    ((OnlCgformHeadMapper)this.baseMapper).deleteOne(var9);
//                }
//
//                this.executeEnhanceSql(var7, formId, var8);
//                this.executeEnhanceJava(var7, "end", var3, var8);
//            }
//        }
//    }
//
//    /** @deprecated */
//    @Deprecated
//    public JSONObject queryFormItem(OnlCgformHead head, String username) {
//        List var3 = this.fieldService.queryAvailableFields(head.getId(), head.getTableName(), head.getTaskId(), false);
//        ArrayList var4 = new ArrayList();
//        List var5;
//        if (oConvertUtils.isEmpty(head.getTaskId())) {
//            var5 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
//            if (var5 != null && var5.size() > 0 && var5.get(0) != null) {
//                var4.addAll(var5);
//            }
//        } else {
//            var5 = this.fieldService.queryDisabledFields(head.getTableName(), head.getTaskId());
//            if (var5 != null && var5.size() > 0 && var5.get(0) != null) {
//                var4.addAll(var5);
//            }
//        }
//
//        JSONObject var15 = com.sxlinks.modules.online.cgform.util.b.a(var3, var4, (com.sxlinks.modules.online.cgform.model.d)null);
//        if (head.getTableType() == 2) {
//            String var6 = head.getSubTableStr();
//            if (oConvertUtils.isNotEmpty(var6)) {
//                String[] var7 = var6.split(",");
//                int var8 = var7.length;
//
//                for(int var9 = 0; var9 < var8; ++var9) {
//                    String var10 = var7[var9];
//                    OnlCgformHead var11 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var10));
//                    if (var11 != null) {
//                        List var12 = this.fieldService.queryAvailableFields(var11.getId(), var11.getTableName(), head.getTaskId(), false);
//                        new ArrayList();
//                        List var13;
//                        if (oConvertUtils.isNotEmpty(head.getTaskId())) {
//                            var13 = this.fieldService.queryDisabledFields(var11.getTableName(), head.getTaskId());
//                        } else {
//                            var13 = this.onlAuthPageService.queryFormDisabledCode(var11.getId());
//                        }
//
//                        JSONObject var14 = new JSONObject();
//                        if (1 == var11.getRelationType()) {
//                            var14 = com.sxlinks.modules.online.cgform.util.b.a(var12, var13, (com.sxlinks.modules.online.cgform.model.d)null);
//                        } else {
//                            var14.put("columns", com.sxlinks.modules.online.cgform.util.b.a(var12, var13));
//                        }
//
//                        var14.put("relationType", var11.getRelationType());
//                        var14.put("view", "tab");
//                        var14.put("order", var11.getTabOrderNum());
//                        var14.put("formTemplate", var11.getFormTemplate());
//                        var14.put("describe", var11.getTableTxt());
//                        var14.put("key", var11.getTableName());
//                        var15.getJSONObject("properties").put(var11.getTableName(), var14);
//                    }
//                }
//            }
//        }
//
//        return var15;
//    }
//
//    public List<String> generateCode(OnlGenerateModel model) throws Exception {
//        TableVo var2 = new TableVo();
//        var2.setEntityName(model.getEntityName());
//        var2.setEntityPackage(model.getEntityPackage());
//        var2.setFtlDescription(model.getFtlDescription());
//        var2.setTableName(model.getTableName());
//        var2.setSearchFieldNum(-1);
//        ArrayList var3 = new ArrayList();
//        ArrayList var4 = new ArrayList();
//        this.a(model.getCode(), var3, var4);
//        OnlCgformHead var5 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getId, model.getCode()));
//        HashMap var6 = new HashMap();
//        var6.put("scroll", var5.getScroll() == null ? "0" : var5.getScroll().toString());
//        String var7 = var5.getFormTemplate();
//        if (oConvertUtils.isEmpty(var7)) {
//            var2.setFieldRowNum(1);
//        } else {
//            var2.setFieldRowNum(Integer.parseInt(var7));
//        }
//
//        if ("Y".equals(var5.getIsTree())) {
//            var6.put("pidField", var5.getTreeParentIdField());
//            var6.put("hasChildren", var5.getTreeIdField());
//            var6.put("textField", var5.getTreeFieldname());
//        }
//
//        var2.setExtendParams(var6);
//        CgformEnum var8 = CgformEnum.getCgformEnumByConfig(model.getJspMode());
//        Object var9 = (new CodeGenerateOne(var2, var3, var4)).generateCodeFile(model.getProjectPath(), var8.getTemplatePath(), var8.getStylePath());
//        if (var9 == null || ((List)var9).size() == 0) {
//            var9 = new ArrayList();
//            ((List)var9).add(" :::::: 生成失败ERROR提醒 :::::: ");
//            ((List)var9).add("1.未找到代码生成器模板，请确认路径是否含有中文或特殊字符！");
//            ((List)var9).add("2.如果是JAR包运行，请参考此文档 http://doc.jeecg.com/2043922");
//        }
//
//        return (List)var9;
//    }
//
//    public List<String> generateOneToMany(OnlGenerateModel model) throws Exception {
//        MainTableVo var2 = new MainTableVo();
//        var2.setEntityName(model.getEntityName());
//        var2.setEntityPackage(model.getEntityPackage());
//        var2.setFtlDescription(model.getFtlDescription());
//        var2.setTableName(model.getTableName());
//        OnlCgformHead var3 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getId, model.getCode()));
//        String var4 = var3.getFormTemplate();
//        if (oConvertUtils.isEmpty(var4)) {
//            var2.setFieldRowNum(1);
//        } else {
//            var2.setFieldRowNum(Integer.parseInt(var4));
//        }
//
//        ArrayList var5 = new ArrayList();
//        ArrayList var6 = new ArrayList();
//        this.a(model.getCode(), var5, var6);
//        List var7 = model.getSubList();
//        ArrayList var8 = new ArrayList();
//        Iterator var9 = var7.iterator();
//
//        while(var9.hasNext()) {
//            OnlGenerateModel var10 = (OnlGenerateModel)var9.next();
//            OnlCgformHead var11 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var10.getTableName()));
//            if (var11 != null) {
//                SubTableVo var12 = new SubTableVo();
//                var12.setEntityName(var10.getEntityName());
//                var12.setEntityPackage(model.getEntityPackage());
//                var12.setTableName(var10.getTableName());
//                var12.setFtlDescription(var10.getFtlDescription());
//                Integer var13 = var11.getRelationType();
//                var12.setForeignRelationType(var13 == 1 ? "1" : "0");
//                ArrayList var14 = new ArrayList();
//                ArrayList var15 = new ArrayList();
//                OnlCgformField var16 = this.a(var11.getId(), var14, var15);
//                if (var16 != null) {
//                    var12.setOriginalForeignKeys(new String[]{var16.getDbFieldName()});
//                    var12.setForeignKeys(new String[]{var16.getDbFieldName()});
//                    var12.setColums(var14);
//                    var12.setOriginalColumns(var15);
//                    var8.add(var12);
//                }
//            }
//        }
//
//        CgformEnum var17 = CgformEnum.getCgformEnumByConfig(model.getJspMode());
//        List var18 = (new CodeGenerateOneToMany(var2, var5, var6, var8)).generateCodeFile(model.getProjectPath(), var17.getTemplatePath(), var17.getStylePath());
//        return var18;
//    }
//
//    private OnlCgformField a(String var1, List<ColumnVo> var2, List<ColumnVo> var3) {
//        LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//        var4.eq("cgformHeadId", var1);
//        var4.orderByAsc("orderNum");
//        List var5 = this.fieldService.list(var4);
//        OnlCgformField var6 = null;
//        Iterator var7 = var5.iterator();
//
//        while(true) {
//            OnlCgformField var8;
//            ColumnVo var9;
//            do {
//                if (!var7.hasNext()) {
//                    return var6;
//                }
//
//                var8 = (OnlCgformField)var7.next();
//                if (oConvertUtils.isNotEmpty(var8.getMainTable())) {
//                    var6 = var8;
//                }
//
//                var9 = new ColumnVo();
//                var9.setFieldLength(var8.getFieldLength());
//                var9.setFieldHref(var8.getFieldHref());
//                var9.setFieldValidType(var8.getFieldValidType());
//                var9.setFieldDefault(var8.getDbDefaultVal());
//                var9.setFieldShowType(var8.getFieldShowType());
//                var9.setFieldOrderNum(var8.getOrderNum());
//                var9.setIsKey(var8.getDbIsKey() == 1 ? "Y" : "N");
//                var9.setIsShow(var8.getIsShowForm() == 1 ? "Y" : "N");
//                var9.setIsShowList(var8.getIsShowList() == 1 ? "Y" : "N");
//                var9.setIsQuery(var8.getIsQuery() == 1 ? "Y" : "N");
//                var9.setQueryMode(var8.getQueryMode());
//                var9.setDictField(var8.getDictField());
//                if (oConvertUtils.isNotEmpty(var8.getDictTable()) && var8.getDictTable().indexOf("where") > 0) {
//                    var9.setDictTable(var8.getDictTable().split("where")[0].trim());
//                } else {
//                    var9.setDictTable(var8.getDictTable());
//                }
//
//                var9.setDictText(var8.getDictText());
//                var9.setFieldDbName(var8.getDbFieldName());
//                var9.setFieldName(oConvertUtils.camelName(var8.getDbFieldName()));
//                var9.setFiledComment(var8.getDbFieldTxt());
//                var9.setFieldDbType(var8.getDbType());
//                var9.setFieldType(this.a(var8.getDbType()));
//                var9.setClassType(var8.getFieldShowType());
//                var9.setClassType_row(var8.getFieldShowType());
//                if (var8.getDbIsNull() != 0 && !"*".equals(var8.getFieldValidType()) && !"1".equals(var8.getFieldMustInput())) {
//                    var9.setNullable("Y");
//                } else {
//                    var9.setNullable("N");
//                }
//
//                if ("switch".equals(var8.getFieldShowType())) {
//                    if (oConvertUtils.isNotEmpty(var8.getFieldExtendJson())) {
//                        var9.setDictField(var8.getFieldExtendJson());
//                    } else {
//                        var9.setDictField("is_open");
//                    }
//                }
//
//                HashMap var10 = new HashMap();
//                JSONObject var11;
//                if (StringUtils.isNotBlank(var8.getFieldExtendJson())) {
//                    try {
//                        var11 = JSONObject.parseObject(var8.getFieldExtendJson());
//                        if (var11 != null) {
//                            var10.putAll(var11.getInnerMap());
//                        }
//                    } catch (JSONException var13) {
//                    }
//                }
//
//                var9.setExtendParams(var10);
//                if ("popup".equals(var8.getFieldShowType())) {
//                    boolean var14 = true;
//                    Object var12 = var10.get("popupMulti");
//                    if (var12 != null) {
//                        var14 = (Boolean)var12;
//                    }
//
//                    var10.put("popupMulti", var14);
//                }
//
//                var9.setSort("1".equals(var8.getSortFlag()) ? "Y" : "N");
//                var9.setReadonly(Integer.valueOf(1).equals(var8.getIsReadOnly()) ? "Y" : "N");
//                if (oConvertUtils.isNotEmpty(var8.getFieldDefaultValue()) && !var8.getFieldDefaultValue().trim().startsWith("${") && !var8.getFieldDefaultValue().trim().startsWith("#{") && !var8.getFieldDefaultValue().trim().startsWith("{{")) {
//                    var9.setDefaultVal(var8.getFieldDefaultValue());
//                }
//
//                if (("file".equals(var8.getFieldShowType()) || "image".equals(var8.getFieldShowType())) && oConvertUtils.isNotEmpty(var8.getFieldExtendJson())) {
//                    var11 = JSONObject.parseObject(var8.getFieldExtendJson());
//                    if (oConvertUtils.isNotEmpty(var11.getString("uploadnum"))) {
//                        var9.setUploadnum(var11.getString("uploadnum"));
//                    }
//                }
//
//                var3.add(var9);
//            } while(var8.getIsShowForm() != 1 && var8.getIsShowList() != 1 && var8.getIsQuery() != 1);
//
//            var2.add(var9);
//        }
//    }
//
//    private String a(String var1) {
//        var1 = var1.toLowerCase();
//        if (var1.indexOf("int") >= 0) {
//            return "java.lang.Integer";
//        } else if (var1.indexOf("double") >= 0) {
//            return "java.lang.Double";
//        } else if (var1.indexOf("decimal") >= 0) {
//            return "java.math.BigDecimal";
//        } else {
//            return var1.indexOf("date") >= 0 ? "java.util.Date" : "java.lang.String";
//        }
//    }
//
//    public void addCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", tbname));
//        if (var3 == null) {
//            throw new DBException("数据库主表[" + tbname + "]不存在");
//        } else {
//            if (var3.getTableType() == 2) {
//                String var4 = var3.getSubTableStr();
//                if (var4 != null) {
//                    String[] var5 = var4.split(",");
//                    String[] var6 = var5;
//                    int var7 = var5.length;
//
//                    for(int var8 = 0; var8 < var7; ++var8) {
//                        String var9 = var6[var8];
//                        String var10 = json.getString("sub-table-design_" + var9);
//                        if (!oConvertUtils.isEmpty(var10)) {
//                            JSONArray var11 = JSONArray.parseArray(URLDecoder.decode(var10, "UTF-8"));
//                            if (var11 != null && var11.size() != 0) {
//                                OnlCgformHead var12 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var9));
//                                if (var12 != null) {
//                                    List var13 = this.fieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var12.getId()));
//                                    String var14 = "";
//                                    String var15 = null;
//                                    Iterator var16 = var13.iterator();
//
//                                    while(var16.hasNext()) {
//                                        OnlCgformField var17 = (OnlCgformField)var16.next();
//                                        if (!oConvertUtils.isEmpty(var17.getMainField())) {
//                                            var14 = var17.getDbFieldName();
//                                            String var18 = var17.getMainField();
//                                            var15 = json.getString(var18);
//                                        }
//                                    }
//
//                                    for(int var19 = 0; var19 < var11.size(); ++var19) {
//                                        JSONObject var20 = var11.getJSONObject(var19);
//                                        if (var15 != null) {
//                                            var20.put(var14, var15);
//                                        }
//
//                                        this.fieldService.executeInsertSQL(com.sxlinks.modules.online.cgform.util.b.c(var9, var13, var20));
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            this.fieldService.saveFormData(var3.getId(), tbname, json, true);
//        }
//    }
//
//    public void editCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
//        OnlCgformHead var3 = (OnlCgformHead)this.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", tbname));
//        if (var3 == null) {
//            throw new DBException("数据库主表[" + tbname + "]不存在");
//        } else {
//            if (var3.getTableType() == 2) {
//                String var4 = var3.getSubTableStr();
//                String[] var5 = var4.split(",");
//                String[] var6 = var5;
//                int var7 = var5.length;
//
//                for(int var8 = 0; var8 < var7; ++var8) {
//                    String var9 = var6[var8];
//                    OnlCgformHead var10 = (OnlCgformHead)((OnlCgformHeadMapper)this.baseMapper).selectOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var9));
//                    if (var10 != null) {
//                        List var11 = this.fieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var10.getId()));
//                        String var12 = "";
//                        String var13 = null;
//                        Iterator var14 = var11.iterator();
//
//                        while(var14.hasNext()) {
//                            OnlCgformField var15 = (OnlCgformField)var14.next();
//                            if (!oConvertUtils.isEmpty(var15.getMainField())) {
//                                var12 = var15.getDbFieldName();
//                                String var16 = var15.getMainField();
//                                var13 = json.getString(var16);
//                            }
//                        }
//
//                        if (!oConvertUtils.isEmpty(var13)) {
//                            this.fieldService.deleteAutoList(var9, var12, var13);
//                            String var18 = json.getString("sub-table-design_" + var9);
//                            if (!oConvertUtils.isEmpty(var18)) {
//                                JSONArray var19 = JSONArray.parseArray(URLDecoder.decode(var18, "UTF-8"));
//                                if (var19 != null && var19.size() != 0) {
//                                    for(int var20 = 0; var20 < var19.size(); ++var20) {
//                                        JSONObject var17 = var19.getJSONObject(var20);
//                                        if (var13 != null) {
//                                            var17.put(var12, var13);
//                                        }
//
//                                        this.fieldService.executeInsertSQL(com.sxlinks.modules.online.cgform.util.b.c(var9, var11, var17));
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            this.fieldService.editFormData(var3.getId(), tbname, json, true);
//        }
//    }
//
//    public Integer getMaxCopyVersion(String physicId) {
//        Integer var2 = ((OnlCgformHeadMapper)this.baseMapper).getMaxCopyVersion(physicId);
//        return var2 == null ? 0 : var2;
//    }
//
//    public void copyOnlineTableConfig(OnlCgformHead physicTable) throws Exception {
//        String var2 = physicTable.getId();
//        OnlCgformHead var3 = new OnlCgformHead();
//        String var4 = UUIDGenerator.generate();
//        var3.setId(var4);
//        var3.setPhysicId(var2);
//        var3.setCopyType(1);
//        var3.setCopyVersion(physicTable.getTableVersion());
//        var3.setTableVersion(1);
//        var3.setTableName(this.b(var2, physicTable.getTableName()));
//        var3.setTableTxt(physicTable.getTableTxt());
//        var3.setFormCategory(physicTable.getFormCategory());
//        var3.setFormTemplate(physicTable.getFormTemplate());
//        var3.setFormTemplateMobile(physicTable.getFormTemplateMobile());
//        var3.setIdSequence(physicTable.getIdSequence());
//        var3.setIdType(physicTable.getIdType());
//        var3.setIsCheckbox(physicTable.getIsCheckbox());
//        var3.setIsPage(physicTable.getIsPage());
//        var3.setIsTree(physicTable.getIsTree());
//        var3.setQueryMode(physicTable.getQueryMode());
//        var3.setTableType(1);
//        var3.setIsDbSynch("N");
//        var3.setIsDesForm(physicTable.getIsDesForm());
//        var3.setDesFormCode(physicTable.getDesFormCode());
//        var3.setTreeParentIdField(physicTable.getTreeParentIdField());
//        var3.setTreeFieldname(physicTable.getTreeFieldname());
//        var3.setTreeIdField(physicTable.getTreeIdField());
//        var3.setRelationType((Integer)null);
//        var3.setTabOrderNum((Integer)null);
//        var3.setSubTableStr((String)null);
//        var3.setThemeTemplate(physicTable.getThemeTemplate());
//        var3.setScroll(physicTable.getScroll());
//        List var5 = this.fieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var2));
//        Iterator var6 = var5.iterator();
//
//        while(var6.hasNext()) {
//            OnlCgformField var7 = (OnlCgformField)var6.next();
//            OnlCgformField var8 = new OnlCgformField();
//            var8.setCgformHeadId(var4);
//            this.a(var7, var8);
//            this.fieldService.save(var8);
//        }
//
//        ((OnlCgformHeadMapper)this.baseMapper).insert(var3);
//    }
//
//    public void initCopyState(List<OnlCgformHead> headList) {
//        List var2 = ((OnlCgformHeadMapper)this.baseMapper).queryCopyPhysicId();
//        Iterator var3 = headList.iterator();
//
//        while(var3.hasNext()) {
//            OnlCgformHead var4 = (OnlCgformHead)var3.next();
//            if (var2.contains(var4.getId())) {
//                var4.setHascopy(1);
//            } else {
//                var4.setHascopy(0);
//            }
//        }
//
//    }
//
//    public void deleteBatch(String ids, String flag) {
//        String[] var3 = ids.split(",");
//        if ("1".equals(flag)) {
//            String[] var4 = var3;
//            int var5 = var3.length;
//
//            for(int var6 = 0; var6 < var5; ++var6) {
//                String var7 = var4[var6];
//
//                try {
//                    this.deleteRecordAndTable(var7);
//                } catch (DBException var9) {
//                    var9.printStackTrace();
//                } catch (SQLException var10) {
//                    var10.printStackTrace();
//                }
//            }
//        } else {
//            this.removeByIds(Arrays.asList(var3));
//        }
//
//    }
//
//    public void updateParentNode(OnlCgformHead head, String dataId) {
//        if ("Y".equals(head.getIsTree())) {
//            String var3 = com.sxlinks.modules.online.cgform.util.b.f(head.getTableName());
//            String var4 = head.getTreeParentIdField();
//            Map var5 = ((OnlCgformHeadMapper)this.baseMapper).queryOneByTableNameAndId(var3, dataId);
//            String var6 = null;
//            if (var5.get(var4) != null && !"0".equals(var5.get(var4))) {
//                var6 = var5.get(var4).toString();
//            } else if (var5.get(var4.toUpperCase()) != null && !"0".equals(var5.get(var4.toUpperCase()))) {
//                var6 = var5.get(var4.toUpperCase()).toString();
//            }
//
//            if (var6 != null) {
//                Integer var7 = ((OnlCgformHeadMapper)this.baseMapper).queryChildNode(var3, var4, var6);
//                if (var7 == 1) {
//                    String var8 = head.getTreeIdField();
//                    this.fieldService.updateTreeNodeNoChild(var3, var8, var6);
//                }
//            }
//        }
//
//    }
//
//    private void b(OnlCgformHead var1, List<OnlCgformField> var2) {
//        List var3 = this.list((Wrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getPhysicId, var1.getId()));
//        if (var3 != null && var3.size() > 0) {
//            Iterator var4 = var3.iterator();
//
//            while(true) {
//                List var6;
//                String var13;
//                ArrayList var19;
//                Iterator var22;
//                label108:
//                do {
//                    while(var4.hasNext()) {
//                        OnlCgformHead var5 = (OnlCgformHead)var4.next();
//                        var6 = this.fieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var5.getId()));
//                        OnlCgformField var9;
//                        if (var6 != null && var6.size() != 0) {
//                            HashMap var15 = new HashMap();
//                            Iterator var16 = var6.iterator();
//
//                            while(var16.hasNext()) {
//                                var9 = (OnlCgformField)var16.next();
//                                var15.put(var9.getDbFieldName(), 1);
//                            }
//
//                            HashMap var17 = new HashMap();
//                            Iterator var18 = var2.iterator();
//
//                            while(var18.hasNext()) {
//                                OnlCgformField var10 = (OnlCgformField)var18.next();
//                                var17.put(var10.getDbFieldName(), 1);
//                            }
//
//                            var19 = new ArrayList();
//                            ArrayList var20 = new ArrayList();
//                            Iterator var11 = var17.keySet().iterator();
//
//                            while(var11.hasNext()) {
//                                String var12 = (String)var11.next();
//                                if (var15.get(var12) == null) {
//                                    var20.add(var12);
//                                } else {
//                                    var19.add(var12);
//                                }
//                            }
//
//                            ArrayList var21 = new ArrayList();
//                            var22 = var15.keySet().iterator();
//
//                            while(var22.hasNext()) {
//                                var13 = (String)var22.next();
//                                if (var17.get(var13) == null) {
//                                    var21.add(var13);
//                                }
//                            }
//
//                            OnlCgformField var23;
//                            if (var21.size() > 0) {
//                                var22 = var6.iterator();
//
//                                while(var22.hasNext()) {
//                                    var23 = (OnlCgformField)var22.next();
//                                    if (var21.contains(var23.getDbFieldName())) {
//                                        this.fieldService.removeById(var23.getId());
//                                    }
//                                }
//                            }
//
//                            if (var20.size() > 0) {
//                                var22 = var2.iterator();
//
//                                while(var22.hasNext()) {
//                                    var23 = (OnlCgformField)var22.next();
//                                    if (var20.contains(var23.getDbFieldName())) {
//                                        OnlCgformField var14 = new OnlCgformField();
//                                        var14.setCgformHeadId(var5.getId());
//                                        this.a(var23, var14);
//                                        this.fieldService.save(var14);
//                                    }
//                                }
//                            }
//                            continue label108;
//                        }
//
//                        Iterator var7 = var2.iterator();
//
//                        while(var7.hasNext()) {
//                            OnlCgformField var8 = (OnlCgformField)var7.next();
//                            var9 = new OnlCgformField();
//                            var9.setCgformHeadId(var5.getId());
//                            this.a(var8, var9);
//                            this.fieldService.save(var9);
//                        }
//                    }
//
//                    return;
//                } while(var19.size() <= 0);
//
//                var22 = var19.iterator();
//
//                while(var22.hasNext()) {
//                    var13 = (String)var22.next();
//                    this.b(var13, var2, var6);
//                }
//            }
//        }
//    }
//
//    private void b(String var1, List<OnlCgformField> var2, List<OnlCgformField> var3) {
//        OnlCgformField var4 = null;
//        Iterator var5 = var2.iterator();
//
//        while(var5.hasNext()) {
//            OnlCgformField var6 = (OnlCgformField)var5.next();
//            if (var1.equals(var6.getDbFieldName())) {
//                var4 = var6;
//            }
//        }
//
//        OnlCgformField var8 = null;
//        Iterator var9 = var3.iterator();
//
//        while(var9.hasNext()) {
//            OnlCgformField var7 = (OnlCgformField)var9.next();
//            if (var1.equals(var7.getDbFieldName())) {
//                var8 = var7;
//            }
//        }
//
//        if (var4 != null && var8 != null) {
//            boolean var10 = false;
//            if (!var4.getDbType().equals(var8.getDbType())) {
//                var8.setDbType(var4.getDbType());
//                var10 = true;
//            }
//
//            if (!var4.getDbDefaultVal().equals(var8.getDbDefaultVal())) {
//                var8.setDbDefaultVal(var4.getDbDefaultVal());
//                var10 = true;
//            }
//
//            if (var4.getDbLength() != var8.getDbLength()) {
//                var8.setDbLength(var4.getDbLength());
//                var10 = true;
//            }
//
//            if (var4.getDbIsNull() != var8.getDbIsNull()) {
//                var8.setDbIsNull(var4.getDbIsNull());
//                var10 = true;
//            }
//
//            if (var10) {
//                this.fieldService.updateById(var8);
//            }
//        }
//
//    }
//
//    private void a(OnlCgformField var1, OnlCgformField var2) {
//        var2.setDbDefaultVal(var1.getDbDefaultVal());
//        var2.setDbFieldName(var1.getDbFieldName());
//        var2.setDbFieldNameOld(var1.getDbFieldNameOld());
//        var2.setDbFieldTxt(var1.getDbFieldTxt());
//        var2.setDbIsKey(var1.getDbIsKey());
//        var2.setDbIsNull(var1.getDbIsNull());
//        var2.setDbLength(var1.getDbLength());
//        var2.setDbPointLength(var1.getDbPointLength());
//        var2.setDbType(var1.getDbType());
//        var2.setDictField(var1.getDictField());
//        var2.setDictTable(var1.getDictTable());
//        var2.setDictText(var1.getDictText());
//        var2.setFieldExtendJson(var1.getFieldExtendJson());
//        var2.setFieldHref(var1.getFieldHref());
//        var2.setFieldLength(var1.getFieldLength());
//        var2.setFieldMustInput(var1.getFieldMustInput());
//        var2.setFieldShowType(var1.getFieldShowType());
//        var2.setFieldValidType(var1.getFieldValidType());
//        var2.setFieldDefaultValue(var1.getFieldDefaultValue());
//        var2.setIsQuery(var1.getIsQuery());
//        var2.setIsShowForm(var1.getIsShowForm());
//        var2.setIsShowList(var1.getIsShowList());
//        var2.setMainField((String)null);
//        var2.setMainTable((String)null);
//        var2.setOrderNum(var1.getOrderNum());
//        var2.setQueryMode(var1.getQueryMode());
//        var2.setIsReadOnly(var1.getIsReadOnly());
//        var2.setSortFlag(var1.getSortFlag());
//        var2.setQueryDefVal(var1.getQueryDefVal());
//        var2.setQueryConfigFlag(var1.getQueryConfigFlag());
//        var2.setQueryDictField(var1.getQueryDictField());
//        var2.setQueryDictTable(var1.getQueryDictTable());
//        var2.setQueryDictText(var1.getQueryDictText());
//        var2.setQueryMustInput(var1.getQueryMustInput());
//        var2.setQueryShowType(var1.getQueryShowType());
//        var2.setQueryValidType(var1.getQueryValidType());
//        var2.setConverter(var1.getConverter());
//    }
//
//    private void a(OnlCgformField var1) {
//        if ("Text".equals(var1.getDbType()) || "Blob".equals(var1.getDbType())) {
//            var1.setDbLength(0);
//            var1.setDbPointLength(0);
//        }
//
//    }
//
//    private String b(String var1, String var2) {
//        List var3 = ((OnlCgformHeadMapper)this.baseMapper).queryAllCopyTableName(var1);
//        int var4 = 0;
//        if (var3 != null || var3.size() > 0) {
//            for(int var5 = 0; var5 < var3.size(); ++var5) {
//                String var6 = (String)var3.get(var5);
//                int var7 = Integer.parseInt(var6.split("\\$")[1]);
//                if (var7 > var4) {
//                    var4 = var7;
//                }
//            }
//        }
//
//        StringBuilder var10000 = (new StringBuilder()).append(var2).append("$");
//        ++var4;
//        return var10000.append(var4).toString();
//    }
//}

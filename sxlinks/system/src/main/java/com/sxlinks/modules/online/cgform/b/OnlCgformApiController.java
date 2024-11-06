////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.b;
//
//import cn.hutool.core.io.FileUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.google.common.collect.Lists;
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.shiro.SecurityUtils;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.aspect.annotation.AutoLog;
//import com.sxlinks.common.aspect.annotation.OnlineAuth;
//import com.sxlinks.common.aspect.annotation.PermissionData;
//import com.sxlinks.common.constant.enums.ModuleType;
//import com.sxlinks.common.system.api.ISysBaseAPI;
//import com.sxlinks.common.system.vo.DictModel;
//import com.sxlinks.common.system.vo.LoginUser;
//import com.sxlinks.common.util.BrowserUtils;
//import com.sxlinks.common.util.SpringContextUtils;
//import com.sxlinks.common.util.TokenUtils;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.auth.service.IOnlAuthPageService;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.model.OnlComplexModel;
//import com.sxlinks.modules.online.cgform.model.OnlGenerateModel;
//import com.sxlinks.modules.online.cgform.model.TreeModel;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformSqlService;
//import com.sxlinks.modules.online.cgform.service.IOnlineService;
//import com.sxlinks.modules.online.cgform.util.b;
//import com.sxlinks.modules.online.config.b.d;
//import com.sxlinks.modules.online.config.exception.BusinessException;
//import com.sxlinks.modules.online.config.exception.DBException;
//import org.jeecgframework.poi.excel.ExcelExportUtil;
//import org.jeecgframework.poi.excel.ExcelImportUtil;
//import org.jeecgframework.poi.excel.entity.ExportParams;
//import org.jeecgframework.poi.excel.entity.ImportParams;
//import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
//import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//@RestController("onlCgformApiController")
//@RequestMapping({"/online/cgform/api"})
//public class OnlCgformApiController {
//    private static final Logger a = LoggerFactory.getLogger(OnlCgformApiController.class);
//    @Autowired
//    private IOnlCgformHeadService onlCgformHeadService;
//    @Autowired
//    private IOnlCgformFieldService onlCgformFieldService;
//    @Autowired
//    private IOnlCgformSqlService onlCgformSqlService;
//    @Autowired
//    private IOnlAuthPageService onlAuthPageService;
//    @Autowired
//    private ISysBaseAPI sysBaseAPI;
//    @Autowired
//    private IOnlineService onlineService;
//    @Value("${jeecg.path.upload}")
//    private String upLoadPath;
//    @Value("${jeecg.uploadType}")
//    private String uploadType;
//
//    public OnlCgformApiController() {
//    }
//
//    @PostMapping({"/addAll"})
//    public Result<?> a(@RequestBody com.sxlinks.modules.online.cgform.model.a var1) {
//        try {
//            String var2 = var1.getHead().getTableName();
//            return d.a(var2) ? Result.error("数据库表[" + var2 + "]已存在,请从数据库导入表单") : this.onlCgformHeadService.addAll(var1);
//        } catch (Exception var3) {
//            a.error("OnlCgformApiController.addAll()发生异常：" + var3.getMessage(), var3);
//            return Result.error("操作失败");
//        }
//    }
//
//    @PutMapping({"/editAll"})
//    @CacheEvict(
//            value = {"sys:cache:online:list", "sys:cache:online:form"},
//            allEntries = true,
//            beforeInvocation = true
//    )
//    public Result<?> b(@RequestBody com.sxlinks.modules.online.cgform.model.a var1) {
//        try {
//            return this.onlCgformHeadService.editAll(var1);
//        } catch (Exception var3) {
//            a.error("OnlCgformApiController.editAll()发生异常：" + var3.getMessage(), var3);
//            return Result.error("操作失败");
//        }
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online列表加载",
//            module = ModuleType.ONLINE
//    )
//    @OnlineAuth("getColumns")
//    @GetMapping({"/getColumns/{code}"})
//    public Result<OnlComplexModel> a(@PathVariable("code") String var1) {
//        Result var2 = new Result();
//        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var3 == null) {
//            var2.error500("实体不存在");
//            return var2;
//        } else {
//            LoginUser var4 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//            OnlComplexModel var5 = this.onlineService.queryOnlineConfig(var3, var4.getUsername());
//            var5.setIsDesForm(var3.getIsDesForm());
//            var5.setDesFormCode(var3.getDesFormCode());
//            var2.setResult(var5);
//            var2.setOnlTable(var3.getTableName());
//            return var2;
//        }
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online列表数据查询",
//            module = ModuleType.ONLINE
//    )
//    @PermissionData
//    @OnlineAuth("getData")
//    @GetMapping({"/getData/{code}"})
//    public Result<Map<String, Object>> a(@PathVariable("code") String var1, HttpServletRequest var2) {
//        Result var3 = new Result();
//        OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var4 == null) {
//            var3.error500("实体不存在");
//            return var3;
//        } else {
//            try {
//                String var5 = var4.getTableName();
//                Map var6 = OnlCgformButtonController.a(var2);
//                Map var7 = this.onlCgformFieldService.queryAutolistPage(var5, var1, var6, (List)null);
//                this.a(var4, var7);
//                var3.setResult(var7);
//            } catch (Exception var8) {
//                a.error(var8.getMessage(), var8);
//                var3.error500("数据库查询失败，" + var8.getMessage());
//            }
//
//            var3.setOnlTable(var4.getTableName());
//            return var3;
//        }
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online表单加载",
//            module = ModuleType.ONLINE
//    )
//    @OnlineAuth("getFormItem")
//    @GetMapping({"/getFormItem/{code}"})
//    public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2) {
//        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var3 == null) {
//            Result.error("表不存在");
//        }
//
//        Result var4 = new Result();
//        LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        JSONObject var6 = this.onlineService.queryOnlineFormItem(var3, var5.getUsername());
//        var4.setResult(OnlCgformButtonController.b(var6));
//        var4.setOnlTable(var3.getTableName());
//        return var4;
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online根据表名加载表单",
//            module = ModuleType.ONLINE
//    )
//    @GetMapping({"/getFormItemBytbname/{table}"})
//    public Result<?> a(@PathVariable("table") String var1, @RequestParam(name = "taskId",required = false) String var2) {
//        Result var3 = new Result();
//        LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//        var4.eq("tableName", var1);
//        OnlCgformHead var5 = (OnlCgformHead)this.onlCgformHeadService.getOne(var4);
//        if (var5 == null) {
//            Result.error("表不存在");
//        }
//
//        LoginUser var6 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        JSONObject var7 = this.onlineService.queryFlowOnlineFormItem(var5, var6.getUsername(), var2);
//        var3.setResult(OnlCgformButtonController.b(var7));
//        var3.setOnlTable(var1);
//        return var3;
//    }
//
//    @OnlineAuth("getEnhanceJs")
//    @GetMapping({"/getEnhanceJs/{code}"})
//    public Result<?> c(@PathVariable("code") String var1, HttpServletRequest var2) {
//        String var3 = this.onlineService.queryEnahcneJsString(var1, "form");
//        return Result.ok(var3);
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online表单数据查询"
//    )
//    @GetMapping({"/form/{code}/{id}"})
//    public Result<?> b(@PathVariable("code") String var1, @PathVariable("id") String var2) {
//        try {
//            Map var3 = this.onlCgformHeadService.queryManyFormData(var1, var2);
//            return Result.ok(OnlCgformButtonController.b(var3));
//        } catch (Exception var4) {
//            a.error("Online表单查询异常：" + var4.getMessage(), var4);
//            return Result.error("查询失败，" + var4.getMessage());
//        }
//    }
//
//    @GetMapping({"/subform/{table}/{mainId}"})
//    public Result<?> c(@PathVariable("table") String var1, @PathVariable("mainId") String var2) {
//        try {
//            Map var3 = this.onlCgformHeadService.querySubFormData(var1, var2);
//            return Result.ok(OnlCgformButtonController.b(var3));
//        } catch (Exception var4) {
//            a.error("Online表单查询异常：" + var4.getMessage(), var4);
//            return Result.error("查询失败，" + var4.getMessage());
//        }
//    }
//
//    @GetMapping({"/subform/list/{table}/{mainId}"})
//    public Result<?> d(@PathVariable("table") String var1, @PathVariable("mainId") String var2) {
//        try {
//            return Result.ok(this.onlCgformHeadService.queryManySubFormData(var1, var2));
//        } catch (Exception var4) {
//            a.error("Online表单查询异常：" + var4.getMessage(), var4);
//            return Result.error("查询失败，" + var4.getMessage());
//        }
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online根据表名查询表单数据",
//            module = ModuleType.ONLINE
//    )
//    @GetMapping({"/form/table_name/{tableName}/{dataId}"})
//    public Result<?> e(@PathVariable("tableName") String var1, @PathVariable("dataId") String var2) {
//        try {
//            LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//            var3.eq("tableName", var1);
//            OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadService.getOne(var3);
//            if (var4 == null) {
//                throw new Exception("OnlCgform tableName: " + var1 + " 不存在！");
//            } else {
//                Result var5 = this.b(var4.getId(), var2);
//                var5.setOnlTable(var1);
//                return var5;
//            }
//        } catch (Exception var6) {
//            a.error("Online表单查询异常，" + var6.getMessage(), var6);
//            return Result.error("查询失败，" + var6.getMessage());
//        }
//    }
//
//    @AutoLog(
//            operateType = 2,
//            value = "online新增数据",
//            module = ModuleType.ONLINE
//    )
//    @OnlineAuth("form")
//    @PostMapping({"/form/{code}"})
//    public Result<String> a(@PathVariable("code") String var1, @RequestBody JSONObject var2, HttpServletRequest var3) {
//        Result var4 = new Result();
//
//        try {
//            String var5 = OnlCgformButtonController.a();
//            var2.put("id", var5);
//            String var6 = TokenUtils.getTokenByRequest(var3);
//            String var7 = this.onlCgformHeadService.saveManyFormData(var1, var2, var6);
//            var4.setSuccess(true);
//            var4.setResult(var5);
//            var4.setOnlTable(var7);
//        } catch (Exception var8) {
//            a.error("OnlCgformApiController.formAdd()发生异常：", var8);
//            var4.setSuccess(false);
//            var4.setMessage("保存失败，" + OnlCgformButtonController.a(var8));
//        }
//
//        return var4;
//    }
//
//    @AutoLog(
//            operateType = 3,
//            value = "online修改数据",
//            module = ModuleType.ONLINE
//    )
//    @OnlineAuth("form")
//    @PutMapping({"/form/{code}"})
//    public Result<?> a(@PathVariable("code") String var1, @RequestBody JSONObject var2) {
//        try {
//            String var3 = this.onlCgformHeadService.editManyFormData(var1, var2);
//            Result var4 = Result.ok("修改成功！");
//            var4.setOnlTable(var3);
//            return var4;
//        } catch (Exception var5) {
//            a.error("OnlCgformApiController.formEdit()发生异常：" + var5.getMessage(), var5);
//            return Result.error("修改失败，" + OnlCgformButtonController.a(var5));
//        }
//    }
//
//    @AutoLog(
//            operateType = 4,
//            value = "online删除数据",
//            module = ModuleType.ONLINE
//    )
//    @OnlineAuth("form")
//    @DeleteMapping({"/form/{code}/{id}"})
//    public Result<?> f(@PathVariable("code") String var1, @PathVariable("id") String var2) {
//        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var3 == null) {
//            return Result.error("实体不存在");
//        } else {
//            try {
//                String var4 = "";
//                if ("Y".equals(var3.getIsTree())) {
//                    var2 = this.onlCgformFieldService.queryTreeChildIds(var3, var2);
//                    var4 = this.onlCgformFieldService.queryTreePids(var3, var2);
//                }
//
//                if (var2.indexOf(",") > 0) {
//                    String var5;
//                    if (var3.getTableType() == 2) {
//                        this.onlCgformFieldService.deleteAutoListMainAndSub(var3, var2);
//                    } else {
//                        var5 = var3.getTableName();
//                        this.onlCgformFieldService.deleteAutoListById(var5, var2);
//                    }
//
//                    if ("Y".equals(var3.getIsTree())) {
//                        var5 = var3.getTableName();
//                        String var6 = var3.getTreeIdField();
//                        String[] var7 = var4.split(",");
//                        String[] var8 = var7;
//                        int var9 = var7.length;
//
//                        for(int var10 = 0; var10 < var9; ++var10) {
//                            String var11 = var8[var10];
//                            this.onlCgformFieldService.updateTreeNodeNoChild(var5, var6, var11);
//                        }
//                    }
//                } else {
//                    this.onlCgformHeadService.deleteOneTableInfo(var1, var2);
//                }
//
//                if (oConvertUtils.isNotEmpty(var3.getIsDesForm()) && "1".equals(var3.getIsDesForm())) {
//                    this.onlCgformFieldService.deleteAutoList("design_form_data", "online_form_data_id", var2);
//                }
//            } catch (Exception var12) {
//                a.error("OnlCgformApiController.formEdit()发生异常：" + var12.getMessage(), var12);
//                return Result.error("删除失败");
//            }
//
//            Result var13 = Result.ok("删除成功!");
//            var13.setOnlTable(var3.getTableName());
//            return var13;
//        }
//    }
//
//    @AutoLog(
//            operateType = 4,
//            value = "online删除数据",
//            module = ModuleType.ONLINE
//    )
//    @DeleteMapping({"/formByCode/{code}/{id}"})
//    public Result<?> g(@PathVariable("code") String var1, @PathVariable("id") String var2) {
//        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var1));
//        if (var3 == null) {
//            return Result.error("实体不存在");
//        } else {
//            try {
//                if (var2.indexOf(",") > 0) {
//                    String var4 = var3.getTableName();
//                    this.onlCgformFieldService.deleteAutoListById(var4, var2);
//                } else {
//                    this.onlCgformHeadService.deleteOneTableInfo(var3.getId(), var2);
//                }
//            } catch (Exception var5) {
//                a.error("OnlCgformApiController.formEdit()发生异常：" + var5.getMessage(), var5);
//                return Result.error("删除失败");
//            }
//
//            Result var6 = Result.ok("删除成功!");
//            var6.setOnlTable(var3.getTableName());
//            return var6;
//        }
//    }
//
//    @OnlineAuth("getQueryInfo")
//    @GetMapping({"/getQueryInfo/{code}"})
//    public Result<?> b(@PathVariable("code") String var1) {
//        try {
//            List var2 = this.onlCgformFieldService.getAutoListQueryInfo(var1);
//            return Result.ok(var2);
//        } catch (Exception var3) {
//            a.error("OnlCgformApiController.getQueryInfo()发生异常：" + var3.getMessage(), var3);
//            return Result.error("查询失败");
//        }
//    }
//
//    @PostMapping({"/doDbSynch/{code}/{synMethod}"})
//    public Result<?> h(@PathVariable("code") String var1, @PathVariable("synMethod") String var2) {
//        try {
//            long var3 = System.currentTimeMillis();
//            this.onlCgformHeadService.doDbSynch(var1, var2);
//            a.info("==同步数据库消耗时间" + (System.currentTimeMillis() - var3) + "毫秒");
//        } catch (Exception var5) {
//            a.error(var5.getMessage(), var5);
//            return Result.error("同步数据库失败，" + OnlCgformButtonController.a(var5));
//        }
//
//        return Result.ok("同步数据库成功!");
//    }
//
//    @OnlineAuth("exportXls")
//    @PermissionData
//    @GetMapping({"/exportXls/{code}"})
//    public void a(@PathVariable("code") String var1, HttpServletRequest var2, HttpServletResponse var3) {
//        OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var4 != null) {
//            String var5 = var4.getTableTxt();
//            String var6 = var2.getParameter("paramsStr");
//            Object var7 = new HashMap();
//            Object var8 = null;
//            if (oConvertUtils.isNotEmpty(var6)) {
//                var7 = (Map)JSONObject.parseObject(var6, Map.class);
//            }
//
//            ((Map)var7).put("pageSize", -521);
//            Map var9 = this.onlCgformFieldService.queryAutolistPage(var4.getTableName(), var4.getId(), (Map)var7, (List)null);
//            List var10 = (List)var9.get("fieldList");
//            Object var11 = (List)var9.get("records");
//            Object var12 = new ArrayList();
//            String var13 = ((Map)var7).get("selections") == null ? null : ((Map)var7).get("selections").toString();
//            List var14;
//            if (oConvertUtils.isNotEmpty(var13)) {
//                var14 = Arrays.asList(var13.split(","));
//                var12 = (List)((List)var11).stream().filter((var1x) -> {
//                    return var14.contains(var1x.get("id"));
//                }).collect(Collectors.toList());
//            } else {
//                if (var11 == null) {
//                    var11 = new ArrayList();
//                }
//
//                ((List)var12).addAll((Collection)var11);
//            }
//
//            com.sxlinks.modules.online.cgform.converter.b.a(1, (List)var12, var10);
//
//            try {
//                this.onlCgformHeadService.executeEnhanceExport(var4, (List)var12);
//            } catch (BusinessException var31) {
//                a.error("导出java增强处理出错", var31.getMessage());
//            }
//
//            var14 = this.a(var10, "id");
//            if (var4.getTableType() == 2 && oConvertUtils.isEmpty(((Map)var7).get("exportSingleOnly"))) {
//                String var15 = var4.getSubTableStr();
//                if (oConvertUtils.isNotEmpty(var15)) {
//                    String[] var16 = var15.split(",");
//                    String[] var17 = var16;
//                    int var18 = var16.length;
//
//                    for(int var19 = 0; var19 < var18; ++var19) {
//                        String var20 = var17[var19];
//                        this.a(var20, (Map)var7, (List)var12, var14);
//                    }
//                }
//            }
//
//            Workbook var33 = ExcelExportUtil.exportExcel(new ExportParams((String)null, var5), var14, (Collection)var12);
//            ServletOutputStream var34 = null;
//
//            try {
//                var3.setContentType("application/x-msdownload;charset=utf-8");
//                String var35 = BrowserUtils.checkBrowse(var2);
//                String var36 = var4.getTableTxt() + "-v" + var4.getTableVersion();
//                if ("MSIE".equalsIgnoreCase(var35.substring(0, 4))) {
//                    var3.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(var36, "UTF-8") + ".xls");
//                } else {
//                    String var37 = new String(var36.getBytes("UTF-8"), "ISO8859-1");
//                    var3.setHeader("content-disposition", "attachment;filename=" + var37 + ".xls");
//                }
//
//                var34 = var3.getOutputStream();
//                var33.write(var34);
//                var3.flushBuffer();
//            } catch (Exception var30) {
//                a.error("--通过流的方式获取文件异常--" + var30.getMessage(), var30);
//            } finally {
//                if (var34 != null) {
//                    try {
//                        var34.close();
//                    } catch (IOException var29) {
//                        a.error(var29.getMessage(), var29);
//                    }
//                }
//
//            }
//
//        }
//    }
//
//    @OnlineAuth("importXls")
//    @PostMapping({"/importXls/{code}"})
//    public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2, HttpServletResponse var3) {
//        long var4 = System.currentTimeMillis();
//        Result var6 = new Result();
//        String var7 = "";
//        String var8 = var2.getParameter("validateStatus");
//        StringBuffer var9 = new StringBuffer();
//
//        try {
//            OnlCgformHead var10 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//            if (var10 == null) {
//                return Result.error("数据库不存在该表记录");
//            }
//
//            LambdaQueryWrapper var11 = new LambdaQueryWrapper();
//            var11.eq("cgformHeadId", var1);
//            List var12 = this.onlCgformFieldService.list(var11);
//            String var13 = var2.getParameter("isSingleTableImport");
//            List var14 = OnlCgformButtonController.e(var12);
//            if (oConvertUtils.isEmpty(var13) && var10.getTableType() == 2 && oConvertUtils.isNotEmpty(var10.getSubTableStr())) {
//                String[] var15 = var10.getSubTableStr().split(",");
//                int var16 = var15.length;
//
//                for(int var17 = 0; var17 < var16; ++var17) {
//                    String var18 = var15[var17];
//                    OnlCgformHead var19 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var18));
//                    if (var19 != null) {
//                        List var20 = this.onlCgformFieldService.list((Wrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var19.getId()));
//                        List var21 = OnlCgformButtonController.b(var20, var19.getTableTxt());
//                        if (var21.size() > 0) {
//                            var14.addAll(var21);
//                        }
//                    }
//                }
//            }
//
//            JSONObject var49 = null;
//            String var50 = var2.getParameter("foreignKeys");
//            if (oConvertUtils.isNotEmpty(var50)) {
//                var49 = JSONObject.parseObject(var50);
//            }
//
//            MultipartHttpServletRequest var51 = (MultipartHttpServletRequest)var2;
//            Map var52 = var51.getFileMap();
//            DataSource var53 = (DataSource)SpringContextUtils.getApplicationContext().getBean(DataSource.class);
//            String var54 = d.a(var53);
//            Iterator var55 = var52.entrySet().iterator();
//
//            while(true) {
//                while(var55.hasNext()) {
//                    Entry var22 = (Entry)var55.next();
//                    MultipartFile var23 = (MultipartFile)var22.getValue();
//                    ImportParams var24 = new ImportParams();
//                    var24.setImageList(var14);
//                    var24.setDataHanlder(new com.sxlinks.modules.online.cgform.util.a(var12, this.upLoadPath, this.uploadType));
//                    List var25 = ExcelImportUtil.importExcel(var23.getInputStream(), Map.class, var24);
//                    if (var25 != null) {
//                        Object var26 = "";
//                        ArrayList var27 = new ArrayList();
//
//                        Map var29;
//                        for(Iterator var28 = var25.iterator(); var28.hasNext(); var29.put("$mainTable$id", var26)) {
//                            var29 = (Map)var28.next();
//                            boolean var30 = false;
//                            Set var31 = var29.keySet();
//                            HashMap var32 = new HashMap();
//                            Iterator var33 = var31.iterator();
//
//                            String var34;
//                            while(var33.hasNext()) {
//                                var34 = (String)var33.next();
//                                if (var34.indexOf("$subTable$") == -1) {
//                                    if (var34.indexOf("$mainTable$") != -1 && oConvertUtils.isNotEmpty(var29.get(var34).toString())) {
//                                        var30 = true;
//                                        var26 = this.a(var10, var53, var54);
//                                    }
//
//                                    var32.put(var34.replace("$mainTable$", ""), var29.get(var34));
//                                }
//                            }
//
//                            if (var30) {
//                                var32.put("id", var26);
//                                var27.add(var32);
//                                var26 = var32.get("id");
//                            }
//
//                            if (var49 != null) {
//                                var33 = var49.keySet().iterator();
//
//                                while(var33.hasNext()) {
//                                    var34 = (String)var33.next();
//                                    System.out.println(var34 + "=" + var49.getString(var34));
//                                    var32.put(var34, var49.getString(var34));
//                                }
//                            }
//                        }
//
//                        if (var27 == null || var27.size() == 0) {
//                            var6.setSuccess(false);
//                            var6.setMessage("导入失败，匹配的数据条数为零!");
//                            return var6;
//                        }
//
//                        if ("1".equals(var8)) {
//                            Map var57 = this.onlCgformSqlService.saveOnlineImportDataWithValidate(var10, var12, var27);
//                            String var59 = (String)var57.get("error");
//                            var7 = (String)var57.get("tip");
//                            if (var59 != null && var59.length() > 0) {
//                                var9.append(var10.getTableTxt() + "导入校验," + var7 + ",详情如下:\r\n" + var59);
//                            }
//                        } else {
//                            this.onlCgformSqlService.saveBatchOnlineTable(var10, var12, var27);
//                        }
//
//                        if (oConvertUtils.isEmpty(var13) && var10.getTableType() == 2 && oConvertUtils.isNotEmpty(var10.getSubTableStr())) {
//                            String[] var58 = var10.getSubTableStr().split(",");
//                            int var60 = var58.length;
//
//                            for(int var61 = 0; var61 < var60; ++var61) {
//                                String var62 = var58[var61];
//                                OnlCgformHead var63 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var62));
//                                if (var63 != null) {
//                                    LambdaQueryWrapper var64 = new LambdaQueryWrapper();
//                                    var64.eq("cgformHeadId", var63.getId());
//                                    List var65 = this.onlCgformFieldService.list(var64);
//                                    ArrayList var35 = new ArrayList();
//                                    String var36 = var63.getTableTxt();
//                                    Iterator var37 = var25.iterator();
//
//                                    while(var37.hasNext()) {
//                                        Map var38 = (Map)var37.next();
//                                        boolean var39 = false;
//                                        HashMap var40 = new HashMap();
//                                        Iterator var41 = var65.iterator();
//
//                                        while(var41.hasNext()) {
//                                            OnlCgformField var42 = (OnlCgformField)var41.next();
//                                            String var43 = var42.getMainTable();
//                                            String var44 = var42.getMainField();
//                                            boolean var45 = var10.getTableName().equals(var43) && oConvertUtils.isNotEmpty(var44);
//                                            String var46 = var36 + "_" + var42.getDbFieldTxt();
//                                            if (var45) {
//                                                var40.put(var42.getDbFieldName(), var38.get("$mainTable$" + var44));
//                                            }
//
//                                            Object var47 = var38.get("$subTable$" + var46);
//                                            if (null != var47 && oConvertUtils.isNotEmpty(var47.toString())) {
//                                                var39 = true;
//                                                var40.put(var42.getDbFieldName(), var47);
//                                            }
//                                        }
//
//                                        if (var39) {
//                                            var40.put("id", this.a(var63, var53, var54));
//                                            var35.add(var40);
//                                        }
//                                    }
//
//                                    if (var35.size() > 0) {
//                                        if ("1".equals(var8)) {
//                                            Map var66 = this.onlCgformSqlService.saveOnlineImportDataWithValidate(var63, var65, var35);
//                                            String var67 = (String)var66.get("error");
//                                            String var68 = (String)var66.get("tip");
//                                            if (var67 != null && var67.length() > 0) {
//                                                var9.append(var63.getTableTxt() + "导入校验," + var68 + ",详情如下:\r\n" + var67);
//                                            }
//                                        } else {
//                                            this.onlCgformSqlService.saveBatchOnlineTable(var63, var65, var35);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        var7 = "识别模版数据错误";
//                        a.error(var7);
//                    }
//                }
//
//                var6.setSuccess(true);
//                if ("1".equals(var8) && var9.length() > 0) {
//                    String var56 = OnlCgformButtonController.a(this.upLoadPath, var10.getTableTxt(), var9);
//                    var6.setResult(var56);
//                    var6.setMessage(var7);
//                    var6.setCode(201);
//                } else {
//                    var6.setMessage("导入成功!");
//                }
//                break;
//            }
//        } catch (Exception var48) {
//            var6.setSuccess(false);
//            var6.setMessage(var48.getMessage());
//            a.error(var48.getMessage(), var48);
//        }
//
//        a.info("=====online导入数据完成,耗时:" + (System.currentTimeMillis() - var4) + "毫秒=====");
//        return var6;
//    }
//
//    @PostMapping({"/doButton"})
//    public Result<?> a(@RequestBody JSONObject var1) {
//        String var2 = var1.getString("formId");
//        String var3 = var1.getString("dataId");
//        String var4 = var1.getString("buttonCode");
//        JSONObject var5 = var1.getJSONObject("uiFormData");
//
//        try {
//            this.onlCgformHeadService.executeCustomerButton(var4, var2, var3);
//        } catch (Exception var7) {
//            a.error(var7.getMessage(), var7);
//            return Result.error("执行失败!");
//        }
//
//        return Result.ok("执行成功!");
//    }
//
//    public Object a(OnlCgformHead var1, DataSource var2, String var3) throws SQLException, DBException {
//        Object var4 = null;
//        String var5 = var1.getIdType();
//        String var6 = var1.getIdSequence();
//        if (oConvertUtils.isNotEmpty(var5) && "UUID".equalsIgnoreCase(var5)) {
//            var4 = OnlCgformButtonController.a();
//        } else {
//            PostgreSQLSequenceMaxValueIncrementer var7;
//            OracleSequenceMaxValueIncrementer var13;
//            if (oConvertUtils.isNotEmpty(var5) && "NATIVE".equalsIgnoreCase(var5)) {
//                if (oConvertUtils.isNotEmpty(var3) && "oracle".equalsIgnoreCase(var3)) {
//                    var13 = new OracleSequenceMaxValueIncrementer(var2, "HIBERNATE_SEQUENCE");
//
//                    try {
//                        var4 = var13.nextLongValue();
//                    } catch (Exception var12) {
//                        a.error(var12.getMessage(), var12);
//                    }
//                } else if (oConvertUtils.isNotEmpty(var3) && "postgres".equalsIgnoreCase(var3)) {
//                    var7 = new PostgreSQLSequenceMaxValueIncrementer(var2, "HIBERNATE_SEQUENCE");
//
//                    try {
//                        var4 = var7.nextLongValue();
//                    } catch (Exception var11) {
//                        a.error(var11.getMessage(), var11);
//                    }
//                } else {
//                    var4 = null;
//                }
//            } else if (oConvertUtils.isNotEmpty(var5) && "SEQUENCE".equalsIgnoreCase(var5)) {
//                if (oConvertUtils.isNotEmpty(var3) && "oracle".equalsIgnoreCase(var3)) {
//                    var13 = new OracleSequenceMaxValueIncrementer(var2, var6);
//
//                    try {
//                        var4 = var13.nextLongValue();
//                    } catch (Exception var10) {
//                        a.error(var10.getMessage(), var10);
//                    }
//                } else if (oConvertUtils.isNotEmpty(var3) && "postgres".equalsIgnoreCase(var3)) {
//                    var7 = new PostgreSQLSequenceMaxValueIncrementer(var2, var6);
//
//                    try {
//                        var4 = var7.nextLongValue();
//                    } catch (Exception var9) {
//                        a.error(var9.getMessage(), var9);
//                    }
//                } else {
//                    var4 = null;
//                }
//            } else {
//                var4 = OnlCgformButtonController.a();
//            }
//        }
//
//        return var4;
//    }
//
//    private void a(Map var1, List<OnlCgformField> var2) {
//        Iterator var3 = var2.iterator();
//
//        while(true) {
//            OnlCgformField var4;
//            String var5;
//            String var6;
//            String var7;
//            do {
//                do {
//                    if (!var3.hasNext()) {
//                        return;
//                    }
//
//                    var4 = (OnlCgformField)var3.next();
//                    var5 = var4.getDictTable();
//                    var6 = var4.getDictField();
//                    var7 = var4.getDictText();
//                } while(oConvertUtils.isEmpty(var5) && oConvertUtils.isEmpty(var6));
//            } while("popup".equals(var4.getFieldShowType()));
//
//            String var9 = String.valueOf(var1.get(var4.getDbFieldName()));
//            List var8;
//            if (oConvertUtils.isEmpty(var5)) {
//                var8 = this.sysBaseAPI.queryDictItemsByCode(var6);
//            } else {
//                var8 = this.sysBaseAPI.queryTableDictItemsByCode(var5, var7, var6);
//            }
//
//            Iterator var10 = var8.iterator();
//
//            while(var10.hasNext()) {
//                DictModel var11 = (DictModel)var10.next();
//                if (var9.equals(var11.getText())) {
//                    var1.put(var4.getDbFieldName(), var11.getValue());
//                }
//            }
//        }
//    }
//
//    private List<ExcelExportEntity> a(List<OnlCgformField> var1, String var2) {
//        ArrayList var3 = new ArrayList();
//
//        for(int var4 = 0; var4 < var1.size(); ++var4) {
//            if ((null == var2 || !var2.equals(((OnlCgformField)var1.get(var4)).getDbFieldName())) && ((OnlCgformField)var1.get(var4)).getIsShowList() == 1) {
//                String var5 = ((OnlCgformField)var1.get(var4)).getDbFieldName();
//                ExcelExportEntity var6 = new ExcelExportEntity(((OnlCgformField)var1.get(var4)).getDbFieldTxt(), var5);
//                if ("image".equals(((OnlCgformField)var1.get(var4)).getFieldShowType())) {
//                    var6.setType(2);
//                    var6.setExportImageType(3);
//                    var6.setImageBasePath(this.upLoadPath);
//                    var6.setHeight(50.0D);
//                    var6.setWidth(60.0D);
//                } else {
//                    int var7 = ((OnlCgformField)var1.get(var4)).getDbLength() == 0 ? 12 : (((OnlCgformField)var1.get(var4)).getDbLength() > 30 ? 30 : ((OnlCgformField)var1.get(var4)).getDbLength());
//                    if (((OnlCgformField)var1.get(var4)).getFieldShowType().equals("date")) {
//                        var6.setFormat("yyyy-MM-dd");
//                    } else if (((OnlCgformField)var1.get(var4)).getFieldShowType().equals("datetime")) {
//                        var6.setFormat("yyyy-MM-dd HH:mm:ss");
//                    }
//
//                    if (var7 < 10) {
//                        var7 = 10;
//                    }
//
//                    var6.setWidth((double)var7);
//                }
//
//                var3.add(var6);
//            }
//        }
//
//        return var3;
//    }
//
//    private void a(String var1, Map<String, Object> var2, List<Map<String, Object>> var3, List<ExcelExportEntity> var4) {
//        OnlCgformHead var5 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var1));
//        LambdaQueryWrapper var6 = new LambdaQueryWrapper();
//        var6.eq("cgformHeadId", var5.getId());
//        var6.orderByAsc("orderNum");
//        List var7 = this.onlCgformFieldService.list(var6);
//        String var8 = "";
//        String var9 = "";
//        Iterator var10 = var7.iterator();
//
//        while(var10.hasNext()) {
//            OnlCgformField var11 = (OnlCgformField)var10.next();
//            if (oConvertUtils.isNotEmpty(var11.getMainField())) {
//                var8 = var11.getMainField();
//                var9 = var11.getDbFieldName();
//                break;
//            }
//        }
//
//        ExcelExportEntity var14 = new ExcelExportEntity(var5.getTableTxt(), var1);
//        var14.setList(this.a(var7, "id"));
//        var4.add(var14);
//
//        for(int var15 = 0; var15 < var3.size(); ++var15) {
//            var2.put(var9, ((Map)var3.get(var15)).get(var8));
//            String var12 = OnlCgformButtonController.a(var5.getTableName(), var7, var2);
//            a.info("-----------动态列表查询子表sql》》" + var12);
//            List var13 = this.onlCgformHeadService.queryListData(var12);
//            com.sxlinks.modules.online.cgform.converter.b.a(1, var13, var7);
//            ((Map)var3.get(var15)).put(var1, OnlCgformButtonController.d(var13));
//        }
//
//    }
//
//    @GetMapping({"/checkOnlyTable"})
//    public Result<?> i(@RequestParam("tbname") String var1, @RequestParam("id") String var2) {
//        OnlCgformHead var3;
//        if (oConvertUtils.isEmpty(var2)) {
//            if (d.a(var1)) {
//                return Result.ok(-1);
//            }
//
//            var3 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var1));
//            if (oConvertUtils.isNotEmpty(var3)) {
//                return Result.ok(-1);
//            }
//        } else {
//            var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var2);
//            if (!var1.equals(var3.getTableName()) && d.a(var1)) {
//                return Result.ok(-1);
//            }
//        }
//
//        return Result.ok(1);
//    }
//
//    @PostMapping({"/codeGenerate"})
//    public Result<?> b(@RequestBody JSONObject var1) {
//        OnlGenerateModel var2 = (OnlGenerateModel)JSONObject.parseObject(var1.toJSONString(), OnlGenerateModel.class);
//        List var3 = null;
//
//        try {
//            if ("1".equals(var2.getJformType())) {
//                var3 = this.onlCgformHeadService.generateCode(var2);
//            } else {
//                var3 = this.onlCgformHeadService.generateOneToMany(var2);
//            }
//
//            return Result.ok(var3);
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            return Result.error(var5.getMessage());
//        }
//    }
//
//    @GetMapping({"/downGenerateCode"})
//    public void a(@RequestParam("fileList") List<String> var1, HttpServletRequest var2, HttpServletResponse var3) {
//        List var4 = (List)var1.stream().filter((var0) -> {
//            return var0.indexOf("src/main/java") == -1 && var0.indexOf("src%5Cmain%5Cjava") == -1;
//        }).collect(Collectors.toList());
//        if (var1 != null && (var4 == null || var4.size() <= 0)) {
//            String var5 = "生成代码_" + System.currentTimeMillis() + ".zip";
//            final String var6 = "/opt/temp/codegenerate/" + var5;
//            File var7 = com.sxlinks.modules.online.cgform.util.d.a(var1, var6);
//            if (var7.exists()) {
//                var3.setContentType("application/force-download");
//                var3.addHeader("Content-Disposition", "attachment;fileName=" + var5);
//                byte[] var8 = new byte[1024];
//                FileInputStream var9 = null;
//                BufferedInputStream var10 = null;
//
//                try {
//                    var9 = new FileInputStream(var7);
//                    var10 = new BufferedInputStream(var9);
//                    ServletOutputStream var11 = var3.getOutputStream();
//
//                    for(int var12 = var10.read(var8); var12 != -1; var12 = var10.read(var8)) {
//                        var11.write(var8, 0, var12);
//                    }
//                } catch (Exception var25) {
//                    var25.printStackTrace();
//                } finally {
//                    if (var10 != null) {
//                        try {
//                            var10.close();
//                        } catch (IOException var24) {
//                            var24.printStackTrace();
//                        }
//                    }
//
//                    if (var9 != null) {
//                        try {
//                            var9.close();
//                        } catch (IOException var23) {
//                            var23.printStackTrace();
//                        }
//                    }
//
//                    class NamelessClass_1 extends Thread {
//                        NamelessClass_1() {
//                        }
//
//                        public void run() {
//                            try {
//                                Thread.sleep(10000L);
//                                FileUtil.del(var6);
//                            } catch (InterruptedException var2) {
//                                var2.printStackTrace();
//                            }
//
//                        }
//                    }
//
//                    (new NamelessClass_1()).start();
//                }
//            }
//
//        } else {
//            a.error(" fileList 不合法！！！", var1);
//        }
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online列表数据查询",
//            module = ModuleType.ONLINE
//    )
//    @GetMapping({"/getTreeData/{code}"})
//    @PermissionData
//    public Result<Map<String, Object>> d(@PathVariable("code") String var1, HttpServletRequest var2) {
//        Result var3 = new Result();
//        OnlCgformHead var4 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var4 == null) {
//            var3.error500("实体不存在");
//            return var3;
//        } else {
//            try {
//                String var5 = var4.getTableName();
//                String var6 = var4.getTreeIdField();
//                String var7 = var4.getTreeParentIdField();
//                ArrayList var8 = Lists.newArrayList(new String[]{var6, var7});
//                Map var9 = OnlCgformButtonController.a(var2);
//                String var10 = null;
//                if (var9.get(var6) != null) {
//                    var10 = var9.get(var6).toString();
//                }
//
//                if (var9.get("hasQuery") != null && "false".equals(var9.get("hasQuery")) && var9.get(var7) == null) {
//                    var9.put(var7, "0");
//                } else {
//                    var9.put("pageSize", -521);
//                    var9.put(var7, var9.get(var7));
//                }
//
//                var9.put(var6, (Object)null);
//                Map var11 = this.onlCgformFieldService.queryAutoTreeNoPage(var5, var1, var9, var8, var7);
//                this.a(var4, var11);
//                var3.setResult(var11);
//            } catch (Exception var12) {
//                a.error(var12.getMessage(), var12);
//                var3.error500("数据库查询失败" + var12.getMessage());
//            }
//
//            var3.setOnlTable(var4.getTableName());
//            return var3;
//        }
//    }
//
//    private void a(OnlCgformHead var1, Map<String, Object> var2) throws BusinessException {
//        List var3 = (List)var2.get("records");
//        this.onlCgformHeadService.executeEnhanceList(var1, "query", var3);
//    }
//
//    @PostMapping({"/crazyForm/{name}"})
//    public Result<?> b(@PathVariable("name") String var1, @RequestBody JSONObject var2) {
//        Result var3 = new Result();
//
//        try {
//            String var4 = OnlCgformButtonController.a();
//            var2.put("id", var4);
//            this.onlCgformHeadService.addCrazyFormData(var1, var2);
//            var3.setResult(var4);
//            var3.setMessage("保存成功");
//            return var3;
//        } catch (Exception var5) {
//            a.error("OnlCgformApiController.formAddForDesigner()发生异常：" + var5.getMessage(), var5);
//            return Result.error("保存失败");
//        }
//    }
//
//    @PutMapping({"/crazyForm/{name}"})
//    public Result<?> c(@PathVariable("name") String var1, @RequestBody JSONObject var2) {
//        try {
//            var2.remove("create_by");
//            var2.remove("create_time");
//            var2.remove("update_by");
//            var2.remove("update_time");
//            this.onlCgformHeadService.editCrazyFormData(var1, var2);
//        } catch (Exception var4) {
//            a.error("OnlCgformApiController.formEditForDesigner()发生异常：" + var4.getMessage(), var4);
//            return Result.error("保存失败");
//        }
//
//        return Result.ok("保存成功!");
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online列表加载",
//            module = ModuleType.ONLINE
//    )
//    @GetMapping({"/getErpColumns/{code}"})
//    public Result<Map<String, Object>> c(@PathVariable("code") String var1) {
//        Result var2 = new Result();
//        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var3 == null) {
//            var2.error500("实体不存在");
//            return var2;
//        } else {
//            HashMap var4 = new HashMap();
//            LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//            OnlComplexModel var6 = this.onlineService.queryOnlineConfig(var3, var5.getUsername());
//            var4.put("main", var6);
//            if ("erp".equals(var3.getThemeTemplate()) && var3.getTableType() == 2) {
//                String var7 = var3.getSubTableStr();
//                if (oConvertUtils.isNotEmpty(var7)) {
//                    ArrayList var8 = new ArrayList();
//                    String[] var9 = var7.split(",");
//                    int var10 = var9.length;
//
//                    for(int var11 = 0; var11 < var10; ++var11) {
//                        String var12 = var9[var11];
//                        OnlCgformHead var13 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var12));
//                        if (var13 != null) {
//                            var8.add(this.onlineService.queryOnlineConfig(var13, var5.getUsername()));
//                        }
//                    }
//
//                    if (var8.size() > 0) {
//                        var4.put("subList", var8);
//                    }
//                }
//            }
//
//            var2.setOnlTable(var3.getTableName());
//            var2.setResult(var4);
//            var2.setSuccess(true);
//            return var2;
//        }
//    }
//
//    @AutoLog(
//            operateType = 1,
//            value = "online表单加载",
//            module = ModuleType.ONLINE
//    )
//    @GetMapping({"/getErpFormItem/{code}"})
//    public Result<?> e(@PathVariable("code") String var1, HttpServletRequest var2) {
//        OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//        if (var3 == null) {
//            Result.error("表不存在");
//        }
//
//        Result var4 = new Result();
//        LoginUser var5 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//        JSONObject var6 = this.onlineService.queryOnlineFormObj(var3, var5.getUsername());
//        var4.setResult(OnlCgformButtonController.b(var6));
//        var4.setOnlTable(var3.getTableName());
//        return var4;
//    }
//
//    @GetMapping({"/querySelectOptions"})
//    public Result<List<TreeModel>> a(@ModelAttribute com.sxlinks.modules.online.cgform.a.a var1) {
//        Result var2 = new Result();
//        List var3 = this.onlCgformFieldService.queryDataListByLinkDown(var1);
//        var2.setResult(var3);
//        var2.setSuccess(true);
//        return var2;
//    }
//}

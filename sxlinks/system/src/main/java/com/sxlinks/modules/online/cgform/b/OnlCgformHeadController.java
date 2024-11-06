////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.b;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Properties;
//import javax.servlet.http.HttpServletRequest;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.constant.enums.CgformEnum;
//import com.sxlinks.common.system.query.QueryGenerator;
//import com.sxlinks.common.system.util.JwtUtil;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJava;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceJs;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformEnhanceSql;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformEnhanceService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import com.sxlinks.modules.online.cgform.util.b;
//import org.jeecgframework.codegenerate.database.DbReadTableUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("onlCgformHeadController")
//@RequestMapping({"/online/cgform/head"})
//public class OnlCgformHeadController {
//  private static final Logger a = LoggerFactory.getLogger(OnlCgformHeadController.class);
//  @Autowired
//  private IOnlCgformHeadService onlCgformHeadService;
//  @Autowired
//  private IOnlCgformFieldService onlCgformFieldService;
//  @Autowired
//  private IOnlCgformEnhanceService onlCgformEnhanceService;
//  private static List<String> b = null;
//  @Autowired
//  ResourceLoader resourceLoader;
//  private static String c;
//
//  public OnlCgformHeadController() {
//  }
//
//  @GetMapping({"/list"})
//  public Result<IPage<OnlCgformHead>> a(OnlCgformHead var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
//    Result var5 = new Result();
//    QueryWrapper var6 = QueryGenerator.initQueryWrapper(var1, var4.getParameterMap());
//    Page var7 = new Page((long)var2, (long)var3);
//    IPage var8 = this.onlCgformHeadService.page(var7, var6);
//    if (var1.getCopyType() != null && var1.getCopyType() == 0) {
//      this.onlCgformHeadService.initCopyState(var8.getRecords());
//    }
//
//    var5.setSuccess(true);
//    var5.setResult(var8);
//    return var5;
//  }
//
//  @PostMapping({"/add"})
//  public Result<OnlCgformHead> a(@RequestBody OnlCgformHead var1) {
//    Result var2 = new Result();
//
//    try {
//      this.onlCgformHeadService.save(var1);
//      var2.success("添加成功！");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @PutMapping({"/edit"})
//  public Result<OnlCgformHead> b(@RequestBody OnlCgformHead var1) {
//    Result var2 = new Result();
//    OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1.getId());
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformHeadService.updateById(var1);
//      if (var4) {
//        var2.success("修改成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/delete"})
//  public Result<?> a(@RequestParam(name = "id",required = true) String var1) {
//    try {
//      this.onlCgformHeadService.deleteRecordAndTable(var1);
//    } catch (SQLException var4) {
//      return Result.error("删除失败" + var4.getMessage());
//    }
//
//    return Result.ok("删除成功!");
//  }
//
//  @DeleteMapping({"/removeRecord"})
//  public Result<?> b(@RequestParam(name = "id",required = true) String var1) {
//    try {
//      this.onlCgformHeadService.deleteRecord(var1);
//    } catch (SQLException var4) {
//      return Result.error("移除失败" + var4.getMessage());
//    }
//
//    return Result.ok("移除成功!");
//  }
//
//  @DeleteMapping({"/deleteBatch"})
//  public Result<OnlCgformHead> a(@RequestParam(name = "ids",required = true) String var1, @RequestParam(name = "flag") String var2) {
//    Result var3 = new Result();
//    if (var1 != null && !"".equals(var1.trim())) {
//      this.onlCgformHeadService.deleteBatch(var1, var2);
//      var3.success("删除成功!");
//    } else {
//      var3.error500("参数不识别！");
//    }
//
//    return var3;
//  }
//
//  @GetMapping({"/queryById"})
//  public Result<OnlCgformHead> c(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      var2.setResult(var3);
//      var2.setSuccess(true);
//    }
//
//    return var2;
//  }
//
//  @GetMapping({"/queryByTableNames"})
//  public Result<?> d(@RequestParam(name = "tableNames",required = true) String var1) {
//    LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//    String[] var3 = var1.split(",");
//    var2.in("tableName", Arrays.asList(var3));
//    List var4 = this.onlCgformHeadService.list(var2);
//    return var4 == null ? Result.error("未找到对应实体") : Result.ok(var4);
//  }
//
//  @PostMapping({"/enhanceJs/{code}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> a(@PathVariable("code") String var1, @RequestBody OnlCgformEnhanceJs var2) {
//    try {
//      var2.setCgformHeadId(var1);
//      this.onlCgformHeadService.saveEnhance(var2);
//      return Result.ok("保存成功!");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("保存失败!");
//    }
//  }
//
//  @GetMapping({"/enhanceJs/{code}"})
//  public Result<?> a(@PathVariable("code") String var1, HttpServletRequest var2) {
//    try {
//      String var3 = var2.getParameter("type");
//      OnlCgformEnhanceJs var4 = this.onlCgformHeadService.queryEnhance(var1, var3);
//      return var4 == null ? Result.error("查询为空") : Result.ok(var4);
//    } catch (Exception var5) {
//      a.error(var5.getMessage(), var5);
//      return Result.error("查询失败!");
//    }
//  }
//
//  @PutMapping({"/enhanceJs/{code}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> b(@PathVariable("code") String var1, @RequestBody OnlCgformEnhanceJs var2) {
//    try {
//      var2.setCgformHeadId(var1);
//      this.onlCgformHeadService.editEnhance(var2);
//      return Result.ok("保存成功!");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("保存失败!");
//    }
//  }
//
//  @GetMapping({"/enhanceButton/{formId}"})
//  public Result<?> b(@PathVariable("formId") String var1, HttpServletRequest var2) {
//    try {
//      List var3 = this.onlCgformHeadService.queryButtonList(var1);
//      return var3 != null && var3.size() != 0 ? Result.ok(var3) : Result.error("查询为空");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("查询失败!");
//    }
//  }
//
//  @GetMapping({"/enhanceSql/{formId}"})
//  public Result<?> c(@PathVariable("formId") String var1, HttpServletRequest var2) {
//    List var3 = this.onlCgformEnhanceService.queryEnhanceSqlList(var1);
//    return Result.OK(var3);
//  }
//
//  @PostMapping({"/enhanceSql/{formId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> a(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceSql var2) {
//    try {
//      var2.setCgformHeadId(var1);
//      if (this.onlCgformEnhanceService.checkOnlyEnhance(var2)) {
//        this.onlCgformEnhanceService.saveEnhanceSql(var2);
//        return Result.ok("保存成功!");
//      } else {
//        return Result.error("保存失败,该按钮已存在增强配置!");
//      }
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("保存失败!");
//    }
//  }
//
//  @PutMapping({"/enhanceSql/{formId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> b(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceSql var2) {
//    try {
//      var2.setCgformHeadId(var1);
//      if (this.onlCgformEnhanceService.checkOnlyEnhance(var2)) {
//        this.onlCgformEnhanceService.updateEnhanceSql(var2);
//        return Result.ok("保存成功!");
//      } else {
//        return Result.error("保存失败,该按钮已存在增强配置!");
//      }
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("保存失败!");
//    }
//  }
//
//  @DeleteMapping({"/enhanceSql"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> e(@RequestParam(name = "id",required = true) String var1) {
//    try {
//      this.onlCgformEnhanceService.deleteEnhanceSql(var1);
//      return Result.ok("删除成功");
//    } catch (Exception var3) {
//      a.error(var3.getMessage(), var3);
//      return Result.error("删除失败!");
//    }
//  }
//
//  @DeleteMapping({"/deletebatchEnhanceSql"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> f(@RequestParam(name = "ids",required = true) String var1) {
//    try {
//      List var2 = Arrays.asList(var1.split(","));
//      this.onlCgformEnhanceService.deleteBatchEnhanceSql(var2);
//      return Result.ok("删除成功");
//    } catch (Exception var3) {
//      a.error(var3.getMessage(), var3);
//      return Result.error("删除失败!");
//    }
//  }
//
//  @GetMapping({"/enhanceJava/{formId}"})
//  public Result<?> a(@PathVariable("formId") String var1, OnlCgformEnhanceJava var2) {
//    List var3 = this.onlCgformEnhanceService.queryEnhanceJavaList(var1);
//    return Result.OK(var3);
//  }
//
//  @PostMapping({"/enhanceJava/{formId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> b(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceJava var2) {
//    try {
//      if (!com.sxlinks.modules.online.cgform.util.b.a(var2)) {
//        return Result.error("类实例化失败，请检查!");
//      } else {
//        var2.setCgformHeadId(var1);
//        if (this.onlCgformEnhanceService.checkOnlyEnhance(var2)) {
//          this.onlCgformEnhanceService.saveEnhanceJava(var2);
//          return Result.ok("保存成功!");
//        } else {
//          return Result.error("保存失败,配置重复了!");
//        }
//      }
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("保存失败!");
//    }
//  }
//
//  @PutMapping({"/enhanceJava/{formId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> c(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceJava var2) {
//    try {
//      if (!com.sxlinks.modules.online.cgform.util.b.a(var2)) {
//        return Result.error("类实例化失败，请检查!");
//      } else {
//        var2.setCgformHeadId(var1);
//        if (this.onlCgformEnhanceService.checkOnlyEnhance(var2)) {
//          this.onlCgformEnhanceService.updateEnhanceJava(var2);
//          return Result.ok("保存成功!");
//        } else {
//          return Result.error("保存失败,配置重复了!");
//        }
//      }
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      return Result.error("保存失败!");
//    }
//  }
//
//  @DeleteMapping({"/enhanceJava"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> g(@RequestParam(name = "id",required = true) String var1) {
//    try {
//      this.onlCgformEnhanceService.deleteEnhanceJava(var1);
//      return Result.ok("删除成功");
//    } catch (Exception var3) {
//      a.error(var3.getMessage(), var3);
//      return Result.error("删除失败!");
//    }
//  }
//
//  @DeleteMapping({"/deleteBatchEnhanceJava"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> h(@RequestParam(name = "ids",required = true) String var1) {
//    try {
//      List var2 = Arrays.asList(var1.split(","));
//      this.onlCgformEnhanceService.deleteBatchEnhanceJava(var2);
//      return Result.ok("删除成功");
//    } catch (Exception var3) {
//      a.error(var3.getMessage(), var3);
//      return Result.error("删除失败!");
//    }
//  }
//
//  @GetMapping({"/queryTables"})
//  public Result<?> a(@RequestParam(name = "tableName",required = false) String var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
//    String var5 = JwtUtil.getUserNameByToken(var4);
//    if (!"admin".equals(var5)) {
//      return Result.error("noadminauth");
//    } else {
//      new ArrayList();
//
//      List var6;
//      try {
//        var6 = DbReadTableUtil.a();
//      } catch (SQLException var12) {
//        a.error(var12.getMessage(), var12);
//        return Result.error("同步失败，未获取数据库表信息");
//      }
//
//      com.sxlinks.modules.online.cgform.util.b.b(var6);
//      var6 = com.sxlinks.modules.online.cgform.util.b.f(var6);
//      List var7 = this.onlCgformHeadService.queryOnlinetables();
//      this.b();
//      var6.removeAll(var7);
//      ArrayList var8 = new ArrayList();
//      Iterator var9 = var6.iterator();
//
//      while(var9.hasNext()) {
//        String var10 = (String)var9.next();
//        if (!this.l(var10)) {
//          HashMap var11 = new HashMap();
//          var11.put("id", var10);
//          var8.add(var11);
//        }
//      }
//
//      return Result.ok(var8);
//    }
//  }
//
//  @PostMapping({"/transTables/{tbnames}"})
//  public Result<?> d(@PathVariable("tbnames") String var1, HttpServletRequest var2) {
//    String var3 = JwtUtil.getUserNameByToken(var2);
//    if (!"admin".equals(var3)) {
//      return Result.error("noadminauth");
//    } else if (oConvertUtils.isEmpty(var1)) {
//      return Result.error("未识别的表名信息");
//    } else if (c != null && c.equals(var1)) {
//      return Result.error("不允许重复生成!");
//    } else {
//      c = var1;
//      String[] var4 = var1.split(",");
//
//      for(int var5 = 0; var5 < var4.length; ++var5) {
//        if (oConvertUtils.isNotEmpty(var4[var5])) {
//          int var6 = this.onlCgformHeadService.count((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var4[var5]));
//          if (var6 <= 0) {
//            a.info("[IP] [online数据库导入表]   --表名：" + var4[var5]);
//            this.onlCgformHeadService.saveDbTable2Online(var4[var5]);
//          }
//        }
//      }
//
//      c = null;
//      return Result.ok("同步完成!");
//    }
//  }
//
//  @GetMapping({"/rootFile"})
//  public Result<?> a() {
//    JSONArray var1 = new JSONArray();
//    File[] var2 = File.listRoots();
//    File[] var3 = var2;
//    int var4 = var2.length;
//
//    for(int var5 = 0; var5 < var4; ++var5) {
//      File var6 = var3[var5];
//      JSONObject var7 = new JSONObject();
//      if (var6.isDirectory()) {
//        var7.put("key", var6.getAbsolutePath());
//        var7.put("title", var6.getPath());
//        var7.put("opened", false);
//        JSONObject var8 = new JSONObject();
//        var8.put("icon", "custom");
//        var7.put("scopedSlots", var8);
//        var7.put("isLeaf", var6.listFiles() == null || var6.listFiles().length == 0);
//      }
//
//      var1.add(var7);
//    }
//
//    return Result.ok(var1);
//  }
//
//  @GetMapping({"/fileTree"})
//  public Result<?> i(@RequestParam(name = "parentPath",required = true) String var1) {
//    JSONArray var2 = new JSONArray();
//    File var3 = new File(var1);
//    File[] var4 = var3.listFiles();
//    File[] var5 = var4;
//    int var6 = var4.length;
//
//    for(int var7 = 0; var7 < var6; ++var7) {
//      File var8 = var5[var7];
//      if (var8.isDirectory() && oConvertUtils.isNotEmpty(var8.getPath())) {
//        JSONObject var9 = new JSONObject();
//        System.out.println(var8.getPath());
//        var9.put("key", var8.getAbsolutePath());
//        var9.put("title", var8.getPath().substring(var8.getPath().lastIndexOf(File.separator) + 1));
//        var9.put("isLeaf", var8.listFiles() == null || var8.listFiles().length == 0);
//        var9.put("opened", false);
//        JSONObject var10 = new JSONObject();
//        var10.put("icon", "custom");
//        var9.put("scopedSlots", var10);
//        var2.add(var9);
//      }
//    }
//
//    return Result.ok(var2);
//  }
//
//  @GetMapping({"/tableInfo"})
//  public Result<?> j(@RequestParam(name = "code",required = true) String var1) {
//    OnlCgformHead var2 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//    if (var2 == null) {
//      return Result.error("未找到对应实体");
//    } else {
//      HashMap var3 = new HashMap();
//      var3.put("main", var2);
//      if (var2.getTableType() == 2) {
//        String var4 = var2.getSubTableStr();
//        if (oConvertUtils.isNotEmpty(var4)) {
//          ArrayList var5 = new ArrayList();
//          String[] var6 = var4.split(",");
//          String[] var7 = var6;
//          int var8 = var6.length;
//
//          for(int var9 = 0; var9 < var8; ++var9) {
//            String var10 = var7[var9];
//            LambdaQueryWrapper var11 = new LambdaQueryWrapper();
//            var11.eq("tableName", var10);
//            OnlCgformHead var12 = (OnlCgformHead)this.onlCgformHeadService.getOne(var11);
//            var5.add(var12);
//          }
//
////          Collections.sort(var5, new Comparator<OnlCgformHead>() {
////            public int a(OnlCgformHead var1, OnlCgformHead var2) {
////              Integer var3 = var1.getTabOrderNum();
////              if (var3 == null) {
////                var3 = 0;
////              }
////
////              Integer var4 = var2.getTabOrderNum();
////              if (var4 == null) {
////                var4 = 0;
////              }
////
////              return var3.compareTo(var4);
////            }
////          });
////          var3.put("sub", var5);
//        }
//      }
//
//      Integer var13 = var2.getTableType();
//      if ("Y".equals(var2.getIsTree())) {
//        var13 = 3;
//      }
//
//      List var14 = CgformEnum.getJspModelList(var13);
//      var3.put("jspModeList", var14);
//      var3.put("projectPath", org.jeecgframework.codegenerate.a.a.m());
//      return Result.ok(var3);
//    }
//  }
//
//  @PostMapping({"/copyOnline"})
//  public Result<?> k(@RequestParam(name = "code",required = true) String var1) {
//    try {
//      OnlCgformHead var2 = (OnlCgformHead)this.onlCgformHeadService.getById(var1);
//      if (var2 == null) {
//        return Result.error("未找到对应实体");
//      }
//
//      this.onlCgformHeadService.copyOnlineTableConfig(var2);
//    } catch (Exception var3) {
//      var3.printStackTrace();
//    }
//
//    return Result.ok();
//  }
//
//  private boolean l(String var1) {
//    Iterator var2 = b.iterator();
//
//    String var3;
//    do {
//      if (!var2.hasNext()) {
//        return false;
//      }
//
//      var3 = (String)var2.next();
//    } while(!var1.startsWith(var3) && !var1.startsWith(var3.toUpperCase()));
//
//    return true;
//  }
//
//  private void b() {
//    if (b == null) {
//      Resource var1 = this.resourceLoader.getResource("classpath:jeecg" + File.separator + "jeecg_config.properties");
//      InputStream var2 = null;
//
//      try {
//        var2 = var1.getInputStream();
//        Properties var3 = new Properties();
//        var3.load(var2);
//        String var4 = var3.getProperty("exclude_table");
//        if (var4 != null) {
//          b = Arrays.asList(var4.split(","));
//        }
//      } catch (IOException var13) {
//        var13.printStackTrace();
//      } finally {
//        if (var2 != null) {
//          try {
//            var2.close();
//          } catch (IOException var12) {
//            var12.printStackTrace();
//          }
//        }
//
//      }
//    }
//
//  }
//}

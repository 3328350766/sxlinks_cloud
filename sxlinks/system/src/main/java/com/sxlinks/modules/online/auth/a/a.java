////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.auth.a;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.online.auth.entity.OnlAuthData;
//import com.sxlinks.modules.online.auth.entity.OnlAuthPage;
//
//import com.sxlinks.modules.online.auth.entity.OnlAuthRelation;
//import com.sxlinks.modules.online.auth.service.IOnlAuthDataService;
//import com.sxlinks.modules.online.auth.service.IOnlAuthPageService;
//import com.sxlinks.modules.online.auth.service.IOnlAuthRelationService;
//import com.sxlinks.modules.online.auth.vo.AuthColumnVO;
//import com.sxlinks.modules.online.auth.vo.AuthPageVO;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformButtonService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
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
//@RestController("onlCgformAuthController")
//@RequestMapping({"/online/cgform/api"})
//public class a {
//  private static final Logger a = LoggerFactory.getLogger(a.class);
//  @Autowired
//  private IOnlCgformFieldService onlCgformFieldService;
//  @Autowired
//  private IOnlAuthDataService onlAuthDataService;
//  @Autowired
//  private IOnlAuthPageService onlAuthPageService;
//  @Autowired
//  private IOnlCgformButtonService onlCgformButtonService;
//  @Autowired
//  private IOnlAuthRelationService onlAuthRelationService;
//  @Autowired
//  private IOnlCgformHeadService onlCgformHeadService;
//
//  public a() {
//  }
//
//  @GetMapping({"/authData/{cgformId}"})
//  public Result<List<OnlAuthData>> a(@PathVariable("cgformId") String var1) {
//    Result var2 = new Result();
//    LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//    var3.eq("cgformId", var1);
//    List var4 = this.onlAuthDataService.list(var3);
//    var2.setResult(var4);
//    var2.setSuccess(true);
//    return var2;
//  }
//
//  @PostMapping({"/authData"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlAuthData> a(@RequestBody OnlAuthData var1) {
//    Result var2 = new Result();
//
//    try {
//      this.onlAuthDataService.save(var1);
//      var2.success("添加成功！");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @PutMapping({"/authData"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlAuthData> b(@RequestBody OnlAuthData var1) {
//    Result var2 = new Result();
//    this.onlAuthDataService.updateById(var1);
//    return var2;
//  }
//
//  @DeleteMapping({"/authData/{id}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> b(@PathVariable("id") String var1) {
//    this.onlAuthDataService.deleteOne(var1);
//    return Result.ok("删除成功!");
//  }
//
//  @GetMapping({"/authButton/{cgformId}"})
//  public Result<Map<String, Object>> c(@PathVariable("cgformId") String var1) {
//    Result var2 = new Result();
//    LambdaQueryWrapper var3 = ((LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var1)).eq("buttonStatus", 1));
//    List var4 = this.onlCgformButtonService.list(var3);
//    LambdaQueryWrapper var5 = (LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformId", var1)).eq("type", 2);
//    List var6 = this.onlAuthPageService.list(var5);
//    HashMap var7 = new HashMap();
//    var7.put("buttonList", var4);
//    var7.put("authList", var6);
//    var2.setResult(var7);
//    var2.setSuccess(true);
//    return var2;
//  }
//
//  @PostMapping({"/authButton"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlAuthPage> a(@RequestBody OnlAuthPage var1) {
//    Result var2 = new Result();
//
//    try {
//      String var3 = var1.getId();
//      boolean var4 = false;
//      if (oConvertUtils.isNotEmpty(var3)) {
//        OnlAuthPage var5 = (OnlAuthPage)this.onlAuthPageService.getById(var3);
//        if (var5 != null) {
//          var4 = true;
//          var5.setStatus(1);
//          this.onlAuthPageService.updateById(var5);
//        }
//      }
//
//      if (!var4) {
//        var1.setStatus(1);
//        this.onlAuthPageService.save(var1);
//      }
//
//      var2.setResult(var1);
//      var2.success("操作成功！");
//    } catch (Exception var6) {
//      a.error(var6.getMessage(), var6);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @PutMapping({"/authButton/{id}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> d(@PathVariable("id") String var1) {
//    LambdaUpdateWrapper var2 = (LambdaUpdateWrapper)((LambdaUpdateWrapper)(new UpdateWrapper()).lambda().eq("id", var1)).set("status", 0);
//    this.onlAuthPageService.update(var2);
//    return Result.ok("操作成功");
//  }
//
//  @GetMapping({"/authColumn/{cgformId}"})
//  public Result<List<AuthColumnVO>> e(@PathVariable("cgformId") String var1) {
//    Result var2 = new Result();
//    LambdaQueryWrapper var3 = new LambdaQueryWrapper();
//    var3.eq("cgformHeadId", var1);
//    var3.orderByAsc("orderNum");
//    //var3.orderByAsc("orderNum");
//    List var4 = this.onlCgformFieldService.list(var3);
//    if (var4 == null || var4.size() == 0) {
//      Result.error("未找到对应字段信息!");
//    }
//
//    LambdaQueryWrapper var5 = (LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformId", var1)).eq("type", 1);
//    List var6 = this.onlAuthPageService.list(var5);
//    ArrayList var7 = new ArrayList();
//    Iterator var8 = var4.iterator();
//
//    while(var8.hasNext()) {
//      OnlCgformField var9 = (OnlCgformField)var8.next();
//      AuthColumnVO var10 = new AuthColumnVO(var9);
//      Integer var11 = 0;
//      boolean var12 = false;
//      boolean var13 = false;
//      boolean var14 = false;
//
//      for(int var15 = 0; var15 < var6.size(); ++var15) {
//        OnlAuthPage var16 = (OnlAuthPage)var6.get(var15);
//        if (var9.getDbFieldName().equals(var16.getCode())) {
//          var11 = var16.getStatus();
//          if (var16.getPage() == 3 && var16.getControl() == 5) {
//            var12 = true;
//          }
//
//          if (var16.getPage() == 5) {
//            if (var16.getControl() == 5) {
//              var13 = true;
//            } else if (var16.getControl() == 3) {
//              var14 = true;
//            }
//          }
//        }
//      }
//
//      var10.setStatus(var11);
//      var10.setListShow(var12);
//      var10.setFormShow(var13);
//      var10.setFormEditable(var14);
//      var7.add(var10);
//    }
//
//    var2.setResult(var7);
//    Result.ok("加载字段权限数据完成");
//    return var2;
//  }
//
//  @PutMapping({"/authColumn"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> a(@RequestBody AuthColumnVO var1) {
//    Result var2 = new Result();
//
//    try {
//      if (var1.getStatus() == 1) {
//        this.onlAuthPageService.enableAuthColumn(var1);
//      } else {
//        this.onlAuthPageService.disableAuthColumn(var1);
//      }
//
//      var2.success("操作成功！");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @PostMapping({"/authColumn"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> b(@RequestBody AuthColumnVO var1) {
//    Result var2 = new Result();
//
//    try {
//      this.onlAuthPageService.switchAuthColumn(var1);
//      var2.success("操作成功！");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @GetMapping({"/authPage/{cgformId}/{type}"})
//  public Result<List<AuthPageVO>> a(@PathVariable("cgformId") String var1, @PathVariable("type") Integer var2) {
//    Result var3 = new Result();
//    List var4 = this.onlAuthPageService.queryAuthByFormId(var1, var2);
//    var3.setResult(var4);
//    var3.setSuccess(true);
//    return var3;
//  }
//
//  @GetMapping({"/validAuthData/{cgformId}"})
//  public Result<List<OnlAuthData>> f(@PathVariable("cgformId") String var1) {
//    Result var2 = new Result();
//    LambdaQueryWrapper var3 = ((LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformId", var1)).eq("status", 1));
//    List var4 = this.onlAuthDataService.list(var3);
//    var2.setResult(var4);
//    var2.setSuccess(true);
//    return var2;
//  }
//
//  @GetMapping({"/roleAuth"})
//  public Result<List<OnlAuthRelation>> a(@RequestParam("roleId") String var1, @RequestParam("cgformId") String var2, @RequestParam("type") Integer var3, @RequestParam("authMode") String var4) {
//    Result var5 = new Result();
//    LambdaQueryWrapper var6 = ((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("roleId", var1)).eq("cgformId", var2)).eq("type", var3)).eq("authMode", var4));
//    List var7 = this.onlAuthRelationService.list(var6);
//    var5.setResult(var7);
//    var5.setSuccess(true);
//    return var5;
//  }
//
//  @PostMapping({"/roleColumnAuth/{roleId}/{cgformId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> a(@PathVariable("roleId") String var1, @PathVariable("cgformId") String var2, @RequestBody JSONObject var3) {
//    Result var4 = new Result();
//    JSONArray var5 = var3.getJSONArray("authId");
//    String var6 = var3.getString("authMode");
//    List var7 = var5.toJavaList(String.class);
//    this.onlAuthRelationService.saveRoleAuth(var1, var2, 1, var6, var7);
//    var4.setSuccess(true);
//    return var4;
//  }
//
//  @PostMapping({"/roleButtonAuth/{roleId}/{cgformId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> b(@PathVariable("roleId") String var1, @PathVariable("cgformId") String var2, @RequestBody JSONObject var3) {
//    Result var4 = new Result();
//    JSONArray var5 = var3.getJSONArray("authId");
//    String var6 = var3.getString("authMode");
//    List var7 = var5.toJavaList(String.class);
//    this.onlAuthRelationService.saveRoleAuth(var1, var2, 2, var6, var7);
//    var4.setSuccess(true);
//    return var4;
//  }
//
//  @PostMapping({"/roleDataAuth/{roleId}/{cgformId}"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<?> c(@PathVariable("roleId") String var1, @PathVariable("cgformId") String var2, @RequestBody JSONObject var3) {
//    Result var4 = new Result();
//    JSONArray var5 = var3.getJSONArray("authId");
//    String var6 = var3.getString("authMode");
//    List var7 = var5.toJavaList(String.class);
//    this.onlAuthRelationService.saveRoleAuth(var1, var2, 3, var6, var7);
//    var4.setSuccess(true);
//    return var4;
//  }
//
//  @GetMapping({"/getAuthColumn/{desformCode}"})
//  public Result<List<AuthColumnVO>> g(@PathVariable("desformCode") String var1) {
//    OnlCgformHead var2 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var1));
//    if (var2 == null) {
//      Result.error("未找到对应字段信息!");
//    }
//
//    Result var3 = new Result();
//    LambdaQueryWrapper var4 = new LambdaQueryWrapper();
//    var4.eq("cgformHeadId", var2.getId());
//    var4.orderByAsc("orderNum");
//    List var5 = this.onlCgformFieldService.list(var4);
//    if (var5 == null || var5.size() == 0) {
//      Result.error("未找到对应字段信息!");
//    }
//
//    ArrayList var6 = new ArrayList();
//    Iterator var7 = var5.iterator();
//
//    while(var7.hasNext()) {
//      OnlCgformField var8 = (OnlCgformField)var7.next();
//      AuthColumnVO var9 = new AuthColumnVO(var8);
//      var9.setTableName(var2.getTableName());
//      var9.setTableNameTxt(var2.getTableTxt());
//      var6.add(var9);
//    }
//
//    if (oConvertUtils.isNotEmpty(var2.getSubTableStr())) {
//      String[] var17 = var2.getSubTableStr().split(",");
//      int var18 = var17.length;
//
//      for(int var19 = 0; var19 < var18; ++var19) {
//        String var10 = var17[var19];
//        OnlCgformHead var11 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq("tableName", var10));
//        if (var11 != null) {
//          LambdaQueryWrapper var12 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformHeadId", var11.getId());
//          List var13 = this.onlCgformFieldService.list(var12);
//          if (var13 != null) {
//            Iterator var14 = var13.iterator();
//
//            while(var14.hasNext()) {
//              OnlCgformField var15 = (OnlCgformField)var14.next();
//              AuthColumnVO var16 = new AuthColumnVO(var15);
//              var16.setTableName(var11.getTableName());
//              var16.setTableNameTxt(var11.getTableTxt());
//              var6.add(var16);
//            }
//          }
//        }
//      }
//    }
//
//    var3.setResult(var6);
//    Result.ok("加载字段权限数据完成");
//    return var3;
//  }
//}

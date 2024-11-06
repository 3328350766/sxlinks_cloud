////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.b;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import java.util.Arrays;
//import javax.servlet.http.HttpServletRequest;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.system.query.QueryGenerator;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformButtonService;
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
//@RestController("onlCgformButtonController")
//@RequestMapping({"/online/cgform/button"})
//public class OnlCgformButtonController {
//  private static final Logger a = LoggerFactory.getLogger(OnlCgformButtonController.class);
//  @Autowired
//  private IOnlCgformButtonService onlCgformButtonService;
//
//  public OnlCgformButtonController() {
//  }
//
//  @GetMapping({"/list/{code}"})
//  public Result<IPage<OnlCgformButton>> a(OnlCgformButton var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4, @PathVariable("code") String var5) {
//    Result var6 = new Result();
//    var1.setCgformHeadId(var5);
//    QueryWrapper var7 = QueryGenerator.initQueryWrapper(var1, var4.getParameterMap());
//    Page var8 = new Page((long)var2, (long)var3);
//    IPage var9 = this.onlCgformButtonService.page(var8, var7);
//    var6.setSuccess(true);
//    var6.setResult(var9);
//    return var6;
//  }
//
//  @PostMapping({"/add"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlCgformButton> a(@RequestBody OnlCgformButton var1) {
//    Result var2 = new Result();
//
//    try {
//      this.onlCgformButtonService.save(var1);
//      var2.success("添加成功！");
//    } catch (Exception var4) {
//      a.error(var4.getMessage(), var4);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @PostMapping({"/aitest"})
//  public Result<OnlCgformButton> a(@RequestBody JSONArray var1) {
//    Result var2 = new Result();
//
//    try {
//      for(int var3 = 0; var3 < var1.size(); ++var3) {
//        JSONObject var4 = var1.getJSONObject(var3);
//        OnlCgformButton var5 = (OnlCgformButton)JSONObject.parseObject(var4.toJSONString(), OnlCgformButton.class);
//        this.onlCgformButtonService.saveButton(var5);
//      }
//
//      var2.success("添加成功！");
//    } catch (Exception var6) {
//      a.error(var6.getMessage(), var6);
//      var2.error500("操作失败");
//    }
//
//    return var2;
//  }
//
//  @PutMapping({"/edit"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlCgformButton> b(@RequestBody OnlCgformButton var1) {
//    Result var2 = new Result();
//    OnlCgformButton var3 = (OnlCgformButton)this.onlCgformButtonService.getById(var1.getId());
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformButtonService.updateById(var1);
//      if (var4) {
//        var2.success("修改成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/delete"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlCgformButton> a(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformButton var3 = (OnlCgformButton)this.onlCgformButtonService.getById(var1);
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformButtonService.removeById(var1);
//      if (var4) {
//        var2.success("删除成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/deleteBatch"})
//  @CacheEvict(
//          value = {"sys:cache:online:list", "sys:cache:online:form"},
//          allEntries = true,
//          beforeInvocation = true
//  )
//  public Result<OnlCgformButton> b(@RequestParam(name = "ids",required = true) String var1) {
//    Result var2 = new Result();
//    if (var1 != null && !"".equals(var1.trim())) {
//      this.onlCgformButtonService.removeByIds(Arrays.asList(var1.split(",")));
//      var2.success("删除成功!");
//    } else {
//      var2.error500("参数不识别！");
//    }
//
//    return var2;
//  }
//
//  @GetMapping({"/queryById"})
//  public Result<OnlCgformButton> c(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformButton var3 = (OnlCgformButton)this.onlCgformButtonService.getById(var1);
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      var2.setResult(var3);
//      var2.setSuccess(true);
//    }
//
//    return var2;
//  }
//}

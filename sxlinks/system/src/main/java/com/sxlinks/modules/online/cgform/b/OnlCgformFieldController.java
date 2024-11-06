////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.b;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import java.util.Arrays;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.system.query.QueryGenerator;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformField;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformHead;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformFieldService;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformHeadService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("onlCgformFieldController")
//@RequestMapping({"/online/cgform/field"})
//public class OnlCgformFieldController {
//  private static final Logger a = LoggerFactory.getLogger(OnlCgformFieldController.class);
//  @Autowired
//  private IOnlCgformHeadService onlCgformHeadService;
//  @Autowired
//  private IOnlCgformFieldService onlCgformFieldService;
//
//  public OnlCgformFieldController() {
//  }
//
//  @GetMapping({"/listByHeadCode"})
//  public Result<?> a(@RequestParam("headCode") String var1) {
//    LambdaQueryWrapper var2 = new LambdaQueryWrapper();
//    var2.eq("tableName", var1);
//    OnlCgformHead var3 = (OnlCgformHead)this.onlCgformHeadService.getOne(var2);
//    return var3 == null ? Result.error("不存在的code") : this.b(var3.getId());
//  }
//
//  @GetMapping({"/listByHeadId"})
//  public Result<?> b(@RequestParam("headId") String var1) {
//    QueryWrapper var2 = new QueryWrapper();
//    var2.eq("cgform_head_id", var1);
//    var2.orderByAsc("order_num");
//    List var3 = this.onlCgformFieldService.list(var2);
//    return Result.ok(var3);
//  }
//
//  @GetMapping({"/list"})
//  public Result<IPage<OnlCgformField>> a(OnlCgformField var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
//    Result var5 = new Result();
//    QueryWrapper var6 = QueryGenerator.initQueryWrapper(var1, var4.getParameterMap());
//    Page var7 = new Page((long)var2, (long)var3);
//    IPage var8 = this.onlCgformFieldService.page(var7, var6);
//    var5.setSuccess(true);
//    var5.setResult(var8);
//    return var5;
//  }
//
//  @PostMapping({"/add"})
//  public Result<OnlCgformField> a(@RequestBody OnlCgformField var1) {
//    Result var2 = new Result();
//
//    try {
//      this.onlCgformFieldService.save(var1);
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
//  public Result<OnlCgformField> b(@RequestBody OnlCgformField var1) {
//    Result var2 = new Result();
//    OnlCgformField var3 = (OnlCgformField)this.onlCgformFieldService.getById(var1.getId());
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformFieldService.updateById(var1);
//      if (var4) {
//        var2.success("修改成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/delete"})
//  public Result<OnlCgformField> c(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformField var3 = (OnlCgformField)this.onlCgformFieldService.getById(var1);
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformFieldService.removeById(var1);
//      if (var4) {
//        var2.success("删除成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/deleteBatch"})
//  public Result<OnlCgformField> d(@RequestParam(name = "ids",required = true) String var1) {
//    Result var2 = new Result();
//    if (var1 != null && !"".equals(var1.trim())) {
//      this.onlCgformFieldService.removeByIds(Arrays.asList(var1.split(",")));
//      var2.success("删除成功!");
//    } else {
//      var2.error500("参数不识别！");
//    }
//
//    return var2;
//  }
//
//  @GetMapping({"/queryById"})
//  public Result<OnlCgformField> e(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformField var3 = (OnlCgformField)this.onlCgformFieldService.getById(var1);
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

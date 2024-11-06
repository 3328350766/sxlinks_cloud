////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.b;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import java.util.Arrays;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.constant.CommonConstant;
//import com.sxlinks.common.system.query.QueryGenerator;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformIndex;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformIndexService;
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
//@RestController("onlCgformIndexController")
//@RequestMapping({"/online/cgform/index"})
//public class OnlCgformIndexController {
//  private static final Logger a = LoggerFactory.getLogger(OnlCgformIndexController.class);
//  @Autowired
//  private IOnlCgformIndexService onlCgformIndexService;
//
//  public OnlCgformIndexController() {
//  }
//
//  @GetMapping({"/listByHeadId"})
//  public Result<?> a(@RequestParam("headId") String var1) {
//    QueryWrapper var2 = new QueryWrapper();
//    var2.eq("cgform_head_id", var1);
//    var2.eq("del_flag", CommonConstant.DEL_FLAG_0);
//    var2.orderByDesc("create_time");
//    List var3 = this.onlCgformIndexService.list(var2);
//    return Result.ok(var3);
//  }
//
//  @GetMapping({"/list"})
//  public Result<IPage<OnlCgformIndex>> a(OnlCgformIndex var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
//    Result var5 = new Result();
//    QueryWrapper var6 = QueryGenerator.initQueryWrapper(var1, var4.getParameterMap());
//    Page var7 = new Page((long)var2, (long)var3);
//    IPage var8 = this.onlCgformIndexService.page(var7, var6);
//    var5.setSuccess(true);
//    var5.setResult(var8);
//    return var5;
//  }
//
//  @PostMapping({"/add"})
//  public Result<OnlCgformIndex> a(@RequestBody OnlCgformIndex var1) {
//    Result var2 = new Result();
//
//    try {
//      this.onlCgformIndexService.save(var1);
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
//  public Result<OnlCgformIndex> b(@RequestBody OnlCgformIndex var1) {
//    Result var2 = new Result();
//    OnlCgformIndex var3 = (OnlCgformIndex)this.onlCgformIndexService.getById(var1.getId());
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformIndexService.updateById(var1);
//      if (var4) {
//        var2.success("修改成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/delete"})
//  public Result<OnlCgformIndex> b(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformIndex var3 = (OnlCgformIndex)this.onlCgformIndexService.getById(var1);
//    if (var3 == null) {
//      var2.error500("未找到对应实体");
//    } else {
//      boolean var4 = this.onlCgformIndexService.removeById(var1);
//      if (var4) {
//        var2.success("删除成功!");
//      }
//    }
//
//    return var2;
//  }
//
//  @DeleteMapping({"/deleteBatch"})
//  public Result<OnlCgformIndex> c(@RequestParam(name = "ids",required = true) String var1) {
//    Result var2 = new Result();
//    if (var1 != null && !"".equals(var1.trim())) {
//      this.onlCgformIndexService.removeByIds(Arrays.asList(var1.split(",")));
//      var2.success("删除成功!");
//    } else {
//      var2.error500("参数不识别！");
//    }
//
//    return var2;
//  }
//
//  @GetMapping({"/queryById"})
//  public Result<OnlCgformIndex> d(@RequestParam(name = "id",required = true) String var1) {
//    Result var2 = new Result();
//    OnlCgformIndex var3 = (OnlCgformIndex)this.onlCgformIndexService.getById(var1);
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

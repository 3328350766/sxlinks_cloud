////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.sxlinks.modules.online.cgform.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.sxlinks.modules.online.cgform.entity.OnlCgformButton;
//import com.sxlinks.modules.online.cgform.mapper.OnlCgformButtonMapper;
//import com.sxlinks.modules.online.cgform.service.IOnlCgformButtonService;
//import org.springframework.stereotype.Service;
//
//@Service("onlCgformButtonServiceImpl")
//public class b extends ServiceImpl<OnlCgformButtonMapper, OnlCgformButton> implements IOnlCgformButtonService {
//  public b() {
//  }
//
//  public void saveButton(OnlCgformButton onlCgformButton) {
//    LambdaQueryWrapper var2 = (LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("buttonCode", onlCgformButton.getButtonCode())).eq("cgformHeadId", onlCgformButton.getCgformHeadId());
//    Integer var3 = ((OnlCgformButtonMapper)this.baseMapper).selectCount(var2);
//    if (var3 == null || var3 == 0) {
//      this.save(onlCgformButton);
//    }
//
//  }
//}

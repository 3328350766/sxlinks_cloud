//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.service.a;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.sxlinks.common.system.vo.SysPermissionDataRuleModel;
import com.sxlinks.modules.online.auth.entity.OnlAuthData;

import com.sxlinks.modules.online.auth.mapper.OnlAuthDataMapper;
import com.sxlinks.modules.online.auth.mapper.OnlAuthRelationMapper;
import com.sxlinks.modules.online.auth.service.IOnlAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("onlAuthDataServiceImpl")
public class a extends ServiceImpl<OnlAuthDataMapper, OnlAuthData> implements IOnlAuthDataService {
  @Autowired
  private OnlAuthRelationMapper onlAuthRelationMapper;

  public a() {
  }

  public void deleteOne(String id) {
    this.removeById(id);
    LambdaQueryWrapper var2 = new LambdaQueryWrapper();
    this.onlAuthRelationMapper.delete((Wrapper)var2.eq("athId", id));
    //this.onlAuthRelationMapper.delete((Wrapper)var2.eq("authId", id));
  }

  public List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String userId, String cgformId) {
    List var3 = ((OnlAuthDataMapper)this.baseMapper).queryRoleAuthData(userId, cgformId);
    List var4 = ((OnlAuthDataMapper)this.baseMapper).queryDepartAuthData(userId, cgformId);
    HashMap var5 = new HashMap();
    Iterator var6 = var3.iterator();

    SysPermissionDataRuleModel var7;
    String var8;
    while(var6.hasNext()) {
      var7 = (SysPermissionDataRuleModel)var6.next();
      var8 = var7.getId();
      if (var5.get(var8) == null) {
        var5.put(var8, var7);
      }
    }

    var6 = var4.iterator();

    while(var6.hasNext()) {
      var7 = (SysPermissionDataRuleModel)var6.next();
      var8 = var7.getId();
      if (var5.get(var8) == null) {
        var5.put(var8, var7);
      }
    }

    Collection var9 = var5.values();
    if (var9 != null && var9.size() != 0) {
      return new ArrayList(var9);
    } else {
      return null;
    }
  }
}

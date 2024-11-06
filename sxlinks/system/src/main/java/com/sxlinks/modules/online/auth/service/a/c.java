//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.service.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.sxlinks.modules.online.auth.entity.OnlAuthRelation;
import com.sxlinks.modules.online.auth.mapper.OnlAuthRelationMapper;
import com.sxlinks.modules.online.auth.service.IOnlAuthRelationService;
import org.springframework.stereotype.Service;

@Service("onlAuthRelationServiceImpl")
public class c extends ServiceImpl<OnlAuthRelationMapper, OnlAuthRelation> implements IOnlAuthRelationService {
  public c() {
  }

  public void saveRoleAuth(String roleId, String cgformId, int type, String authMode, List<String> authIds) {
    LambdaQueryWrapper var6 = (LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformId", cgformId)).eq("type", type)).eq("authMode", authMode)).eq("roleId", roleId);
    ((OnlAuthRelationMapper)this.baseMapper).delete(var6);
    ArrayList var7 = new ArrayList();
    Iterator var8 = authIds.iterator();

    while(var8.hasNext()) {
      String var9 = (String)var8.next();
      OnlAuthRelation var10 = new OnlAuthRelation();
      var10.setAuthId(var9);
      var10.setCgformId(cgformId);
      var10.setRoleId(roleId);
      var10.setType(type);
      var10.setAuthMode(authMode);
      var7.add(var10);
    }

    this.saveBatch(var7);
  }
}

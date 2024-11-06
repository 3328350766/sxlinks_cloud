//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import com.sxlinks.modules.online.auth.entity.OnlAuthRelation;

public interface IOnlAuthRelationService extends IService<OnlAuthRelation> {
  void saveRoleAuth(String var1, String var2, int var3, String var4, List<String> var5);
}

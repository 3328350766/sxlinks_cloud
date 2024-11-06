//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import com.sxlinks.common.system.vo.SysPermissionDataRuleModel;
import com.sxlinks.modules.online.auth.entity.OnlAuthData;

public interface IOnlAuthDataService extends IService<OnlAuthData> {
  void deleteOne(String var1);

  List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String var1, String var2);
}

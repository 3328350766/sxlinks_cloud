//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sxlinks.common.system.vo.SysPermissionDataRuleModel;
import com.sxlinks.modules.online.auth.entity.OnlAuthData;

public interface OnlAuthDataMapper extends BaseMapper<OnlAuthData> {
  List<SysPermissionDataRuleModel> queryRoleAuthData(@Param("userId") String var1, @Param("cgformId") String var2);

  List<SysPermissionDataRuleModel> queryDepartAuthData(@Param("userId") String var1, @Param("cgformId") String var2);
}

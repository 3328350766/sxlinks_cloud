//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sxlinks.modules.online.auth.entity.OnlAuthPage;
import com.sxlinks.modules.online.auth.vo.AuthPageVO;

public interface OnlAuthPageMapper extends BaseMapper<OnlAuthPage> {
  List<AuthPageVO> queryRoleAuthByFormId(@Param("roleId") String var1, @Param("cgformId") String var2, @Param("type") int var3);

  List<AuthPageVO> queryAuthColumnByFormId(@Param("cgformId") String var1);

  List<AuthPageVO> queryAuthButtonByFormId(@Param("cgformId") String var1);

  List<AuthPageVO> queryRoleDataAuth(@Param("roleId") String var1, @Param("cgformId") String var2);

  List<String> queryRoleNoAuthCode(@Param("userId") String var1, @Param("cgformId") String var2, @Param("control") Integer var3, @Param("page") Integer var4, @Param("type") Integer var5);
}

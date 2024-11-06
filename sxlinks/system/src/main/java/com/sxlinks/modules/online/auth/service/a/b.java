//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sxlinks.modules.online.auth.service.a;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.online.auth.entity.OnlAuthPage;
import com.sxlinks.modules.online.auth.entity.OnlAuthRelation;
import com.sxlinks.modules.online.auth.mapper.OnlAuthPageMapper;
import com.sxlinks.modules.online.auth.mapper.OnlAuthRelationMapper;
import com.sxlinks.modules.online.auth.service.IOnlAuthPageService;
import com.sxlinks.modules.online.auth.vo.AuthColumnVO;
import com.sxlinks.modules.online.auth.vo.AuthPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("onlAuthPageServiceImpl")
public class b extends ServiceImpl<OnlAuthPageMapper, OnlAuthPage> implements IOnlAuthPageService {
  @Autowired
  private OnlAuthRelationMapper onlAuthRelationMapper;

  public b() {
  }

  public void disableAuthColumn(AuthColumnVO authColumnVO) {
    LambdaUpdateWrapper var2 = (LambdaUpdateWrapper)((LambdaUpdateWrapper)((LambdaUpdateWrapper)((LambdaUpdateWrapper)(new UpdateWrapper()).lambda().eq("cgformId", authColumnVO.getCgformId())).eq("code", authColumnVO.getCode())).eq("type", 1)).set("status", 0);
    this.update(var2);
  }

  @Transactional
  public void enableAuthColumn(AuthColumnVO authColumnVO) {
    String var2 = authColumnVO.getCgformId();
    String var3 = authColumnVO.getCode();
    LambdaQueryWrapper var4 = (LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformId", var2)).eq("code", var3)).eq("type", 1);
    List var5 = this.list(var4);
    if (var5 != null && var5.size() > 0) {
      LambdaUpdateWrapper var6 = (LambdaUpdateWrapper)((LambdaUpdateWrapper)((LambdaUpdateWrapper)((LambdaUpdateWrapper)(new UpdateWrapper()).lambda().eq("cgformId", var2)).eq("code", var3)).eq("type", 1)).set("status", 1);
      this.update(var6);
    } else {
      ArrayList var7 = new ArrayList();
      var7.add(new OnlAuthPage(var2, var3, 3, 5));
      var7.add(new OnlAuthPage(var2, var3, 5, 5));
      var7.add(new OnlAuthPage(var2, var3, 5, 3));
      this.saveBatch(var7);
    }

  }

  public void switchAuthColumn(AuthColumnVO authColumnVO) {
    String var2 = authColumnVO.getCgformId();
    String var3 = authColumnVO.getCode();
    int var4 = authColumnVO.getSwitchFlag();
    if (var4 == 1) {
      this.switchListShow(var2, var3, authColumnVO.isListShow());
    } else if (var4 == 2) {
      this.switchFormShow(var2, var3, authColumnVO.isFormShow());
    } else if (var4 == 3) {
      this.switchFormEditable(var2, var3, authColumnVO.isFormEditable());
    }

  }

  @Transactional
  public void switchFormShow(String cgformId, String code, boolean flag) {
    this.a(cgformId, code, 5, 5, flag);
  }

  @Transactional
  public void switchFormEditable(String cgformId, String code, boolean flag) {
    this.a(cgformId, code, 3, 5, flag);
  }

  @Transactional
  public void switchListShow(String cgformId, String code, boolean flag) {
    this.a(cgformId, code, 5, 3, flag);
  }

  public List<AuthPageVO> queryRoleAuthByFormId(String roleId, String cgformId, int type) {
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleAuthByFormId(roleId, cgformId, type);
  }

  public List<AuthPageVO> queryRoleDataAuth(String roleId, String cgformId) {
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleDataAuth(roleId, cgformId);
  }

  public List<AuthPageVO> queryAuthByFormId(String cgformId, int type) {
    return type == 1 ? ((OnlAuthPageMapper)this.baseMapper).queryAuthColumnByFormId(cgformId) : ((OnlAuthPageMapper)this.baseMapper).queryAuthButtonByFormId(cgformId);
  }

  public List<String> queryRoleNoAuthCode(String cgformId, Integer control, Integer page) {
    LoginUser var4 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
    String var5 = var4.getId();
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(var5, cgformId, control, page, (Integer)null);
  }

  public List<String> queryFormDisabledCode(String cgformId) {
    return this.queryRoleNoAuthCode(cgformId, 3, 5);
  }

  public List<String> queryHideCode(String userId, String cgformId, boolean isList) {
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, isList ? 3 : 5, (Integer)null);
  }

  public List<String> queryListHideColumn(String userId, String cgformId) {
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 3, 1);
  }

  public List<String> queryFormHideColumn(String userId, String cgformId) {
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 5, 1);
  }

  public List<String> queryFormHideButton(String userId, String cgformId) {
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 5, 2);
  }

  public List<String> queryHideCode(String cgformId, boolean isList) {
    LoginUser var3 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
    String var4 = var3.getId();
    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(var4, cgformId, 5, isList ? 3 : 5, (Integer)null);
  }

  public List<String> queryListHideButton(String userId, String cgformId) {
    if (userId == null) {
      LoginUser var3 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
      userId = var3.getId();
    }

    return ((OnlAuthPageMapper)this.baseMapper).queryRoleNoAuthCode(userId, cgformId, 5, 3, 2);
  }

  private void a(String var1, String var2, int var3, int var4, boolean var5) {
    LambdaQueryWrapper var6 = (LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq("cgformId", var1)).eq("code", var2)).eq("control", var3)).eq("page", var4)).eq("type", 1);
    OnlAuthPage var7 = (OnlAuthPage)((OnlAuthPageMapper)this.baseMapper).selectOne(var6);
    if (var5) {
      if (var7 == null) {
        OnlAuthPage var8 = new OnlAuthPage();
        var8.setCgformId(var1);
        var8.setCode(var2);
        var8.setControl(var3);
        var8.setPage(var4);
        var8.setType(1);
        var8.setStatus(1);
        ((OnlAuthPageMapper)this.baseMapper).insert(var8);
      } else if (var7.getStatus() == 0) {
        var7.setStatus(1);
        ((OnlAuthPageMapper)this.baseMapper).updateById(var7);
      }
    } else if (!var5 && var7 != null) {
      String var9 = var7.getId();
      ((OnlAuthPageMapper)this.baseMapper).deleteById(var9);
      this.onlAuthRelationMapper.delete((Wrapper)(new LambdaQueryWrapper()).eq("authId", var9));
    }

  }
}

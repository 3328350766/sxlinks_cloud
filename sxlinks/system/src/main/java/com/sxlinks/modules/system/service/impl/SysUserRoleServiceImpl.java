package com.sxlinks.modules.system.service.impl;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.sxlinks.modules.system.entity.SysRole;
import com.sxlinks.modules.system.entity.SysUser;
import com.sxlinks.modules.system.entity.SysUserRole;
import com.sxlinks.modules.system.mapper.SysUserRoleMapper;
import com.sxlinks.modules.system.service.ISysRoleService;
import com.sxlinks.modules.system.service.ISysUserRoleService;
import com.sxlinks.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @Author baba
 * @since 2018-12-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

}

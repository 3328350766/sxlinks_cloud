package com.sxlinks.modules.system.service.energyCenter.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataYear;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryEnergyDeviceDataYearMapper;
import com.sxlinks.modules.system.service.energyCenter.ISummaryEnergyDeviceDataYearService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@Service
public class SummaryEnergyDeviceDataYearImpl extends ServiceImpl<SummaryEnergyDeviceDataYearMapper, SummaryEnergyDeviceDataYear> implements ISummaryEnergyDeviceDataYearService {
//    @Autowired
//    SysRoleMapper sysRoleMapper;
//    @Autowired
//    SysUserMapper sysUserMapper;
//
//    @Override
//    public Result importExcelCheckRoleCode(MultipartFile file, ImportParams params) throws Exception {
//        List<Object> listSysRoles = ExcelImportUtil.importExcel(file.getInputStream(), SysRole.class, params);
//        int totalCount = listSysRoles.size();
//        List<String> errorStrs = new ArrayList<>();
//
//        // 去除 listSysRoles 中重复的数据
//        for (int i = 0; i < listSysRoles.size(); i++) {
//            String roleCodeI =((SysRole)listSysRoles.get(i)).getRoleCode();
//            for (int j = i + 1; j < listSysRoles.size(); j++) {
//                String roleCodeJ =((SysRole)listSysRoles.get(j)).getRoleCode();
//                // 发现重复数据
//                if (roleCodeI.equals(roleCodeJ)) {
//                    errorStrs.add("第 " + (j + 1) + " 行的 roleCode 值：" + roleCodeI + " 已存在，忽略导入");
//                    listSysRoles.remove(j);
//                    break;
//                }
//            }
//        }
//        // 去掉 sql 中的重复数据
//        Integer errorLines=0;
//        Integer successLines=0;
//        List<String> list = ImportExcelUtil.importDateSave(listSysRoles, ISysRoleService.class, errorStrs, CommonConstant.SQL_INDEX_UNIQ_SYS_ROLE_CODE);
//         errorLines+=list.size();
//         successLines+=(listSysRoles.size()-errorLines);
//        return ImportExcelUtil.imporReturnRes(errorLines,successLines,list);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean deleteRole(String roleid) {
//        //1.删除角色和用户关系
//        sysRoleMapper.deleteRoleUserRelation(roleid);
//        //2.删除角色和权限关系
//        sysRoleMapper.deleteRolePermissionRelation(roleid);
//        //3.删除角色
//        this.removeById(roleid);
//        return true;
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean deleteBatchRole(String[] roleIds) {
//        //1.删除角色和用户关系
//        sysUserMapper.deleteBathRoleUserRelation(roleIds);
//        //2.删除角色和权限关系
//        sysUserMapper.deleteBathRolePermissionRelation(roleIds);
//        //3.删除角色
//        this.removeByIds(Arrays.asList(roleIds));
//        return true;
//    }
}

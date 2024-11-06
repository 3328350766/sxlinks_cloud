package com.sxlinks.modules.system.service.productCenter.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.biz.RedisKeyUtil;
import com.sxlinks.common.domain.bo.BaseAttrItemBo;
import com.sxlinks.common.domain.dto.response.device.DeviceRtResDto;
import com.sxlinks.common.domain.storage.EsMessage;
import com.sxlinks.common.metadata.BcMetaType;
import com.sxlinks.utils.JSONProvider;
import com.sxlinks.common.util.RedisUtil;
import com.sxlinks.dao.cache.impl.RedisDaoImpl;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.DeviceProperty;
import com.sxlinks.modules.system.mapper.productCenter.DeviceMapper;
import com.sxlinks.modules.system.mapper.productCenter.DevicePropertyMapper;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {
    @Resource
    DevicePropertyMapper propertyDao;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisDaoImpl redisDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
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

    @Override
    public Map<String, List<DeviceRtResDto>> queryRtByDevCodes(List<String> deviceCodes) {
        Map<String, List<DeviceRtResDto>> resultMap = new HashMap<>();
        for(String deviceCode: deviceCodes){
            List<DeviceRtResDto> listItem = this.queryRtByDevCode(deviceCode);
            resultMap.put(deviceCode, listItem);
        }
        return resultMap;
    }

    @Override
    public List<DeviceRtResDto> queryRtByDevCode(String deviceCode) {
        String redisKey = RedisKeyUtil.buildRtCacheKey(deviceCode, "PROP");
        List<DeviceRtResDto> resultFinal = new ArrayList<>();
        //Map<String, String> map = redisTemplate.getHashMapAll(redisKey);
        //Map<String,String> map = redisUtil.hGetAll(redisKey);
        Map map = redisDao.hGetAll(redisKey);
        List<DeviceProperty> lsProp=propertyDao.selectList(new QueryWrapper<DeviceProperty>().eq("device_code",deviceCode));
       // List<ProductFuncItemResDto> propList = productFuncService.ListFuncByProductCode(productCode, 1, funcTypeEnum);
        //this.removeUnuse(redisKey, propList, map);
        //map = cacheTemplate.getHashMapAll(redisKey);
        // 只会把identifier存在的数据返回给前端
        for (DeviceProperty prop : lsProp) {
            DeviceRtResDto item = new DeviceRtResDto();
            item.setIdentifier(prop.getIdentifier());
            item.setPropName(prop.getName());
            if(map.containsKey((Object)prop.getIdentifier())) {
                String jsonStr = map.get((Object) prop.getIdentifier()).toString();
                item.setDataType(prop.getDataType());
                if (StringUtils.isEmpty(jsonStr)) {
                    item.setValue("/");
                    item.setArrivedTime("/");
                } else {
                    EsMessage bo = JSON.parseObject(jsonStr, EsMessage.class);
                    item.setValue(bo.getRequest());
                    item.setArrivedTime(bo.getTimestamp());
                    BaseAttrItemBo attrItemBo = JSONProvider.parseObjectDefValue(prop.getAttr(), BaseAttrItemBo.class);
                    if (!StringUtils.isEmpty(attrItemBo.getUnit())) {
                        item.setUnit(attrItemBo.getUnit());
                    }
                    if (BcMetaType.BOOLEAN.getType().equals(attrItemBo.getDataType())) {

//                        if ((Boolean) bo.getRequest()) { //将字符串转成boolean
////                            item.setValue(attrItemBo.getBool1());
////                        } else {
////                            item.setValue(attrItemBo.getBool0());
////                        }
                        item.setValue(bo.getRequest());
                    }

                }
            }
            resultFinal.add(item);
        }
        return resultFinal;
    }

}

package com.sxlinks.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysScope;
import com.sxlinks.modules.system.model.SysScopeSimple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 区域 Mapper 接口
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface SysScopeMapper extends BaseMapper<SysScope> {

    List<SysScopeSimple> findAll();

    List<SysScope> getByCode(@Param("list") List<String> list);

    List<SysScopeSimple> getSimpleByParent(String parentCode);
}

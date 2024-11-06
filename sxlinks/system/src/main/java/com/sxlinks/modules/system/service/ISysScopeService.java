package com.sxlinks.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.SysScope;
import com.sxlinks.modules.system.model.SysScopeSimple;

import java.util.List;

/**
 * <p>
 * 区域 服务类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface ISysScopeService extends IService<SysScope> {

	/**
	 * 通过父级行政代码查询区域
	 * @param parentCode
	 * @return
	 */
	List<SysScope> getByParent(String parentCode);

	List<SysScopeSimple> getSimpleByParent(String parentCode);

	List<SysScope> getByCode(List<String> list);

	List<SysScopeSimple> findAll();

	String getCountyCode(String code);
}

package com.sxlinks.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.system.entity.SysScope;
import com.sxlinks.modules.system.mapper.SysScopeMapper;
import com.sxlinks.modules.system.model.SysScopeSimple;
import com.sxlinks.modules.system.service.ISysScopeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 区域 服务实现类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
@Service
@Slf4j
public class SysScopeServiceImpl extends ServiceImpl<SysScopeMapper, SysScope> implements ISysScopeService {

	@Resource
	private SysScopeMapper sysScopeMapper;

	@Override
	public List<SysScope> getByParent(String parentCode) {
		if (StringUtils.isNotBlank(parentCode)){
			return list(new LambdaQueryWrapper<SysScope>()
					.eq(SysScope::getParentCode,parentCode));
		}
		return new ArrayList<>();
	}

	@Override
	public List<SysScope> getByCode(List<String> list) {
		return sysScopeMapper.getByCode(list);
	}

	@Override
	public List<SysScopeSimple> getSimpleByParent(String parentCode) {
		return sysScopeMapper.getSimpleByParent(parentCode);
	}

	@Override
	public List<SysScopeSimple> findAll() {
		return sysScopeMapper.findAll();
	}

	public String getCountyCode(String code) {
		if (StringUtils.isNotBlank(code) && code.length() > 5){
			return StringUtils.join(code.substring(0,6),"000000");
		}
		return null;
	}
}

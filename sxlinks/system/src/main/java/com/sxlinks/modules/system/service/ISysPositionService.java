package com.sxlinks.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.SysPosition;

/**
 * @Description: 职务表
 * @Author: jeecg-boot
 * @Date: 2019-09-19
 * @Version: V1.0
 */
public interface ISysPositionService extends IService<SysPosition> {

    /**
     * 通过code查询
     */
    SysPosition getByCode(String code);

}

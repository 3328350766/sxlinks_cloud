package com.sxlinks.modules.message.service;

import java.util.List;

import com.sxlinks.common.system.base.service.JeecgService;
import com.sxlinks.modules.message.entity.SysMessageTemplate;

/**
 * @Description: 消息模板
 * @Author: jeecg-boot
 * @Date:  2022-03-23
 * @Version: V1.0
 */
public interface ISysMessageTemplateService extends JeecgService<SysMessageTemplate> {
    List<SysMessageTemplate> selectByCode(String code);
}

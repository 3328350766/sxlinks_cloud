package com.sxlinks.modules.message.service.impl;

import com.sxlinks.common.system.base.service.impl.JeecgServiceImpl;
import com.sxlinks.modules.message.entity.SysMessage;
import com.sxlinks.modules.message.mapper.SysMessageMapper;
import com.sxlinks.modules.message.service.ISysMessageService;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:  2022-03-23
 * @Version: V1.0
 */
@Service
public class SysMessageServiceImpl extends JeecgServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

}

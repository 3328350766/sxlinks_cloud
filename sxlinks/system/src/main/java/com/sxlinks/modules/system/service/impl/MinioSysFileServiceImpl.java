package com.sxlinks.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.system.entity.SysFile;
import com.sxlinks.modules.system.mapper.SysFileMapper;
import com.sxlinks.modules.system.service.ISysFileService;
import org.springframework.stereotype.Service;


/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
public class MinioSysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

}

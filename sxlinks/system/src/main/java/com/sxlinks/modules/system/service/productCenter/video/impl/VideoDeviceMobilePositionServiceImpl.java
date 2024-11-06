package com.sxlinks.modules.system.service.productCenter.video.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.biz.RedisKeyUtil;
import com.sxlinks.common.domain.bo.BaseAttrItemBo;

import com.sxlinks.common.domain.storage.EsMessage;
import com.sxlinks.common.metadata.BcMetaType;
import com.sxlinks.common.util.RedisUtil;
import com.sxlinks.dao.cache.impl.RedisDaoImpl;

import com.sxlinks.modules.system.entity.productCenter.video.VideoDeviceMobilePosition;

import com.sxlinks.modules.system.mapper.productCenter.video.VideoDeviceMobilePositionMapper;

import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceMobilePositionService;
import com.sxlinks.utils.JSONProvider;
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
public class VideoDeviceMobilePositionServiceImpl extends ServiceImpl<VideoDeviceMobilePositionMapper, VideoDeviceMobilePosition> implements IVideoDeviceMobilePositionService {

}

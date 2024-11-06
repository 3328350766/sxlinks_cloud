package com.sxlinks.modules.system.service.productCenter.video.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;

import com.sxlinks.modules.system.mapper.productCenter.video.VideoDeviceMapper;

import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceService;
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
public class VideoDeviceServiceImpl extends ServiceImpl<VideoDeviceMapper, VideoDevice> implements IVideoDeviceService {

    @Override
    public IPage<VideoDevice> customPage(IPage<VideoDevice> deviceIPage) {
        return baseMapper.getVideoDevices(deviceIPage);
    }

    @Override
    public VideoDevice getDeviceByDeviceId(String deviceId) {
        return baseMapper.getDeviceByDeviceId(deviceId);
    }

    @Override
    public Integer add(VideoDevice videoDevice) {
        return baseMapper.add(videoDevice);
    }

    @Override
    public Integer del(String id) {
        return baseMapper.del(id);
    }

    @Override
    public Integer update(VideoDevice videoDevice) {
        return baseMapper.update(videoDevice);
    }
}

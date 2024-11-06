package com.sxlinks.modules.system.service.productCenter.video;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
public interface IVideoDeviceService extends IService<VideoDevice> {

    IPage<VideoDevice> customPage(IPage<VideoDevice> deviceIPage);

    VideoDevice getDeviceByDeviceId(String deviceId);

    Integer add(VideoDevice videoDevice);

    Integer del(String id);

    Integer update(VideoDevice videoDevice);
}

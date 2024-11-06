package com.sxlinks.modules.system.service.productCenter.video;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.common.base.BaseTree;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDeviceChannel;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
public interface IVideoDeviceChannelService extends IService<VideoDeviceChannel> {

    Integer add(VideoDeviceChannel deviceChannel);

    Integer update(VideoDeviceChannel channel);

    IPage<VideoDeviceChannel> custom(IPage<VideoDeviceChannel> iPage,String deviceId);

    /**
     * 树形查询接口
     * @param deviceId 设备ID
     * @param parentId 父ID
     * @param onlyCatalog 只获取目录
     * @return
     */
    List<BaseTree<VideoDeviceChannel>> queryVideoDeviceTree(String deviceId, String parentId, boolean onlyCatalog);
}

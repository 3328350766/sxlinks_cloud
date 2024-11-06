package com.sxlinks.modules.system.service.productCenter.video;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import com.sxlinks.modules.system.zlm.dto.SSRCInfo;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
public interface IVideoMediaServerService extends IService<VideoMediaServer> {

    int add(VideoMediaServer mediaServerItem);

    int update(VideoMediaServer mediaServerItem);

    void del(String id);

    /**
     * 获取流媒体服务器信息
     * @param device
     */
    VideoMediaServer getNewMediaServerItem(VideoDevice device);

    VideoMediaServer queryOne(String id);

    /**
     * 获取负载最低的节点
     * @return MediaServerItem
     */
    VideoMediaServer getMediaServerForMinimumLoad();

    IPage<VideoMediaServer> custom(IPage iPage);

    SSRCInfo openRTPServer(VideoMediaServer mediaServerItem, String streamId, boolean ssrcCheck, boolean isPlayback);

    SSRCInfo openRTPServer(VideoMediaServer mediaServerItem, String streamId, String ssrc, boolean ssrcCheck, boolean isPlayback);

    SSRCInfo openRTPServer(VideoMediaServer mediaServerItem, String streamId, String ssrc, boolean ssrcCheck, boolean isPlayback, Integer port);
}

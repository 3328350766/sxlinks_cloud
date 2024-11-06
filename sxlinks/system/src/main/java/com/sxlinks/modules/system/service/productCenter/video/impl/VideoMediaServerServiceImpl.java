package com.sxlinks.modules.system.service.productCenter.video.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import com.sxlinks.modules.system.mapper.productCenter.video.VideoMediaServerMapper;
import com.sxlinks.modules.system.service.productCenter.video.IVideoMediaServerService;
import com.sxlinks.modules.system.zlm.VideoManagerConstants;
import com.sxlinks.modules.system.zlm.dto.SSRCInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@Service
public class VideoMediaServerServiceImpl extends ServiceImpl<VideoMediaServerMapper, VideoMediaServer> implements IVideoMediaServerService {


    @Override
    public int add(VideoMediaServer mediaServerItem) {
        return baseMapper.add(mediaServerItem);
    }

    @Override
    public int update(VideoMediaServer mediaServerItem) {
        return baseMapper.update(mediaServerItem);
    }

    @Override
    public void del(String id) {
        baseMapper.delOne(id);
    }

    @Override
    public VideoMediaServer getNewMediaServerItem(VideoDevice device) {
        if (device == null) {
            return null;
        }
        VideoMediaServer videoMediaServer;
        if (ObjectUtils.isEmpty(device.getMediaServerId()) || "auto".equals(device.getMediaServerId())) {
            videoMediaServer = getMediaServerForMinimumLoad();
        } else {
            videoMediaServer = baseMapper.getByVideoMediaServerId(device.getMediaServerId());
        }
        if (videoMediaServer == null) {
            return null;
        }
        return videoMediaServer;
    }

    @Override
    public VideoMediaServer getMediaServerForMinimumLoad() {
        return null;
    }

    @Override
    public VideoMediaServer queryOne(String id) {
        return baseMapper.queryOne(id);
    }

    @Override
    public IPage<VideoMediaServer> custom(IPage iPage) {
        return baseMapper.customPage(iPage);
    }

    @Override
    public SSRCInfo openRTPServer(VideoMediaServer mediaServerItem, String streamId, boolean ssrcCheck, boolean isPlayback) {
        return openRTPServer(mediaServerItem, streamId, null, ssrcCheck,isPlayback);
    }

    @Override
    public SSRCInfo openRTPServer(VideoMediaServer mediaServerItem, String streamId, String ssrc, boolean ssrcCheck, boolean isPlayback) {
        return openRTPServer(mediaServerItem, streamId, ssrc, ssrcCheck, isPlayback, null);
    }

    @Override
    public SSRCInfo openRTPServer(VideoMediaServer mediaServerItem, String streamId, String presetSsrc, boolean ssrcCheck, boolean isPlayback, Integer port) {
//        if (mediaServerItem == null || mediaServerItem.getId() == null) {
//            return null;
//        }
//        // 获取mediaServer可用的ssrc
//        String key = VideoManagerConstants.MEDIA_SERVER_PREFIX + userSetting.getServerId() + "_" + mediaServerItem.getId();
//
//        SsrcConfig ssrcConfig = mediaServerItem.getSsrcConfig();
//        if (ssrcConfig == null) {
//            logger.info("media server [ {} ] ssrcConfig is null", mediaServerItem.getId());
//            return null;
//        }else {
//            String ssrc;
//            if (presetSsrc != null) {
//                ssrc = presetSsrc;
//            }else {
//                if (isPlayback) {
//                    ssrc = ssrcConfig.getPlayBackSsrc();
//                }else {
//                    ssrc = ssrcConfig.getPlaySsrc();
//                }
//            }
//
//            if (streamId == null) {
//                streamId = String.format("%08x", Integer.parseInt(ssrc)).toUpperCase();
//            }
//            int rtpServerPort;
//            if (mediaServerItem.isRtpEnable()) {
//                rtpServerPort = zlmrtpServerFactory.createRTPServer(mediaServerItem, streamId, ssrcCheck?Integer.parseInt(ssrc):0, port);
//            } else {
//                rtpServerPort = mediaServerItem.getRtpProxyPort();
//            }
//            RedisUtil.set(key, mediaServerItem);
//            return new SSRCInfo(rtpServerPort, ssrc, streamId);

//        }
        return new SSRCInfo(0,null,null);
    }
}

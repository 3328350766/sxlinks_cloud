package com.sxlinks.modules.system.service.productCenter.video;

import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import com.sxlinks.modules.system.zlm.dto.PlayResult;
import com.sxlinks.modules.system.zlm.ZlmHttpHookSubscribe;
import com.sxlinks.modules.system.zlm.bean.InviteTimeOutCallback;
import com.sxlinks.modules.system.zlm.dto.SSRCInfo;
import com.sxlinks.modules.system.zlm.dto.SipSubscribe;

/**
 * 点播处理
 */
public interface IPlayService {

    void play(VideoMediaServer mediaServerItem, SSRCInfo ssrcInfo, VideoDevice device, String channelId,
              ZlmHttpHookSubscribe.Event hookEvent, SipSubscribe.Event errorEvent,
              InviteTimeOutCallback timeoutCallback, String uuid);

    PlayResult play(VideoMediaServer mediaServerItem, String deviceId, String channelId, ZlmHttpHookSubscribe.Event event, SipSubscribe.Event errorEvent, Runnable timeoutCallback);
}

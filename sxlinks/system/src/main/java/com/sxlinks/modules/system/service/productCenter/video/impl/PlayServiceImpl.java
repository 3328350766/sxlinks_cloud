package com.sxlinks.modules.system.service.productCenter.video.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxlinks.common.api.dto.message.DeferredResultHolder;
import com.sxlinks.common.api.dto.message.RequestMessage;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import com.sxlinks.modules.system.service.IRedisCatchStorage;
import com.sxlinks.modules.system.service.IVideoManagerStorage;
import com.sxlinks.modules.system.service.productCenter.video.IPlayService;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceService;
import com.sxlinks.modules.system.service.productCenter.video.IVideoMediaServerService;
import com.sxlinks.modules.system.zlm.dto.ErrorCode;
import com.sxlinks.modules.system.zlm.dto.PlayResult;
import com.sxlinks.modules.system.zlm.dto.StreamInfo;
import com.sxlinks.modules.system.zlm.dto.WVPResult;
import com.sxlinks.modules.system.zlm.ZLMRESTfulUtils;
import com.sxlinks.modules.system.zlm.ZlmHttpHookSubscribe;
import com.sxlinks.modules.system.zlm.bean.InviteTimeOutCallback;
import com.sxlinks.modules.system.zlm.dto.SSRCInfo;
import com.sxlinks.modules.system.zlm.dto.SipSubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Objects;
import java.util.UUID;

@Service
public class PlayServiceImpl implements IPlayService {

    private Logger logger = LoggerFactory.getLogger(PlayServiceImpl.class);

    @Autowired
    private IVideoDeviceService iVideoDeviceService;

    @Autowired
    private IVideoMediaServerService iVideoMediaServerService;

    @Autowired
    private DeferredResultHolder resultHolder;

    @Autowired
    private IRedisCatchStorage redisCatchStorage;

    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ZLMRESTfulUtils zlmresTfulUtils;

    @Autowired
    private IVideoManagerStorage storager;

    @Override
    public PlayResult play(VideoMediaServer mediaServerItem, String deviceId, String channelId,
                           ZlmHttpHookSubscribe.Event hookEvent, SipSubscribe.Event errorEvent,
                           Runnable timeoutCallback) {
        if (mediaServerItem == null) {
            return null;
//            throw new Exception(ErrorCode.ERROR100.getCode(), "未找到可用的zlm");
        }

        PlayResult playResult = new PlayResult();
        RequestMessage msg = new RequestMessage();
        String key = DeferredResultHolder.CALLBACK_CMD_PLAY + deviceId + channelId;
        msg.setKey(key);
        String uuid = UUID.randomUUID().toString();
        msg.setId(uuid);
        playResult.setUuid(uuid);
        DeferredResult<WVPResult<StreamInfo>> result = new DeferredResult<>(18000l);
        playResult.setResult(result);
        // 录像查询以channelId作为deviceId查询
        resultHolder.put(key, uuid, result);

        VideoDevice device = iVideoDeviceService.getDeviceByDeviceId(deviceId);
        StreamInfo streamInfo = redisCatchStorage.queryPlayByDevice(deviceId, channelId);
        playResult.setDevice(device);

        result.onCompletion(() -> {
            // 点播结束时调用截图接口
            taskExecutor.execute(() -> {
                // TODO 应该在上流时调用更好，结束也可能是错误结束
                String path = "snap";
                String fileName = deviceId + "_" + channelId + ".jpg";
                WVPResult wvpResult = (WVPResult) result.getResult();
                if (Objects.requireNonNull(wvpResult).getCode() == 0) {
                    StreamInfo streamInfoForSuccess = (StreamInfo) wvpResult.getData();

                    // 代码需放开
//                    VideoMediaServer mediaInfo = iVideoMediaServerService.getMediaServerForMinimumLoad();

                    // 代码需删除
                    VideoDevice tempDevice = new VideoDevice();
                    tempDevice.setMediaServerId("FQ3TF8yT83wh5Wvz");

                    VideoMediaServer mediaInfo = iVideoMediaServerService.getNewMediaServerItem(tempDevice);
                    String streamUrl = streamInfoForSuccess.getFmp4();

                    // 请求截图
                    logger.info("[请求截图]: " + fileName);
                    zlmresTfulUtils.getSnap(mediaInfo, streamUrl, 15, 1, path, fileName);
                }
            });
        });

        if (streamInfo != null) {
            String streamId = streamInfo.getStream();
            if (streamId == null) {
                WVPResult wvpResult = new WVPResult();
                wvpResult.setCode(ErrorCode.ERROR100.getCode());
                wvpResult.setMsg("点播失败， redis缓存streamId等于null");
                msg.setData(wvpResult);
                resultHolder.invokeAllResult(msg);
                return playResult;
            }
            String mediaServerId = streamInfo.getMediaServerId();
            VideoMediaServer mediaInfo = iVideoMediaServerService.queryOne(mediaServerId);

            JSONObject rtpInfo = zlmresTfulUtils.getRtpInfo(mediaInfo, streamId);
            if (rtpInfo.getInteger("code") == 0) {
                if (rtpInfo.getBoolean("exist")) {
                    int localPort = rtpInfo.getInteger("local_port");
                    if (localPort == 0) {
                        logger.warn("[点播]，点播时发现rtpServerC存在，但是尚未开始推流");
                        // 此时说明rtpServer已经创建但是流还没有推上来
                        WVPResult wvpResult = new WVPResult();
                        wvpResult.setCode(ErrorCode.ERROR100.getCode());
                        wvpResult.setMsg("点播已经在进行中，请稍候重试");
                        msg.setData(wvpResult);

                        resultHolder.invokeAllResult(msg);
                        return playResult;
                    } else {
                        WVPResult wvpResult = new WVPResult();
                        wvpResult.setCode(ErrorCode.SUCCESS.getCode());
                        wvpResult.setMsg(ErrorCode.SUCCESS.getMsg());
                        wvpResult.setData(streamInfo);
                        msg.setData(wvpResult);

                        resultHolder.invokeAllResult(msg);
                        if (hookEvent != null) {
                            hookEvent.response(mediaServerItem, JSONObject.parseObject(JSON.toJSONString(streamInfo)));
                        }
                    }

                } else {
                    redisCatchStorage.stopPlay(streamInfo);
                    storager.stopPlay(streamInfo.getDeviceID(), streamInfo.getChannelId());
                    streamInfo = null;
                }
            } else {
                //zlm连接失败
                redisCatchStorage.stopPlay(streamInfo);
                storager.stopPlay(streamInfo.getDeviceID(), streamInfo.getChannelId());
                streamInfo = null;

            }
        }
        if (streamInfo == null) {
            String streamId = null;
            if (mediaServerItem.isRtpEnable()) {
                streamId = String.format("%s_%s", device.getDeviceId(), channelId);
            }
            SSRCInfo ssrcInfo = iVideoMediaServerService.openRTPServer(mediaServerItem, streamId, device.isSsrcCheck(), false);
            logger.info(JSONObject.toJSONString(ssrcInfo));
            play(mediaServerItem, ssrcInfo, device, channelId, (mediaServerItemInUse, response) -> {
                if (hookEvent != null) {
                    hookEvent.response(mediaServerItem, response);
                }
            }, event -> {
                // sip error错误
                WVPResult wvpResult = new WVPResult();
                wvpResult.setCode(ErrorCode.ERROR100.getCode());
                wvpResult.setMsg(String.format("点播失败， 错误码： %s, %s", event.statusCode, event.msg));
                msg.setData(wvpResult);
                resultHolder.invokeAllResult(msg);
                if (errorEvent != null) {
                    errorEvent.response(event);
                }
            }, (code, msgStr) -> {
                // invite点播超时
                WVPResult wvpResult = new WVPResult();
                wvpResult.setCode(ErrorCode.ERROR100.getCode());
                if (code == 0) {
                    wvpResult.setMsg("点播超时，请稍候重试");
                } else if (code == 1) {
                    wvpResult.setMsg("收流超时，请稍候重试");
                }
                msg.setData(wvpResult);
                // 回复之前所有的点播请求
                resultHolder.invokeAllResult(msg);
            }, uuid);
        }
        return playResult;
    }

    @Override
    public void play(VideoMediaServer mediaServerItem, SSRCInfo ssrcInfo, VideoDevice device, String channelId,
                     ZlmHttpHookSubscribe.Event hookEvent, SipSubscribe.Event errorEvent,
                     InviteTimeOutCallback timeoutCallback, String uuid) {

//        String streamId = null;
//        if (mediaServerItem.isRtpEnable()) {
//            streamId = String.format("%s_%s", device.getDeviceId(), channelId);
//        }
//        if (ssrcInfo == null) {
//            ssrcInfo = iVideoMediaServerService.openRTPServer(mediaServerItem, streamId, device.isSsrcCheck(), false);
//        }
//        logger.info("[点播开始] deviceId: {}, channelId: {},收流端口： {}, 收流模式：{}, SSRC: {}, SSRC校验：{}", device.getDeviceId(), channelId, ssrcInfo.getPort(), device.getStreamMode(), ssrcInfo.getSsrc(), device.isSsrcCheck());
//        // 超时处理
//        String timeOutTaskKey = UUID.randomUUID().toString();
//        SSRCInfo finalSsrcInfo = ssrcInfo;
//        System.out.println("设置超时任务： " + timeOutTaskKey);
//        dynamicTask.startDelay(timeOutTaskKey, () -> {
//
//            logger.info("[点播超时] 收流超时 deviceId: {}, channelId: {}，端口：{}, SSRC: {}", device.getDeviceId(), channelId, finalSsrcInfo.getPort(), finalSsrcInfo.getSsrc());
//            timeoutCallback.run(1, "收流超时");
//            // 点播超时回复BYE 同时释放ssrc以及此次点播的资源
//            try {
//                cmder.streamByeCmd(device, channelId, finalSsrcInfo.getStream(), null);
//            } catch (InvalidArgumentException | ParseException | SipException e) {
//                logger.error("[点播超时]， 发送BYE失败 {}", e.getMessage());
//            } catch (SsrcTransactionNotFoundException e) {
//                timeoutCallback.run(0, "点播超时");
//                mediaServerService.releaseSsrc(mediaServerItem.getId(), finalSsrcInfo.getSsrc());
//                mediaServerService.closeRTPServer(mediaServerItem, finalSsrcInfo.getStream());
//                streamSession.remove(device.getDeviceId(), channelId, finalSsrcInfo.getStream());
//            }
//        }, userSetting.getPlayTimeout());
//        final String ssrc = ssrcInfo.getSsrc();
//        final String stream = ssrcInfo.getStream();
//        //端口获取失败的ssrcInfo 没有必要发送点播指令
//        if (ssrcInfo.getPort() <= 0) {
//            logger.info("[点播端口分配异常]，deviceId={},channelId={},ssrcInfo={}", device.getDeviceId(), channelId, ssrcInfo);
//            return;
//        }
//        try {
//            cmder.playStreamCmd(mediaServerItem, ssrcInfo, device, channelId, (MediaServerItem mediaServerItemInuse, JSONObject response) -> {
//                logger.info("收到订阅消息： " + response.toJSONString());
//                System.out.println("停止超时任务： " + timeOutTaskKey);
//                dynamicTask.stop(timeOutTaskKey);
//                // hook响应
//                onPublishHandlerForPlay(mediaServerItemInuse, response, device.getDeviceId(), channelId, uuid);
//                hookEvent.response(mediaServerItemInuse, response);
//                logger.info("[点播成功] deviceId: {}, channelId: {}", device.getDeviceId(), channelId);
//
//            }, (event) -> {
//                ResponseEvent responseEvent = (ResponseEvent) event.event;
//                String contentString = new String(responseEvent.getResponse().getRawContent());
//                // 获取ssrc
//                int ssrcIndex = contentString.indexOf("y=");
//                // 检查是否有y字段
//                if (ssrcIndex >= 0) {
//                    //ssrc规定长度为10字节，不取余下长度以避免后续还有“f=”字段 TODO 后续对不规范的非10位ssrc兼容
//                    String ssrcInResponse = contentString.substring(ssrcIndex + 2, ssrcIndex + 12);
//                    // 查询到ssrc不一致且开启了ssrc校验则需要针对处理
//                    if (ssrc.equals(ssrcInResponse)) {
//                        return;
//                    }
//                    logger.info("[点播消息] 收到invite 200, 发现下级自定义了ssrc: {}", ssrcInResponse);
//                    if (!mediaServerItem.isRtpEnable() || device.isSsrcCheck()) {
//                        logger.info("[点播消息] SSRC修正 {}->{}", ssrc, ssrcInResponse);
//
//                        if (!mediaServerItem.getSsrcConfig().checkSsrc(ssrcInResponse)) {
//                            // ssrc 不可用
//                            // 释放ssrc
//                            mediaServerService.releaseSsrc(mediaServerItem.getId(), finalSsrcInfo.getSsrc());
//                            streamSession.remove(device.getDeviceId(), channelId, finalSsrcInfo.getStream());
//                            event.msg = "下级自定义了ssrc,但是此ssrc不可用";
//                            event.statusCode = 400;
//                            errorEvent.response(event);
//                            return;
//                        }
//
//                        // 单端口模式streamId也有变化，需要重新设置监听
//                        if (!mediaServerItem.isRtpEnable()) {
//                            // 添加订阅
//                            HookSubscribeForStreamChange hookSubscribe = HookSubscribeFactory.on_stream_changed("rtp", stream, true, "rtsp", mediaServerItem.getId());
//                            subscribe.removeSubscribe(hookSubscribe);
//                            hookSubscribe.getContent().put("stream", String.format("%08x", Integer.parseInt(ssrcInResponse)).toUpperCase());
//                            subscribe.addSubscribe(hookSubscribe, (MediaServerItem mediaServerItemInUse, JSONObject response) -> {
//                                logger.info("[ZLM HOOK] ssrc修正后收到订阅消息： " + response.toJSONString());
//                                dynamicTask.stop(timeOutTaskKey);
//                                // hook响应
//                                onPublishHandlerForPlay(mediaServerItemInUse, response, device.getDeviceId(), channelId, uuid);
//                                hookEvent.response(mediaServerItemInUse, response);
//                            });
//                        }
//                        // 关闭rtp server
//                        mediaServerService.closeRTPServer(mediaServerItem, finalSsrcInfo.getStream());
//                        // 重新开启ssrc server
//                        mediaServerService.openRTPServer(mediaServerItem, finalSsrcInfo.getStream(), ssrcInResponse, device.isSsrcCheck(), false, finalSsrcInfo.getPort());
//
//                    }
//                }
//            }, (event) -> {
//                dynamicTask.stop(timeOutTaskKey);
//                mediaServerService.closeRTPServer(mediaServerItem, finalSsrcInfo.getStream());
//                // 释放ssrc
//                mediaServerService.releaseSsrc(mediaServerItem.getId(), finalSsrcInfo.getSsrc());
//
//                streamSession.remove(device.getDeviceId(), channelId, finalSsrcInfo.getStream());
//                errorEvent.response(event);
//            });
//        } catch (InvalidArgumentException | SipException | ParseException e) {
//
//            logger.error("[命令发送失败] 点播消息: {}", e.getMessage());
//            dynamicTask.stop(timeOutTaskKey);
//            mediaServerService.closeRTPServer(mediaServerItem, finalSsrcInfo.getStream());
//            // 释放ssrc
//            mediaServerService.releaseSsrc(mediaServerItem.getId(), finalSsrcInfo.getSsrc());
//
//            streamSession.remove(device.getDeviceId(), channelId, finalSsrcInfo.getStream());
//            SipSubscribe.EventResult eventResult = new SipSubscribe.EventResult(new CmdSendFailEvent(null));
//            eventResult.msg = "命令发送失败";
//            errorEvent.response(eventResult);
//        }
    }
}

package com.sxlinks.modules.api.controller.video;

import com.sxlinks.common.api.vo.Result;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import com.sxlinks.modules.system.service.productCenter.video.IPlayService;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceService;
import com.sxlinks.modules.system.service.productCenter.video.IVideoMediaServerService;
import com.sxlinks.modules.system.zlm.dto.PlayResult;
import com.sxlinks.modules.system.zlm.dto.StreamInfo;
import com.sxlinks.modules.system.zlm.dto.WVPResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@Api(tags = "国标设备点播")
@CrossOrigin
@RestController
@RequestMapping("/video/play")
public class VideoPlayController {

	private final static Logger logger = LoggerFactory.getLogger(VideoPlayController.class);
//
//	@Autowired
//	private SIPCommander cmder;
//
//	@Autowired
//	private VideoStreamSessionManager streamSession;

	@Autowired
	private IVideoDeviceService iVideoDeviceService;
//
//	@Autowired
//	private IRedisCatchStorage redisCatchStorage;
//
//	@Autowired
//	private ZLMRESTfulUtils zlmresTfulUtils;
//
//	@Autowired
//	private DeferredResultHolder resultHolder;
//
	@Autowired
	private IPlayService playService;
//
//	@Autowired
//	private IMediaService mediaService;

	@Autowired
	private IVideoMediaServerService iVideoMediaServerService;

	@ApiOperation(value = "开始点播")
	@GetMapping("/start/{deviceId}/{channelId}")
	public DeferredResult<WVPResult<StreamInfo>> play(@PathVariable String deviceId,
													  @PathVariable String channelId) {
		// 获取可用的zlm
		VideoDevice device = iVideoDeviceService.getDeviceByDeviceId(deviceId);

		// 临时添加,这里手动设置一下服务器id，为了测试能否成功调起视频
		device.setMediaServerId("FQ3TF8yT83wh5Wvz");

		VideoMediaServer newMediaServerItem = iVideoMediaServerService.getNewMediaServerItem(device);
		PlayResult playResult = playService.play(newMediaServerItem, deviceId, channelId, null, null, null);

		return playResult.getResult();
	}

//
//	@ApiOperation(value = "停止点播")
//	@GetMapping("/stop/{deviceId}/{channelId}")
//	public JSONObject playStop(@PathVariable String deviceId, @PathVariable String channelId) {
//
//		logger.debug(String.format("设备预览/回放停止API调用，streamId：%s_%s", deviceId, channelId ));
//
//		if (deviceId == null || channelId == null) {
//			throw new ControllerException(ErrorCode.ERROR400);
//		}
//
//		Device device = storager.queryVideoDevice(deviceId);
//		if (device == null) {
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "设备[" + deviceId + "]不存在");
//		}
//
//		StreamInfo streamInfo = redisCatchStorage.queryPlayByDevice(deviceId, channelId);
//		if (streamInfo == null) {
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "点播未找到");
//		}
//
//		try {
//			logger.warn("[停止点播] {}/{}", device.getDeviceId(), channelId);
//			cmder.streamByeCmd(device, channelId, streamInfo.getStream(), null, null);
//		} catch (InvalidArgumentException | SipException | ParseException | SsrcTransactionNotFoundException e) {
//			logger.error("[命令发送失败] 停止点播， 发送BYE: {}", e.getMessage());
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "命令发送失败: " + e.getMessage());
//		}
//		redisCatchStorage.stopPlay(streamInfo);
//
//		storager.stopPlay(streamInfo.getDeviceID(), streamInfo.getChannelId());
//		JSONObject json = new JSONObject();
//		json.put("deviceId", deviceId);
//		json.put("channelId", channelId);
//		return json;
//
//	}
//
//	/**
//	 * 将不是h264的视频通过ffmpeg 转码为h264 + aac
//	 * @param streamId 流ID
//	 */
//	@ApiOperation(value = "将不是h264的视频通过ffmpeg 转码为h264 + aac")
//	@PostMapping("/convert/{streamId}")
//	public JSONObject playConvert(@PathVariable String streamId) {
//		StreamInfo streamInfo = redisCatchStorage.queryPlayByStreamId(streamId);
//		if (streamInfo == null) {
//			streamInfo = redisCatchStorage.queryPlayback(null, null, streamId, null);
//		}
//		if (streamInfo == null) {
//			logger.warn("视频转码API调用失败！, 视频流已经停止!");
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "未找到视频流信息, 视频流可能已经停止");
//		}
//		MediaServerItem mediaInfo = mediaServerService.getOne(streamInfo.getMediaServerId());
//		JSONObject rtpInfo = zlmresTfulUtils.getRtpInfo(mediaInfo, streamId);
//		if (!rtpInfo.getBoolean("exist")) {
//			logger.warn("视频转码API调用失败！, 视频流已停止推流!");
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "未找到视频流信息, 视频流可能已停止推流");
//		} else {
//			String dstUrl = String.format("rtmp://%s:%s/convert/%s", "127.0.0.1", mediaInfo.getRtmpPort(),
//					streamId );
//			String srcUrl = String.format("rtsp://%s:%s/rtp/%s", "127.0.0.1", mediaInfo.getRtspPort(), streamId);
//			JSONObject jsonObject = zlmresTfulUtils.addFFmpegSource(mediaInfo, srcUrl, dstUrl, "1000000", true, false, null);
//			logger.info(jsonObject.toJSONString());
//			if (jsonObject != null && jsonObject.getInteger("code") == 0) {
//				JSONObject data = jsonObject.getJSONObject("data");
//				if (data != null) {
//					JSONObject result = new JSONObject();
//					result.put("key", data.getString("key"));
//					StreamInfo streamInfoResult = mediaService.getStreamInfoByAppAndStreamWithCheck("convert", streamId, mediaInfo.getId(), false);
//					result.put("StreamInfo", streamInfoResult);
//					return result;
//				}else {
//					throw new ControllerException(ErrorCode.ERROR100.getCode(), "转码失败");
//				}
//			}else {
//				throw new ControllerException(ErrorCode.ERROR100.getCode(), "转码失败");
//			}
//		}
//	}
//
//	/**
//	 * 结束转码
//	 */
//	@ApiOperation(value = "结束转码")
//	@PostMapping("/convertStop/{key}")
//	public void playConvertStop(@PathVariable String key, String mediaServerId) {
//		if (mediaServerId == null) {
//			throw new ControllerException(ErrorCode.ERROR400.getCode(), "流媒体：" + mediaServerId + "不存在" );
//		}
//		MediaServerItem mediaInfo = mediaServerService.getOne(mediaServerId);
//		if (mediaInfo == null) {
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "使用的流媒体已经停止运行" );
//		}else {
//			JSONObject jsonObject = zlmresTfulUtils.delFFmpegSource(mediaInfo, key);
//			logger.info(jsonObject.toJSONString());
//			if (jsonObject != null && jsonObject.getInteger("code") == 0) {
//				JSONObject data = jsonObject.getJSONObject("data");
//				if (data == null || data.getBoolean("flag") == null || !data.getBoolean("flag")) {
//					throw new ControllerException(ErrorCode.ERROR100 );
//				}
//			}else {
//				throw new ControllerException(ErrorCode.ERROR100 );
//			}
//		}
//	}
//
//	@ApiOperation(value = "语音广播命令")
//    @GetMapping("/broadcast/{deviceId}")
//    @PostMapping("/broadcast/{deviceId}")
//    public DeferredResult<String> broadcastApi(@PathVariable String deviceId) {
//        if (logger.isDebugEnabled()) {
//            logger.debug("语音广播API调用");
//        }
//        Device device = storager.queryVideoDevice(deviceId);
//		DeferredResult<String> result = new DeferredResult<>(3 * 1000L);
//		String key  = DeferredResultHolder.CALLBACK_CMD_BROADCAST + deviceId;
//		if (resultHolder.exist(key, null)) {
//			result.setResult("设备使用中");
//			return result;
//		}
//		String uuid  = UUID.randomUUID().toString();
//        if (device == null) {
//
//			resultHolder.put(key, key,  result);
//			RequestMessage msg = new RequestMessage();
//			msg.setKey(key);
//			msg.setId(uuid);
//			JSONObject json = new JSONObject();
//			json.put("DeviceID", deviceId);
//			json.put("CmdType", "Broadcast");
//			json.put("Result", "Failed");
//			json.put("Description", "Device 不存在");
//			msg.setData(json);
//			resultHolder.invokeResult(msg);
//			return result;
//		}
//		try {
//			cmder.audioBroadcastCmd(device, (event) -> {
//				RequestMessage msg = new RequestMessage();
//				msg.setKey(key);
//				msg.setId(uuid);
//				JSONObject json = new JSONObject();
//				json.put("DeviceID", deviceId);
//				json.put("CmdType", "Broadcast");
//				json.put("Result", "Failed");
//				json.put("Description", String.format("语音广播操作失败，错误码： %s, %s", event.statusCode, event.msg));
//				msg.setData(json);
//				resultHolder.invokeResult(msg);
//			});
//		} catch (InvalidArgumentException | SipException | ParseException e) {
//			logger.error("[命令发送失败] 语音广播: {}", e.getMessage());
//			throw new ControllerException(ErrorCode.ERROR100.getCode(), "命令发送失败: " + e.getMessage());
//		}
//
//		result.onTimeout(() -> {
//			logger.warn("语音广播操作超时, 设备未返回应答指令");
//			RequestMessage msg = new RequestMessage();
//			msg.setKey(key);
//			msg.setId(uuid);
//			JSONObject json = new JSONObject();
//			json.put("DeviceID", deviceId);
//			json.put("CmdType", "Broadcast");
//			json.put("Result", "Failed");
//			json.put("Error", "Timeout. Device did not response to broadcast command.");
//			msg.setData(json);
//			resultHolder.invokeResult(msg);
//		});
//		resultHolder.put(key, uuid, result);
//		return result;
//	}
//
//	@ApiOperation(value = "获取所有的ssrc")
//	@GetMapping("/ssrc")
//	public JSONObject getSSRC() {
//		if (logger.isDebugEnabled()) {
//			logger.debug("获取所有的ssrc");
//		}
//		JSONArray objects = new JSONArray();
//		List<SsrcTransaction> allSsrc = streamSession.getAllSsrc();
//		for (SsrcTransaction transaction : allSsrc) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("deviceId", transaction.getDeviceId());
//			jsonObject.put("channelId", transaction.getChannelId());
//			jsonObject.put("ssrc", transaction.getSsrc());
//			jsonObject.put("streamId", transaction.getStream());
//			objects.add(jsonObject);
//		}
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("data", objects);
//		jsonObject.put("count", objects.size());
//		return jsonObject;
//	}
}

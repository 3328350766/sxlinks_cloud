package com.sxlinks.modules.system.zlm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "流信息")
public class StreamInfo {

    @ApiModelProperty(value = "应用名")
    private String app;
    @ApiModelProperty(value = "流ID")
    private String stream;
    @ApiModelProperty(value = "设备编号")
    private String deviceID;
    @ApiModelProperty(value = "通道编号")
    private String channelId;
    @ApiModelProperty(value = "HTTP-FLV流地址")
    private String flv;

    @ApiModelProperty(value = "IP")
    private String ip;
    @ApiModelProperty(value = "HTTPS-FLV流地址")
    private String https_flv;
    @ApiModelProperty(value = "Websocket-FLV流地址")
    private String ws_flv;
    @ApiModelProperty(value = "Websockets-FLV流地址")
    private String wss_flv;
    @ApiModelProperty(value = "HTTP-FMP4流地址")
    private String fmp4;
    @ApiModelProperty(value = "HTTPS-FMP4流地址")
    private String https_fmp4;
    @ApiModelProperty(value = "Websocket-FMP4流地址")
    private String ws_fmp4;
    @ApiModelProperty(value = "Websockets-FMP4流地址")
    private String wss_fmp4;
    @ApiModelProperty(value = "HLS流地址")
    private String hls;
    @ApiModelProperty(value = "HTTPS-HLS流地址")
    private String https_hls;
    @ApiModelProperty(value = "Websocket-HLS流地址")
    private String ws_hls;
    @ApiModelProperty(value = "Websockets-HLS流地址")
    private String wss_hls;
    @ApiModelProperty(value = "HTTP-TS流地址")
    private String ts;
    @ApiModelProperty(value = "HTTPS-TS流地址")
    private String https_ts;
    @ApiModelProperty(value = "Websocket-TS流地址")
    private String ws_ts;
    @ApiModelProperty(value = "Websockets-TS流地址")
    private String wss_ts;
    @ApiModelProperty(value = "RTMP流地址")
    private String rtmp;
    @ApiModelProperty(value = "RTMPS流地址")
    private String rtmps;
    @ApiModelProperty(value = "RTSP流地址")
    private String rtsp;
    @ApiModelProperty(value = "RTSPS流地址")
    private String rtsps;
    @ApiModelProperty(value = "RTC流地址")
    private String rtc;

    @ApiModelProperty(value = "RTCS流地址")
    private String rtcs;
    @ApiModelProperty(value = "流媒体ID")
    private String mediaServerId;
    @ApiModelProperty(value = "流编码信息")
    private Object tracks;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "进度（录像下载使用）")
    private double progress;

    @ApiModelProperty(value = "是否暂停（录像回放使用）")
    private boolean pause;

    public static class TransactionInfo{
        public String callId;
        public String localTag;
        public String remoteTag;
        public String branch;
    }

    private TransactionInfo transactionInfo;
}

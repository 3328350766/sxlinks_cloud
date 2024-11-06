package com.sxlinks.common.domain.gateway.mq;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 存储在队列中的对象
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: QueueMsgBo.java, v 0.1 2020-12-09  Exp $$
  */
@Data
public class UpgradeMessageBo {
    /**固件ID*/
    private Long id;
    @NotNull(message = "未上传附件")
    private String url;
    @NotNull(message = "版本号不能为空")
    private String firmwareVersion;
    @NotNull(message = "序列号不能为空")
    private Integer seqNo;
    @NotNull(message = "产品不能为空")
    private String productCode;
    private String signType = "md5";
    @NotNull(message = "签名不能为空")
    private String signCode;
    private String productName;
    private String fileBase64;
    private Integer pushType;
    /**必须有设备编码，不管前端是批量升级还是逐个升级，但是到这里全是逐个升级*/
    private String deviceCode;
    private Long taskId;
    private String messageId;
}

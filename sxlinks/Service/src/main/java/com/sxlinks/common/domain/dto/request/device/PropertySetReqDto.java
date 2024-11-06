package com.sxlinks.common.domain.dto.request.device;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 属性设置
  * @author baba
  * @version Id: InvokeReqDto.java, v 0.1 2020-12-29  Exp $$
  */
@Data
public class PropertySetReqDto {
    @NotNull(message = "设备编码不能为空")
    String     deviceCode;
    /**下发服务标识符 */
    @NotNull(message = "标识符不能为空")
    String     identifier;
    /**消息体**/
    JSONObject command;
    /**值**/
    String value;
    /**远程调用消息体*/
    Map<String, Object> remoteCommand;
    /**调用的时间*/
    Date   timestamp = new Date();
}

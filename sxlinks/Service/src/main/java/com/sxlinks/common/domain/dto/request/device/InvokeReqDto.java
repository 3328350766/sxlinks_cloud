package com.sxlinks.common.domain.dto.request.device;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: InvokeReqDto.java, v 0.1 2020-12-29  Exp $$
  */
@Data
public class InvokeReqDto {
    @NotNull(message = "设备编码不能为空")
    String     deviceCode;
    /**下发服务标识符 */
    @NotNull(message = "标识符不能为空")
    String     identifier;
    /**消息体*/
    JSONObject command;
    /**远程调用消息体*/
    Map<String, Object> remoteCommand;
    /**设备超时响应时间，默认3s*/
    Integer timeout = 3;
    String     dataType;
    @NotNull(message = "产品编码不能为空")
    String productCode;
    Date   timestamp = new Date();
}

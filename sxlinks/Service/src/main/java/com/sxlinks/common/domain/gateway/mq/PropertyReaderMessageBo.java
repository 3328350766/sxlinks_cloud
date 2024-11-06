package com.sxlinks.common.domain.gateway.mq;

import com.sxlinks.common.domain.dto.response.device.DevicePageResDto;
import com.sxlinks.common.domain.message.DeviceDownMessage;
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
public class PropertyReaderMessageBo {
    DevicePageResDto devicePageResDto;
    DeviceDownMessage deviceDownMessage;
}

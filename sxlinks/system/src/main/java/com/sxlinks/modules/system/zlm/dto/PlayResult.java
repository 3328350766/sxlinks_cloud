package com.sxlinks.modules.system.zlm.dto;

import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import lombok.Data;
import org.springframework.web.context.request.async.DeferredResult;

@Data
public class PlayResult {

    private DeferredResult<WVPResult<StreamInfo>> result;

    private String uuid;

    private VideoDevice device;
}

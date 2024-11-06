package com.sxlinks.common.domain.dto.request.upgrade;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * com.sxlinks.common.domain.dto.request.upgrade
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/12
 */
@Data
public class UpgradeReqDto implements Delayed {
    @NotNull(message = "固件ID空")
    Long firmId;
    List<String> deviceCode;
    @NotNull(message = "任务ID空")
    Long taskId;
    /**升级时间*/
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date upgradeTime;

    @Override
    public long getDelay(TimeUnit unit) {
        return upgradeTime.getTime() - System.currentTimeMillis();

    }

    @Override
    public int compareTo(Delayed o) {
        UpgradeReqDto upgradeReqDto = (UpgradeReqDto) o;
        long diff = this.upgradeTime.getTime() - upgradeReqDto.upgradeTime.getTime();
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }
}

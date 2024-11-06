package com.sxlinks.modules.system.service.dataCenter;

import com.sxlinks.common.domain.dto.PageReqDto;
import com.sxlinks.common.domain.dto.PageResDto;
import com.sxlinks.common.domain.dto.request.DevBatchAddReqDto;
import com.sxlinks.common.domain.dto.request.device.DevCreateReqDto;
import com.sxlinks.common.domain.dto.request.device.DeviceImportReqDto;
import com.sxlinks.common.domain.dto.request.device.DeviceRtItemReqDto;
import com.sxlinks.common.domain.dto.response.device.DeviceRtHistoryResDto;

import java.util.List;

/**
  * ByteCub.cn.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author bytecub@163.com  songbin
  * @version Id: IAdminDeviceService.java, v 0.1 2020-12-28  Exp $$
  */
public interface IDeviceDataService {
    /**
     * 搜索设备具体某个属性设置历史数据
     * @param searchPage PageReqDto
     * @return
     * */
    PageResDto<DeviceRtHistoryResDto> searchSetItem(PageReqDto<DeviceRtItemReqDto> searchPage);
    /**
     * 搜索设备具体某个属性上报历史数据
     * @param searchPage PageReqDto
     * @return
     * */
    PageResDto<DeviceRtHistoryResDto> searchReportItem(PageReqDto<DeviceRtItemReqDto> searchPage);

}

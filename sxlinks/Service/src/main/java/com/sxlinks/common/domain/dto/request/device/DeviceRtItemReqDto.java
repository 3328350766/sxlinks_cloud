package com.sxlinks.common.domain.dto.request.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sxlinks.plugin.es.domain.SearchItem;
import lombok.Data;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DeviceRtItemReqDto.java, v 0.1 2020-12-27  Exp $$
  */
@Data
public class DeviceRtItemReqDto {
    String productCode;
    String deviceCode;
    String funcType;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    Date   startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    Date   endDate;
    String identifier;
    String dataType;
    //2022-06-14新增
    List<SearchItem> searchItemList;    //搜索条件字段
}

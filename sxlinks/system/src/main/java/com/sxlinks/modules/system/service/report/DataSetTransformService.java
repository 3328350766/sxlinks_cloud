
package com.sxlinks.modules.system.service.report;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.report.DataSetTransform;
import com.sxlinks.modules.system.entity.report.DataSetTransformDto;

import java.util.List;

/**
* @desc DataSetTransform 数据集数据转换服务接口
* @author Raod
* @date 2021-03-18 12:13:15.591309400
**/
public interface DataSetTransformService extends IService<DataSetTransform> {

    List<JSONObject> transform(List<DataSetTransformDto> dataSetTransformDtoList, List<JSONObject> data);

}

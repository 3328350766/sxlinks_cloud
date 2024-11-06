package com.sxlinks.modules.system.service.report;

import com.alibaba.fastjson.JSONObject;
import com.sxlinks.modules.system.entity.report.DataSetTransformDto;

import java.util.List;

/**
 * Created by raodeming on 2021/3/23.
 */
public interface TransformStrategy {
    /**
     * 数据清洗转换 类型
     * @return
     */
    String type();

    /***
     * 清洗转换算法接口
     * @param def
     * @param data
     * @return
     */
    List<JSONObject> transform(DataSetTransformDto def, List<JSONObject> data);
}

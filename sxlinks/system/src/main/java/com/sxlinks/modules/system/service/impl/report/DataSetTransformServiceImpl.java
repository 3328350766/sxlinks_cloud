package com.sxlinks.modules.system.service.impl.report;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.system.entity.report.DataSetTransform;
import com.sxlinks.modules.system.entity.report.DataSetTransformDto;
import com.sxlinks.modules.system.mapper.report.DataSetTransformMapper;
import com.sxlinks.modules.system.service.report.DataSetTransformService;
import com.sxlinks.modules.system.service.report.TransformStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @desc DataSetTransform 数据集数据转换服务实现
* @author Raod
* @date 2021-03-18 12:13:15.591309400
**/
@Service
//@RequiredArgsConstructor
public class DataSetTransformServiceImpl extends ServiceImpl<DataSetTransformMapper, DataSetTransform> implements DataSetTransformService {

    private final Map<String, TransformStrategy> queryServiceImplMap = new HashMap<>();
    private ApplicationContext applicationContext;
//
//    @Autowired
//    private DataSetTransformMapper dataSetTransformMapper;
//
//
    public TransformStrategy getTarget(String type) {
        return queryServiceImplMap.get(type);
    }

//    @Override
    public void afterPropertiesSet() {
        Map<String, TransformStrategy> beanMap = applicationContext.getBeansOfType(TransformStrategy.class);
        //遍历该接口的所有实现，将其放入map中
        for (TransformStrategy serviceImpl : beanMap.values()) {
            queryServiceImplMap.put(serviceImpl.type(), serviceImpl);
        }
    }
//
//    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public List<JSONObject> transform(List<DataSetTransformDto> dataSetTransformDtoList, List<JSONObject> data) {
        if (dataSetTransformDtoList == null || dataSetTransformDtoList.size() <= 0) {
            return data;
        }

        for (DataSetTransformDto dataSetTransformDto : dataSetTransformDtoList) {
           data = getTarget(dataSetTransformDto.getTransformType()).transform(dataSetTransformDto, data);
        }
        return data;
    }
}

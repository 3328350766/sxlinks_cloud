package com.sxlinks.modules.system.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.report.DataSet;
import com.sxlinks.modules.system.entity.report.DataSetDto;
import com.sxlinks.modules.system.entity.report.OriginalDataDto;

import java.util.List;

/**
* @desc DataSet 数据集服务接口
* @author Raod
* @date 2021-03-18 12:11:31.150755900
**/
public interface DataSetService extends IService<DataSet> {

    /**
     * 单条详情
     * @param id
     * @return
     */
    DataSetDto detailSet(Long id);

    /**
     * 单条详情
     * @param setCode
     * @return
     */
    DataSetDto detailSet(String setCode);

    /**
     * 新增数据集、添加查询参数、数据转换
     * @param dto
     */
    DataSetDto insertSet(DataSetDto dto);

    /**
     * 更新数据集、添加查询参数、数据转换
     * @param dto
     */
    void updateSet(DataSetDto dto);

    /**
     * 删除数据集、添加查询参数、数据转换
     * @param id
     */
    void deleteSet(Long id);

    /**
     * 获取数据
     * @param dto
     * @return
     */
    OriginalDataDto getData(DataSetDto dto);

    /**
     *
     * @param dto
     * @return
     */
    OriginalDataDto testTransform(DataSetDto dto);

    /**
     * 获取所有数据集
     * @return
     */
    List<DataSet> queryAllDataSet();
}

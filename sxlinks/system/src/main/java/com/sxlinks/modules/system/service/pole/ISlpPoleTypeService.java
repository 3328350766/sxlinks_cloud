package com.sxlinks.modules.system.service.pole;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.pole.SlpPoleType;

import java.util.List;

/**
 * 灯杆类型Service接口
 *
 * @author wll
 * @date 2022-12-13
 */
public interface ISlpPoleTypeService extends IService<SlpPoleType> {
    /**
     * 查询灯杆类型
     *
     * @param poleTypeId 灯杆类型主键
     * @return 灯杆类型
     */
    public SlpPoleType selectSlpPoleTypeByPoleTypeId(Long poleTypeId);

    /**
     * 查询灯杆类型列表
     *
     * @param slpPoleType 灯杆类型
     * @return 灯杆类型集合
     */
    public List<SlpPoleType> selectSlpPoleTypeList(SlpPoleType slpPoleType);

    /**
     * 新增灯杆类型
     *
     * @param slpPoleType 灯杆类型
     * @return 结果
     */
    public int insertSlpPoleType(SlpPoleType slpPoleType);

    /**
     * 修改灯杆类型
     *
     * @param slpPoleType 灯杆类型
     * @return 结果
     */
    public int updateSlpPoleType(SlpPoleType slpPoleType);

    /**
     * 批量删除灯杆类型
     *
     * @param poleTypeIds 需要删除的灯杆类型主键集合
     * @return 结果
     */
    public int deleteSlpPoleTypeByPoleTypeIds(Long[] poleTypeIds);

    /**
     * 删除灯杆类型信息
     *
     * @param poleTypeId 灯杆类型主键
     * @return 结果
     */
    public int deleteSlpPoleTypeByPoleTypeId(Long poleTypeId);
}

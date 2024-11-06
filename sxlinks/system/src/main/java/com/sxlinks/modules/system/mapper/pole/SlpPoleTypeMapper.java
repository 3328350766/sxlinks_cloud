package com.sxlinks.modules.system.mapper.pole;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.pole.SlpPoleType;

import java.util.List;

/**
 * 灯杆类型Mapper接口
 *
 * @author wll
 * @date 2022-12-13
 */
public interface SlpPoleTypeMapper extends BaseMapper<SlpPoleType> {
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
     * 删除灯杆类型
     *
     * @param poleTypeId 灯杆类型主键
     * @return 结果
     */
    public int deleteSlpPoleTypeByPoleTypeId(Long poleTypeId);

    /**
     * 批量删除灯杆类型
     *
     * @param poleTypeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSlpPoleTypeByPoleTypeIds(Long[] poleTypeIds);
}

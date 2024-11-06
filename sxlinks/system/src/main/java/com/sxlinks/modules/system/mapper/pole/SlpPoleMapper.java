package com.sxlinks.modules.system.mapper.pole;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.pole.SlpPole;

import java.util.List;

/**
 * 灯杆信息Mapper接口
 *
 * @author wll
 * @date 2022-12-13
 */
public interface SlpPoleMapper extends BaseMapper<SlpPole> {
    /**
     * 查询灯杆信息
     *
     * @param poleId 灯杆信息主键
     * @return 灯杆信息
     */
    public SlpPole selectSlpPoleByPoleId(Long poleId);

    /**
     * 查询灯杆信息列表
     *
     * @param slpPole 灯杆信息
     * @return 灯杆信息集合
     */
    public List<SlpPole> selectSlpPoleList(SlpPole slpPole);

    /**
     * 新增灯杆信息
     *
     * @param slpPole 灯杆信息
     * @return 结果
     */
    public int insertSlpPole(SlpPole slpPole);

    /**
     * 修改灯杆信息
     *
     * @param slpPole 灯杆信息
     * @return 结果
     */
    public int updateSlpPole(SlpPole slpPole);

    /**
     * 删除灯杆信息
     *
     * @param poleId 灯杆信息主键
     * @return 结果
     */
    public int deleteSlpPoleByPoleId(Long poleId);

    /**
     * 批量删除灯杆信息
     *
     * @param poleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSlpPoleByPoleIds(Long[] poleIds);
}

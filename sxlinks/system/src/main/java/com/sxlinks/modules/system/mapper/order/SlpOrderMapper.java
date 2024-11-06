package com.sxlinks.modules.system.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.order.SlpOrder;

import java.util.Date;
import java.util.List;

/**
 * 工单Mapper接口
 *
 * @author ruoyi
 * @date 2022-11-14
 */
public interface SlpOrderMapper extends BaseMapper<SlpOrder> {
    /**
     * 查询工单
     *
     * @param orderId 工单主键
     * @return 工单
     */
    public SlpOrder selectSlpOrderByOrderId(Long orderId);

    /**
     * 查询工单列表
     *
     * @param slpOrder 工单
     * @return 工单集合
     */
    public List<SlpOrder> selectSlpOrderList(SlpOrder slpOrder);

    /**
     * 新增工单
     *
     * @param slpOrder 工单
     * @return 结果
     */
    public int insertSlpOrder(SlpOrder slpOrder);

    /**
     * 修改工单
     *
     * @param slpOrder 工单
     * @return 结果
     */
    public int updateSlpOrder(SlpOrder slpOrder);

    /**
     * 删除工单
     *
     * @param orderId 工单主键
     * @return 结果
     */
    public int deleteSlpOrderByOrderId(String orderId);

    /**
     * 批量删除工单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSlpOrderByOrderIds(Long[] orderIds);

    /**
     * 查询工单状态
     * @return 结果
     */
    List<SlpOrder> selectStateNameList();

    /**
     * 查询当前时间
     * @return 结果
     */
    Date selectTime();
}

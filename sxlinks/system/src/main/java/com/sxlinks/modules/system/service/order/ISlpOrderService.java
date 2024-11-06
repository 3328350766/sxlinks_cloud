package com.sxlinks.modules.system.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.order.SlpOrder;

import java.util.List;

/**
 * 工单Service接口
 *
 * @author ruoyi
 * @date 2022-11-14
 */
public interface ISlpOrderService extends IService<SlpOrder> {
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
    public boolean insertSlpOrder(SlpOrder slpOrder);

    /**
     * 修改工单
     *
     * @param slpOrder 工单
     * @return 结果
     */
    public boolean updateSlpOrder(SlpOrder slpOrder);

    /**
     * 修改工单
     *
     * @param slpOrder 工单
     * @return 结果
     */
    public boolean updateAll(SlpOrder slpOrder);

    /**
     * 批量删除工单
     *
     * @param orderIds 需要删除的工单主键集合
     * @return 结果
     */
    public boolean deleteSlpOrderByOrderIds(String[] orderIds);

    /**
     * 删除工单信息
     *
     * @param orderId 工单主键
     * @return 结果
     */
    public int deleteSlpOrderByOrderId(String orderId);

    /**
     * 查询状态名称
     *
     * @return 结果
     */
    List<SlpOrder> selectStateNameList();
}

package com.sxlinks.modules.system.service.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.constant.OrderStateType;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.system.entity.order.SlpOrder;
import com.sxlinks.modules.system.mapper.order.SlpOrderMapper;
import com.sxlinks.modules.system.service.order.ISlpOrderService;

import com.sxlinks.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.minidao.util.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 工单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-14
 */
@Service
public class SlpOrderServiceImpl extends ServiceImpl<SlpOrderMapper,SlpOrder> implements ISlpOrderService {
    /**
     * 工单mapper
     */
    @Resource
    private SlpOrderMapper slpOrderMapper;
    /**
     * 站内信mapper
     */
//    @Autowired
//    private IccMessageMapper iccMessageMapper;

    /**
     * 查询工单
     *
     * @param orderId 工单主键
     * @return 工单
     */
    @Override
    public SlpOrder selectSlpOrderByOrderId(Long orderId) {
        SlpOrder slpOrder = getById(orderId);
        slpOrder.setOrderManagementId(slpOrder.getOrderId() + "");
        return slpOrder;
    }

    /**
     * 查询工单列表
     *
     * @param slpOrder 工单
     * @return 工单
     */
    @Override
    public List<SlpOrder> selectSlpOrderList(SlpOrder slpOrder) {
        List<SlpOrder> slpOrders = slpOrderMapper.selectSlpOrderList(slpOrder);
        slpOrders.forEach(a -> a.setOrderManagementId(a.getOrderId() + ""));
        return slpOrders;
    }

    /**
     * 新增工单
     *
     * @param slpOrder 工单
     * @return 结果
     */
    @Override
    public boolean insertSlpOrder(SlpOrder slpOrder) {
        //雪花序列id
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        slpOrder.setOrderId(idWorker.nextId() + "");
        //如果没有状态值 说明第一次添加
        if (slpOrder.getOrderState() == null) {
            slpOrder.setOrderState(1);
        }
        //用户信息
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        slpOrder.setOrderCreator(loginUser.getUsername());
        slpOrder.setCreateBy(loginUser.getUsername());
        if (StringUtils.isEmpty(slpOrder.getIfOrderState())){
            slpOrder.setIfOrderState(OrderStateType.ORDER_STATE_SEND_SINGLE);
        }
        return save(slpOrder);
    }

    /**
     * 修改工单
     *
     * @param slpOrder 工单
     * @return 结果
     */
    @Override
    public boolean updateSlpOrder(SlpOrder slpOrder) {
        //用户信息
//        LoginUser loginUser = SecurityUtil.getLoginUser();

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SlpOrder order = new SlpOrder();
        order.setUpdateBy(loginUser.getUsername());
        order.setUpdateTime(new Date());
        order.setIfOrderState(slpOrder.getIfOrderState());
        //被派单人id
        order.setOrderDispatched(slpOrder.getOrderDispatched());
        //如果为派单 IfOrderState = "0"
        if (slpOrder.getIfOrderState().equals(OrderStateType.ORDER_STATE_SEND_SINGLE)) {
            //派单人
            order.setOrderDistributeLeaflets(loginUser.getUsername());
            order.setOrderSendTime(new Date());
            //派单站内信
//            message(order);
            order.setOrderState(2);
        }
        //如果为接单 IfOrderState = "1"
        if (slpOrder.getIfOrderState().equals(OrderStateType.ORDER_STATE_ORDER)) {
            order.setOrderHandler(loginUser.getUsername());
            order.setOrderAcceptTime(new Date());
            //接单站内信
//            message(order);
            order.setOrderState(3);
        }
        // 如果为解决工单 IfOrderState = "2"
        if (slpOrder.getIfOrderState().equals(OrderStateType.ORDER_STATE_TO_SOLVE)) {
            BeanUtils.copyProperties(slpOrder, order);
            order.setOrderAuditor(loginUser.getUsername());
            order.setOrderHandleTime(new Date());
            //解决工单站内信
//            message(order);
            order.setOrderState(0);
        }
        //如果为修改信息 IfOrderState = "3"
        if (slpOrder.getIfOrderState().equals(OrderStateType.ORDER_STATE_MODIFY)) {
            BeanUtils.copyProperties(slpOrder, order);
        }
        order.setOrderId(slpOrder.getOrderId());
        return updateById(order);
    }

    @Override
    public boolean updateAll(SlpOrder slpOrder) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        slpOrder.setUpdateBy(loginUser.getUsername());
        slpOrder.setUpdateTime(new Date());
        return updateById(slpOrder);
    }

    /**
     * 批量删除工单
     *
     * @param orderIds 需要删除的工单主键
     * @return 结果
     */
    @Override
    public boolean deleteSlpOrderByOrderIds(String[] orderIds) {
        Long[] str2 = new Long[orderIds.length];
        for (int i = 0; i < orderIds.length; i++) {
            str2[i] = Long.valueOf(orderIds[i]);
        }
        List<String> ids = Arrays.asList(orderIds);
        return removeByIds(ids);
    }

    /**
     * 删除工单信息
     *
     * @param orderId 工单主键
     * @return 结果
     */
    @Override
    public int deleteSlpOrderByOrderId(String orderId) {
        return slpOrderMapper.deleteSlpOrderByOrderId(orderId);
    }

    @Override
    public List<SlpOrder> selectStateNameList() {
        return slpOrderMapper.selectStateNameList();
    }

    /**
     * 站内信处理
     *
     * @param order 入参 工单id 判断条件 用户id
     * @return 结果
     */
//    public int message(SlpOrder order) {
//        IccMessage iccMessage = new IccMessage();
//        //雪花序列id
//        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//        iccMessage.setMessageId(idWorker.nextId());
//        //用户信息
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        Long userid = loginUser.getUserid();
//        // 如果为派单
//        if (order.getIfOrderState().equals(OrderStateType.ORDER_STATE_SEND_SINGLE)) {
//            Long userId = Long.valueOf(order.getOrderDispatched());
//            iccMessage.setUserId(userId);
//            iccMessage.setMessageContent("尊敬的用户您好，您有一条单号为“ " + order.getOrderId() + " “的派单消息，请及时接单!");
//        } // 接单
//        else if (order.getIfOrderState().equals(OrderStateType.ORDER_STATE_ORDER)) {
//            iccMessage.setMessageContent("尊敬的用户您好，您有一条单号为“ " + order.getOrderId() + " “的接单消息，请及时解决!");
//            iccMessage.setUserId(userid);
//        } // 解决工单
//        else {
//            iccMessage.setMessageContent("尊敬的用户您好，您已解决单号为“ " + order.getOrderId() + " “的工单消息！");
//            iccMessage.setUserId(userid);
//        }
//        return iccMessageMapper.insertIccMessage(iccMessage);
//    }

}

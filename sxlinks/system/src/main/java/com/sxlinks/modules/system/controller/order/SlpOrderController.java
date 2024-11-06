package com.sxlinks.modules.system.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.order.SlpOrder;
import com.sxlinks.modules.system.entity.productCenter.Project;
import com.sxlinks.modules.system.service.order.ISlpOrderService;
import com.sxlinks.modules.system.service.productCenter.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 工单Controller
 *
 * @author ruoyi
 * @date 2022-11-14
 */
@RestController
@RequestMapping("/orderManagement")
@Api(value = "工单管理", tags = {"工单管理"})
public class SlpOrderController {

    @Autowired
    private ISlpOrderService slpOrderService;

    @Autowired
    private IProjectService iProjectService;

    /**
     * 查询工单列表
     */
    @ApiOperation(value = "工单列表")
    @GetMapping("/list")
    public Result<IPage<SlpOrder>> list(
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,SlpOrder slpOrder,
            HttpServletRequest req) {
        Result<IPage<SlpOrder>> result = new Result<>();
        QueryWrapper<SlpOrder> queryWrapper = QueryGenerator.initQueryWrapper(slpOrder, req.getParameterMap());
        Page<SlpOrder> page = new Page<>(pageNo, pageSize);
        IPage<SlpOrder> pageList = slpOrderService.page(page, queryWrapper);
        transOrder(pageList.getRecords());
        for (SlpOrder record : pageList.getRecords()) {
            transOrder(record);
        }
        result.setSuccess(true);
        result.setResult(pageList);

        return result;
    }

    @GetMapping("/stateNameList")
    public List<SlpOrder> selectStateNameList() {
        return slpOrderService.selectStateNameList();
    }

    /**
     * 导出工单列表
     */
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SlpOrder slpOrder) {
//        List<SlpOrder> list = slpOrderService.selectSlpOrderList(slpOrder);
//        ExcelUtil<SlpOrder> util = new ExcelUtil<>(SlpOrder. class);
//        util.exportExcel(response, list, "工单数据");
//    }

    /**
     * 获取工单详细信息
     */
    @ApiOperation(value = "工单详细")
    @GetMapping(value = "/query/{orderId}")
    public Result getInfo(@PathVariable("orderId") Long orderId) {
        SlpOrder slpOrder = slpOrderService.selectSlpOrderByOrderId(orderId);
        transOrder(slpOrder);
        return Result.OK(slpOrder);
    }

    /**
     * 新增工单
     */
    // @Log(title = "工单", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增工单")
    @PostMapping("/add")
    public Result add(@RequestBody SlpOrder slpOrder) {
        slpOrderService.insertSlpOrder(slpOrder);
        return Result.OK();
    }

    /**
     * 修改工单
     */
    // @Log(title = "工单", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "派单,接单,解决,修改工单")
    @PutMapping("/edit")
    public Result edit(@RequestBody SlpOrder slpOrder) {
        if (StringUtils.isBlank(slpOrder.getIfOrderState())) {
            return Result.error("ifOrderState字段必传:派单(0),接单(1),解决(2),修改(3)");
        }
        slpOrderService.updateSlpOrder(slpOrder);
        return Result.OK();
    }

    /**
     * 删除工单
     */
    // @Log(title = "工单", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除工单")
    @DeleteMapping("/remove/{orderIds}")
    public Result remove(@PathVariable String[] orderIds) {
        slpOrderService.deleteSlpOrderByOrderIds(orderIds);
        return Result.OK();
    }

    private void transOrder(SlpOrder order) {
        if (Objects.nonNull(order.getOrderState())) {
            switch (order.getOrderState()) {
                case 0:
                    order.setStateName("完成");
                    break;
                case 1:
                    order.setStateName("下单");
                    break;
                case 2:
                    order.setStateName("已派单");
                    break;
                default:
                    order.setStateName("已接单");
            }
        }

        if (Objects.nonNull(order.getOrderInvoice())) {
            switch (order.getOrderInvoice()) {
                case 0:
                    order.setInvoice("未开票");
                    break;
                case 1:
                    order.setInvoice("已开票");
                    break;
                default:
                    order.setStateName("");
            }
        }
    }

    // 查询项目名称
    private void transOrder(List<SlpOrder> list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            List<Long> ids = list.stream().map(SlpOrder::getOrderProject).distinct().collect(Collectors.toList());
            List<Project> projectList = iProjectService.listByIds(ids);
            Map<Long,String> idNameMap = projectList.stream().collect(Collectors.toMap(Project::getId,Project::getName));
            for (SlpOrder slpOrder : list) {
                slpOrder.setProjectName(idNameMap.get(slpOrder.getOrderProject()));
            }
        }
    }
}

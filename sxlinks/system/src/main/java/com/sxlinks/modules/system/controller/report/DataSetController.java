package com.sxlinks.modules.system.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.controller.report.bean.ResponseBean;
import com.sxlinks.modules.system.controller.report.holder.UserContentHolder;
import com.sxlinks.modules.system.controller.report.param.DataSetTestTransformParam;
import com.sxlinks.modules.system.entity.report.DataSet;
import com.sxlinks.modules.system.entity.report.DataSetDto;
import com.sxlinks.modules.system.entity.report.DataSource;
import com.sxlinks.modules.system.entity.report.OriginalDataDto;
import com.sxlinks.modules.system.service.report.DataSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO
 *
 * @author chenkening
 * @date 2021/3/26 10:19
 */
@RestController
@Api(tags = "数据集管理")
@RequestMapping("/dataSet")
public class DataSetController {

    @Autowired
    private DataSetService dataSetService;
    @Resource
    private BaseCommonService baseCommonService;

    @ApiOperation(value = "获取数据集分页列表", tags = "数据集管理")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<DataSet>> queryPageList(DataSet configure,
                                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                  HttpServletRequest req) {
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Result<IPage<DataSet>> result = new Result<IPage<DataSet>>();
        QueryWrapper<DataSet> queryWrapper = QueryGenerator.initQueryWrapper(configure, req.getParameterMap());
        queryWrapper.eq("enable_flag","1");//启用标志
        queryWrapper.eq("delete_flag","0");//删除标志
        queryWrapper.orderByDesc("create_time");
        //非管理员情况下，只能查看当前用户数据
        if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
        Page<DataSet> page = new Page<DataSet>(pageNo, pageSize);
        IPage<DataSet> pageList = dataSetService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        //日志记录
        baseCommonService.addLog("用户名: " + user.getUsername() + ",获取数据集数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

        return result;
    }
    
    @GetMapping("/detailBysetId/{id}")
    @ApiOperation(value = "明细")
    public ResponseBean detailBysetId(@PathVariable("id") Long id) {
        DataSetDto dataSetDto = dataSetService.detailSet(id);
        ResponseBean responseBean = ResponseBean.builder().data(dataSetDto).message("成功").build();
        return responseBean;
    }

    @GetMapping({"/detailBysetCode/{setCode}"})
    @ApiOperation(value = "")
    public ResponseBean detailBysetCode(@PathVariable("setCode") String setCode) {
        DataSetDto dataSetDto = dataSetService.detailSet(setCode);
        return ResponseBean.builder().data(dataSetDto).message("成功").build();
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增")
    public ResponseBean insert(@RequestBody DataSetDto dto) {
        DataSetDto dataSetDto = dataSetService.insertSet(dto);
        return ResponseBean.builder().data(dataSetDto).build();
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "修改")
    public ResponseBean update(@RequestBody DataSetDto dto) {
        String username = UserContentHolder.getContext().getUsername();
        dataSetService.updateSet(dto);
        return ResponseBean.builder().build();
    }

    /**
     *   通过id删除
     * @param id
     * @return
     */
    //@RequiresRoles({"admin"})
    @ApiOperation(value = "删除数据集", tags = "数据集管理")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        DataSet c=dataSetService.getById(id);
        c.setEnableFlag(0);
        c.setDeleteFlag(1);
        dataSetService.updateById(c); //软删除
        return Result.ok("删除数据集成功");
    }
//
//    @DeleteMapping({"/{id}"})
//    @ApiOperation(value = "删除")
//    public ResponseBean deleteById(@PathVariable("id") Long id) {
//        this.logger.info("{}删除服务开始，参数ID：{}", this.getClass().getSimpleName(), id);
//        dataSetService.deleteSet(id);
//        this.logger.info("{}删除服务结束", this.getClass().getSimpleName());
//        return this.responseSuccess();
//    }
//
    /**
     * 测试 数据转换是否正确
     * @param param
     * @return
     */
    @PostMapping("/testTransform")
    @ApiOperation(value = "明细")
    public ResponseBean testTransform(@Validated @RequestBody DataSetTestTransformParam param) {
        DataSetDto dto = new DataSetDto();
        BeanUtils.copyProperties(param, dto);

        return ResponseBean.builder().data(dataSetService.testTransform(dto)).build();
    }

    /**
     * 获取所有数据集
     * @return
     */
    @GetMapping("/queryAllDataSet")
    @ApiOperation(value = "获取所有数据集")
    public ResponseBean queryAllDataSet() {
        List<DataSet> list = dataSetService.queryAllDataSet();
        return ResponseBean.builder().data(list).build();
    }
}

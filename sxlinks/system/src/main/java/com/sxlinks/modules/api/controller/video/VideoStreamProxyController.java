package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoStreamProxy;
import com.sxlinks.modules.system.service.productCenter.video.IVideoStreamProxyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频流代理Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频流代理管理")
@RestController
@RequestMapping("/video/streamProxy")
public class VideoStreamProxyController {

    @Autowired
    private IVideoStreamProxyService iVideoStreamProxyService;

    /**
     * 查询视频流代理信息列表
     */
    @ApiOperation(value = "查询视频流代理信息列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoStreamProxy proxy,
                       HttpServletRequest req) {
        Result<IPage<VideoStreamProxy>> result = new Result<>();
        QueryWrapper<VideoStreamProxy> queryWrapper = QueryGenerator.initQueryWrapper(proxy, req.getParameterMap());
        Page<VideoStreamProxy> page = new Page<>(pageNo, pageSize);
        IPage<VideoStreamProxy> pageList = iVideoStreamProxyService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频流代理信息详细信息
     */
    @ApiOperation(value = "获取视频流代理信息详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") Long deviceId) {
        VideoStreamProxy VideoStreamProxy = iVideoStreamProxyService.getById(deviceId);
        return Result.OK(VideoStreamProxy);
    }

    /**
     * 新增视频流代理信息
     */
    @ApiOperation(value = "新增视频流代理信息")
    @PostMapping("/add")
    public Result add(@RequestBody VideoStreamProxy proxy) {
        iVideoStreamProxyService.saveOrUpdate(proxy);
        return Result.OK();
    }

    /**
     * 修改视频流代理信息
     */
    @ApiOperation(value = "修改视频流代理信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoStreamProxy proxy) {
        iVideoStreamProxyService.saveOrUpdate(proxy);
        return Result.OK();
    }

    /**
     * 删除视频流代理信息
     */
    @ApiOperation(value = "删除视频流代理信息")
    @DeleteMapping("/remove/{deviceIds}")
    public Result remove(@PathVariable Long[] deviceIds) {
        iVideoStreamProxyService.removeById(deviceIds);
        return Result.OK();
    }
}

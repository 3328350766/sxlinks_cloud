package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoStreamPush;
import com.sxlinks.modules.system.service.productCenter.video.IVideoStreamPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频流推送通道Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频流推送通道管理")
@RestController
@RequestMapping("/video/streamPush")
public class VideoStreamPushChannelController {

    @Autowired
    private IVideoStreamPushService iVideoStreamPushService;

    /**
     * 查询视频流推送通道信息列表
     */
    @ApiOperation(value = "查询视频流推送通道信息列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoStreamPush push,
                       HttpServletRequest req) {
        Result<IPage<VideoStreamPush>> result = new Result<>();
        QueryWrapper<VideoStreamPush> queryWrapper = QueryGenerator.initQueryWrapper(push, req.getParameterMap());
        Page<VideoStreamPush> page = new Page<>(pageNo, pageSize);
        IPage<VideoStreamPush> pageList = iVideoStreamPushService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频流推送通道信息详细信息
     */
    @ApiOperation(value = "获取视频流推送通道信息详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") Long deviceId) {
        VideoStreamPush push = iVideoStreamPushService.getById(deviceId);
        return Result.OK(push);
    }

    /**
     * 新增视频流推送通道信息
     */
    @ApiOperation(value = "新增视频流推送通道信息")
    @PostMapping("/add")
    public Result add(@RequestBody VideoStreamPush push) {
        iVideoStreamPushService.saveOrUpdate(push);
        return Result.OK();
    }

    /**
     * 修改视频流推送通道信息
     */
    @ApiOperation(value = "修改视频流推送通道信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoStreamPush push) {
        iVideoStreamPushService.saveOrUpdate(push);
        return Result.OK();
    }

    /**
     * 删除视频流推送通道信息
     */
    @ApiOperation(value = "删除视频流推送通道信息")
    @DeleteMapping("/remove/{deviceIds}")
    public Result remove(@PathVariable Long[] deviceIds) {
        iVideoStreamPushService.removeById(deviceIds);
        return Result.OK();
    }
}

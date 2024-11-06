package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoParentPlatform;
import com.sxlinks.modules.system.service.productCenter.video.IVideoParentPlatformService;
import com.sxlinks.modules.system.service.productCenter.video.IVideoParentPlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频平台Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频平台管理")
@RestController
@RequestMapping("/video/platform")
public class VideoParentPlatformController {

    @Autowired
    private IVideoParentPlatformService iVideoParentPlatformService;

    /**
     * 查询视频平台信息列表
     */
    @ApiOperation(value = "查询视频平台信息列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoParentPlatform platform,
                       HttpServletRequest req) {
        Result<IPage<VideoParentPlatform>> result = new Result<>();
        QueryWrapper<VideoParentPlatform> queryWrapper = QueryGenerator.initQueryWrapper(platform, req.getParameterMap());
        Page<VideoParentPlatform> page = new Page<>(pageNo, pageSize);
        IPage<VideoParentPlatform> pageList = iVideoParentPlatformService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频平台信息详细信息
     */
    @ApiOperation(value = "获取视频平台信息详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") Long deviceId) {
        VideoParentPlatform VideoParentPlatform = iVideoParentPlatformService.getById(deviceId);
        return Result.OK(VideoParentPlatform);
    }

    /**
     * 新增视频平台信息
     */
    @ApiOperation(value = "新增视频平台信息")
    @PostMapping("/add")
    public Result add(@RequestBody VideoParentPlatform platform) {
        iVideoParentPlatformService.saveOrUpdate(platform);
        return Result.OK();
    }

    /**
     * 修改视频平台信息
     */
    @ApiOperation(value = "修改视频平台信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoParentPlatform platform) {
        iVideoParentPlatformService.saveOrUpdate(platform);
        return Result.OK();
    }

    /**
     * 删除视频平台信息
     */
    @ApiOperation(value = "删除视频平台信息")
    @DeleteMapping("/remove/{deviceIds}")
    public Result remove(@PathVariable Long[] deviceIds) {
        iVideoParentPlatformService.removeById(deviceIds);
        return Result.OK();
    }
}

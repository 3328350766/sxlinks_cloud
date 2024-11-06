package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDeviceMobilePosition;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceMobilePositionService;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceMobilePositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务器Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频设备移动位置管理")
@RestController
@RequestMapping("/video/mobile")
public class VideoDeviceMobilePositionController {

    @Autowired
    private IVideoDeviceMobilePositionService iVideoDeviceMobilePositionService;

    /**
     * 查询视频设备移动位置列表
     */
    @ApiOperation(value = "查询视频设备移动位置列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoDeviceMobilePosition position,
                       HttpServletRequest req) {
        Result<IPage<VideoDeviceMobilePosition>> result = new Result<>();
        QueryWrapper<VideoDeviceMobilePosition> queryWrapper = QueryGenerator.initQueryWrapper(position, req.getParameterMap());
        Page<VideoDeviceMobilePosition> page = new Page<>(pageNo, pageSize);
        IPage<VideoDeviceMobilePosition> pageList = iVideoDeviceMobilePositionService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频设备移动位置详细信息
     */
    @ApiOperation(value = "获取视频设备移动位置详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") Long deviceId) {
        VideoDeviceMobilePosition position = iVideoDeviceMobilePositionService.getById(deviceId);
        return Result.OK(position);
    }

    /**
     * 新增视频设备移动位置
     */
    @ApiOperation(value = "新增视频设备移动位置")
    @PostMapping("/add")
    public Result add(@RequestBody VideoDeviceMobilePosition position) {
        iVideoDeviceMobilePositionService.saveOrUpdate(position);
        return Result.OK();
    }

    /**
     * 修改视频设备移动位置
     */
    @ApiOperation(value = "修改视频设备移动位置")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoDeviceMobilePosition position) {
        iVideoDeviceMobilePositionService.saveOrUpdate(position);
        return Result.OK();
    }

    /**
     * 删除视频设备移动位置
     */
    @ApiOperation(value = "删除视频设备移动位置")
    @DeleteMapping("/remove/{deviceIds}")
    public Result remove(@PathVariable Long[] deviceIds) {
        iVideoDeviceMobilePositionService.removeById(deviceIds);
        return Result.OK();
    }
}

package com.sxlinks.modules.api.controller.miniProgram.productCenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频设备Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频设备")
@RestController
@RequestMapping("/miniProgram/video/device")
public class MiniVideoDeviceController {

    @Autowired
    private IVideoDeviceService iVideoDeviceService;

    /**
     * 查询视频设备信息列表
     */
    @ApiOperation(value = "查询视频设备信息列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoDevice videoDevice,
                       HttpServletRequest req) {
        Result<IPage<VideoDevice>> result = new Result<>();
        QueryWrapper<VideoDevice> queryWrapper = QueryGenerator.initQueryWrapper(videoDevice, req.getParameterMap());
        Page<VideoDevice> page = new Page<>(pageNo, pageSize);
        IPage<VideoDevice> pageList = iVideoDeviceService.customPage(page);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频设备信息详细信息
     */
    @ApiOperation(value = "获取视频设备信息详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") String deviceId) {
        VideoDevice VideoDevice = iVideoDeviceService.getDeviceByDeviceId(deviceId);
        return Result.OK(VideoDevice);
    }

    /**
     * 新增视频设备信息
     */
    @ApiOperation(value = "新增视频设备信息")
    @PostMapping("/add")
    public Result add(@RequestBody VideoDevice device) {
        iVideoDeviceService.add(device);
        return Result.OK();
    }

    /**
     * 修改视频设备信息
     */
    @ApiOperation(value = "修改视频设备信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoDevice device) {
        iVideoDeviceService.update(device);
        return Result.OK();
    }

    /**
     * 删除视频设备信息
     */
    @ApiOperation(value = "删除视频设备信息")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        iVideoDeviceService.del(id);
        return Result.OK();
    }
}

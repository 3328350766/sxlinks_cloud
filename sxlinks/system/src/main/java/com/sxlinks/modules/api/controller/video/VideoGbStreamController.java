package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoGbStream;
import com.sxlinks.modules.system.service.productCenter.video.IVideoGbStreamService;
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
@Api(tags = "视频流管理")
@RestController
@RequestMapping("/video/gb")
public class VideoGbStreamController {

    @Autowired
    private IVideoGbStreamService iVideoGbStreamService;

    /**
     * 查询视频流列表
     */
    @ApiOperation(value = "查询视频流列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoGbStream stream,
                       HttpServletRequest req) {
        Result<IPage<VideoGbStream>> result = new Result<>();
        QueryWrapper<VideoGbStream> queryWrapper = QueryGenerator.initQueryWrapper(stream, req.getParameterMap());
        Page<VideoGbStream> page = new Page<>(pageNo, pageSize);
        IPage<VideoGbStream> pageList = iVideoGbStreamService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频流详细信息
     */
    @ApiOperation(value = "获取视频流详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") Long deviceId) {
        VideoGbStream stream = iVideoGbStreamService.getById(deviceId);
        return Result.OK(stream);
    }

    /**
     * 新增视频流
     */
    @ApiOperation(value = "新增视频流")
    @PostMapping("/add")
    public Result add(@RequestBody VideoGbStream stream) {
        iVideoGbStreamService.saveOrUpdate(stream);
        return Result.OK();
    }

    /**
     * 修改视频流
     */
    @ApiOperation(value = "修改视频流")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoGbStream stream) {
        iVideoGbStreamService.saveOrUpdate(stream);
        return Result.OK();
    }

    /**
     * 删除视频流
     */
    @ApiOperation(value = "删除视频流")
    @DeleteMapping("/remove/{deviceIds}")
    public Result remove(@PathVariable Long[] deviceIds) {
        iVideoGbStreamService.removeById(deviceIds);
        return Result.OK();
    }
}

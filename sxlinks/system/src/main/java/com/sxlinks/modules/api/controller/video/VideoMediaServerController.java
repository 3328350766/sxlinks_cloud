package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import com.sxlinks.modules.system.service.productCenter.video.IVideoMediaServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频服务器Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频服务器管理")
@RestController
@RequestMapping("/video/server")
public class VideoMediaServerController {

    @Autowired
    private IVideoMediaServerService iVideoMediaServerService;

    /**
     * 查询视频服务器信息列表
     */
    @ApiOperation(value = "查询视频服务器信息列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoMediaServer mediaServer,
                       HttpServletRequest req) {
        Result<IPage<VideoMediaServer>> result = new Result<>();
        QueryWrapper<VideoMediaServer> queryWrapper = QueryGenerator.initQueryWrapper(mediaServer, req.getParameterMap());
        Page<VideoMediaServer> page = new Page<>(pageNo, pageSize);
        IPage<VideoMediaServer> pageList = iVideoMediaServerService.custom(page);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取视频服务器信息详细信息
     */
    @ApiOperation(value = "获取视频服务器信息详细信息")
    @GetMapping(value = "/query/{id}")
    public Result getInfo(@PathVariable("id") String id) {
        VideoMediaServer mediaServer = iVideoMediaServerService.queryOne(id);
        return Result.OK(mediaServer);
    }

    /**
     * 新增视频服务器信息
     */
    @ApiOperation(value = "新增视频服务器信息")
    @PostMapping("/add")
    public Result add(@RequestBody VideoMediaServer mediaServer) {
        iVideoMediaServerService.add(mediaServer);
        return Result.OK();
    }

    /**
     * 修改视频服务器信息
     */
    @ApiOperation(value = "修改视频服务器信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoMediaServer mediaServer) {
        iVideoMediaServerService.update(mediaServer);
        return Result.OK();
    }

    /**
     * 删除视频服务器信息
     */
    @ApiOperation(value = "删除视频服务器信息")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        iVideoMediaServerService.del(id);
        return Result.OK();
    }
}

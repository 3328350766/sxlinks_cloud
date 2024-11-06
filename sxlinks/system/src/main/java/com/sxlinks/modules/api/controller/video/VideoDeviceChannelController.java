package com.sxlinks.modules.api.controller.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.base.BaseTree;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDeviceChannel;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "视频通道管理")
@RestController
@RequestMapping("/video/channel")
public class VideoDeviceChannelController {

    @Autowired
    private IVideoDeviceChannelService iVideoDeviceChannelService;

    /**
     * 查询视频通道列表
     */
    @ApiOperation(value = "查询视频通道列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       VideoDeviceChannel channel,
                       HttpServletRequest req) {
        Result<IPage<VideoDeviceChannel>> result = new Result<>();
        QueryWrapper<VideoDeviceChannel> queryWrapper = QueryGenerator.initQueryWrapper(channel, req.getParameterMap());
        Page<VideoDeviceChannel> page = new Page<>(pageNo, pageSize);
        IPage<VideoDeviceChannel> pageList = iVideoDeviceChannelService.custom(page,channel.getDeviceId());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 查询国标树
     * @param deviceId 设备ID
     * @param parentId 父ID
     * @param page 当前页
     * @param count 每页条数
     * @return 国标设备
     */
    @ApiOperation(value = "通道分页树")
    @GetMapping("/tree/{deviceId}")
    public Result<IPage<BaseTree<VideoDeviceChannel>>> getTree(@PathVariable String deviceId,
                                           @RequestParam(required = false) String parentId,
                                           @RequestParam(required = false) Boolean onlyCatalog,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer count){
        if (onlyCatalog == null) {
            onlyCatalog = false;
        }

        List<BaseTree<VideoDeviceChannel>> treeData = iVideoDeviceChannelService.queryVideoDeviceTree(deviceId, parentId, onlyCatalog);
        if (treeData == null || (page - 1) * count > treeData.size()) {
            IPage<BaseTree<VideoDeviceChannel>> pageInfo = new Page<>();
            pageInfo.setCurrent(page);
            pageInfo.setTotal(treeData == null? 0 : treeData.size());
            pageInfo.setSize(count);
            pageInfo.setRecords(new ArrayList<>());
            return Result.OK(pageInfo);
        }

        int toIndex = Math.min(page * count, treeData.size());
        // 处理分页
        List<BaseTree<VideoDeviceChannel>> trees = treeData.subList((page - 1) * count, toIndex);
        IPage<BaseTree<VideoDeviceChannel>> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setTotal(treeData.size());
        pageInfo.setSize(count);
        pageInfo.setRecords(trees);

        return Result.OK(pageInfo);
    }

    /**
     * 获取视频通道详细信息
     */
    @ApiOperation(value = "获取视频通道详细信息")
    @GetMapping(value = "/query/{deviceId}")
    public Result getInfo(@PathVariable("deviceId") Long deviceId) {
        VideoDeviceChannel channel = iVideoDeviceChannelService.getById(deviceId);
        return Result.OK(channel);
    }

    /**
     * 新增视频通道
     */
    @ApiOperation(value = "新增视频通道")
    @PostMapping("/add")
    public Result add(@RequestBody VideoDeviceChannel channel) {
        iVideoDeviceChannelService.add(channel);
        return Result.OK();
    }

    /**
     * 修改视频通道
     */
    @ApiOperation(value = "修改视频通道")
    @PutMapping("/edit")
    public Result edit(@RequestBody VideoDeviceChannel channel) {
        iVideoDeviceChannelService.update(channel);
        return Result.OK();
    }
}

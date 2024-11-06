package com.sxlinks.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.MinioUtil;
import com.sxlinks.modules.system.entity.SysFile;
import com.sxlinks.modules.system.service.ISysFileService;
import com.sxlinks.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@Api(tags = "pc-文件管理")
@RequestMapping("/file")
@RestController
public class SysFileController {

    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;

    /**
     * 文件上传请求
     */
    @ApiOperation(value = "附件上传")
    @PostMapping("upload")
    public Result upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = MinioUtil.upload(file,"");
            SysFile sysFile = new SysFile();
            sysFile.setFileName(FileUtils.getFileName(file.getOriginalFilename()));
            sysFile.setExtendType(FileUtils.getExtendType(file.getOriginalFilename()));
            // 塞入文件大小
            sysFile.setFileSize(file.getSize() / 1000);
            sysFile.setFileUrl(url);
            return Result.OK(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return Result.error("上传文件失败,请联系管理员");
    }

    /**
     * 新增附件
     */
    @ApiOperation(value = "新增附件")
    @PostMapping("/add")
    public Result add(@RequestBody SysFile file) {
        if (StringUtils.isBlank(file.getFileType())) {
            return Result.error("文件类型必传");
        }
        if (StringUtils.isBlank(file.getFileUrl())) {
            return Result.error("文件地址必传");
        }
        if (StringUtils.isBlank(file.getFileName())) {
            return Result.error("文件名称必传");
        }
        if (Objects.isNull(file.getFileSize())) {
            return Result.error("文件大小必传");
        }
        if (StringUtils.isBlank(file.getExtendType())) {
            // 获取拓展名
            file.setExtendType(FileUtils.getExtendType(file.getFileUrl()));
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        file.setCreateBy(loginUser.getUsername());
        file.setCreateTime(new Date());
        sysFileService.save(file);
        return Result.OK(file);
    }

    /**
     * 附件列表
     */
    @ApiOperation(value = "附件列表")
    @GetMapping("/list")
    public Result<IPage<SysFile>> list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                       @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize,
                                       SysFile sysfile,
                                       HttpServletRequest req) {
        QueryWrapper<SysFile> queryWrapper = QueryGenerator.initQueryWrapper(sysfile, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
        IPage<SysFile> page = sysFileService.page(new Page<>(pageNo,pageSize),queryWrapper);
        for (SysFile record : page.getRecords()) {
            trans(record);
        }
        return Result.OK(page);
    }

    /**
     * @功能：删除文件
     * @param id
     * @return
     */
    @ApiOperation(value = "删除文件")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestParam(name="id") String id) {
        SysFile file = sysFileService.getById(id);
        if (Objects.nonNull(file) && StringUtils.isNotBlank(file.getFileUrl())){
            MinioUtil.removeObject(null,file.getFileUrl().substring(file.getFileUrl().lastIndexOf("/")));
        }
        Boolean ok = sysFileService.removeById(id);
        if(ok) {
            return Result.OK();
        }else{
            return Result.error("操作失败！");
        }
    }

    /**
     * 修改附件
     */
    @ApiOperation(value = "修改附件")
    @PutMapping("/edit")
    public Result edit(@RequestBody SysFile file) {
        if (StringUtils.isBlank(file.getId())) {
            return Result.error("id必传");
        }
        sysFileService.updateById(file);
        return Result.OK();
    }

    private void trans(SysFile sysFile) {
        if (StringUtils.isNotBlank(sysFile.getFileType())){
            switch (sysFile.getFileType()){
                case "1":
                    sysFile.setFileTypeName("图片");
                    break;
                case "2":
                    sysFile.setFileTypeName("视频");
                    break;
                case "3":
                    sysFile.setFileTypeName("音频");
                    break;
                case "4":
                    sysFile.setFileTypeName("其他");
                    break;
            }
        }
    }
}
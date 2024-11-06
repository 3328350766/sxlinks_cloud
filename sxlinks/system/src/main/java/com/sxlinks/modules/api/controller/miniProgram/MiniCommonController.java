package com.sxlinks.modules.api.controller.miniProgram;

import com.sxlinks.common.exception.MiniException;
import com.sxlinks.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.imports.base.ImportFileServiceI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author gaoliang
 * @since 2018-12-17
 */
@RestController
@RequestMapping(value = "/miniProgram/common", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "小程序公共接口", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Validated
public class MiniCommonController {

    @Value(value = "${jeecg.path.upload}")
    private String uploadPath;

    @Value(value = "${jeecg.uploadType}")
    private String uploadType;

    @Resource
    private ImportFileServiceI importFileService;

    private static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    @ApiOperation("文件上传")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@ApiParam(value = "文件", required = true) @RequestPart(value = "file") @NotNull(message = "文件不能为空") MultipartFile file) {
        try {
            return CommonUtils.uploadOnlineImage(file.getBytes(), uploadPath, "", uploadType);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage());
        }
        throw new MiniException(500, "文件上传失败");
    }

    @ApiOperation("预览图片")
    @GetMapping(value = "/static/**")
    public void view(HttpServletRequest request, HttpServletResponse response) {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String imgPath = extractPathFromPattern(request);
        if (StringUtils.isBlank(imgPath)) {
            return;
        }
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imgPath = imgPath.replace("..", "");
            if (imgPath.endsWith(",")) {
                imgPath = imgPath.substring(0, imgPath.length() - 1);
            }
            String filePath = uploadPath + File.separator + imgPath;
            File file = new File(filePath);
            if (!file.exists()) {
                response.setStatus(404);
                throw new RuntimeException("文件[" + imgPath + "]不存在..");
            }
//            response.setContentType("application/force-download");// 设置强制下载不打开
//            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(filePath)));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (IOException e) {
            log.error("预览文件失败" + e.getMessage());
            response.setStatus(404);
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
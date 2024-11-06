package com.sxlinks.common.biz;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sxlinks.common.enums.BCErrorEnum;
import com.sxlinks.common.exception.BCGException;
import com.sxlinks.utils.OSSClientUntil;
import com.sxlinks.utils.file.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * com.sxlinks.common.biz
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/15
 */
@Service
@Slf4j
public class FileUploadBiz {
    private final String MODE_OSS = "oss";
    private final String MODE_LOCAL = "local";

    @Value("${bytecub.upload.mode:local}")
    private String uploadMode;


    @Value("${bytecub.profile:null}")
    private String filePath;
    @Value("${bytecub.profile.url:null}")
    private String prefixUrl;


    @Value("${aliyun.oss.accessKeyId:null}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret:null}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket:null}")
    private String bucket;
    @Value("${aliyun.oss.upload.point:null}")
    private String uploadPoint;
    @Value("${aliyun.oss.download.point:null}")
    private String downloadPoint;
    @Value("${aliyun.oss.firmware.folder:null}")
    private String firmwareFolder;

   public String uploadFile( MultipartFile file){
       if(MODE_OSS.equalsIgnoreCase(uploadMode)){
            return this.oss(file);
       }else if(MODE_LOCAL.equalsIgnoreCase(uploadMode)){
          return  this.local(file);
       }

       throw BCGException.genException(BCErrorEnum.INNER_EXCEPTION);
   }

   private String local( MultipartFile file){
       try{
           String fileName = FileUploadUtils.upload(filePath, file);
           String url =   prefixUrl + fileName;
           return url;
       }catch (Exception e){
           throw  new RuntimeException("上传文件异常");
       }
   }

    private String oss( MultipartFile file){
        try{
            return OSSClientUntil.ossUploadFile(file, firmwareFolder, uploadPoint, downloadPoint, accessKeyId, accessKeySecret, bucket);
        }catch (Exception e){
            throw  new RuntimeException("上传文件异常");
        }
    }
}

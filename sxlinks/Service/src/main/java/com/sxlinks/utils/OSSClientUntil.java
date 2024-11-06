package com.sxlinks.utils;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;

/**
 * 阿里云OSS客户端工具类.
 *
 * @author
 * @since 2018年7月23日10:50:28
 */
public class OSSClientUntil {

  private static OSSClientUntil instance = null;

  private OSSClient ossClient = null;


  private OSSClientUntil(String endpoint, String accessKeyId, String accessKeySecret,
                         String bucket) {
    if (null == ossClient) {
      ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
      if (!ossClient.doesBucketExist(bucket)) {
        ossClient.createBucket(bucket);
      }
    }
  }

  public static OSSClientUntil getInstance(String endpoint, String accessKeyId,
                                                                     String accessKeySecret, String bucket) {
    if (instance == null) {
      instance = new OSSClientUntil(endpoint, accessKeyId, accessKeySecret, bucket);
    }
    return instance;
  }

  public OSSClient getOssClient() {
    return ossClient;
  }

    /**
     *  上传文件到OSS服务器
     * @param file 上传文件
     * @param folder 上传路径
     * @param uploadPoint  上传域名信息
     * @param downloadPoint 下载域名信息
     * @param accessKeyId ID
     * @param accessKeySecret 密码
     * @param bucket
     * @return 返回文件上传路径
     * @throws IOException
     */
  public static String ossUploadFile(MultipartFile file, String folder, String uploadPoint, String downloadPoint, String accessKeyId, String accessKeySecret, String bucket) throws IOException {
      OSSClient ossClient = OSSClientUntil
              .getInstance(uploadPoint,accessKeyId, accessKeySecret,bucket).getOssClient();
      String address = getAddress(UUID.randomUUID().toString().replace("-", ""),folder);
      ossClient.putObject( bucket, address, file.getInputStream());
      return downloadPoint+"/"+address;
  }

    /**
     * 生成OSS文件名
     * @param fileName
     * @return
     */
    private static String getAddress(String   fileName,String   folder) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = uuid;
        String address = folder + "/" + newFileName+"/"+fileName;
        return address;
    }

}

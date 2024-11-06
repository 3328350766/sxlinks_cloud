package com.sxlinks.utils.file;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * com.sxlinks.utils.file project bytecub bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/8
 */
@Slf4j
public class Base64FileUtil {
    public static void main(String[] args){
        String base64 = http2base64("http://127.0.0.1/profile/2021/04/08/65beece8874e406aa8ba5c6d091f277a.xlsx");
        try{
            log.info("长度:{}", base64.length());
            generateFile(base64, "D://q.xlsx");
        }catch (Exception e){
            log.warn("", e);
        }

    }
    /**
     * 文件转化成base64字符串      
     * * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String http2base64(String uri) {
        InputStream in = null;
        String base64 = null;
        URLConnection connection = null;// 获取URLConnection对象
        ByteArrayOutputStream out = null;
        // 读取文件字节数组
        try {
            URL url = new URL(uri);
            connection = url.openConnection();
            in = new BufferedInputStream(connection.getInputStream());
            out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] data = out.toByteArray();
            base64 = byte2base64(data);
        } catch (Exception e) {
            log.warn("", e);
            throw new RuntimeException("转化文件异常");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.warn("", e);
            }
        }
        return base64;
    }
    /**
     * 文件转化成base64字符串      
     * * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String local2base64(String filePath) {
        InputStream in = null;
        String base64 = null;
        // 读取文件字节数组
        try {
            in = new FileInputStream(filePath);
            base64 = stream2base64(in);
        } catch (IOException e) {
            log.warn("", e);
            throw new RuntimeException("转化文件异常");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.warn("", e);
            }
        }
        return base64;
    }

    /**
     * 文件转化成base64字符串      
     * * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String stream2base64(InputStream inputStream) {

        InputStream in = null;
        byte[] data = null;
        // 读取文件字节数组
        try {
            in = inputStream;
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            log.warn("", e);
            throw new RuntimeException("转化文件异常");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.warn("", e);
            }
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回 Base64 编码过的字节数组字符串
        return encoder.encode(data);
    }
    /**
     * 文件转化成base64字符串      
     * * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String byte2base64(byte[] data) {


        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回 Base64 编码过的字节数组字符串
        return encoder.encode(data);
    }
    /**
     *      
     * * base64字符串转化成文件，可以是JPEG、PNG、TXT和AVI等等      
     * @param base64FileStr     
     * @param filePath
     *      
     *  @return
     *  @throws Exception      
     */
    public static boolean generateFile(String base64FileStr, String filePath) throws Exception {
        // 数据为空
        if (base64FileStr == null) {
            System.out.println(" 不行，oops！ ");
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码,对字节数组字符串进行Base64解码并生成文件
        byte[] byt = decoder.decodeBuffer(base64FileStr);
        for (int i = 0, len = byt.length; i < len; ++i) {
            // 调整异常数据
            if (byt[i] < 0) {
                byt[i] += 256;
            }
        }
        OutputStream out = null;
        InputStream input = new ByteArrayInputStream(byt);
        try {
            // 生成指定格式的文件
            out = new FileOutputStream(filePath);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = input.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
        } catch (IOException e) {
            log.warn("", e);
        } finally {
            out.flush();
            out.close();
        }
        return true;
    }
}

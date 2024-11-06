package com.sxlinks.modules.system.zlm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class ZLMRESTfulUtils {

    private final static Logger logger = LoggerFactory.getLogger(ZLMRESTfulUtils.class);




    public interface RequestCallback{
        void run(JSONObject response);
    }

    private OkHttpClient getClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //todo 暂时写死超时时间 均为5s
        httpClientBuilder.connectTimeout(5,TimeUnit.SECONDS);  //设置连接超时时间
        httpClientBuilder.readTimeout(5,TimeUnit.SECONDS);     //设置读取超时时间
        if (logger.isDebugEnabled()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
                logger.debug("http请求参数：" + message);
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            // OkHttp進行添加攔截器loggingInterceptor
            httpClientBuilder.addInterceptor(logging);
        }
        return httpClientBuilder.build();
    }


    public JSONObject sendPost(VideoMediaServer mediaServerItem, String api, Map<String, Object> param, RequestCallback callback) {
        OkHttpClient client = getClient();

        if (mediaServerItem == null) {
            return null;
        }
        String url = String.format("http://%s:%s/index/api/%s",  mediaServerItem.getIp(), mediaServerItem.getHttpPort(), api);
        JSONObject responseJSON = new JSONObject();
        //-2自定义流媒体 调用错误码
        responseJSON.put("code",-2);
        responseJSON.put("msg","流媒体调用失败");

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("secret",mediaServerItem.getSecret());
        if (param != null && param.keySet().size() > 0) {
            for (String key : param.keySet()){
                if (param.get(key) != null) {
                    builder.add(key, param.get(key).toString());
                }
            }
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
            if (callback == null) {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String responseStr = responseBody.string();
                            responseJSON = JSON.parseObject(responseStr);
                        }
                    }else {
                        response.close();
                        Objects.requireNonNull(response.body()).close();
                    }
                }catch (IOException e) {
                    logger.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()));

                    if(e instanceof SocketTimeoutException){
                        //读取超时超时异常
                        logger.error(String.format("读取ZLM数据失败: %s, %s", url, e.getMessage()));
                    }
                    if(e instanceof ConnectException){
                        //判断连接异常，我这里是报Failed to connect to 10.7.5.144
                        logger.error(String.format("连接ZLM失败: %s, %s", url, e.getMessage()));
                    }

                }catch (Exception e){
                    logger.error(String.format("访问ZLM失败: %s, %s", url, e.getMessage()));
                }
            }else {
                client.newCall(request).enqueue(new Callback(){

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response){
                        if (response.isSuccessful()) {
                            try {
                                String responseStr = Objects.requireNonNull(response.body()).string();
                                callback.run(JSON.parseObject(responseStr));
                            } catch (IOException e) {
                                logger.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()));
                            }

                        }else {
                            response.close();
                            Objects.requireNonNull(response.body()).close();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        logger.error(String.format("连接ZLM失败: %s, %s", call.request().toString(), e.getMessage()));

                        if(e instanceof SocketTimeoutException){
                            //读取超时超时异常
                            logger.error(String.format("读取ZLM数据失败: %s, %s", call.request().toString(), e.getMessage()));
                        }
                        if(e instanceof ConnectException){
                            //判断连接异常，我这里是报Failed to connect to 10.7.5.144
                            logger.error(String.format("连接ZLM失败: %s, %s", call.request().toString(), e.getMessage()));
                        }
                    }
                });
            }



        return responseJSON;
    }

    public void sendGetForImg(VideoMediaServer mediaServerItem, String api, Map<String, Object> params, String targetPath, String fileName) {
        String url = String.format("http://%s:%s/index/api/%s", mediaServerItem.getIp(), mediaServerItem.getHttpPort(), api);
        logger.debug(url);
        HttpUrl parseUrl = HttpUrl.parse(url);
        if (parseUrl == null) {
            return;
        }
        HttpUrl.Builder httpBuilder = parseUrl.newBuilder();

        httpBuilder.addQueryParameter("secret", mediaServerItem.getSecret());
        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue().toString());
            }
        }

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();
        logger.info(request.toString());
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("response body contentType: " + Objects.requireNonNull(response.body()).contentType());
                if (targetPath != null) {
                    File snapFolder = new File(targetPath);
                    if (!snapFolder.exists()) {
                        if (!snapFolder.mkdirs()) {
                            logger.warn("{}路径创建失败", snapFolder.getAbsolutePath());
                        }

                    }
                    File snapFile = new File(targetPath + File.separator + fileName);
                    FileOutputStream outStream = new FileOutputStream(snapFile);

                    outStream.write(Objects.requireNonNull(response.body()).bytes());
                    outStream.close();
                } else {
                    logger.error(String.format("[ %s ]请求失败: %s %s", url, response.code(), response.message()));
                }
                Objects.requireNonNull(response.body()).close();
            } else {
                logger.error(String.format("[ %s ]请求失败: %s %s", url, response.code(), response.message()));
            }
        } catch (ConnectException e) {
            logger.error(String.format("连接ZLM失败: %s, %s", e.getCause().getMessage(), e.getMessage()));
            logger.info("请检查media配置并确认ZLM已启动...");
        } catch (IOException e) {
            logger.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()));
        }
    }

    public JSONObject getRtpInfo(VideoMediaServer mediaServerItem, String stream_id){
        Map<String, Object> param = new HashMap<>();
        param.put("stream_id",stream_id);
        return sendPost(mediaServerItem, "getRtpInfo",param, null);
    }

    public void getSnap(VideoMediaServer mediaServerItem, String flvUrl, int timeout_sec, int expire_sec, String targetPath, String fileName) {
        Map<String, Object> param = new HashMap<>(3);
        param.put("url", flvUrl);
        param.put("timeout_sec", timeout_sec);
        param.put("expire_sec", expire_sec);
        sendGetForImg(mediaServerItem, "getSnap", param, targetPath, fileName);
    }
}

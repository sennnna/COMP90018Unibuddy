package com.example.unibody.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class HttpUtils {

    //配置okHttp
    private OkHttpClient client;

    private static HttpUtils httpUtils = null;

    private HttpUtils(){
        //初始化okhttp
        client = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.SECONDS)
                .writeTimeout(3000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)
                .build();
    }

    public static HttpUtils getInstance(){
        if (httpUtils == null){
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    //get请求
    public Call getHttp(String url){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return client.newCall(request);
    }


    //post请求
    public Call postHttp(String url, RequestBody requestBody){
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return client.newCall(request);
    }

    public Call post(Request request){
        return client.newCall(request);
    }

    //文件上传请求
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Call uploadMultipart(String url,File imageFile, String imageName, Map<String,String> data){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart(imageName,imageFile.getName(),RequestBody.create(MediaType.parse("image/png"),imageFile));
        data.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String o) {
                builder.addFormDataPart(s,o);
            }
        });
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return client.newCall(request);
    }

}

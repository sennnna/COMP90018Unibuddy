package com.example.unibody.me.fragment.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiClient {
    private static ApiClient instance;
    private static String BASE_URL = "http://3.26.21.18/";


    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();


    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }


    public static ApiClient getInstance() {

        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }



    public <T> void doPost(ApiBuilder builder, final CallBack<T> onCallback) {
        ApiService service = getService();
        Call<ResponseBody> call;
        call = service.post(builder.url,builder.body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    onCallback.onFail("failed" + response.message() + response.code());
                } else {
                    try {
                        onCallback.onResponse((T) response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onCallback.onFail(t.getMessage());
                Log.d("NET---", t.getMessage());
            }
        });
    }

    public <T> void doGet(ApiBuilder builder, final CallBack<T> onCallback) {
        ApiService service = getService();
        Call<ResponseBody> call;
        call = service.get(builder.url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    onCallback.onFail("failed" + response.message() + response.code());
                } else {
                    try {
                        onCallback.onResponse((T) response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onCallback.onFail(t.getMessage());
                Log.d("NET---", t.getMessage());
            }
        });
    }


}

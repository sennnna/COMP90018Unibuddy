package com.example.unibody.me.fragment.util;

import android.content.Context;
import android.util.Log;

import com.example.unibody.me.fragment.http.CallBack;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Util {

    public static String user = "test1";

    /**
     * dp-->px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int getAgeByBirth(String birth) {
        if (birth == null || birth.equals("")) {
            return 0;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = null;
        try {
            birthday = simpleDateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();


        Calendar bir = Calendar.getInstance();
        bir.setTime(birthday);

        if (cal.before(birthday)) {
            return 0;
        }

        int nowYear = cal.get(Calendar.YEAR);
        int nowMonth = cal.get(Calendar.MONTH);
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);

        int birthYear = bir.get(Calendar.YEAR);
        int birthMonth = bir.get(Calendar.MONTH);
        int birthDay = bir.get(Calendar.DAY_OF_MONTH);


        int age = nowYear - birthYear;

        if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
            age--;
        }
        return age;
    }

    public static  void multiUpload(List<File> fileList, String content, final CallBack onCallback) {
        if (fileList == null || fileList.size() == 0) return;


        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder.setType(MultipartBody.FORM)
                .addFormDataPart("username", Util.user)
                .addFormDataPart("context", content);

        for (int i = 0; i < fileList.size(); i++) {
                    builder.addFormDataPart(
                            "file",
                            fileList.get(i).getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i))
                    );
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://3.26.21.18/api/v1/moment/addMomentWithImg")
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("AAAA", "onFailure: " + e.toString());
                        onCallback.onFail(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();

                        onCallback.onResponse("sucess");

                    }
                });
    }


    public static <T> void uploadPhoto(boolean isHeadImg, File file, String username, final CallBack<T> onCallback) {
        if (file == null) return;


        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder.setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)
                );
        String url = isHeadImg ? "user/upLoadAvatar" : "user/upLoadAlbum";
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://3.26.21.18/api/v1/" + url)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call,  IOException e) {
                        Log.e("AAAA", "upload album onFailure: " + e.toString());
                        onCallback.onFail(e.toString());
                    }

                    @Override
                    public void onResponse(Call call,Response response) throws IOException {
                        String result = response.body().string();
                        Log.e("AAAA", "upload album success：" + result);

                    }
                });
    }
}

package com.example.unibody.login.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.unibody.MainActivity;
import com.example.unibody.constant.UserInfoConstant;
import com.example.unibody.custom.LoadingDialog;
import com.example.unibody.login.activity.LoginActivity;
import com.example.unibody.login.activity.PersonInfoGuideActivity;
import com.example.unibody.login.domain.PersonInfo;
import com.example.unibody.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginViewModel extends AndroidViewModel {

    private final String URL = "http://13.211.172.199/api/v1/";

    private static LoginViewModel loginViewModel = null;
    private String phoneNumber;

    private LoadingDialog loadingDialog;
    private HttpUtils httpUtils;

    private String verificationCoded = "";
    private String sendPhone = "";

    public LoginViewModel(@NonNull Application application) {
        super(application);
        httpUtils = HttpUtils.getInstance();
    }

    public static LoginViewModel getLoginViewModel(){
        return loginViewModel;
    }

    public void setLoadingDialog(LoadingDialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    public void setLoginViewModel(LoginViewModel loginViewModel){
        this.loginViewModel = loginViewModel;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //发送请求,判断是否为新用户
    public Call isNewUser(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return httpUtils.getHttp(URL + "isNewUser?phoneNumber=" + phoneNumber);
    }

    //发送请求,判断验证码是否正确
    public void judgeCode(String code,String phoneNumber,LoginActivity loginActivity){
        if (!code.equals(verificationCoded) || !phoneNumber.equals(phoneNumber)){
            loadingDialog.dismiss("Verification code error");
        }
        loadingDialog.open();
        RequestBody requestBody = RequestBody.create(
                "{\"phone_num\":\"" + phoneNumber + "\"}",
                MediaType.parse("application/json; charset=utf-8")
        );
        Call call = httpUtils.postHttp(URL + "auth/loginBySms", requestBody);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Looper.prepare();
                loadingDialog.dismiss("Connection timed out");
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("返回:",result);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    String message = (String) jsonObject.get("message");
                    //新用户  跳转到信息填写界面
                    loadingDialog.dismiss();
                    loginActivity.startActivity(new Intent(loginActivity,
                            PersonInfoGuideActivity.class));
                    return;
                } catch (JSONException e) {
                    try {
                        jsonObject = new JSONObject(result);
                        JSONObject u = (JSONObject) jsonObject.get("user");
                        //老用户
                        String avatar_url = (String) u.get("avatar_url");
                        String username = (String) u.get("username");
                        SharedPreferences.Editor config = getApplication().getSharedPreferences("config", Context.MODE_PRIVATE).edit();
                        config.putString("image_head",avatar_url);
                        config.putBoolean("isLogin",true);
                        config.putString("username",username);
                        config.putString("phone_number",phoneNumber);
                        config.commit();
                        //跳转到MainActivity
                        loadingDialog.dismiss();
                        loginActivity.startActivity(new Intent(loginActivity,
                                MainActivity.class));
                        System.out.println("运行....");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        Looper.prepare();
                        loadingDialog.dismiss("Login failed");
                        Looper.loop();
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    //发送请求,请求验证码
    public void sendCode(String phoneNumber,LoginActivity loginActivity){
        this.phoneNumber = phoneNumber;
        this.sendPhone = phoneNumber;
        loadingDialog.open();
        RequestBody requestBody = RequestBody.create(
                "{\"phone_num\":\"" + phoneNumber + "\"}",
                MediaType.parse("application/json; charset=utf-8")
        );
        Call call = httpUtils.postHttp(URL + "auth/sendSms",requestBody);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Looper.prepare();
                loadingDialog.dismiss("Failed to send verification code");
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    JSONObject r = (JSONObject) jsonObject.get("response");
                    String message = (String) r.get("message");
                    if ("SUCCESS".equals(message)){
                        loginActivity.startTime();
                        verificationCoded = (String) r.get("verificationCode");
                        Looper.prepare();
                        loadingDialog.dismiss("The verification code was sent successfully");
                        Looper.loop();
                    }
                    Log.d("返回:",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    loadingDialog.dismiss("Failed to send verification code");
                    Looper.loop();
                }
            }
        });
    }

    //发送请求,上传个人信息
    public Call uploadPersonInfo(PersonInfo personInfo){
        personInfo.setPhoneNumber(phoneNumber);
        Log.d("Gender",personInfo.getGender());
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", personInfo.getName())
                .addFormDataPart("password", "")
                .addFormDataPart("phone_number", personInfo.getPhoneNumber())
                .addFormDataPart("email", "")
                .addFormDataPart("gender", personInfo.getGender())
                .addFormDataPart("status", personInfo.getStatus())
                .addFormDataPart("birth", personInfo.getBirth())
                .addFormDataPart("university", personInfo.getSchool())
                .addFormDataPart("height", personInfo.getHeight())
                .addFormDataPart("sign", "")
                .addFormDataPart("faculty", "")
                .addFormDataPart("major", "")
                .addFormDataPart("quiz", personInfo.getQuiz1())
                .addFormDataPart("quiz", personInfo.getQuiz2())
                .build();
        return httpUtils.postHttp(URL + "user/register", requestBody);
    }

    public Call uploadHeadImage(File file,String username){
        RequestBody requestBody = RequestBody.create(
                file,MediaType.parse("image/png")
        );
        return httpUtils.postHttp(URL + "/user/upLoadAvatar",
                new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file",file.getName(),
                                requestBody
                        )
                        .addFormDataPart("username",username)
                        .build()
        );
    }

    public void getAllUser(){
        Call http = httpUtils.getHttp(URL + "/user");
        http.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println("响应:"+response.body().string());
            }
        });
    }
}

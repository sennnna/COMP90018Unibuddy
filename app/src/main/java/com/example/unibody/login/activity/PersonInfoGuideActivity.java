package com.example.unibody.login.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unibody.MainActivity;
import com.example.unibody.R;
import com.example.unibody.custom.LoadingDialog;
import com.example.unibody.login.adapter.InfoGuideViewAdapter;
import com.example.unibody.login.domain.PersonInfo;
import com.example.unibody.login.fragment.HeadSelectFragment;
import com.example.unibody.login.fragment.InfoSchoolFragment;
import com.example.unibody.login.fragment.InfoStatusFragment;
import com.example.unibody.login.fragment.PersonBasicInfoFragment;
import com.example.unibody.login.fragment.QuestionFragment;
import com.example.unibody.login.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonInfoGuideActivity extends AppCompatActivity {
    private ViewPager2 viewPage;  //viewPage
    private ImageButton back_btn;  //Tab bar back btn
    private TextView center_tv;  //Tab bar center text
    private TextView op_tv;   //Tab bar right operation text

    private final HeadSelectFragment headSelectFragment = new HeadSelectFragment();
    private final PersonBasicInfoFragment personBasicInfoFragment = new PersonBasicInfoFragment();
    private final InfoSchoolFragment infoSchoolFragment = new InfoSchoolFragment();
    private final InfoStatusFragment infoStatusFragment = new InfoStatusFragment();
    private final QuestionFragment questionFragment = new QuestionFragment();


    private List<Fragment> fragmentList = new ArrayList<>();
    private int index = 0;

    private LoginViewModel loginViewModel;

    private LoadingDialog loadingDialog;

    private PersonInfo personInfo = new PersonInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

        setContentView(R.layout.activity_person_info_guide);
        loadingDialog = new LoadingDialog(PersonInfoGuideActivity.this);
        initView();
        initViewPager2();
        loginViewModel = LoginViewModel.getLoginViewModel();

        op_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setRequiredItems()){
                    return;
                }
                index++;
                if (index < fragmentList.size()){
                    viewPage.setCurrentItem(index);
                    if (index == fragmentList.size() - 1){
                        op_tv.setText("Skip");
                    }
                    changeCenterText();
                }else {
                   //发送请求,请求成功跳转界面
                    String path = String.valueOf(personInfo.getHeadUri()).replaceAll("file://", "");
                    System.out.println(path);
                    File file = new File(path);
                    System.out.println(file.getName());
                    System.out.println(personInfo.getName());

                    Call call = loginViewModel.uploadPersonInfo(personInfo);
                    loadingDialog.open();
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            loadingDialog.dismiss("Network Timeout");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String result = response.body().string();
                            JSONObject jsonObject = null;
                            System.out.println("上传个人信息:"+result);
                            try {
                                jsonObject = new JSONObject(result);
                                JSONObject u = (JSONObject) jsonObject.get("user");
                                String userID = (String) u.get("userID");
                                String username = (String) u.get("username");
                                Call call1 = loginViewModel.uploadHeadImage(file, username);
                                Response execute = call1.execute();

                                String r = execute.body().string();
                                jsonObject = new JSONObject(r);
                                String avatar_url = (String) jsonObject.get("avatar_url");

                                Log.d("上传头像",r);
                                SharedPreferences.Editor config = getSharedPreferences("config", Context.MODE_PRIVATE).edit();
                                config.putString("image_head",avatar_url);
                                config.putBoolean("isLogin",true);
                                config.commit();

                                startActivity(new Intent(PersonInfoGuideActivity.this, MainActivity.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Looper.prepare();
                                loadingDialog.dismiss("Upload failed");
                                Looper.loop();
                            }
                        }
                    });
                }
            }
        });

        back_btn.setOnClickListener(v ->{
            index--;
            if (index > -1){
                viewPage.setCurrentItem(index);
                if (index < fragmentList.size() - 1){
                    op_tv.setText("Next");
                }
                changeCenterText();
            }else {
                finish();
            }
        });
    }

    //bind widget
    private void initView(){
        viewPage = findViewById(R.id.viewpage);
        back_btn = findViewById(R.id.back);
        center_tv = findViewById(R.id.center_tv);
        center_tv.setText("Login in");
        op_tv = findViewById(R.id.op_tv);
    }

    //init ViewPager2
    private void initViewPager2(){
        fragmentList.add(headSelectFragment);
        fragmentList.add(personBasicInfoFragment);
        fragmentList.add(infoSchoolFragment);
        fragmentList.add(infoStatusFragment);
        fragmentList.add(questionFragment);
        viewPage.setUserInputEnabled(false);  //prohibit user scroll
        viewPage.setAdapter(new InfoGuideViewAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList));

    }

    private void changeCenterText(){
        if (index == 1){
            center_tv.setText("Basic Information");
            op_tv.setVisibility(View.VISIBLE);
        }else if (index == 3){
            center_tv.setText("Basic Information");
        }else if (index == 4){
            center_tv.setText("Questionnaire");
        }
    }

    //返回false为有必填项未填
    private boolean setRequiredItems(){
        switch (index){
            case 0:
                //获取设置的头像和昵称
                Uri headUri = headSelectFragment.getHeadUri();
                String name = headSelectFragment.getName();
                if (headUri == null || name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please set your avatar and nickname",Toast.LENGTH_SHORT).show();
                    return false;
                }
                personInfo.setHeadUri(headUri);
                personInfo.setName(name);
                return true;
            case 1:
                //获取设置的性别,身高,生日
                String birth = personBasicInfoFragment.getBirth();
                String height = personBasicInfoFragment.getHeight();
                String gender = personBasicInfoFragment.getGender();
                personInfo.setBirth(birth);
                personInfo.setHeight(height);
                personInfo.setGender(gender);
                return true;
            case 2:
                //设置学校
                String school = infoSchoolFragment.getSchool();
                personInfo.setSchool(school);
                return true;
            case 3:
                //设置状态
                String status = infoStatusFragment.getStatus();
                personInfo.setStatus(status);
                return true;
            case 4:
                //设置问题
                String quiz1 = questionFragment.quiz1();
                String quiz2 = questionFragment.quiz2();
                personInfo.setQuiz1(quiz1);
                personInfo.setQuiz2(quiz2);
                return true;
            default:
                return false;
        }
    }
}

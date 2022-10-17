package com.example.unibody.login.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.unibody.MainActivity;
import com.example.unibody.R;
import com.example.unibody.login.adapter.InfoGuideViewAdapter;
import com.example.unibody.login.fragment.HeadSelectFragment;
import com.example.unibody.login.fragment.InfoSchoolFragment;
import com.example.unibody.login.fragment.InfoStatusFragment;
import com.example.unibody.login.fragment.PersonBasicInfoFragment;
import com.example.unibody.login.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

        setContentView(R.layout.activity_person_info_guide);
        initView();
        initViewPager2();


        op_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index < fragmentList.size()){
                    viewPage.setCurrentItem(index);
                    if (index == fragmentList.size() - 1){
                        op_tv.setText("Skip");
                    }
                    changeCenterText();
                }else {
                    //跳转界面
                    startActivity(new Intent(PersonInfoGuideActivity.this, MainActivity.class));
                }
            }
        });

        back_btn.setOnClickListener(v ->{
            index--;
            if (index > -1){
                viewPage.setCurrentItem(index);
                if (index != fragmentList.size()-1){
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
}
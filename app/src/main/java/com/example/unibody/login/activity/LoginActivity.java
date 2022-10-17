package com.example.unibody.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unibody.R;


/**
 * Login page
 */
public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText phone_number_edit,code_edit;
    private TextView send_code_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        login_btn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,PersonInfoGuideActivity.class));
        });
    }

    private void initView(){
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

        login_btn = findViewById(R.id.login_btn);
        phone_number_edit = findViewById(R.id.phone_number_edit);
        code_edit = findViewById(R.id.code_edit);
        send_code_tv = findViewById(R.id.send_code_tv);
    }
}
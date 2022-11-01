package com.example.unibody.login.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.unibody.MainActivity;
import com.example.unibody.R;
import com.example.unibody.custom.LoadingDialog;
import com.example.unibody.login.viewmodel.LoginViewModel;


/**
 * Login page
 */
public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText phone_number_edit,code_edit;
    private TextView send_code_tv;
    private LoginViewModel loginViewModel;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*
            判断是否已经登录过,返回true则表示已经登录,直接进入MainActivity
            返回false表示还未登录
         */
        loadingDialog = new LoadingDialog(LoginActivity.this);
        loadingDialog.open();
        SharedPreferences config = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean isLogin = config.getBoolean("isLogin", false);
        if (isLogin){
            loadingDialog.dismiss();
            startActivity(new Intent(this,MainActivity.class));
            return;
        }

        loadingDialog.dismiss();

        loginViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LoginViewModel.class);
        loginViewModel.setLoginViewModel(loginViewModel);

        loginViewModel.setLoadingDialog(loadingDialog);

        initView();

        login_btn.setOnClickListener(v -> {
            String phoneNumber = phone_number_edit.getText().toString().trim();
            String code = code_edit.getText().toString().trim();

            if (phoneNumber == null || "".equals(phoneNumber)){
                //弹出提示,手机号为空
                Toast.makeText(LoginActivity.this,"Mobile number cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }

            if (code == null || "".equals(code)){
                //弹出提示,验证码为空
                Toast.makeText(LoginActivity.this,"The verification code cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }

            //发送请求,判断验证码是否正确
            loginViewModel.judgeCode(code,phoneNumber,LoginActivity.this);
        });

        send_code_tv.setOnClickListener(v -> {
            String phoneNumber = phone_number_edit.getText().toString().trim();
            if (phoneNumber == null || "".equals(phoneNumber)){
                //弹出提示,手机号为空
                Toast.makeText(LoginActivity.this,"Mobile number cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }
            loginViewModel.sendCode(phoneNumber,LoginActivity.this);

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

    public void startTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 59;
                    while (i > 0){
                        String min;
                        if (i <10){
                            min = "Resend(0"+i+"s)";
                        }else {
                            min = "Resend(" + i + "s)";
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                send_code_tv.setText(min);
                                send_code_tv.setEnabled(false);
                            }
                        });
                        Thread.sleep(1000);
                        i--;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            send_code_tv.setText("Send Code");
                            send_code_tv.setEnabled(true);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

package com.example.unibody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.unibody.login.activity.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences config = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean isLogin = config.getBoolean("isLogin", false);
//        if (isLogin){
//            loadingDialog.dismiss();
//            startActivity(new Intent(this,MainActivity.class));
//            return;
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 3;
                    while (i > 0){
                        Thread.sleep(1000);
                        i--;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isLogin){
                                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                                return;
                            }else {
                                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
package com.example.unibody.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.unibody.R;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.Util;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhotoActivity extends AppCompatActivity {

    private String index;
    private String isDelAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ImageView photo = findViewById(R.id.photo);
        ImageView ivDel = findViewById(R.id.iv_del);
        RelativeLayout llDel = findViewById(R.id.ll_del);
        String path = getIntent().getStringExtra("path");
        String canDel = getIntent().getStringExtra("canDel");
        isDelAlbum = getIntent().getStringExtra("isDelAlbum");
        index = getIntent().getStringExtra("index");
        if (canDel.equals("")) {
            llDel.setVisibility(View.GONE);
        } else {
            llDel.setVisibility(View.VISIBLE);
        }
        Glide.with(this).load(path).into(photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoActivity.this.finish();
            }
        });

        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 删除照片
                if (isDelAlbum == null || isDelAlbum.equals("")) {
                    //不需要去服务器删除照片
                    Intent intent = getIntent();
                    intent.putExtra("index", index);
                    setResult(RESULT_OK, intent);
                    PhotoActivity.this.finish();
                } else {
                    delPhoto();
                }

            }
        });
    }

    private void delPhoto() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        String str;
        map.put("index", index);
        JSONObject object = new JSONObject(map);
        str = object.toString();
        Log.e("AAAA", "delPhoto: ===>" + str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/deleteUserAlbum").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                Log.e("AAAA", "onResponse: " + data.toString());
                Toast.makeText(PhotoActivity.this, "delete album success", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                PhotoActivity.this.finish();
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
                Toast.makeText(PhotoActivity.this, "delete album fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
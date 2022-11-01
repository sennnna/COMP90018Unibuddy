package com.example.unibody.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.example.unibody.me.fragment.adapter.MomentsAdapter;
import com.example.unibody.me.fragment.bean.MomentBean;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyMomentActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView add;
    private RecyclerView rvShare;
    private MomentsAdapter adapter;
    private List<MomentBean.MomentsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_moment);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMomentActivity.this.finish();
            }
        });
        add = findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMomentActivity.this, com.example.unibody.me.fragment.AddMomentActivity.class);
                startActivity(intent);
                MyMomentActivity.this.finish();
            }
        });
        rvShare = findViewById(R.id.rv_share);

        rvShare.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MomentsAdapter(this, list, new MomentsAdapter.CallBack() {

            @Override
            public void delMoment(String id) {
                deleteMoment(id);
            }

            @Override
            public void showBigPhoto(String path) {
                Intent intent = new Intent(MyMomentActivity.this, com.example.unibody.me.fragment.PhotoActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("canDel", "");
                startActivity(intent);
            }
        });
        rvShare.setAdapter(adapter);

        loadData();
    }

    private void deleteMoment(String id) {
        HashMap<String, String> map = new HashMap<>();
        String str;
        map.put("id", id);
        JSONObject object = new JSONObject(map);
        str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/moment/deleteMoment").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                Log.e("AAAA", "onResponse: " + data.toString());
                Toast.makeText(MyMomentActivity.this, "delete moment success", Toast.LENGTH_SHORT).show();
                loadData();
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
                Toast.makeText(MyMomentActivity.this, "delete moment fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/moment/getMomentByUsername").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
//                List<MomentBean> moments = new ArrayList<MomentBean>();
//
//                Type type = new TypeToken<ArrayList<MomentBean>>() {
//                }.getType();

                MomentBean moments = new Gson().fromJson(data.toString(), MomentBean.class);

                list.clear();
                list.addAll(moments.getMoments());
                adapter.notifyDataSetChanged();


                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }
}
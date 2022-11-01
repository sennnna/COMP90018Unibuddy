package com.example.unibody.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.example.unibody.me.fragment.adapter.MyFriendsAdapter;
import com.example.unibody.me.fragment.bean.FriendBean;
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

public class MyFriendsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyFriendsAdapter adapter;
    private ImageView back;
    private List<FriendBean.FriendsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends_activity);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFriendsActivity.this.finish();
            }
        });
        recyclerView = findViewById(R.id.rv_friends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MyFriendsAdapter(this, list, new MyFriendsAdapter.CallBack() {
            @Override
            public void clickItem(String friend) {
                Intent intent = new Intent(MyFriendsActivity.this, UserInfoActivity.class);
                intent.putExtra("username",friend);
                startActivity(intent);
            }

            @Override
            public void showBigPhoto(String path) {
                Intent intent = new Intent(MyFriendsActivity.this, PhotoActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("canDel", "");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        loadData();
    }


    private void loadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserFriends").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                FriendBean bean = new Gson().fromJson(data.toString(), FriendBean.class);
                List<FriendBean.FriendsBean> friends = bean.getFriends();
                list.clear();
                list.addAll(friends);
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
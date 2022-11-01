package com.example.unibody.me.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unibody.R;
import com.example.unibody.me.fragment.bean.HeadImgBean;
import com.example.unibody.me.fragment.bean.User;
import com.example.unibody.me.fragment.bean.UserInfo;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.CircleImageView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView back;
    private Button chart;
    private CircleImageView headImg;
    private TextView phone, email, bio, username;
    private LinearLayout llPhone, llEmail, llPwd, llBio;
    private UserInfo userInfo = new UserInfo();
    private View linePwd;
    private String friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        friendName = getIntent().getStringExtra("username");
        headImg = findViewById(R.id.head_iv);
        back = findViewById(R.id.btn_back);
        chart = findViewById(R.id.btn_chart);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone_text);
        email = findViewById(R.id.email_text);
        bio = findViewById(R.id.bio_text);
        llPhone = findViewById(R.id.ll_phone);
        llEmail = findViewById(R.id.ll_email);
        llPwd = findViewById(R.id.ll_pwd);
        linePwd = findViewById(R.id.pwd_line);
        llBio = findViewById(R.id.ll_Bio);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoActivity.this.finish();
            }
        });

        if (friendName == null || friendName.equals("")) {
            headImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateHeadImg();
                }
            });
            llPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUpdateDialog(1);
                }
            });
            llEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUpdateDialog(2);
                }
            });
            llPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUpdateDialog(3);
                }
            });

            llBio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUpdateDialog(4);
                }
            });

        } else {
            llPwd.setVisibility(View.GONE);
            linePwd.setVisibility(View.GONE);
        }

        loadHeadImg();
        loadData();
    }

    private void updateHeadImg() {
        if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, null);//打开系统相册
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri originalUri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);


                    File file = new File(path);
                    Glide.with(UserInfoActivity.this).load(path).apply(new RequestOptions().placeholder(headImg.getDrawable())).into(headImg);
                    Util.uploadPhoto(true, file, Util.user, new CallBack<Response>() {
                        @Override
                        public void onResponse(Response data) {
                            Toast.makeText(UserInfoActivity.this, "update avatar success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(String msg) {
                            Toast.makeText(UserInfoActivity.this, "update avatar fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }

    private void showUpdateDialog(int type) {
        switch (type) {
            case 1:
                show("update Phone", phone, type);
                break;
            case 2:
                show("update Email", email, type);
                break;
            case 3:
                show("update Password", null, type);
                break;
            case 4:
                show("update Bio", bio, type);
                break;
        }
    }

    private void show(String title, TextView tv, int type) {
        final EditText text = new EditText(this);
        if (tv != null) {
            text.setText(tv.getText().toString());
        }
        new AlertDialog.Builder(UserInfoActivity.this)
                .setTitle(title)
                .setView(text)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = text.getText().toString();
                        if (tv != null) {
                            tv.setText(result);
                        }
                        commit(type, result);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void commit(int type, String text) {
        HashMap<String, String> map = new HashMap<>();
        userInfo.setUsername(Util.user);
        map.put("username", Util.user);
        String str;
        if (type == 4) {
            map.put("sign", text);
            JSONObject object = new JSONObject(map);
            str = object.toString();
        } else {
            switch (type) {
                case 1:
                    userInfo.setPhone_number(text);
                    break;
                case 2:
                    userInfo.setEmail(text);
                    break;
                case 3:
                    userInfo.setPassword(text);
                    break;
                case 4:

                    break;
            }
            Gson gson = new Gson();
            str = gson.toJson(userInfo);
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        String url = (type == 4) ? "api/v1/user/setSign" : "api/v1/user/updateUserInfo";
        ApiBuilder builder = new ApiBuilder().Url(url).Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                Log.e("AAAA", "onResponse: " + data.toString());
                Toast.makeText(UserInfoActivity.this, "update info success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
                Toast.makeText(UserInfoActivity.this, "update info fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadData() {
        HashMap<String, String> map = new HashMap<>();
        if (friendName == null || friendName.equals("")) {
            map.put("username", Util.user);
        }else {
            map.put("username", friendName);
        }
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserByUsername").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                User user = new Gson().fromJson(data.toString(), User.class);
                userInfo.setPassword(user.getUser().getPassword());
                userInfo.setEmail(user.getUser().getEmail());
                userInfo.setGender(user.getUser().getGender());
                userInfo.setUniversity(user.getUser().getUniversity());
                userInfo.setStatus(user.getUser().getStatus());
                userInfo.setUsername(user.getUser().getUsername());
                userInfo.setPhone_number(user.getUser().getPhone_number());
                userInfo.setAge(Util.getAgeByBirth(user.getUser().getBirth()) + "");

                phone.setText(userInfo.getPhone_number());
                email.setText(userInfo.getEmail());
                bio.setText(user.getUser().getSign());
                username.setText(userInfo.getUsername());


                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }

    private void loadHeadImg() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserAvatar").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                HeadImgBean bean = new Gson().fromJson(data.toString(), HeadImgBean.class);
                Glide.with(UserInfoActivity.this).load(bean.getAvatar_url()).apply(new RequestOptions().placeholder(headImg.getDrawable())).into(headImg);

                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }
}
package com.example.unibody.me.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.unibody.R;
import com.example.unibody.me.fragment.adapter.PhotoAdapter;
import com.example.unibody.me.fragment.bean.AlbumBean;
import com.example.unibody.me.fragment.bean.PhotoState;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.GridViewForScrollView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlbumsActivity extends AppCompatActivity {

    ImageView btnBack;
    GridViewForScrollView gridPhoto;

    private PhotoAdapter adapter;
    private List<PhotoState> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlbumsActivity.this.finish();
            }
        });
        gridPhoto = findViewById(R.id.grid_photo);

        init();
        loadData();
    }

    private void init() {
        adapter = new PhotoAdapter(this, photos, new PhotoAdapter.Callback() {

            @Override
            public void onTakePhotoClick() {
                if (ContextCompat.checkSelfPermission(AlbumsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AlbumsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                }
            }

            @Override
            public void toPhotoDetail(int position) {
                Intent intent = new Intent(AlbumsActivity.this, com.example.unibody.me.fragment.PhotoActivity.class);
                String path;
                if (photos.get(position).isUrl()) {
                    path = photos.get(position).getOriginPath();
                } else {
                    if (TextUtils.isEmpty(photos.get(position).getPath())) {
                        path = "file://" + photos.get(position).getOriginPath();
                    } else {
                        path = "file://" + photos.get(position).getPath();
                    }
                }
                intent.putExtra("path", path);
                intent.putExtra("index", position+"");
                intent.putExtra("canDel", "1");
                intent.putExtra("isDelAlbum", "1");
                startActivityForResult(intent, 3);
            }

            @Override
            public void reUpload(String path) {
                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getPath().equals(path)) {
                        photos.get(i).setState(PhotoAdapter.STATE_UPING);

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        gridPhoto.setAdapter(adapter);
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

                    Log.e("AAAA", "onActivityResult: path:" + path);

                    addToImgPathList(path);
                    adapter.notifyDataSetChanged();
                }
                break;

            case 3:
                if (resultCode == RESULT_OK && data != null) {
                    String index = data.getStringExtra("index");
                    int i = Integer.parseInt(index);
                    photos.remove(i);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void loadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserAlbum").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                AlbumBean bean = new Gson().fromJson(data.toString(), AlbumBean.class);
                List<String> list = bean.getAlbum();
                photos.clear();
                for (int i = 0; i < list.size(); i++) {
                    PhotoState photo = new PhotoState();
                    photo.setOriginPath(list.get(i));
                    photo.setIsUrl(true);
                    photo.setState(PhotoAdapter.STATE_SUCCESS_UP);
                    photos.add(photo);
                }

                adapter.notifyDataSetChanged();


                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }

    /**
     * 压缩图片
     *
     * @param path
     */
    private void compressFile(String path) {
        new Compressor(this)
                .compressToFileAsFlowable(new File(path))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        setCompressPath(path, file.getPath());
                        Log.e("AAAA", "accept: ======after compress==============="+file.length()+"  ||  " + file.getPath());

                        updateAlbum(file);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });

    }


    private void updateAlbum(File file) {
        String path =file.getPath();
        Util.uploadPhoto(false, file, Util.user, new CallBack<Response>() {
            @Override
            public void onResponse(Response data) {
                Toast.makeText(AlbumsActivity.this, "update album success", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getPath().equals(path)) {
                        photos.get(i).setState(PhotoAdapter.STATE_SUCCESS_UP);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AlbumsActivity.this, "update album fail", Toast.LENGTH_SHORT).show();
                    }
                });

                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getPath().equals(path)) {
                        photos.get(i).setState(PhotoAdapter.STATE_FAILED_UP);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setCompressPath(String orgin, String afterCompress) {
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).getOriginPath().equals(orgin)) {
                photos.get(i).setPath(afterCompress);
            }
        }
    }

    private void addToImgPathList(String path) {
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).getOriginPath().equals(path)) {
                return;
            }
        }
        PhotoState photo = new PhotoState();
        photo.setOriginPath(path);
        photo.setState(PhotoAdapter.STATE_UPING);
        Log.e("AAAA", "addToImgPathList: ===>"+new File(path).length() );
        compressFile(path);
        photos.add(photo);
        adapter.notifyDataSetChanged();
    }

}

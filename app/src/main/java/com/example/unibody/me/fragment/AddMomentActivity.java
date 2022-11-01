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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.unibody.R;
import com.example.unibody.me.fragment.adapter.PhotoAdapter;
import com.example.unibody.me.fragment.bean.PhotoState;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.GridViewForScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddMomentActivity extends AppCompatActivity {

    private ImageView back;
    private TextView sent;
    private EditText content;
    GridViewForScrollView gridPhoto;
    private PhotoAdapter adapter;
    private List<PhotoState> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        back = findViewById(R.id.btn_back);
        sent = findViewById(R.id.tv_sent);
        content = findViewById(R.id.et_content);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMomentActivity.this, MyMomentActivity.class);
                startActivity(intent);
                AddMomentActivity.this.finish();
            }
        });
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sent();
            }
        });
        gridPhoto = findViewById(R.id.grid_photo);
        init();
    }

    private void init() {
        adapter = new PhotoAdapter(this, photos, new PhotoAdapter.Callback() {

            @Override
            public void onTakePhotoClick() {
                if (ContextCompat.checkSelfPermission(AddMomentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddMomentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);//打开系统相册
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                }
            }

            @Override
            public void toPhotoDetail(int position) {
                Intent intent = new Intent(AddMomentActivity.this, PhotoActivity.class);
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
                intent.putExtra("index", position + "");
                intent.putExtra("canDel", "1");
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

    /**
     *
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
                        Log.e("AAAA", "accept: ======after compress ===============" + file.getPath());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();

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
        photo.setState(PhotoAdapter.STATE_SUCCESS_UP);
        compressFile(path);
        photos.add(photo);
    }

    /**
     *
     */
    private void sent() {
        String context = content.getText().toString();
        List<File> list = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            String path = photos.get(i).getPath();
            if (path == null || path.equals("")) {
                path = photos.get(i).getOriginPath();
            }
            File file = new File(path);
            list.add(file);
        }


        Util.multiUpload(list, context, new CallBack() {
            @Override
            public void onResponse(Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddMomentActivity.this, "sent moment success", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFail(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddMomentActivity.this, "sent moment fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
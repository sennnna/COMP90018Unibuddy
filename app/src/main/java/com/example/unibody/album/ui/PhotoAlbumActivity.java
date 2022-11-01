package com.example.unibody.album.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.unibody.R;
import com.example.unibody.album.adapter.PhotoAlbumAdapter;
import com.example.unibody.album.viewmodel.PhotoAlbumViewModel;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

public class PhotoAlbumActivity extends AppCompatActivity {

    private RecyclerView album_list;
    private ImageButton close_btn;
    private Button send_btn;

    private boolean isCheck = true;

    private GridLayoutManager gridLayoutManager;

    private PhotoAlbumViewModel photoAlbumViewModel;
    private PhotoAlbumAdapter photoAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        initView();

        photoAlbumViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(PhotoAlbumViewModel.class);
        photoAlbumViewModel.getPhotoUriList().observe(this, new Observer<List<Uri>>() {
            @Override
            public void onChanged(List<Uri> uris) {
                photoAlbumAdapter = new PhotoAlbumAdapter(uris,isCheck,gridLayoutManager);
                album_list.setAdapter(photoAlbumAdapter);
                photoAlbumAdapter.setOnItemClickListener(new PhotoAlbumAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (isCheck){
                            //预览
                            //跳转到下一个Activity
                        }else {
                            cropImage(uris.get(position));
                        }
                    }
                });
                photoAlbumAdapter.setOnCheckBoxListener(new PhotoAlbumAdapter.onCheckBoxListener() {
                    @Override
                    public void onChangeListener(boolean b, int position) {
                        if (b) {
                            photoAlbumViewModel.addImage(uris.get(position));
                        }else {
                            photoAlbumViewModel.removeImage(uris.get(position));
                        }
                        if (!PhotoAlbumViewModel.imageList.isEmpty()){
                            send_btn.setEnabled(true);
                        }
                    }
                });
            }
        });

        close_btn.setOnClickListener(v -> {
            PhotoAlbumViewModel.imageList.clear();
            finish();
        });
        send_btn.setOnClickListener(v -> {
            finish();
        });
    }

    private void initView(){
        album_list = findViewById(R.id.album_list);
        close_btn = findViewById(R.id.close_btn);
        send_btn = findViewById(R.id.send_btn);

        isCheck = getIntent().getBooleanExtra("isCheck",true);

        if (!isCheck){
            send_btn.setVisibility(View.GONE);
        }
        gridLayoutManager = new GridLayoutManager(this, 4);
        album_list.setLayoutManager(gridLayoutManager);
    }

    private void cropImage(Uri uri){
        //设置头像
        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(getResources().getColor(R.color.teal_200));
        // 修改状态栏颜色
        options.setStatusBarColor(getResources().getColor(R.color.teal_700));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
//                            options.setFreeStyleCropEnabled(true);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 圆
        options.setCircleDimmedLayer(true);
        // 不显示网格线
        options.setShowCropGrid(false);
        options.setToolbarColor(getResources().getColor(R.color.main_color));

        File file = new File(getExternalCacheDir(),"head.png");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 设置源uri及目标uri
        UCrop.of(uri, Uri.fromFile(file))
                // 长宽比
                .withAspectRatio(1, 1)
                // 配置参数
                .withOptions(options)
                .start(PhotoAlbumActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  UCrop.REQUEST_CROP&&data != null){
            photoAlbumViewModel.setHead_img(UCrop.getOutput(data));
            PhotoAlbumActivity.this.finish();
        }
    }
}
}

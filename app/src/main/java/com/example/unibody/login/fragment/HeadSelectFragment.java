package com.example.unibody.login.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.unibody.R;
import com.example.unibody.album.ui.PhotoAlbumActivity;
import com.example.unibody.album.viewmodel.PhotoAlbumViewModel;
import com.example.unibody.utils.PermissionUtil;

public class HeadSelectFragment extends Fragment {

    private String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final int REQUEST_CODE = 101;

    private ImageView head_image;
    private EditText name_tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_head_select, container, false);
        head_image = view.findViewById(R.id.head_image);
        name_tv = view.findViewById(R.id.name_tv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //image onclick listen
        head_image.setOnClickListener(v -> {
            //检查权限,没有权限则申请权限
            if (PermissionUtil.checkPermissions(PERMISSIONS_STORAGE,requireActivity(),REQUEST_CODE)) {
                Intent intent = new Intent(requireActivity(), PhotoAlbumActivity.class);
                intent.putExtra("isCheck",false);
                startActivity(intent);
            }
        });

        PhotoAlbumViewModel.getHead_img().observe(requireActivity(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                if (uri != null){
                    Glide
                            .with(getView()).load(uri)
                            .skipMemoryCache(true)//跳过内存缓存
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                            .into(head_image);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(requireActivity(), "授权失败", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    public boolean isNameEmpty(){
        return name_tv.getText().toString().isEmpty();
    }

    public String getName(){
        return  name_tv.getText().toString();
    }


}
package com.example.unibody.album.viewmodel;


import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumViewModel extends AndroidViewModel {

    private MutableLiveData<List<Uri>> photoUriList = new MutableLiveData<>();

    private static MutableLiveData<Uri> head_img = new MutableLiveData<>();


    public static List<Uri> imageList = new ArrayList<>();

    public PhotoAlbumViewModel(@NonNull Application application) {
        super(application);
        loadAlbum();
    }

    //加载相册
    private void loadAlbum(){
        Cursor cursor = getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            List<Uri> list = new ArrayList<>();
            while (cursor.moveToNext()){
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                list.add(uri);
            }
            photoUriList.setValue(list);
        }
    }

    public MutableLiveData<List<Uri>> getPhotoUriList() {
        return photoUriList;
    }

    public static MutableLiveData<Uri> getHead_img() {
        return head_img;
    }

    public void setHead_img(Uri head_img) {
        this.head_img.setValue(head_img);
    }

    public void addImage(Uri uri){
        imageList.add(uri);
    }

    public void removeImage(Uri uri){
        imageList.remove(uri);
    }

    public static List<Uri> getImageList() {
        List<Uri> uris = imageList;
        imageList.clear();
        return uris;
    }
}

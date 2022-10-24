package com.example.unibody.album.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unibody.R;

import java.util.List;

public class PhotoAlbumAdapter extends RecyclerView.Adapter<PhotoAlbumAdapter.ViewHolder> {

    private List<Uri> photoUrlList;
    private boolean isCheck;
    private GridLayoutManager gridLayoutManager;

    private OnItemClickListener onItemClickListener;
    private onCheckBoxListener onCheckBoxListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface onCheckBoxListener{
        void onChangeListener(boolean b,int position);
    }

    public PhotoAlbumAdapter(List<Uri> photoUrlList, boolean isCheck, GridLayoutManager gridLayoutManager){
        this.photoUrlList = photoUrlList;
        this.isCheck = isCheck;
        this.gridLayoutManager = gridLayoutManager;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView)
            .load(photoUrlList.get(position))
            .into(holder.imageView);
        if (!isCheck){
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.imageView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(position);
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckBoxListener.onChangeListener(isChecked,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoUrlList.size();
    }

    public void setOnCheckBoxListener(PhotoAlbumAdapter.onCheckBoxListener onCheckBoxListener) {
        this.onCheckBoxListener = onCheckBoxListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            checkBox = itemView.findViewById(R.id.checkbox);

            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width =
                    gridLayoutManager.getWidth()/gridLayoutManager.getSpanCount();
            layoutParams.height =
                    gridLayoutManager.getWidth()/gridLayoutManager.getSpanCount() - 2*imageView.getPaddingLeft();
            imageView.setLayoutParams(layoutParams);
        }
    }

}

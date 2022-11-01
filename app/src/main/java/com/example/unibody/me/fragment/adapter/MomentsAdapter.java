package com.example.unibody.me.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unibody.R;
import com.example.unibody.me.fragment.bean.MomentBean;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.CircleImageView;
import com.example.unibody.me.fragment.view.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.Holder> {

    private final CallBack callBack;
    private List<MomentBean.MomentsBean> mList;
    private Context context;

    public interface CallBack {

        void delMoment(String id);

        void showBigPhoto(String path);
    }

    public MomentsAdapter(Context context, List<MomentBean.MomentsBean> list, CallBack callBack) {
        this.context = context;
        this.mList = list;
        this.callBack = callBack;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moments, null);
        final Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(mList.get(position).getUser().getAvatar_url()).apply(new RequestOptions().placeholder(holder.headImg.getDrawable())).into(holder.headImg);
        ArrayList<String> list = new ArrayList();
        list.addAll(mList.get(position).getImgUrl());
        String name =mList.get(position).getUser().getUsername();
        holder.nameTv.setText(name);
        if (name.equals(Util.user)){
            holder.delImg.setVisibility(View.VISIBLE);
        }else {
            holder.delImg.setVisibility(View.GONE);
        }
        holder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.delMoment(mList.get(position).getId());
            }
        });

        holder.timeTv.setText(mList.get(position).getDatetime());
        holder.descTv.setText(mList.get(position).getContext());
        NinePhotoAdapter adapter = new NinePhotoAdapter(context, list, new NinePhotoAdapter.Callback() {
            @Override
            public void onPhotoClick(String path) {
                callBack.showBigPhoto(path);
            }
        });
        holder.gridImg.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        private CircleImageView headImg;
        private ImageView delImg;
        private TextView nameTv;
        private TextView descTv;
        private TextView timeTv;
        private GridViewForScrollView gridImg;

        public Holder(View itemView) {
            super(itemView);
            headImg = itemView.findViewById(R.id.iv_head);
            delImg = itemView.findViewById(R.id.iv_del);
            nameTv = itemView.findViewById(R.id.tv_name);
            timeTv = itemView.findViewById(R.id.tv_time);
            descTv = itemView.findViewById(R.id.tv_desc);
            gridImg = itemView.findViewById(R.id.grid_photo);
        }
    }
}

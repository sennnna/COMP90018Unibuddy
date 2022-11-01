package com.example.unibody.me.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unibody.R;
import com.example.unibody.me.fragment.bean.PhotoState;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.CustomRoundImageView;

import java.util.List;


public class PhotoAdapter extends BaseAdapter {

    public static final int STATE_NO_UP = 0;
    public static final int STATE_UPING = 1;
    public static final int STATE_SUCCESS_UP = 2;
    public static final int STATE_FAILED_UP = -1;

    private List<PhotoState> lists;
    private LayoutInflater layoutInflater;
    private Callback callback;
    private Context context;
    private final int mCountLimit = 9;

    public interface Callback {

        void onTakePhotoClick();

        void toPhotoDetail(int position);

        void reUpload(String path);
    }

    public PhotoAdapter(Context context, List<PhotoState> lists, Callback callback) {
        this.lists = lists;
        this.callback = callback;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (lists.size() < mCountLimit) {
            return lists.size() + 1;
        } else {
            return mCountLimit;
        }
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_photo_, null);
            holder = new ViewHolder();
            holder.imageView = (CustomRoundImageView) convertView.findViewById(R.id.iv_photo);
            holder.close = (ImageView) convertView.findViewById(R.id.iv_close);
            holder.failed = (ImageView) convertView.findViewById(R.id.iv_failed);
            holder.mask = (View) convertView.findViewById(R.id.mask);
            holder.loading = (ProgressBar) convertView.findViewById(R.id.loading);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Util.dp2px(context, 127), Util.dp2px(context, 127));
//        params.gravity = Gravity.CENTER;
        holder.imageView.setLayoutParams(params);
        if (position == getCount() - 1 && lists.size() < mCountLimit) {
            Glide.with(context).load(R.drawable.ic_add_photo).apply(new RequestOptions().placeholder(holder.imageView.getDrawable())).into(holder.imageView);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onTakePhotoClick();
                }
            });
            holder.close.setVisibility(View.GONE);
            holder.loading.setVisibility(View.GONE);
            holder.failed.setVisibility(View.GONE);
            holder.mask.setVisibility(View.GONE);
        } else {
            if (lists.get(position).isUrl()){
                Glide.with(context).load(lists.get(position).getOriginPath()).apply(new RequestOptions().placeholder(R.drawable.anim_loading)).into(holder.imageView);
            }else {
                if (TextUtils.isEmpty(lists.get(position).getPath())) {
                    Glide.with(context).load("file://" + lists.get(position).getOriginPath()).apply(new RequestOptions().placeholder(holder.imageView.getDrawable())).into(holder.imageView);
                } else {
                    Glide.with(context).load("file://" + lists.get(position).getPath()).apply(new RequestOptions().placeholder(holder.imageView.getDrawable())).into(holder.imageView);
                }
            }
            holder.close.setVisibility(View.GONE);
            holder.loading.setVisibility(View.GONE);
            holder.failed.setVisibility(View.GONE);
            holder.mask.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.toPhotoDetail(position);
                }
            });
        }
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.reUpload(lists.get(position).getPath());

            }
        });
        return convertView;
    }

    class ViewHolder {
        CustomRoundImageView imageView;
        View mask;
        ImageView close;
        ImageView failed;
        ProgressBar loading;
    }

}
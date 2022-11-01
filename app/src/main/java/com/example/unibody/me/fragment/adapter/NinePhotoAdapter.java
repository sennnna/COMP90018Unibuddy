package com.example.unibody.me.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unibody.R;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.CustomRoundImageView;

import java.util.List;


public class NinePhotoAdapter extends BaseAdapter {

    private List<String> lists;
    private LayoutInflater layoutInflater;
    private Callback callback;
    private Context context;

    public interface Callback {

        void onPhotoClick(String path);
    }

    public NinePhotoAdapter(Context context, List<String> lists, Callback callback) {
        this.lists = lists;
        this.callback = callback;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
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
            convertView = layoutInflater.inflate(R.layout.item_nine_grid_photo, null);
            holder = new ViewHolder();
            holder.imageView = (CustomRoundImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Util.dp2px(context, 127), Util.dp2px(context, 127));
        holder.imageView.setLayoutParams(params);

        Glide.with(context).load(lists.get(position)).apply(new RequestOptions().placeholder(holder.imageView.getDrawable())).into(holder.imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onPhotoClick(lists.get(position));
            }
        });

        return convertView;
    }

    class ViewHolder {
        CustomRoundImageView imageView;
    }

}
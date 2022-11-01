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
import com.example.unibody.me.fragment.bean.FriendBean;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.CircleImageView;

import java.util.List;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.Holder> {

    private final CallBack callBack;
    private List<FriendBean.FriendsBean> mList;
    private Context context;

    public interface CallBack {
        void clickItem(String friend);

        void showBigPhoto(String path);
    }

    public MyFriendsAdapter(Context context, List<FriendBean.FriendsBean> list, CallBack callBack) {
        this.context = context;
        this.mList = list;
        this.callBack = callBack;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_friends, null);
        final Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(mList.get(position).getAvatar_url()).apply(new RequestOptions().placeholder(holder.headImg.getDrawable())).into(holder.headImg);
        holder.school.setText(mList.get(position).getUniversity());
        holder.sign.setText(mList.get(position).getSign());
        holder.distance.setText(String.format("%.2f", Float.parseFloat(mList.get(position).getDistance())) + "KM");
        holder.username.setText(mList.get(position).getUsername());
        holder.age.setText(Util.getAgeByBirth(mList.get(position).getBirth()) + "");
        boolean isMale = mList.get(position).getGender().equalsIgnoreCase("male");
        Glide.with(context).load(isMale ? R.drawable.male : R.drawable.female).into(holder.sex);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.clickItem(mList.get(position).getUsername());
            }
        });
        holder.headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.showBigPhoto(mList.get(position).getAvatar_url());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        private CircleImageView headImg;
        private TextView username, age, sign, school, distance;
        private ImageView sex;

        public Holder(View itemView) {
            super(itemView);
            headImg = itemView.findViewById(R.id.iv_head);
            username = itemView.findViewById(R.id.tv_name);
            age = itemView.findViewById(R.id.tv_age);
            sign = itemView.findViewById(R.id.tv_sign);
            sex = itemView.findViewById(R.id.iv_sex);
            distance = itemView.findViewById(R.id.tv_distance);
            school = itemView.findViewById(R.id.school);
        }
    }
}

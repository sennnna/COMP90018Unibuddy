package com.example.unibody.me.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unibody.R;
import com.example.unibody.me.fragment.bean.HeadImgBean;
import com.example.unibody.me.fragment.bean.User;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.ConstellationUtil;
import com.example.unibody.me.fragment.util.Util;
import com.example.unibody.me.fragment.view.CircleImageView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MeFragment extends Fragment {

    private CircleImageView headImg;
    private ImageView statusImg;
    private TextView statusTv;
    private TextView IDTv;
    private TextView ageTv;
    private TextView nameTv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        headImg = view.findViewById(R.id.head_iv);
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.unibody.me.fragment.UserInfoActivity.class);
                startActivity(intent);
            }
        });
        IDTv = view.findViewById(R.id.tv_id);
        nameTv = view.findViewById(R.id.tv_name);
        ageTv = view.findViewById(R.id.tv_age);
        statusImg = view.findViewById(R.id.iv_status);
        statusTv = view.findViewById(R.id.tv_status);
        TextView itemFriends = view.findViewById(R.id.item_friends);
        itemFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.unibody.me.fragment.MyFriendsActivity.class);
                startActivity(intent);
            }
        });
        TextView itemAlbums = view.findViewById(R.id.item_albums);
        itemAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.unibody.me.fragment.AlbumsActivity.class);
                startActivity(intent);
            }
        });
        TextView itemSent = view.findViewById(R.id.item_sent);
        itemSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到我已发布的朋友圈
                Intent intent = new Intent(getActivity(), com.example.unibody.me.fragment.MyMomentActivity.class);
                startActivity(intent);
            }
        });
        statusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStatusDialog();
            }
        });
        loadHeadImg();
        loadDataFromServer();

        return view;
    }

    private void loadHeadImg() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserAvatar").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                HeadImgBean bean = new Gson().fromJson(data.toString(), HeadImgBean.class);

                if (null != getActivity()) {
                    Glide.with(getActivity()).load(bean.getAvatar_url()).apply(new RequestOptions().placeholder(headImg.getDrawable())).into(headImg);
                }
                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }

    private void showStatusDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("choose status");
        final String[] status = {"Single", "Duo"};
        builder.setItems(status, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStatus(status[which]);
            }
        });
        builder.show();
    }

    private void updateStatus(String status) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        map.put("status", status);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/updateUserStatus").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                //1.解析列表
//                List<UserBean> rs=new ArrayList<UserBean>();
//
//                Type type = new TypeToken<ArrayList<UserBean>>() {}.getType();
//
//                rs=new Gson().fromJson(data.toString(), type);
//
//                for(UserBean o:rs){
//                    Log.e("AAAA", "Username: "+o.getUsername());
//
//                }

                //解析对象
                User user = new Gson().fromJson(data.toString(), User.class);

                String status = user.getUser().getStatus();
                statusTv.setText(status);
                boolean isSingle = status.equalsIgnoreCase("single");
                if (null != getActivity())
                    Glide.with(getActivity()).load(isSingle ? R.drawable.ic_single : R.drawable.ic_in_love).into(statusImg);
           }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }

    private void loadDataFromServer() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", Util.user);
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserByUsername").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {


                User user = new Gson().fromJson(data.toString(), User.class);

                IDTv.setText(user.getUser().getUserID());
                nameTv.setText(user.getUser().getUsername());
                String status = user.getUser().getStatus();
                statusTv.setText(status);
                boolean isSingle = status.equalsIgnoreCase("single");
                if (null != getActivity()) {
                    Glide.with(getActivity()).load(isSingle ? R.drawable.ic_single : R.drawable.ic_in_love).into(statusImg);
//                    Glide.with(getActivity()).load(user.getUser().getAvatar_url()).apply(new RequestOptions().placeholder(headImg.getDrawable())).into(headImg);
                }
                ageTv.setText(Util.getAgeByBirth(user.getUser().getBirth()) + " " + ConstellationUtil.calculateConstellation(user.getUser().getBirth()));

                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }

}

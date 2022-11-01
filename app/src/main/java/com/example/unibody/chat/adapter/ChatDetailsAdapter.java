package com.example.unibody.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unibody.R;
import com.example.unibody.chat.domain.ChatDetails;

import java.util.Calendar;
import java.util.List;

public class ChatDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ChatDetails> chatDetailsList;
    private int head_image;
    private Calendar nowCalendar;

    private final int SYSTEM_NOTICE = -1;
    private final int TEXT_MSG = 0;
    private final int IMAGE_MSG = 1;
    private final int LEFT_TEXT_MSG = 2;
    private final int LEFT_IMAGE_MSG = 3;
    private final int RIGHT_TEXT_MSG = 4;
    private final int RIGHT_IMAGE_MSG = 5;

    public ChatDetailsAdapter(List<ChatDetails> chatDetailsList,int head_image){
        this.chatDetailsList = chatDetailsList;
        this.head_image = head_image;
        this.nowCalendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case SYSTEM_NOTICE:
                return new SystemViewHolder(inflater.inflate(R.layout.chat_system,parent,false));  //返回SystemViewHolder
            case LEFT_TEXT_MSG:
                return new LeftTextViewHolder(inflater.inflate(R.layout.chat_left_text,parent,false));  //返回LeftTextViewHolder
            case LEFT_IMAGE_MSG:
                return new LeftImageViewHolder(inflater.inflate(R.layout.chat_left_image,parent,false));  //LeftImageViewHolder
            case RIGHT_TEXT_MSG:
                return new RightTextViewHolder(inflater.inflate(R.layout.caht_right_text,parent,false));  //RightTextViewHolder
            case RIGHT_IMAGE_MSG:
                return new RightImageViewHolder(inflater.inflate(R.layout.chat_right_image,parent,false)); //RightImageViewHolder
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LeftTextViewHolder){
            buildLeftTextView((LeftTextViewHolder) holder,position);
        }else if (holder instanceof RightTextViewHolder){
            buildRightTextView((RightTextViewHolder) holder,position);
        }else if (holder instanceof LeftImageViewHolder){
            buildLeftImageView((LeftImageViewHolder) holder,position);
        }else if (holder instanceof RightImageViewHolder){
            buildRightImageView((RightImageViewHolder) holder,position);
        }else if (holder instanceof SystemViewHolder){
            buildSystemView((SystemViewHolder) holder,position);
        }
    }

    private void buildLeftTextView(LeftTextViewHolder holder,int position){
        ChatDetails chatDetails = chatDetailsList.get(position);
        holder.head_image.setImageResource(head_image);
        holder.msg_tv.setText(chatDetails.msg);
        holder.time_tv.setText(buildTime(chatDetails.time));
    }

    private void buildRightTextView(RightTextViewHolder holder,int position){
        ChatDetails chatDetails = chatDetailsList.get(position);
        holder.head_image.setImageResource(head_image);
        holder.msg_tv.setText(chatDetails.msg);
        holder.time_tv.setText(buildTime(chatDetails.time));
    }

    private void buildLeftImageView(LeftImageViewHolder holder,int position){
        ChatDetails chatDetails = chatDetailsList.get(position);
        holder.head_image.setImageResource(head_image);
        holder.time_tv.setText(buildTime(chatDetails.time));
        Glide.with(holder.itemView)
                .load(chatDetails.msg)
                .into(holder.imageView);
    }

    private void buildRightImageView(RightImageViewHolder holder,int position){
        ChatDetails chatDetails = chatDetailsList.get(position);
        holder.head_image.setImageResource(head_image);
        holder.time_tv.setText(buildTime(chatDetails.time));
        Glide.with(holder.itemView)
                .load(chatDetails.msg)
                .into(holder.imageView);
    }

    private void buildSystemView(SystemViewHolder holder,int position){
        holder.msg_tv.setText(chatDetailsList.get(position).msg);
    }

    private String buildTime(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if (calendar.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH)){
            return calendar.get(Calendar.HOUR_OF_DAY) + ":"+calendar.get(Calendar.MINUTE);
        }else if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)){
            return calendar.get(Calendar.MONTH) + "-"+calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            return calendar.get(Calendar.YEAR) + "-"+calendar.get(Calendar.MONTH) + "-"+calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatDetails chatDetails = chatDetailsList.get(position);
        if (chatDetails.type == SYSTEM_NOTICE){
            return SYSTEM_NOTICE;
        }else if (chatDetails.type == TEXT_MSG){
            if (chatDetails.isMeSend){
                return RIGHT_TEXT_MSG;
            }else {
                return LEFT_TEXT_MSG;
            }
        }else if (chatDetails.type == IMAGE_MSG){
            if (chatDetails.isMeSend){
                return RIGHT_IMAGE_MSG;
            }else {
                return LEFT_IMAGE_MSG;
            }
        }
        return -100;
    }

    @Override
    public int getItemCount() {
        return chatDetailsList.size();
    }

    class LeftTextViewHolder extends RecyclerView.ViewHolder {

        private ImageView head_image;
        private TextView msg_tv;
        private TextView time_tv;

        public LeftTextViewHolder(@NonNull View itemView) {
            super(itemView);
            head_image = itemView.findViewById(R.id.head_image);
            msg_tv = itemView.findViewById(R.id.msg_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
        }
    }

    class RightTextViewHolder extends RecyclerView.ViewHolder {

        private ImageView head_image;
        private TextView msg_tv;
        private TextView time_tv;

        public RightTextViewHolder(@NonNull View itemView) {
            super(itemView);
            head_image = itemView.findViewById(R.id.head_image);
            msg_tv = itemView.findViewById(R.id.msg_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
        }
    }

    class LeftImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView head_image;
        private ImageView imageView;
        private TextView time_tv;

        public LeftImageViewHolder(@NonNull View itemView) {
            super(itemView);
            head_image = itemView.findViewById(R.id.head_image);
            imageView = itemView.findViewById(R.id.image);
            time_tv = itemView.findViewById(R.id.time_tv);
        }
    }

    class RightImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView head_image;
        private ImageView imageView;
        private TextView time_tv;

        public RightImageViewHolder(@NonNull View itemView) {
            super(itemView);
            head_image = itemView.findViewById(R.id.head_image);
            imageView = itemView.findViewById(R.id.image);
            time_tv = itemView.findViewById(R.id.time_tv);
        }
    }

    class SystemViewHolder extends RecyclerView.ViewHolder{

        private TextView msg_tv;

        public SystemViewHolder(@NonNull View itemView) {
            super(itemView);
            msg_tv = itemView.findViewById(R.id.msg_tv);
        }
    }



}

package com.example.unibody.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.example.unibody.chat.domain.Chat;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<Chat> chatList;

    private OnItemClickListener onItemClickListener;

    private Calendar nowCalendar;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ChatListAdapter(List<Chat> chatList){
        this.chatList = chatList;
        nowCalendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (onItemClickListener != null){
            holder.rela.setOnClickListener(v -> {
                onItemClickListener.onItemClick(position);
            });
        }
        Chat chat = chatList.get(position);
        if (chat != null){
            holder.head_image.setImageResource(chat.otherImage);
            holder.msg_tv.setText(chat.lastMsg);
            holder.name_tv.setText(chat.name);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(chat.lastTime);
            if (calendar.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH)){
                holder.time_tv.setText(calendar.get(Calendar.HOUR) + ":"+calendar.get(Calendar.MINUTE));
            }else if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)){
                holder.time_tv.setText(calendar.get(Calendar.MONTH) + "-"+calendar.get(Calendar.DAY_OF_MONTH));
            }else {
                holder.time_tv.setText(calendar.get(Calendar.YEAR) + "-"+calendar.get(Calendar.MONTH) + "-"+calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout rela;
        private ImageView head_image;
        private TextView name_tv;
        private TextView time_tv;
        private TextView msg_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rela = itemView.findViewById(R.id.rela);
            head_image = itemView.findViewById(R.id.head_image);
            name_tv = itemView.findViewById(R.id.name_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            msg_tv = itemView.findViewById(R.id.msg_tv);
        }
    }

}

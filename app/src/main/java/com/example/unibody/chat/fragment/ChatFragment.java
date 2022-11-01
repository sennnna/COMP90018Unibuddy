package com.example.unibody.chat.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unibody.R;
import com.example.unibody.chat.activity.ChatDetailsActivity;
import com.example.unibody.chat.adapter.ChatListAdapter;
import com.example.unibody.chat.domain.Chat;
import com.example.unibody.chat.viewmodel.ChatViewModel;

import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView chat_list;
    private ChatViewModel chatViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        chatViewModel = new ViewModelProvider(requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ChatViewModel.class);
        ChatViewModel.setChatViewModel(chatViewModel);
        chatViewModel.chatLiveData.observe(requireActivity(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                ChatListAdapter chatListAdapter = new ChatListAdapter(chats);
                chat_list.setAdapter(chatListAdapter);
                chatListAdapter.setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(requireContext(), ChatDetailsActivity.class);
                        intent.putExtra("chat_id",chats.get(position).id);
                        intent.putExtra("index",position);
                        startActivity(intent);
                    }
                });

            }
        });

    }

    private void initView(View view){

        requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色

        chat_list = view.findViewById(R.id.chat_list);

        chat_list.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}

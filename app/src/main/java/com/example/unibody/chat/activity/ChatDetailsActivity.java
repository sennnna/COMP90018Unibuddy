package com.example.unibody.chat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.unibody.R;
import com.example.unibody.album.ui.PhotoAlbumActivity;
import com.example.unibody.album.viewmodel.PhotoAlbumViewModel;
import com.example.unibody.chat.adapter.ChatDetailsAdapter;
import com.example.unibody.chat.domain.ChatDetails;
import com.example.unibody.chat.viewmodel.ChatDetailsViewModel;
import com.example.unibody.chat.viewmodel.ChatViewModel;
import com.example.unibody.utils.PermissionUtil;

import java.util.Calendar;
import java.util.List;

public class ChatDetailsActivity extends AppCompatActivity {
    private String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ChatViewModel chatViewModel;
    private ChatDetailsViewModel chatDetailsViewModel;

    private int chat_id;
    private int index;

    private TextView name_tv;
    private ImageButton back_btn;
    private ImageButton image_btn;
    private ImageButton send_btn;
    private EditText msg_edit;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        chatViewModel = ChatViewModel.getChatViewModel();
        chatDetailsViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(
                getApplication()
        )).get(ChatDetailsViewModel.class);
        init();
        chatDetailsViewModel.chatDetailsLivData.observe(this, new Observer<List<ChatDetails>>() {
            @Override
            public void onChanged(List<ChatDetails> chatDetails) {
                ChatDetailsAdapter chatDetailsAdapter = new ChatDetailsAdapter(chatDetails, chatViewModel.chatLiveData.getValue().get(index).otherImage);
                recyclerView.setAdapter(chatDetailsAdapter);
            }
        });
        send_btn.setOnClickListener(v -> {
            String msg = msg_edit.getText().toString();
            if (msg == null || "".equals(msg)){
                return;
            }

            long time = Calendar.getInstance().getTimeInMillis();
            chatDetailsViewModel.insertChatDetails(new ChatDetails(
                    chat_id,
                    time,
                    msg,
                    true,
                    0
            ));
            chatViewModel.updateLastMsg(index,msg,time,0);
            msg_edit.setText("");
        });
        back_btn.setOnClickListener(v ->{
            finish();
        });
        image_btn.setOnClickListener(v -> {
            if (PermissionUtil.checkPermissions(PERMISSIONS_STORAGE,ChatDetailsActivity.this,0)) {
                startActivity(new Intent(ChatDetailsActivity.this, PhotoAlbumActivity.class));
            }
        });
    }

    private void init(){
        Intent intent = getIntent();
        chat_id = intent.getIntExtra("chat_id",-1);
        index = intent.getIntExtra("index",-1);
        chatDetailsViewModel.getAllChatDetails(chat_id);

        name_tv = findViewById(R.id.name_tv);
        back_btn = findViewById(R.id.back_btn);
        image_btn = findViewById(R.id.image_btn);
        send_btn = findViewById(R.id.send_btn);
        msg_edit = findViewById(R.id.msg_edit);
        recyclerView = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        name_tv.setText(chatViewModel.chatLiveData.getValue().get(index).name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("运行1");
        List<Uri> imageList = PhotoAlbumViewModel.imageList;
        System.out.println(imageList.size());
        if (imageList != null && !imageList.isEmpty()){
            System.out.println("运行2");
            long time = Calendar.getInstance().getTimeInMillis();
            for (Uri uri : imageList) {
                chatDetailsViewModel.insertChatDetails(new ChatDetails(
                        chat_id,
                        time,
                        uri.toString(),
                        true,
                        1
                ));
                chatViewModel.updateLastMsg(index,"[image]",time,1);
                imageList.clear();
            }
        }
    }
}
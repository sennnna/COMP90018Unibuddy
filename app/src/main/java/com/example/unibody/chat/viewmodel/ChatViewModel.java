package com.example.unibody.chat.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.unibody.R;
import com.example.unibody.chat.domain.Chat;
import com.example.unibody.chat.domain.ChatDetails;
import com.example.unibody.room.UniBuddyDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    public MutableLiveData<List<Chat>> chatLiveData = new MutableLiveData<>();

    private static ChatViewModel chatViewModel = null;
    private UniBuddyDatabase uniBuddyDatabase;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        uniBuddyDatabase = UniBuddyDatabase.getInstance(application.getApplicationContext());
        initChatList();
    }

    public static void setChatViewModel(ChatViewModel chatViewModel) {
        ChatViewModel.chatViewModel = chatViewModel;
    }

    public static ChatViewModel getChatViewModel() {
        return chatViewModel;
    }

    private void initChatList(){
        SharedPreferences config = getApplication().getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean firstInsert = config.getBoolean("firstInsert", true);
        final List<ChatDetails> chatDetailsList = new ArrayList<>();
        final List<Chat> chatList = new ArrayList<>();
        if (firstInsert){
            chatList.add(new Chat(0,R.drawable.hiroshi,"Hiroshi",
                    "thx, I'm from the class of 2021 in the School of Engineering, how about you?",
                    1666698821012L,0));
            chatList.add(new Chat(1,R.drawable.anny,"Anny",
                    "That's fateful",
                    1666699078003L,0));
            chatList.add(new Chat(2,R.drawable.khalida,"Khalida",
                    "check out my album",
                    1666700487022L,0));

            chatDetailsList.add(new ChatDetails(0, 0, 0L,
                            "You have passed the exam from Hiroshi",
                            false, -1
                    )
            );
            chatDetailsList.add(new ChatDetails(1, 0, 1666698748023L,
                            "Congratulations you passed!",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(2, 0, 1666698760031L,
                            "Wow this is my first time using this app to answer a questionnaire..",
                            true, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(3, 0, 1666698776050L,
                            "I like your answer sheet and you are my alumni.",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(4, 0, 1666698821012L,
                            "thx, I'm from the class of 2021 in the School of Engineering, how about you?",
                            true, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(5, 1, 0L,
                            "You've let Anny pass your exam",
                            true, -1
                    )
            );
            chatDetailsList.add(new ChatDetails(6, 1, 1666699041019L,
                            "！！",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(7, 1, 1666699043012L,
                            "I took COMP90018 with you last semester, remember?",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(8, 1, 1666699049042L,
                            "how did you find me?o.0",
                            true, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(9, 1, 1666699069052L,
                            "Only 200 meters between us...",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(10, 1, 1666699078003L,
                            "That's fateful",
                            true, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(11, 2, 0L,
                            "You have passed the exam from Khalida",
                            false, -1
                    )
            );
            chatDetailsList.add(new ChatDetails(12, 2, 1666700271006L,
                            "I saw your post, you like diving right?",
                            true, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(13, 2, 1666700278041L,
                            "Ye",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(14, 2, 1666700281033L,
                            "I'm actually a professional diver",
                            true, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(15, 2, 1666700311011L,
                            "hh how do you prove it",
                            false, 0
                    )
            );
            chatDetailsList.add(new ChatDetails(16, 2, 1666700487022L,
                            "check out my album",
                            true, 0
                    )
            );
            for (Chat chat1 : chatList) {
                uniBuddyDatabase.chatDao().insertChatList(chat1);
            }
            for (ChatDetails chatDetails : chatDetailsList) {
                uniBuddyDatabase.chatDetailsDao().insertChatDetails(chatDetails);
            }
            SharedPreferences.Editor edit = config.edit();
            edit.putBoolean("firstInsert",false);
            edit.commit();

        }else {
            //查询数据库
            chatList.addAll(uniBuddyDatabase.chatDao().getAllChatList());
        }
        chatLiveData.setValue(chatList);
    }


    public void updateLastMsg(int index,String lastMsg,long lastTime,int type){
        List<Chat> value = chatLiveData.getValue();
        Chat chat = value.get(index);
        chat.lastMsg = lastMsg;
        chat.lastTime = lastTime;
        chat.type = type;
        value.set(index,chat);
        uniBuddyDatabase.chatDao().updateLastChat(chat);
        chatLiveData.setValue(value);
    }

}

package com.example.unibody.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.unibody.chat.dao.ChatDetailsDao;
import com.example.unibody.chat.domain.ChatDetails;
import com.example.unibody.room.UniBuddyDatabase;

import java.util.List;

public class ChatDetailsViewModel extends AndroidViewModel {

    public MutableLiveData<List<ChatDetails>> chatDetailsLivData = new MutableLiveData<>();

    private ChatDetailsDao chatDetailsDao;

    public ChatDetailsViewModel(@NonNull Application application) {
        super(application);
        chatDetailsDao = UniBuddyDatabase.getInstance(application.getApplicationContext()).chatDetailsDao();
    }

    public void getAllChatDetails(int chat_id){
        List<ChatDetails> allChatDetailsByChatId = chatDetailsDao.getAllChatDetailsByChatId(chat_id);
        chatDetailsLivData.setValue(allChatDetailsByChatId);
    }

    public void insertChatDetails(ChatDetails chatDetails){
        List<ChatDetails> value = chatDetailsLivData.getValue();
        value.add(chatDetails);
        chatDetailsLivData.setValue(value);
        chatDetailsDao.insertChatDetails(chatDetails);
    }

}

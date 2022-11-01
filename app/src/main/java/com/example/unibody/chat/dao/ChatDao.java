package com.example.unibody.chat.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.unibody.chat.domain.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChatList(Chat chat);

    @Update
    void updateLastChat(Chat chat);

    @Delete
    void deleteChat(Chat chat);

    @Query("SELECT * FROM chat")
    List<Chat> getAllChatList();

    @Query("SELECT * FROM chat WHERE id = :id")
    Chat getChatById(int id);
}

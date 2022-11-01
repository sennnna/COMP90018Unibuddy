package com.example.unibody.chat.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.unibody.chat.domain.ChatDetails;

import java.util.List;

@Dao
public interface ChatDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChatDetails(ChatDetails chatDetails);

    @Delete
    void deleteChatDetails(ChatDetails chatDetails);

    @Update
    void updateChatDetails(ChatDetails chatDetails);

    @Query("SELECT * FROM chat_details WHERE target = :target")
    List<ChatDetails> getAllChatDetailsByChatId(int target);
}

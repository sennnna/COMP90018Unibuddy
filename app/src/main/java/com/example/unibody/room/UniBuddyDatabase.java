package com.example.unibody.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.unibody.chat.dao.ChatDao;
import com.example.unibody.chat.dao.ChatDetailsDao;
import com.example.unibody.chat.domain.Chat;
import com.example.unibody.chat.domain.ChatDetails;

@Database(entities = {Chat.class,ChatDetails.class},version = 1,exportSchema = false)
public abstract class UniBuddyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "unibuddy.db";

    private static UniBuddyDatabase uniBuddyDatabase = null;

    public static UniBuddyDatabase getInstance(Context context){
        if (uniBuddyDatabase == null){
            uniBuddyDatabase = Room
                    .databaseBuilder(context,UniBuddyDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return uniBuddyDatabase;
    }

    public abstract ChatDao chatDao();

    public abstract ChatDetailsDao chatDetailsDao();

}

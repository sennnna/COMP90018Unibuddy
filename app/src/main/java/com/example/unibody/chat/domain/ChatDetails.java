package com.example.unibody.chat.domain;

import androidx.room.*;

@Entity(tableName = "chat_details")
public class ChatDetails {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;
    public int target;
    public long time;
    public String msg;
    public boolean isMeSend;
    public int type;

    public ChatDetails(int id, int target, long time, String msg, boolean isMeSend,int type) {
        this.id = id;
        this.target = target;
        this.time = time;
        this.msg = msg;
        this.isMeSend = isMeSend;
        this.type = type;
    }

    @Ignore
    public ChatDetails(int target, long time, String msg, boolean isMeSend,int type) {
        this.target = target;
        this.time = time;
        this.msg = msg;
        this.isMeSend = isMeSend;
        this.type = type;
    }
}

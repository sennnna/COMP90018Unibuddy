package com.example.unibody.chat.domain;


import androidx.room.*;

@Entity(tableName = "chat")
public class Chat {

    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;
    public int otherImage;
    public String name;
    public String lastMsg;
    public long lastTime;
    public int type;

    public Chat(int id, int otherImage, String name, String lastMsg, long lastTime,int type) {
        this.id = id;
        this.otherImage = otherImage;
        this.name = name;
        this.lastMsg = lastMsg;
        this.lastTime = lastTime;
        this.type = type;
    }

    @Ignore
    public Chat(int otherImage, String name, String lastMsg, long lastTime,int type) {
        this.otherImage = otherImage;
        this.name = name;
        this.lastMsg = lastMsg;
        this.lastTime = lastTime;
        this.type = type;
    }
}

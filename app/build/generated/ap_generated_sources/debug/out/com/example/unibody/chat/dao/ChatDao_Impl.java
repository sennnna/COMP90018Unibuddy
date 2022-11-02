package com.example.unibody.chat.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.unibody.chat.domain.Chat;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatDao_Impl implements ChatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Chat> __insertionAdapterOfChat;

  private final EntityDeletionOrUpdateAdapter<Chat> __deletionAdapterOfChat;

  private final EntityDeletionOrUpdateAdapter<Chat> __updateAdapterOfChat;

  public ChatDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChat = new EntityInsertionAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `chat` (`id`,`otherImage`,`name`,`lastMsg`,`lastTime`,`type`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.otherImage);
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        if (value.lastMsg == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.lastMsg);
        }
        stmt.bindLong(5, value.lastTime);
        stmt.bindLong(6, value.type);
      }
    };
    this.__deletionAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `chat` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `chat` SET `id` = ?,`otherImage` = ?,`name` = ?,`lastMsg` = ?,`lastTime` = ?,`type` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.otherImage);
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        if (value.lastMsg == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.lastMsg);
        }
        stmt.bindLong(5, value.lastTime);
        stmt.bindLong(6, value.type);
        stmt.bindLong(7, value.id);
      }
    };
  }

  @Override
  public void insertChatList(final Chat chat) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChat.insert(chat);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteChat(final Chat chat) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfChat.handle(chat);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateLastChat(final Chat chat) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfChat.handle(chat);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Chat> getAllChatList() {
    final String _sql = "SELECT * FROM chat";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final List<Chat> _result = new ArrayList<Chat>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Chat _item;
        _item = __entityCursorConverter_comExampleUnibodyChatDomainChat(_cursor);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Chat getChatById(final int id) {
    final String _sql = "SELECT * FROM chat WHERE id = :id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final Chat _result;
      if(_cursor.moveToFirst()) {
        _result = __entityCursorConverter_comExampleUnibodyChatDomainChat(_cursor);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  private Chat __entityCursorConverter_comExampleUnibodyChatDomainChat(Cursor cursor) {
    final Chat _entity;
    final int _cursorIndexOfId = cursor.getColumnIndex("id");
    final int _cursorIndexOfOtherImage = cursor.getColumnIndex("otherImage");
    final int _cursorIndexOfName = cursor.getColumnIndex("name");
    final int _cursorIndexOfLastMsg = cursor.getColumnIndex("lastMsg");
    final int _cursorIndexOfLastTime = cursor.getColumnIndex("lastTime");
    final int _cursorIndexOfType = cursor.getColumnIndex("type");
    final int _tmpId;
    if (_cursorIndexOfId == -1) {
      _tmpId = 0;
    } else {
      _tmpId = cursor.getInt(_cursorIndexOfId);
    }
    final int _tmpOtherImage;
    if (_cursorIndexOfOtherImage == -1) {
      _tmpOtherImage = 0;
    } else {
      _tmpOtherImage = cursor.getInt(_cursorIndexOfOtherImage);
    }
    final String _tmpName;
    if (_cursorIndexOfName == -1) {
      _tmpName = null;
    } else {
      _tmpName = cursor.getString(_cursorIndexOfName);
    }
    final String _tmpLastMsg;
    if (_cursorIndexOfLastMsg == -1) {
      _tmpLastMsg = null;
    } else {
      _tmpLastMsg = cursor.getString(_cursorIndexOfLastMsg);
    }
    final long _tmpLastTime;
    if (_cursorIndexOfLastTime == -1) {
      _tmpLastTime = 0;
    } else {
      _tmpLastTime = cursor.getLong(_cursorIndexOfLastTime);
    }
    final int _tmpType;
    if (_cursorIndexOfType == -1) {
      _tmpType = 0;
    } else {
      _tmpType = cursor.getInt(_cursorIndexOfType);
    }
    _entity = new Chat(_tmpId,_tmpOtherImage,_tmpName,_tmpLastMsg,_tmpLastTime,_tmpType);
    return _entity;
  }
}

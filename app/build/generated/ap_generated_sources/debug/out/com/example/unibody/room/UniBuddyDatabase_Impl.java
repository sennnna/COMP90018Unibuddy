package com.example.unibody.room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.unibody.chat.dao.ChatDao;
import com.example.unibody.chat.dao.ChatDao_Impl;
import com.example.unibody.chat.dao.ChatDetailsDao;
import com.example.unibody.chat.dao.ChatDetailsDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UniBuddyDatabase_Impl extends UniBuddyDatabase {
  private volatile ChatDao _chatDao;

  private volatile ChatDetailsDao _chatDetailsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `chat` (`id` INTEGER NOT NULL, `otherImage` INTEGER NOT NULL, `name` TEXT, `lastMsg` TEXT, `lastTime` INTEGER NOT NULL, `type` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `chat_details` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `target` INTEGER NOT NULL, `time` INTEGER NOT NULL, `msg` TEXT, `isMeSend` INTEGER NOT NULL, `type` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '292c54973a3d54ca3453f88827f238fd')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `chat`");
        _db.execSQL("DROP TABLE IF EXISTS `chat_details`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsChat = new HashMap<String, TableInfo.Column>(6);
        _columnsChat.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChat.put("otherImage", new TableInfo.Column("otherImage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChat.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChat.put("lastMsg", new TableInfo.Column("lastMsg", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChat.put("lastTime", new TableInfo.Column("lastTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChat.put("type", new TableInfo.Column("type", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChat = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChat = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChat = new TableInfo("chat", _columnsChat, _foreignKeysChat, _indicesChat);
        final TableInfo _existingChat = TableInfo.read(_db, "chat");
        if (! _infoChat.equals(_existingChat)) {
          return new RoomOpenHelper.ValidationResult(false, "chat(com.example.unibody.chat.domain.Chat).\n"
                  + " Expected:\n" + _infoChat + "\n"
                  + " Found:\n" + _existingChat);
        }
        final HashMap<String, TableInfo.Column> _columnsChatDetails = new HashMap<String, TableInfo.Column>(6);
        _columnsChatDetails.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatDetails.put("target", new TableInfo.Column("target", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatDetails.put("time", new TableInfo.Column("time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatDetails.put("msg", new TableInfo.Column("msg", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatDetails.put("isMeSend", new TableInfo.Column("isMeSend", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatDetails.put("type", new TableInfo.Column("type", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatDetails = new TableInfo("chat_details", _columnsChatDetails, _foreignKeysChatDetails, _indicesChatDetails);
        final TableInfo _existingChatDetails = TableInfo.read(_db, "chat_details");
        if (! _infoChatDetails.equals(_existingChatDetails)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_details(com.example.unibody.chat.domain.ChatDetails).\n"
                  + " Expected:\n" + _infoChatDetails + "\n"
                  + " Found:\n" + _existingChatDetails);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "292c54973a3d54ca3453f88827f238fd", "229123674b62123d174884fa016b2923");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "chat","chat_details");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `chat`");
      _db.execSQL("DELETE FROM `chat_details`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ChatDao chatDao() {
    if (_chatDao != null) {
      return _chatDao;
    } else {
      synchronized(this) {
        if(_chatDao == null) {
          _chatDao = new ChatDao_Impl(this);
        }
        return _chatDao;
      }
    }
  }

  @Override
  public ChatDetailsDao chatDetailsDao() {
    if (_chatDetailsDao != null) {
      return _chatDetailsDao;
    } else {
      synchronized(this) {
        if(_chatDetailsDao == null) {
          _chatDetailsDao = new ChatDetailsDao_Impl(this);
        }
        return _chatDetailsDao;
      }
    }
  }
}

package com.ahaya.earthquakeviewer.sample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


/**
 * @Usage
 * HoardDBOpenHelper hoardDBOpenHelper = new HoardDBOpenHelper(
 * context,
 * HoardDBOpenHelper.DATABASE_NAME,null,
 * HoardDBOpenHelper.DATABASE_VERSION
 * );
 * SQLiteDatabase db;
 * try {
 *     db = hoardDBOpenHelper.getWritableDatabase();
 * }catch (SQLiteException e){
 *     db = hoardDBOpenHelper.getReadableDatabase();
 * }
 */
public class HoardDBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDatabase.db";
    public static final String DATABASE_TABLE = "GoldHoards";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            " create table " + DATABASE_TABLE + "( " +
                    HoardContract.KEY_ID +" integer primary key autoincrement, " +
                    HoardContract.KEY_GOLD_HOARD_NAME_COLUMN + "text not null, " +
                    HoardContract.KEY_GOLD_HOARD_COLUMN + " float, "+
                    HoardContract.KEY_GOLD_HOARD_ACCESSIBLE_COLUMN +" integer);";

    public HoardDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
//当磁盘中不存在数据库且helper累需要创建新数据库时调用；
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }
//当存在的数据库版本不匹配时调用，这意味着磁盘上的数据库版本需要升级到当前版本；
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //打印版本升级;
        Log.w("TASKDB_ADAPTER","Upgrading from version" +
                oldVersion + " to " +
                newVersion + " , which will destory all old data ");
        //通过version 比较，来升级数据库
        /**
         * @Warning 这里直接删除了数据 但是对于在线数据库不同步或难以重新获得的重要数据时 应该将已有数据迁移到新表中；
         */
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        //创建一个新的数据库
        onCreate(db);

    }
}

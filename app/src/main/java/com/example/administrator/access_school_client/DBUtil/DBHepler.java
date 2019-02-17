package com.example.administrator.access_school_client.DBUtil;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/31 11:18.
 */
public class DBHepler extends SQLiteOpenHelper{

    public DBHepler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHepler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //消息表
        String sql = "create table if not exists message(" +
                "id Integer primary key AUTOINCREMENT not null ," + //主键id
                "fromwho Integer," +//发送者id
                "towho Integer," +//目标id
                "msg varchar(50)," +//发送时间
                "time datetime," +
                "isComemsg Integer)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.administrator.access_school_client.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.access_school_client.DBUtil.DBHepler;
import com.example.administrator.access_school_client.bean.MessageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/2/2 11:09.
 */
public class MsgDao {
    SQLiteDatabase sqLiteDatabase = null;
    DBHepler dbHepler = null;

    public MsgDao(Context mcontext){
        //各种初始化
        dbHepler = new DBHepler(mcontext,"msg.db",null,1);
        sqLiteDatabase = dbHepler.getWritableDatabase();
    }

    public List<MessageEntity> getSimpleHistoryMessage(int who){
        //查询相应from
        List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
        String sql = "select * from message where fromwho in " +
                "(select DISTINCT (fromwho or towho) as who from message where who != ?)" +
                "or towho in " +
                "(select DISTINCT (fromwho or towho) as who from message where who != ?)" +
                " order by time asc limit 0,1";
        Cursor cursor = sqLiteDatabase.rawQuery(sql , new String[]{});
        while(cursor.moveToNext()){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setFrom(cursor.getInt(cursor.getColumnIndex("fromwho")));
            messageEntity.setTo(cursor.getInt(cursor.getColumnIndex("towho")));
            messageEntity.setMessage(cursor.getString(cursor.getColumnIndex("msg")));
            messageEntity.setTime(cursor.getString(cursor.getColumnIndex("time")));
            messageEntity.setComeMsg(cursor.getInt(cursor.getColumnIndex("isComemsg")));
            messageEntities.add(messageEntity);
        }
        return messageEntities;
    }

    public List<MessageEntity> getHistoryMessage(int who){
//        Log.e("MsgDao:::::::",position+"回显!!!");
        //查询相应from
        List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
        String sql = "select * from message where towho = ? or fromwho= ? order by time asc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(who),String.valueOf(who)});
        while(cursor.moveToNext()){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setFrom(cursor.getInt(cursor.getColumnIndex("fromwho")));
            messageEntity.setTo(cursor.getInt(cursor.getColumnIndex("towho")));
            messageEntity.setMessage(cursor.getString(cursor.getColumnIndex("msg")));
            messageEntity.setTime(cursor.getString(cursor.getColumnIndex("time")));
            messageEntity.setComeMsg(cursor.getInt(cursor.getColumnIndex("isComemsg")));
            messageEntities.add(messageEntity);
        }
        return messageEntities;
    }

    public int getMessageRows(){
        int rows = 0;//rows默认是0
        //查询不同来源的fromwho的数量
        String sql = "select count(DISTINCT fromwho) as count from message";
        Cursor cursor = sqLiteDatabase.rawQuery(sql , new String[]{});
        while(cursor.moveToNext()){
            rows = cursor.getInt(cursor.getColumnIndex("count"));
        }
        return rows;
    }

    public List<MessageEntity> getMessage(){
        List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
        String sql = "select * from message";
        Cursor cursor = sqLiteDatabase.rawQuery(sql , new String[]{});
        while(cursor.moveToNext()){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setFrom(cursor.getInt(cursor.getColumnIndex("fromwho")));
            messageEntity.setTo(cursor.getInt(cursor.getColumnIndex("towho")));
            messageEntity.setMessage(cursor.getString(cursor.getColumnIndex("msg")));
            messageEntity.setTime(cursor.getString(cursor.getColumnIndex("time")));
            messageEntity.setComeMsg(cursor.getInt(cursor.getColumnIndex("isComemsg")));
            messageEntities.add(messageEntity);
        }
        return messageEntities;
    }

    public void addMessage(MessageEntity messageEntity){
        String sql = "insert into message(fromwho,towho,msg,time,isComemsg) values(?,?,?,?,?)";
        sqLiteDatabase.execSQL(sql,new String[]{String.valueOf(messageEntity.getFrom()),
                String.valueOf(messageEntity.getTo()),
                messageEntity.getMessage(),
                messageEntity.getTime(),
                String.valueOf(messageEntity.isComeMsg())
        });
    }
}

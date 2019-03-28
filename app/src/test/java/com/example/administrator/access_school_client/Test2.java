package com.example.administrator.access_school_client;

import com.example.administrator.access_school_client.DBUtil.DBHepler;
import com.example.administrator.access_school_client.Util.SharedPreferencesUtils;

import org.junit.Test;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/3/8 08:55.
 */
public class Test2 {

    @Test
    public void m1(){
        String username = SharedPreferencesUtils.getUserName("username");
        System.out.print(username);
    }

    public void m2(){
//        dbHepler = new DBHepler(mcontext,"msg.db",null,1);
//        sqLiteDatabase = dbHepler.getWritableDatabase();
    }
}

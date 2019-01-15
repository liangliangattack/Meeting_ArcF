package com.example.administrator.access_school_client.Service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.access_school_client.Util.Base64;
import com.example.administrator.access_school_client.bean.TransObj;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class MusicService extends Service {
    private MediaPlayer player;
    private String speakUrl;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        speakUrl = intent.getStringExtra("speakUrl");
//        Toast.makeText(this, ""+speakUrl+"00000", Toast.LENGTH_SHORT).show();
        Log.e(this.getClass().getSimpleName(),speakUrl);
        player = new MediaPlayer();
        try {
            //Toast.makeText(this, ""+speakUrl+"1111111", Toast.LENGTH_SHORT).show();

            //设置play的资源
            player.setDataSource(speakUrl);
            //准备资源
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("服务", "准备播放音乐");
        Log.e("服务", "开始");
//        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //当执行完了onCreate后，就会执行onBind把操作歌曲的方法返回
        speakUrl = intent.getStringExtra("speakUrl");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //这里只执行一次，用于准备播放器

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    //该方法包含关于歌曲的操作
    public class MyBinder extends Binder {

        //判断是否处于播放状态
        public boolean isPlaying(){
            return player.isPlaying();
        }

        //播放或暂停歌曲
        public void play() {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
            Log.e("服务", "播放音乐");
        }

//        //返回歌曲的长度，单位为毫秒
//        public int getDuration(){
//            return player.getDuration();
//        }
//
//        //返回歌曲目前的进度，单位为毫秒
//        public int getCurrenPostion(){
//            return player.getCurrentPosition();
//        }
//
//        //设置歌曲播放的进度，单位为毫秒
//        public void seekTo(int mesc){
//            player.seekTo(mesc);
//        }
    }
}


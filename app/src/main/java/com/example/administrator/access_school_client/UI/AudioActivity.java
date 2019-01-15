package com.example.administrator.access_school_client.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Service.MusicService;
import com.example.administrator.access_school_client.Util.Base64;
import com.example.administrator.access_school_client.bean.TransObj;
import com.example.administrator.access_school_client.bean.TransText;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/4 14:02.
 */
public class AudioActivity extends AppCompatActivity{

    private ImageView play;
    private EditText english;
    private MyConnection conn;
    private MusicService.MyBinder musicControl;
    private Button transbtn;
    private Boolean isBind = false;
    private Spinner sp;
    private String to = "zh-CHS";
    private String speakUrl = null;
    private String tSpeakUrl = null;
    private String q = null;
    private TextView soundaudio;
    private TextView transaudio;
    private TextView transwebaudio;
    private LinearLayout transall;
//    private SeekBar seekBar;
//    private static final int UPDATE_PROGRESS = 0;
    private Handler handler = new Handler(){
    @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    updatePlayImg();
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_audio);
        play = findViewById(R.id.play);
        english = findViewById(R.id.english);
        transbtn = findViewById(R.id.trans);
        sp = findViewById(R.id.style_audio);
        soundaudio = findViewById(R.id.sound_audio);
        transaudio = findViewById(R.id.trans_audio);
        transwebaudio = findViewById(R.id.trans_web_audio);
        transall = findViewById(R.id.trans_all_ll);

        conn = new MyConnection();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String style = parent.getItemAtPosition(position).toString();
                switch (style){
                    case "中文":to = "zh-CHS";break;
                    case "英文":to = "en";break;
                    case "韩文":to = "ko";break;
                    case "法文":to = "fr";break;
                    case "俄文":to = "ru";break;
                    case "葡萄牙文":to = "pt";break;
                    case "西班牙文":to = "es";break;
                    case "越南文":to = "vi";break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                to = "zh-CHS";
            }
        });

        transbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AudioActivity.this, ""+english.getText().toString(), Toast.LENGTH_SHORT).show();
                q = english.getText().toString();
                //首先实现网路操作翻译

                String appKey = "3f2fd394a462345f";
                String appSecret = "9vcWJHrxW985AIp21CaR1TPYlgfXAOBs";
                Log.e(this.getClass().getSimpleName(),"：");
                texttrans(appKey,appSecret);
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setVisibility(View.GONE);
//                transall.setVisibility(View.GONE);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //播放
                play();
            }
        });
    }

    public void texttrans(String appKey,String appSecret){
        /** 文本翻译接口地址 */
        String url = "http://openapi.youdao.com/api";

        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();

        Log.e(this.getClass().getSimpleName(),"");

        String salt = String.valueOf(System.currentTimeMillis());
        String from = "auto";
//        String type = "1";
        String sign = null;
        params.put("appKey",appKey);
        params.put("salt",salt);
        params.put("from",from);
        params.put("to",to);
//        params.put("type",type);

        /** 请求文本翻译 */

        params.put("q", q);
        try {
            sign = MD5(appKey + q + salt +appSecret,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("sign",sign);

//        result = requestForHttp(url,params);
        Log.e(this.getClass().getSimpleName(),""+to);
        http.post(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                getR(o);
                Log.e(this.getClass().getSimpleName(),"onsuccess");
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e(this.getClass().getSimpleName(),"failer");
            }
        });
    }

    void getR(Object o){
        Gson gson = new Gson();
        JsonObject jsonObject1 = gson.fromJson(o.toString(),JsonObject.class);
        try {
//            StringBuffer stringBuffer = new StringBuffer();
            TransText tt = new TransText();
//            JSONObject jsonObject = new JSONObject(String.valueOf(jsonObject1.get("translation")));
            tt.setTranslation(jsonObject1.get("translation").toString());
            Log.e("", jsonObject1.get("translation").toString());

            Log.e(this.getClass().getSimpleName(),tt.getTranslation());

//            JSONArray jsonArray = new JSONArray(jsonObject1.get("basic").toString());

            tt.setTranslation(jsonObject1.get("translation").toString());


            JsonObject jsonObject = jsonObject1.getAsJsonObject("basic");
            if(jsonObject!=null) {
                //设置文本的音标
                String[] basic = new String[5];
                basic[0] = jsonObject.get("phonetic").toString();
                basic[1] = jsonObject.get("uk-phonetic").toString();
                basic[2] = jsonObject.get("us-phonetic").toString();
                basic[3] = jsonObject.get("uk-speech").toString();
                basic[4] = jsonObject.get("us-speech").toString();
                tt.setBasic(basic);
                soundaudio.setText("phonetic:"+basic[0]+"\r\n英式音标:"+basic[1]+"\r\n美式音标:"+basic[2]);

            //设置文本的基本释义
            JSONArray array = new JSONArray(jsonObject.get("explains").toString());
            StringBuffer explains = new StringBuffer();
            for (int i=0;i<array.length();i++){
                Log.e(this.getClass().getSimpleName(),array.getString(i));
                explains.append(array.getString(i)+"\r\n");
            }
            tt.setExplains(explains);
            }else{
                tt.setBasic(null);
                tt.setExplains(null);
            }
            transaudio.setText(tt.getExplains());


            //设置文本的web释义
            JSONArray jsonArray = new JSONArray(jsonObject1.get("web").toString());
            if(jsonArray != null) {
//                List<String> webs = new ArrayList<String>();
                String result="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String key = jo.get("key").toString();
                    JSONArray ja = new JSONArray(jo.get("value").toString());
                    String str = "";
                    StringBuffer stringBuffer = new StringBuffer(key+"\r\n");
                    for (int j = 0; j < ja.length(); j++) {
                        String s = ja.get(j).toString();
                        stringBuffer.append(s.toString() + ";");
                    }
                    str = stringBuffer.toString();
                    result+="\r\n"+str;
                }
                tt.setWeb(result);
            }else {
                tt.setWeb(null);
            }
            transwebaudio.setText(tt.getWeb());


            //设置文本发音网址
//            tt.settSpeakUrl(jsonObject1.get("tSpeakUrl").toString());
//            //设置文本发音网址
//            tt.setSpeakUrl(jsonObject1.get("speakUrl").toString());
//
//            tSpeakUrl = tt.gettSpeakUrl();
//            speakUrl = tt.getSpeakUrl();
//            Log.e("AudioActivity",jsonObject1.get("speakUrl".toString()));

            StringBuffer stringBuffer = new StringBuffer("http://dict.youdao.com/dictvoice?audio=");
            stringBuffer.append(q);
            speakUrl = stringBuffer.toString();
//            for(int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                TransObj t = new TransObj(jsonObject.getString("context"),
//                        jsonObject.getString("tranContent"));
//                transObjs.add(t);
//                String str = i+"."+t.getContext()+"\n"+t.getTranContent()+"\n"+"  ";
//                stringBuffer.append(str);
//            }
//            display.setText(stringBuffer);
//            bar.setVisibility(View.GONE);
//            display.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e("TransImgAct","--------------------------------------------------");
            e.printStackTrace();
            Log.e("TransImgAct","--------------------------------------------------");
        }

        //在这里启动音乐播放服务
        //然后实现读音网址的返回
        //准备读音..................

        //判断是否是句子
        if(isSingle()) {
            play.setVisibility(View.VISIBLE);
        }
//        transall.setVisibility(View.VISIBLE);
        Intent intent3 = new Intent(AudioActivity.this, MusicService.class);
        //使用混合的方法开启服务
//                    speakUrl
        intent3.putExtra("speakUrl", speakUrl);
//                    intent3.putExtra("to",to);
        startService(intent3);
        bindService(intent3, conn, BIND_AUTO_CREATE);
        isBind = true;
        Toast.makeText(AudioActivity.this, "正在启动", Toast.LENGTH_SHORT).show();

//                }
//                else {
//                    Toast.makeText(AudioActivity.this, "请输入英文单词....", Toast.LENGTH_SHORT).show();
//                }

    }

    private Boolean isSingle(){
        Boolean issingle = true;
        String[] s = q.split("\\s+");
        if(s.length >= 2){
            issingle = false;
        }
        return issingle;
    }

    public static String getBase64OfFile(File file){
        byte[] data = null;
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(file));
            data = new byte[in.available()];
            in.read(data);

        }catch (Exception e){
            e.printStackTrace();
        }
        return Base64.encode(data);
    }

    /**
     * MD5加密
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    private String isEnglish(String text){
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(text);
        if(m.matches() ){
            Toast.makeText(AudioActivity.this,"输入的是数字", Toast.LENGTH_SHORT).show();
            return "math";
        }
        p=Pattern.compile("[a-zA-Z]");
        m=p.matcher(text);
        if(m.matches()){
            Toast.makeText(AudioActivity.this,"输入的是字母", Toast.LENGTH_SHORT).show();
            return "english";
        }
        p=Pattern.compile("[\u4e00-\u9fa5]");
        m=p.matcher(text);
        if(m.matches()){
            Toast.makeText(AudioActivity.this,"输入的是汉字", Toast.LENGTH_SHORT).show();
            return "zhong";
        }
        else {
            return "null";
        }
    }

    private class MyConnection implements ServiceConnection {

        //服务启动完成后会进入到这个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得service中的MyBinder
            musicControl = (MusicService.MyBinder) service;


            //更新按钮
            updatePlayImg();
            //设置进度条的最大值
//            seekBar.setMax(musicControl.getDuration());
            //设置进度条的进度
//            seekBar.setProgress(musicControl.getCurrenPostion());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //进入到界面后开始更新进度条
//        if (musicControl != null){
//            handler.sendEmptyMessage(UPDATE_PROGRESS);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出应用后与service解除绑定
        if(isBind) {
            unbindService(conn);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止更新进度条的进度
//        handler.removeCallbacksAndMessages(null);
    }

    //更新进度条
    private void updateProgress() {
//        int currenPostion = musicControl.getCurrenPostion();
//        seekBar.setProgress(currenPostion);
//        //使用Handler每500毫秒更新一次进度条
//        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
    }


    //更新按钮的文字
    public void updatePlayImg() {
        if (musicControl.isPlaying()) {
            play.setImageResource(R.drawable.stop);
//            handler.sendEmptyMessage(UPDATE_PROGRESS);
        } else {
            play.setImageResource(R.drawable.play);
        }
        handler.sendEmptyMessageDelayed(1,500);
    }

    //调用MyBinder中的play()方法
    public void play() {
        musicControl.play();
        updatePlayImg();
    }

}

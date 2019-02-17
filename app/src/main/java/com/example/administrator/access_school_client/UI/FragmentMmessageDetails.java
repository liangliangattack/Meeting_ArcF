package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.google.gson.JsonObject;
import com.guo.android_extend.java.network.socket.SocketServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentMmessageDetails extends Fragment implements View.OnClickListener{

    private Button startscoket;
    private Button send;
    private EditText message;
    private EditText display;

    private StringBuffer stringBuffer = new StringBuffer();

    BufferedReader reader;
    BufferedWriter writer;
    private Socket socket;

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    stringBuffer.append(msg.obj);
                    display.setText(stringBuffer.toString());
                    break;
                    default:break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_mmessage_details,container,false);
        startscoket = (Button)view.findViewById(R.id.startScoket);
        startscoket.setOnClickListener(this);
        send = (Button)view.findViewById(R.id.ms_details_btn_send);
        send.setOnClickListener(this);
        message = (EditText) view.findViewById(R.id.ms_details_et_message);
        display = (EditText) view.findViewById(R.id.ms_details_et_display);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startScoket:
                //初始化
                initScoket();
                break;
            case R.id.ms_details_btn_send:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        if(message.getText()!=null){
            try {
                //封装json数据上传服务器
                JSONObject json = new JSONObject();
                try {
                    json.put("to",1);
                    json.put("msg",message.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                writer.write(message.getText().toString()+"\n");
                writer.write(json.toString()+"\n");
                Log.e(null,"发出json:"+json.toString());
                //flush写入缓存里面的数据
                writer.flush();

                String str = "\n"+"我: "+message.getText()+getTime(System.currentTimeMillis())+"\n";
                handler.obtainMessage(0,str).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //初始化
    private void initScoket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean isReceiving = true;
                    socket = new Socket("192.168.1.7",1234);
                    //获取socket的输入流
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));

                    //接受服务端的数据
                    while (isReceiving){
                        if(reader.ready()){
                            handler.obtainMessage(0,reader.readLine()).sendToTarget();
                        }
                        Thread.sleep(200);
                    }
                    writer.close();
                    reader.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 得到自己定义的时间格式的样式
     * @param millTime
     * @return
     */
    private String getTime( long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        System. out.println(sdf.format(d));
        return sdf.format(d);
    }
}

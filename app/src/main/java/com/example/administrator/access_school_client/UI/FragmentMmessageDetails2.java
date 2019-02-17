//package com.example.administrator.access_school_client.UI;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.administrator.access_school_client.R;
//import com.example.administrator.access_school_client.adapter.MyMsgAdapter;
//import com.example.administrator.access_school_client.bean.MessageEntity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@SuppressLint("ValidFragment")
//public class FragmentMmessageDetails2 extends Fragment implements View.OnClickListener{
//
////    private Button startscoket;
//    private Button send;
//    private EditText message;
//    private EditText display;
//    private TextView chat_title_nick;
//
//    private List<MessageEntity> msgs = new ArrayList<MessageEntity>();
//    private MyMsgAdapter myMsgAdapter;
//    private ListView chat_content_list;
//
//    private StringBuffer stringBuffer = new StringBuffer();
//
//    BufferedReader reader;
//    BufferedWriter writer;
//    private Socket socket;
//    boolean isConnectSocket;
//
//    private MyCallBack callBack;
//    int pos = 0;
//
//    public FragmentMmessageDetails2(MyCallBack callback){
//        this.callBack = callback;
//    }
//
//    private Handler handler= new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 0:
//                    //更新消息Adapter
//                    Log.e("notifyDataSetChanged","notifyDataSetChanged");
//                    myMsgAdapter.notifyDataSetChanged();
//                    chat_content_list.setSelection(msgs.size()-1);
//                    break;
//                    default:break;
//            }
//        }
//    };
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view =inflater.inflate(R.layout.fragment_mmessage_details2,container,false);
////        startscoket = (Button)view.findViewById(R.id.startScoket);
////        startscoket.setOnClickListener(this);
//        send = (Button)view.findViewById(R.id.ms_details_btn_send2);
//        send.setOnClickListener(this);
//        message = (EditText) view.findViewById(R.id.ms_details_et_message2);
////        display = (EditText) view.findViewById(R.id.ms_details_et_display);
//        chat_content_list = (ListView)view.findViewById(R.id.chat_content_list);
//        chat_title_nick = view.findViewById(R.id.chat_title_nick);
//
////        chat_title_nick.setText();
//        pos = getArguments().getInt("item");
//        //msg需要初始化之前的消息 将以前的聊天数据进行回显
//        //请求后台数据
//        getHistoryMessageData();
//
//        //createViewde时候初始化Socket
//        initScoket();
//
//        //Toast.makeText(getContext(), "Adapter初始化一次", Toast.LENGTH_SHORT).show();
//        myMsgAdapter = new MyMsgAdapter(getActivity(),msgs);
//        chat_content_list.setAdapter(myMsgAdapter);
//        chat_content_list.setSelection(msgs.size()-1);
//
//        return view;
//    }
//
//    //模拟数据
//    private void getHistoryMessageData() {
//        //getDta的时候进行分拣  哪些是属于此聊天窗口的
//        //根据from和to分拣 排序
//
//        MessageEntity messageEntity = new MessageEntity();
//        messageEntity.setFrom(2);
//        messageEntity.setTo(1);
//        messageEntity.setMessage("Hello");
//        messageEntity.setTime("2019/1/30 10:48");
//        messageEntity.setComeMsg(true);
//        msgs.add(messageEntity);
//
//        MessageEntity messageEntity2 = new MessageEntity();
//        messageEntity2.setFrom(2);
//        messageEntity2.setTo(1);
//        messageEntity2.setMessage("Hello");
//        messageEntity2.setTime("2019/1/30 10:48");
//        messageEntity2.setComeMsg(false);
//        msgs.add(messageEntity2);
//
//        MessageEntity messageEntity3 = new MessageEntity();
//        messageEntity3.setFrom(2);
//        messageEntity3.setTo(1);
//        messageEntity3.setTime("2019/1/30 10:48");
//        messageEntity3.setComeMsg(true);
//        messageEntity3.setMessage("what`s your name?");
//        msgs.add(messageEntity3);
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        try {
//            //拿到listView的最后一行数据进行回调
//            MessageEntity messageEntity = msgs.get(msgs.size()-1);
//            MessageEntity messageEsay = new MessageEntity();
//            messageEsay.setMessage(messageEntity.getMessage());
//            messageEsay.setTime(messageEntity.getTime());
//            //回调FragmentMS的callback设置content
//            callBack.callBack(pos,messageEsay);
//
//            //fragment销毁的时候关闭socket
//            Toast.makeText(getContext(), "socket关闭！", Toast.LENGTH_SHORT).show();
//            if(socket!=null) {
//                socket.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.startScoket:
//                //初始化
//                initScoket();
//                break;
//            case R.id.ms_details_btn_send2:
//                if(isConnectSocket) {
//                    sendMsg();
//                }else{
//                    Toast.makeText(getContext(), "抱歉，请先连接服务器！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//                default:break;
//        }
//    }
//
//    private void sendMsg() {
//        if(message.getText()!=null){
//            try {
//                //封装json数据上传服务器
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("to",1);
//                    json.put("msg",message.getText().toString());
////                    json.put("from",1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                writer.write(message.getText().toString()+"\n");
//                writer.write(json.toString()+"\n");
//                Log.e(null,"发出json:"+json.toString());
//                //flush写入缓存里面的数据
//                writer.flush();
//
//                //将自己发出的消息写入消息队列
//                MessageEntity messagesend = new MessageEntity();
//                messagesend.setComeMsg(false);
//                messagesend.setTime(getTime(System.currentTimeMillis()));
//                //这个from值需要确认................
//                messagesend.setFrom(1);
//                messagesend.setTo(1);
//                messagesend.setMessage(message.getText().toString());
//                msgs.add(messagesend);
//
//                Log.e("message的大小1：",String.valueOf(msgs.size()));
////                String str = "\n"+"我: "+message.getText()+getTime(System.currentTimeMillis())+"\n";
//                handler.obtainMessage(0,messagesend).sendToTarget();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    //初始化
//    private void initScoket() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    boolean isReceiving = true;
//                    socket = new Socket("192.168.1.7",1234);
//                    //这个时候soocket初始化完成 连接完成
//                    isConnectSocket = true;
//
//                    //获取socket的输入流
//                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
//                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
//
//                    //接受服务端的数据
//                    while (isReceiving){
//                        boolean isready = reader.ready();
//                        if(isready){
//                            Log.e("服务传来的数据到达：","服务传来的数据到达");
//                            JSONObject json = new JSONObject(reader.readLine());
//                            //将收到的消息写入消息队列
//                            MessageEntity messageCome = new MessageEntity();
//                            messageCome.setComeMsg(true);
//                            messageCome.setTime(json.getString("time"));
//                            //将发过来的to消息封装到from里面 Adapter里面用来显示发送者的name
//                            messageCome.setFrom(json.getInt("from"));
//                            messageCome.setMessage(json.getString("msg"));
//                            msgs.add(messageCome);
//                            Log.e("message的大小2：",String.valueOf(msgs.size()));
//
//                            handler.obtainMessage(0,messageCome).sendToTarget();
//                        }
//                        Thread.sleep(200);
//                    }
//                    writer.close();
//                    reader.close();
//                    socket.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//
//    public interface MyCallBack{//自定义接口更新消息列表的简略消息显示
//        public void callBack(int pos, MessageEntity messageEsay);
//    }
//
//    /**
//     * 得到自己定义的时间格式的样式
//     * @param millTime
//     * @return
//     */
//    private String getTime( long millTime) {
//        Date d = new Date(millTime);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
//        System. out.println(sdf.format(d));
//        return sdf.format(d);
//    }
//}

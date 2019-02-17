package com.example.administrator.access_school_client.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.MessageEntity;

import java.net.ConnectException;
import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/28 20:46.
 */
public class MyMsgAdapter extends BaseAdapter{

    private Context mcontext;
    private List<MessageEntity> msgs;
    private LayoutInflater layoutInflater;

    public MyMsgAdapter(Context mcontext, List<MessageEntity> msgs){
        this.mcontext = mcontext;
        this.msgs = msgs;
        layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageEntity messageEntity = msgs.get(position);
        boolean isC = messageEntity.isComeMsg() == 1 ? true : false;
        ViewHolder viewHolder = new ViewHolder();
        //if(convertView == null){
            //发来的消息和自己发出的消息有不同的布局，也就是左右之分
            if(!isC){
                convertView = layoutInflater.inflate(R.layout.chatting_item_msg_right,parent,false);
            }else{
                convertView = layoutInflater.inflate(R.layout.chatting_item_msg_left,parent,false);
            }
//            viewHolder = new ViewHolder();
            Log.e("是否是自己的：+",isC+"");
            viewHolder.tvUserName = convertView.findViewById(R.id.lr_username);
            viewHolder.tvSendTime = convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvContent = convertView.findViewById(R.id.tv_chatcontent);

//            int flag = 1;
//            if(isComemsg) {
//                convertView.setTag(flag,viewHolder);
//            }
        //}
//        else{
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        viewHolder.tvUserName.setText(String.valueOf(messageEntity.getFrom()));
        viewHolder.tvSendTime.setText(messageEntity.getTime());
//        Toast.makeText(mcontext,"message:"+messageEntity.getMessage(),Toast.LENGTH_LONG).show();
        viewHolder.tvContent.setText(messageEntity.getMessage());
        return convertView;
    }

    class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
}

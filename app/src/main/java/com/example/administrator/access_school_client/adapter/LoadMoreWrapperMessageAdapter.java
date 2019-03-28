package com.example.administrator.access_school_client.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.access_school_client.MainActivity;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.SharedPreferencesUtils;
import com.example.administrator.access_school_client.bean.MessageEntity;
import com.example.administrator.access_school_client.bean.User;
import com.example.administrator.access_school_client.dao.MsgDao;

import java.util.List;

/**
 * 上拉加载更多
 * Created by yangle on 2017/10/12.
 */

public class LoadMoreWrapperMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageEntity> dataList;
    private Context mcontext;
    private OnItemClickListener mListener;

    public LoadMoreWrapperMessageAdapter(Context mcontext , List<MessageEntity> dataList , OnItemClickListener mListener) {
        this.mcontext = mcontext;
        this.dataList = dataList;
        this.mListener =  mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_message_recyclerview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        MessageEntity messageEsay = (MessageEntity) dataList.get(position);
//        if(position == 0){//设置系统通知的头像
//            recyclerViewHolder.ivpicture.setImageResource(R.drawable.ic_launcher);
//        }
        MsgDao msgDao1 = new MsgDao(mcontext);
        List<User> users = msgDao1.queryUser
                (Integer.parseInt(SharedPreferencesUtils.getUserName("userId")));
        //获得当前用户的头像
        recyclerViewHolder.ivpicture.setImageResource(getPicture2());
        recyclerViewHolder.tvtitle.setText(String.valueOf(users.get(0).getName()));
        recyclerViewHolder.tvtime.setText(String.valueOf(messageEsay.getTime()));
        recyclerViewHolder.tvcontent.setText(messageEsay.getMessage());

        recyclerViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });
    }

    //设置对方的头像
    private int getPicture2() {
        if(Integer.parseInt(SharedPreferencesUtils.getUserName("userId")) == 1){
            return R.drawable.lihua0;
        }return R.drawable.lihua1;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        LinearLayout item;
        TextView tvtitle;
        TextView tvcontent;
        TextView tvtime;
        ImageView ivpicture;
        
        RecyclerViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.ms_ll);
            tvcontent = (TextView) itemView.findViewById(R.id.ms_tv_content);
            tvtitle = (TextView) itemView.findViewById(R.id.ms_tv_title);
            tvtime = (TextView) itemView.findViewById(R.id.ms_tv_time);
            ivpicture = (ImageView) itemView.findViewById(R.id.ms_iv_picture);
        }
    }

    public interface OnItemClickListener{
        public void onClick(int position);
    }

}

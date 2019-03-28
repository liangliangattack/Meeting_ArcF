package com.example.administrator.access_school_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

import java.util.List;

/**
 * Created by zhouwei on 16/11/30.
 */

public class MyAdapter extends RecyclerView.Adapter{
    private List<String> mData;
    private Listener listener;

    public void setData(List<String> data) {
        mData = data;
    }

    public MyAdapter(Listener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTextView.setText(mData.get(position));

       viewHolder.mTextView.setOnClickListener((View v)-> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_content);
        }
    }

    public interface Listener{
        void onClick(int position);
    }
}

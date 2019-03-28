package com.example.administrator.access_school_client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.Fragments.FragmentSQ.Meet;
import com.example.administrator.access_school_client.R;

import java.util.List;

/**
 * 上拉加载更多
 * Created by yangle on 2017/10/12.
 */

public class LoadMoreWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meet> dataList;
    private Context mcontext;
    private OnItemClickListener mListener;

    public LoadMoreWrapperAdapter(Context mcontext , List<Meet> dataList , OnItemClickListener mListener) {
        this.mcontext = mcontext;
        this.dataList = dataList;
        this.mListener =  mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recyclerview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvtitle.setText(dataList.get(position).getName());
        recyclerViewHolder.tvleader.setText(dataList.get(position).getPerson());
        recyclerViewHolder.tvtime.setText(dataList.get(position).getTime());
        if(dataList.get(position).getIsRun()==2){
            recyclerViewHolder.ivpoint.setImageResource(R.mipmap.r1);
        }
        recyclerViewHolder.item.setOnClickListener((View view) -> mListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        CardView item;
        TextView tvtitle;
        TextView tvleader;
        TextView tvtime;
        ImageView ivpoint;
        
        RecyclerViewHolder(View itemView) {
            super(itemView);
            item = (CardView) itemView.findViewById(R.id.ml_item);
            tvtitle = (TextView) itemView.findViewById(R.id.ml_tv_title);
            tvleader = (TextView) itemView.findViewById(R.id.ml_tv_leader);
            tvtime = (TextView) itemView.findViewById(R.id.ml_tv_time);
            ivpoint = (ImageView) itemView.findViewById(R.id.ml_iv_point);
        }
    }

    public interface OnItemClickListener{
        public void onClick(int position);
    }
}

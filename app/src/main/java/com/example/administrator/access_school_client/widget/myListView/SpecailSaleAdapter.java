package com.example.administrator.access_school_client.widget.myListView;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.access_school_client.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecailSaleAdapter extends BasicAdapter<SpecialSaleEntity.LiveBeanEityty.LivesBean> {

    private Context context=null;

    public SpecailSaleAdapter(Context context) {
        super();
        this.context=context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
             view = LayoutInflater.from(context).inflate(R.layout.item_special_sale_layout,null);
            viewHolder = new ViewHolder(view);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        initItemData(viewHolder, getItem(i));
        return view;
    }

    private void initItemData(ViewHolder holder, SpecialSaleEntity.LiveBeanEityty.LivesBean bean) {
        if (bean!=null){
            holder.specialImage.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(bean.pic).error(null).into(holder.specialImage);//////////////
            holder.specialLabel.setText(bean.sellername);
            holder.specialName.setText(bean.content);


            holder.specialLeftTime.setText("剩余"+bean.endtime);
//              TimeUtils.setLeftTime(bean.endtime,holder.specialLeftTime);
        }
    }

    private void initItemLeftTime(final ViewHolder holder, long endTime) {
        Date date = TimeUtils.convertTimeToDate(endTime + "");
        long timeDiff = (date.getTime() - TimeUtils.getExactlyCurrentTime());
        if (timeDiff > 0) {
            final CountDownTimer  timer = new CountDownTimer(timeDiff, 1000) {
                @Override
                public void onTick(long t) {
                    holder.specialLeftTime.setText("剩余  "+TimeUtils.convertTimeLeft(t));
                }

                @Override
                public void onFinish() {
                    holder.specialLeftTime.setText("活动已结束!");
                }
            };
            timer.start();
        }
    }

    class ViewHolder {
        @BindView(R.id.special_image)
        ImageView specialImage;
        @BindView(R.id.special_label)
        TextView specialLabel;
        @BindView(R.id.special_name)
        TextView specialName;
        @BindView(R.id.special_left_time)
        TextView specialLeftTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }

        public void reset() {
        }
    }
}

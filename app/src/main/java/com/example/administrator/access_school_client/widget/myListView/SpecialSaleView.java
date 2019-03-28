//package com.example.administrator.access_school_client.widget.myListView;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.example.administrator.access_school_client.R;
//import com.example.administrator.access_school_client.widget.Lamp.SimpleLinearLayout;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by user on 2016/8/26.
// * 特卖
// */
//public class SpecialSaleView extends SimpleLinearLayout {
//
//    @BindView(R.id.special_more)
//    TextView specialMore;
//    @BindView(R.id.special_view)
//    ListView specialListview;
//
//    private SpecailSaleAdapter specialAdapter=null;
//
//    public SpecialSaleView(Context context) {
//        super(context);
//    }
//
//    public SpecialSaleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void initViews() {
//        contentView = inflate(mContext, R.layout.layout_special_sale, this);
//        ButterKnife.bind(this);
//        init();
//        initTouch();
//    }
//
//    private void init() {
//
//    }
//
//    //这里要把父类的touch事件传给子类，不然边上的会滑不动
//    private void initTouch() {
//        setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return specialListview.dispatchTouchEvent(event);
//            }
//        });
//    }
//
//    public void setSpecialData(List<SpecialSaleEntity.LiveBeanEityty.LivesBean> list) {
//        specialAdapter=new SpecailSaleAdapter(mContext);
//        specialListview.setAdapter(specialAdapter);
//          if (specialAdapter!=null){
//              specialAdapter.setList(list);
//          }
//    }
//}

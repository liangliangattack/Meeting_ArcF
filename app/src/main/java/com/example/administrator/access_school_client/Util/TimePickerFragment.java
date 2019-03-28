package com.example.administrator.access_school_client.Util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/31 08:58.
 */
//实现OnTimeSetListener接口
@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private String time = "";
    private DataCallBack dataCallBack;
    public TimePickerFragment(DataCallBack dataCallBack){
        this.dataCallBack = dataCallBack;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //新建日历类用于获取当前时间
        int hour = 0;
        int minute = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }

        //返回TimePickerDialog对象
        //因为实现了OnTimeSetListener接口，所以第二个参数直接传入this
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    //重写onCreate和newInstance方法代替构造器 解决问题
//    public static TimePickerFragment newInstance(DataCallBack dataCallBack) {
//
//        //传递Bundle数据在onCreate中接受数据
//        //问题在于接口无法传递 由于不能序列化 将接口变为抽象了类实现序列化 再传参数
////        Bundle args = new Bundle();
////        args.putSerializable("datacallback",dataCallBack);
////
//        TimePickerFragment fragment = new TimePickerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dataCallBack = (DataCallBack) getArguments().getSerializable("datcallback");
    }

    //实现OnTimeSetListener的onTimeSet方法
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //判断activity是否是DataCallBack(这是自己定义的一个接口)的一个实例
//        if(getActivity() instanceof DataCallBack){
            Log.e("！！！！！！！！！！！！！！！！","回调DataCallBack接口设置时间！！！！！！");
            //将activity强转为DataCallBack
//            DataCallBack dataCallBack = (DataCallBack) getActivity();
            time = time + hourOfDay + "点" + minute + "分";
            //调用activity的getData方法将数据传回activity显示
            dataCallBack.getData(time);
//        }
    }

    public void setTime(String date){
        time += date;
    }

}

package com.example.administrator.access_school_client.Util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/31 08:51.
 */
@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private String date;
    private DataCallBack dataCallBack;

    //重载存在问题
    public DatePickerFragment(DataCallBack dataCallBack){
        this.dataCallBack = dataCallBack;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //得到Calendar类实例，用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //返回DatePickerDialog对象
        //因为实现了OnDateSetListener接口，所以第二个参数直接传入this
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //重写onCreate和newInstance方法代替构造器 解决问题
    //同时再实例化的时候不能再普通了 例子如下：
    //SplashViewPagerFragment splashViewPagerFragment=SplashViewPagerFragment.newInstance(1,"测试");
    //。。。。。。。。。。。
    //最终由于继承的分类智能有一个 抽象类没位子了  被Fragment超类占据 使用注解....
//    public static DatePickerFragment newInstance(DataCallBack dataCallBack) {
//
//        //传递Bundle数据在onCreate中接受数据
//        //问题在于接口无法传递 由于不能序列化 将接口变为抽象了类实现序列化 再传参数
//        Bundle args = new Bundle();
//        args.putSerializable("datacallback",dataCallBack);
//
//        DatePickerFragment fragment = new DatePickerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataCallBack = (DataCallBack) getArguments().getSerializable("datcallback");
    }

    //实现OnDateSetListener接口的onDateSet()方法
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //这样子写就将选择时间的fragment和选择日期的fragment完全绑定在一起
        //使用的时候只需直接调用DatePickerFragment的show()方法
        //即可选择完日期后选择时间
        TimePickerFragment timePicker = new TimePickerFragment(dataCallBack);
//        TimePickerFragment timePicker = TimePickerFragment.newInstance(dataCallBack);

        timePicker.show(getFragmentManager(), "time_picker");
        //将用户选择的日期传到TimePickerFragment
        date = year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日";
        timePicker.setTime(date);
    }
}
package com.example.administrator.access_school_client.Fragments.FragmentSQ;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.UI.FragmentStartTime;
import com.example.administrator.access_school_client.Util.DataCallBack;
import com.example.administrator.access_school_client.Util.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentMSend extends Fragment implements View.OnClickListener ,DataCallBack{

    private LinearLayout starttime;
    private TextView starttime_display;
    private TextView endtime_display;
    private TextView id_meeting;
    private TextView meetingName;
    private Spinner spinner;
    private LinearLayout explain;
    private LinearLayout mtroom;
    private LinearLayout time;
    private Button bt_publish;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_msend,container,false);
        bt_publish = view.findViewById(R.id.ms_bt_publish);
        starttime = view.findViewById(R.id.ms_ll_starttime);
        endtime_display = view.findViewById(R.id.ms_ll_endtime_display);
        starttime_display = view.findViewById(R.id.ms_ll_starttime_display);
        explain = view.findViewById(R.id.ms_ll_explain);
        time = view.findViewById(R.id.ms_ll_time);
        mtroom = view.findViewById(R.id.ms_ll_mtroom);
        spinner = view.findViewById(R.id.spinner);
        id_meeting = view.findViewById(R.id.id_meeting);
        meetingName = view.findViewById(R.id.meetingName);
        bt_publish.setOnClickListener(this);
        starttime.setOnClickListener(this);
        explain.setOnClickListener(this);
        mtroom.setOnClickListener(this);
        time.setOnClickListener(this);
        initV();

        return view;
    }

    private void initV() {
        List<String> datas = new ArrayList<String>();
        for(int i=0;i<6;i++){
            datas.add("会议室"+(i+1));
        }
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext());
        spinnerAdapter.setDatas(datas);
        spinner.setAdapter(spinnerAdapter);
        /**选项选择监听*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meetingName.setText(datas.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*初始化会议id的生成*/
        String time = getTime(System.currentTimeMillis());
//        Toast.makeText(getContext(), ""+time, Toast.LENGTH_SHORT).show();
        id_meeting.setText(time);
    }
    /**
     * 得到自己定义的时间格式的样式
     * @param millTime
     * @return
     */
    private String getTime( long millTime) {
        Date d = new Date(millTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss" );
        System. out.println(sdf.format(d));
        return sdf.format(d);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ms_bt_publish:
                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ms_ll_time:
//                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ms_ll_mtroom:
//                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ms_ll_starttime:
                //实例化对象
                DatePickerFragment datePickerFragment = new DatePickerFragment(this);
                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment.show(getFragmentManager(), "date_picker");
                break;
            case R.id.ms_ll_endtime_display:
                //实例化对象
                DatePickerFragment datePickerFragment2 = new DatePickerFragment(this);
                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment2.show(getFragmentManager(), "date_picker");
                break;
            case R.id.ms_ll_explain:
                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void getData(String data) {
        Log.e("！！！！！！！！！！！！！222！！！","回调DataCallBack接口设置时间！！！！！！");
        starttime_display.setText(data);
    }
}

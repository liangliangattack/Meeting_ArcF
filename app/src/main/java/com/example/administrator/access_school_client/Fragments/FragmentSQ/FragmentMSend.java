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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.UI.FragmentStartTime;
import com.example.administrator.access_school_client.Util.DataCallBack;
import com.example.administrator.access_school_client.Util.DatePickerFragment;

public class FragmentMSend extends Fragment implements View.OnClickListener ,DataCallBack{

    private LinearLayout starttime;
    private TextView starttime_display;
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
        starttime_display = view.findViewById(R.id.ms_ll_starttime_display);
        explain = view.findViewById(R.id.ms_ll_explain);
        time = view.findViewById(R.id.ms_ll_time);
        mtroom = view.findViewById(R.id.ms_ll_mtroom);
        bt_publish.setOnClickListener(this);
        starttime.setOnClickListener(this);
        explain.setOnClickListener(this);
        mtroom.setOnClickListener(this);
        time.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ms_bt_publish:
                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ms_ll_time:
                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ms_ll_mtroom:
                Toast.makeText(getContext(), "创建成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ms_ll_starttime:
                //实例化对象
                DatePickerFragment datePickerFragment = new DatePickerFragment(this);
//                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(this);

                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment.show(getFragmentManager(), "date_picker");

//                fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.drawlayout_content,new FragmentStartTime())
//                        .addToBackStack(null)
//                        .commit();
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

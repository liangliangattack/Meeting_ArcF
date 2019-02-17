package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.DataCallBack;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/25 17:08.
 */
public class FragmentStartTime extends Fragment implements DataCallBack{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_starttime,container,false);

        return view;
    }

    @Override
    public void getData(String data) {

    }
}

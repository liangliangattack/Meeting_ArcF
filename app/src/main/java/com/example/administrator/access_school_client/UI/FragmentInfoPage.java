package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

import butterknife.BindView;

public class FragmentInfoPage extends Fragment {

    @BindView(R.id.jg)
    TextView jg;
    @BindView(R.id.qm)
    TextView qm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_frienddetail,null);
        jg = rootview.findViewById(R.id.jg);
        qm = rootview.findViewById(R.id.qm);
        jg.setText("我自己");
        qm.setText("我自己的签名");

        return rootview;
    }
}

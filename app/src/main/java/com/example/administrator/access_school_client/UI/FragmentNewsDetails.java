package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.DataC;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/8/31 19:22.
 */
public class FragmentNewsDetails extends Fragment {

    ImageView picture;
    TextView title;
    TextView content;
    TextView time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_news_details,null);
        picture = rootview.findViewById(R.id.iv_details);
        time = rootview.findViewById(R.id.time_detials);
        title = rootview.findViewById(R.id.title_detials);
        content = rootview.findViewById(R.id.content_detials);
        int position;
        if(getArguments()!=null){
            position = getArguments().getInt("item");
        }
        else{
            position = 0;
        }
        picture.setImageResource(DataC.picture[position]);
        time.setText(DataC.date[position]);
        title.setText(DataC.title1[position]);
        content.setText(DataC.content1[position]);



        return rootview;
    }
}

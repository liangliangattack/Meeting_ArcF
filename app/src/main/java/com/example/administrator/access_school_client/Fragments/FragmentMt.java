package com.example.administrator.access_school_client.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.access_school_client.Fragments.FragmentSQ.FragmentMList;
import com.example.administrator.access_school_client.Fragments.FragmentSQ.FragmentMSend;
import com.example.administrator.access_school_client.Fragments.Fragmentscharts.FragmentCH;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.UI.Fragmentnews;
import com.example.administrator.access_school_client.charts.ComboLineColumnChartActivity;
import com.example.administrator.access_school_client.charts.LineColumnDependencyActivity;
import com.example.administrator.access_school_client.charts.PreviewLineChartActivity;
import com.example.administrator.access_school_client.charts.SpeedChartActivity;
import com.example.administrator.access_school_client.charts.TempoChartActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/7/18 16:27.
 */
public class FragmentMt extends Fragment{
    TabLayout tabLayout;
    FragmentManager fragmentManager;
    FragmentMList fragmentMList;
    FragmentMSend fragmentMSend;
//    FragmentCH fragmentCH;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;
    List<String> list;
    List<Fragment> fragments;
    ImageView ptiv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.mt_fragment, container , false);
        tabLayout=rootview.findViewById(R.id.tab_layout);
        viewPager=rootview.findViewById(R.id.viewpager);
        ptiv = rootview.findViewById(R.id.iv_pt);
        //设置监听事件的方法
        ptiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

//                if(fragmentCH==null) {
//                    fragmentCH = new FragmentCH();
//                }
                transaction.replace(R.id.drawlayout_content, new FragmentCH());
//                transaction.hide(FragmentMt.this);
                transaction.addToBackStack(null).commit();
                //Activity
//                Intent intent = new Intent();
//                intent.setClass(getContext(), ComboLineColumnChartActivity.class);
//                startActivity(intent);
            }
        });
        fragmentMSend =new FragmentMSend();
        fragmentMList =new FragmentMList();
        list=new ArrayList<>();
        fragments=new ArrayList<>();
        fragments.add(fragmentMList);
        fragments.add(fragmentMSend);
        list.add("会议列表");
        list.add("发布会议");
        fragmentManager=getChildFragmentManager();
        viewPager.setOffscreenPageLimit(list.size());//预加载
         fragmentPagerAdapter= new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }


            @Override
            public int getCount() {
                return list.size();
            }

             @Nullable
             @Override
             public CharSequence getPageTitle(int position) {
                 return list.get(position);
             }
         };


        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootview;
    }
}

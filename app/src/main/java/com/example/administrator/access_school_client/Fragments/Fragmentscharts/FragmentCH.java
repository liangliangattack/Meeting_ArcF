package com.example.administrator.access_school_client.Fragments.Fragmentscharts;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.Fragments.Fragmentscharts.subs.FragmentDistribution;
import com.example.administrator.access_school_client.Fragments.Fragmentscharts.subs.FragmentMtUsed;
import com.example.administrator.access_school_client.Fragments.Fragmentscharts.subs.FragmentMtUsed2;
import com.example.administrator.access_school_client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/24 12:01.
 */
public class FragmentCH extends Fragment implements View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tv_back;
    private FragmentMtUsed2 fragmentMtUsed;
    private FragmentDistribution fragmentDistribution;
    private List<Fragment> fragments ;
    private List<String> names;
    //一个是管理子fragment的childmanager
    private FragmentManager childfragmentManager;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment_ch,null,false);
        tabLayout = view.findViewById(R.id.tl_ch);
        viewPager = view.findViewById(R.id.vp_ch);
        tv_back = view.findViewById(R.id.ch_tv_back);
        tv_back.setOnClickListener(this);
        fragments = new ArrayList<Fragment>();
        names = new ArrayList<String>();
        fragmentMtUsed = new FragmentMtUsed2();
        fragmentDistribution = new FragmentDistribution();
        names.add("会议室使用统计/(日)");
        fragments.add(fragmentMtUsed);
        names.add("会议次数统计");
        fragments.add(fragmentDistribution);
        viewPager.setOffscreenPageLimit(names.size());

        childfragmentManager = getChildFragmentManager();
        fragmentPagerAdapter = new FragmentPagerAdapter(childfragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return names.get(position);
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

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ch_tv_back:
                if(fragmentTransaction==null) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                }
                //Toast.makeText(getContext(), "隐藏！", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

                //返回的时候隐藏本身
//                fragmentTransaction.hide(this);
//                fragmentTransaction.show();
                break;
        }
    }
}

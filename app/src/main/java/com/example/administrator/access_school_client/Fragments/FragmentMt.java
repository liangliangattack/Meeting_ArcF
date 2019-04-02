package com.example.administrator.access_school_client.Fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.access_school_client.Fragments.FragmentSQ.FragmentMList;
import com.example.administrator.access_school_client.Fragments.FragmentSQ.FragmentMSend;
import com.example.administrator.access_school_client.Fragments.Fragmentscharts.FragmentCH;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.UI.VerifyMeetingInvitation;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

import static android.content.Context.NOTIFICATION_SERVICE;

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
    ImageView toast;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0)
            displayNotification();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.mt_fragment, container , false);
        tabLayout=rootview.findViewById(R.id.tab_layout);
        viewPager=rootview.findViewById(R.id.viewpager);
        ptiv = rootview.findViewById(R.id.iv_pt);
        toast = rootview.findViewById(R.id.toast);
        toast.setOnClickListener((View v)->new Thread(()->handler.sendEmptyMessageDelayed(0,5000)).start());
        //设置监听事件的方法
        ptiv.setOnClickListener(v -> {
            //fragment
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.drawlayout_content, new FragmentCH());
//                transaction.hide(FragmentMt.this);
            transaction.addToBackStack(null).commit();
            //Activity
//                Intent intent = new Intent();
//                intent.setClass(getContext(), ComboLineColumnChartActivity.class);
//                startActivity(intent);
        });
        fragmentMSend =new FragmentMSend();
        fragmentMList =new FragmentMList();
        list=new ArrayList<>();
        fragments=new ArrayList<>();
        fragments.add(fragmentMList);
        fragments.add(fragmentMSend);
        list.add("会议列表");
        list.add("发布会议");
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!这里是getChildFragmentManager需要子类Manager
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

    public void displayNotification() {

        Intent i = new Intent(getActivity(),VerifyMeetingInvitation.class);
        i.putExtra("notificationID", 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0,i , PendingIntent.FLAG_UPDATE_CURRENT );
        NotificationManager mNotifyMgr = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder( getActivity() ).setContentTitle("李华的会议")
                        .setContentText("邀请您进入会议！")
                        .setSmallIcon(R.mipmap.meetingpicture)
                        // 点击消失
                        .setAutoCancel( true )
                        // 设置该通知优先级
                        .setPriority( Notification.PRIORITY_MAX )
                        .setLargeIcon( BitmapFactory.decodeResource(getResources(), R.mipmap.icon ) )
                        // 通知首次出现在通知栏，带上升动画效果的
                        .setWhen( System.currentTimeMillis())
                        // 通知产生的时间，会在通知信息里显示
                        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        .setDefaults( Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND );
        notifyBuilder.setContentIntent(pendingIntent);
        mNotifyMgr.notify( 1, notifyBuilder.build());
    }

    public void display(){
        Toast.makeText(getActivity(), "display", Toast.LENGTH_SHORT).show();
        // 1.展示的内容
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                getActivity())
                .setContentTitle("门禁识别结果")
                .setContentText("识别成功！")
                .setSmallIcon(R.mipmap.icon)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.icon));
        // 2.点击通知栏的跳转
        Intent intent = new Intent(getActivity(), VerifyMeetingInvitation.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        // 3.发出通知
        Notification notification = null;
        notification = notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }
}

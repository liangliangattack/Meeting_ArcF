package com.example.administrator.access_school_client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.example.administrator.access_school_client.Fragments.FragmentMS;
import com.example.administrator.access_school_client.Fragments.FragmentMS2;
import com.example.administrator.access_school_client.Fragments.FragmentSY;
import com.example.administrator.access_school_client.Fragments.FragmentWS;
import com.example.administrator.access_school_client.Fragments.FragmentMt;
import com.example.administrator.access_school_client.Fragments.FragmentWD;
import com.example.administrator.access_school_client.UI.FastActivity;
import com.example.administrator.access_school_client.Util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentSY syfragment;
    private FragmentMS2 msfragment;
    private FragmentMt ggfragment;
    private FragmentWD wdfragment;

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

//    private TextView title;
    private LinearLayout ll_shouye , ll_weisheng , ll_gg, ll_wd;
    private ImageView iv_shouye , iv_weisheng , iv_gg,iv_wd;
//    private ImageView setting;
    private DrawerLayout drawerLayout;
    private List<Fragment> fragments;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
//                    display();
//                    displayNotification();
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

//        SharedPreferencesUtils.setUserName("userId",)

        initView();

        initEvent();
        handler.sendEmptyMessageDelayed(1,145000);
//        handler.sendEmptyMessageDelayed(1,5000);
//        Intent intent = new Intent(this, AudioService.class);
//        intent.putExtra("query", "english");
//        bindService(intent, new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//
//            }
//        }, Context.BIND_AUTO_CREATE);
    }

    public void initView(){
        syfragment = new FragmentSY();
        msfragment = new FragmentMS2();
        ggfragment = new FragmentMt();
        wdfragment = new FragmentWD();

        viewPager = findViewById(R.id.viewpager_main);

        ll_shouye = findViewById(R.id.ll_shouye);
        ll_weisheng = findViewById(R.id.ll_weisheng);
        ll_gg = findViewById(R.id.ll_gg);
        ll_wd = findViewById(R.id.ll_wd);

        iv_shouye = findViewById(R.id.iv_shouye);
        iv_weisheng = findViewById(R.id.iv_weisheng);
        iv_gg = findViewById(R.id.iv_gg);
        iv_wd = findViewById(R.id.iv_wd);
//        title = findViewById(R.id.tv_title);
//        setting = findViewById(R.id.iv_setting);
//        drawerLayout = findViewById(R.id.drawlayout_content);
        fragments = new ArrayList<Fragment>();
        fragments.add(syfragment);
        fragments.add(msfragment);
        fragments.add(ggfragment);
        fragments.add(wdfragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);

        //设置滑动监听事件当页面滑动的时候改变锁定当前页面
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int item = viewPager.getCurrentItem();
                change(item);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void change(int item){//设置图片点击时的不同显示
        init();

        switch (item){
            case 0:
                iv_shouye.setImageResource(R.drawable.sy_press);
//                title.setText("首页");
                break;
            case 1:
                iv_weisheng.setImageResource(R.drawable.ms_press);
//                title.setText("消息");
                break;
            case 2:
                iv_gg.setImageResource(R.drawable.mt_press);
//                title.setText("会议");
                break;
            case 3:
                iv_wd.setImageResource(R.drawable.my_press);
//                title.setText("我的");
                //初始化模糊：
                //FragmentWD.blur();
                break;
        }
    }

    public void setSelected(int item){
        change(item);
        viewPager.setCurrentItem(item);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ll_shouye:
                setSelected(0);
                //Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_weisheng:
                setSelected(1);
                //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_gg:
                setSelected(2);
                //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_wd:
                setSelected(3);
                //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.iv_setting://设置的点击
////                Toast.makeText(this, "侧边栏", Toast.LENGTH_SHORT).show();
////                drawerLayout.openDrawer(Gravity.LEFT);
////                break;
        }
    }

    public void initEvent(){
        ll_shouye.setOnClickListener(this);
        ll_weisheng.setOnClickListener(this);
        ll_gg.setOnClickListener(this);
        ll_wd.setOnClickListener(this);
//        setting.setOnClickListener(this);
    }

    public void init(){//所有图片恢复
        iv_shouye.setImageResource(R.drawable.sy);
        iv_weisheng.setImageResource(R.drawable.ms);
        iv_gg.setImageResource(R.drawable.mt);
        iv_wd.setImageResource(R.drawable.my);
    }
}

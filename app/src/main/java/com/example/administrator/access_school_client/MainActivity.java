package com.example.administrator.access_school_client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.Fragments.FragmentSY;
import com.example.administrator.access_school_client.Fragments.FragmentWS;
import com.example.administrator.access_school_client.Fragments.FragmentGG;
import com.example.administrator.access_school_client.Fragments.FragmentWD;
import com.example.administrator.access_school_client.Service.AudioService;
import com.example.administrator.access_school_client.UI.FastActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentSY syfragment;
    private FragmentWS wsfragment;
    private FragmentGG ggfragment;
    private FragmentWD wdfragment;

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private TextView title;
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
                    displayNotification();
                    break;
                case 2:
                    break;
            }
        }
    };

    private void displayNotification() {
        Intent i = new Intent(this, FastActivity.class);
        i.putExtra("notificationID", 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,i , PendingIntent.FLAG_UPDATE_CURRENT );
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder( this ).setContentTitle("门禁识别结果")
                        .setContentText("识别成功！")
                        .setSmallIcon( R.drawable.zhisu)
                        // 点击消失
                        .setAutoCancel( true )
                        // 设置该通知优先级
                        .setPriority( Notification.PRIORITY_MAX )
                        .setLargeIcon( BitmapFactory.decodeResource(getResources(), R.drawable.zhisu ) )
                        // 通知首次出现在通知栏，带上升动画效果的
                        .setWhen( System.currentTimeMillis())
                        // 通知产生的时间，会在通知信息里显示
                        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        .setDefaults( Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND );
        notifyBuilder.setContentIntent(pendingIntent);
        mNotifyMgr.notify( 1, notifyBuilder.build());
    }

    public void display(){
        // 1.展示的内容
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this)
                .setContentTitle("门禁识别结果")
                .setContentText("识别成功！")
                .setSmallIcon(R.drawable.zhisu)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.zhisu));
        // 2.点击通知栏的跳转
        Intent intent = new Intent(this, FastActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        // 3.发出通知
        Notification notification = null;
        notification = notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

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
        wsfragment = new FragmentWS();
        ggfragment = new FragmentGG();
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
        title = findViewById(R.id.tv_title);
//        setting = findViewById(R.id.iv_setting);
        drawerLayout = findViewById(R.id.drawlayout_content);
        fragments = new ArrayList<Fragment>();
        fragments.add(syfragment);
        fragments.add(wsfragment);
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
                iv_shouye.setImageResource(R.mipmap.shouye_press);
                title.setText("首页");
                break;
            case 1:
                iv_weisheng.setImageResource(R.mipmap.weisheng_press);
                title.setText("卫生");
                break;
            case 2:
                iv_gg.setImageResource(R.mipmap.message_press);
                title.setText("请假保修");
                break;
            case 3:
                iv_wd.setImageResource(R.mipmap.mine_press);
                title.setText("我的");
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
        iv_shouye.setImageResource(R.mipmap.shouye);
        iv_weisheng.setImageResource(R.mipmap.weisheng);
        iv_gg.setImageResource(R.mipmap.message);
        iv_wd.setImageResource(R.mipmap.mine);
    }
}

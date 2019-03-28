package com.example.administrator.access_school_client.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.CircleImageView;
import com.example.administrator.access_school_client.Util.CustomPopWindow;
import com.example.administrator.access_school_client.Util.SharedPreferencesUtils;
import com.example.administrator.access_school_client.adapter.MyAdapter;
import com.example.administrator.access_school_client.bean.User;
import com.example.administrator.access_school_client.dao.MsgDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/3/21 08:31.
 */
public class FriendDetailActivity extends AppCompatActivity {

    @BindView(R.id.setting_frienddetail)
    public ImageView setting_frienddetail;
    @BindView(R.id.back_fd)
    public ImageView back_frienddetail;
    @BindView(R.id.userpicture_frienddetail)
    public CircleImageView circleImageView;
    @BindView(R.id.username_frienddetail)
    public TextView username_frienddetail;
    private CustomPopWindow mListPopWindow = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frienddetail);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        circleImageView.setImageResource(getPicture2());
        MsgDao msgDao1 = new MsgDao(this);
        List<User> users = msgDao1.queryUser
                (Integer.parseInt(SharedPreferencesUtils.getUserName("userId")));
        username_frienddetail.setText(String.valueOf(users.get(0).getName()));
    }

    //设置对方的头像
    private int getPicture2() {
        if(Integer.parseInt(SharedPreferencesUtils.getUserName("userId")) == 1){
            return R.drawable.lihua0;
        }return R.drawable.lihua1;
    }

    @OnClick({R.id.setting_frienddetail,R.id.back_fd})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setting_frienddetail:
//                CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
//                        .setView(R.layout.pop_layout1)
//                        .setFocusable(true)
//                        .setOutsideTouchable(true)
//                        .setAnimationStyle(R.style.CustomPopWindowStyle)
//                        .create()
//                        .showAsDropDown(setting_frienddetail,0,10);

                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
                //处理popWindow 显示内容
                handleListView(contentView);
                //创建并显示popWindow
                mListPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                        .create()
                        .showAsDropDown(setting_frienddetail,0,20);
                break;
            case R.id.back_fd:
                onBackPressed();
                break;
        }
    }
    private void handleListView(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter((int position)->{
            switch (position){
                case 0:
                    break;
                case 1:
                    Toast.makeText(this, "标记成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mListPopWindow.dissmiss();
                    break;
                default:
                    break;
            }
        });
        adapter.setData(mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private List<String> mockData(){
        List<String> data = new ArrayList<>();
        data.add("删除");
        data.add("标记");
        data.add("返回");
        return data;
    }
}

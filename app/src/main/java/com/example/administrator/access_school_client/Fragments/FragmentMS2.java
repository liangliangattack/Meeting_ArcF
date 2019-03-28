package com.example.administrator.access_school_client.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.access_school_client.MainActivity;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Service.SocketService;
import com.example.administrator.access_school_client.UI.FastActivity;
import com.example.administrator.access_school_client.UI.FragmentAddFriend;
import com.example.administrator.access_school_client.UI.FragmentMmessageDetails3;
import com.example.administrator.access_school_client.UI.FriendActivity;
import com.example.administrator.access_school_client.UI.TransImgAct;
import com.example.administrator.access_school_client.Util.CustomPopWindow;
import com.example.administrator.access_school_client.Util.SharedPreferencesUtils;
import com.example.administrator.access_school_client.Util.TimeUtil;
import com.example.administrator.access_school_client.adapter.LoadMoreWrapperMessageAdapter;
import com.example.administrator.access_school_client.bean.MessageEntity;
import com.example.administrator.access_school_client.bean.User;
import com.example.administrator.access_school_client.dao.MsgDao;
import com.example.administrator.access_school_client.listener.EndlessRecyclerOnScrollListener;
import com.example.administrator.access_school_client.wrapper.LoadMoreWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/27 10:14.
 */
public class FragmentMS2 extends Fragment implements ServiceConnection {

    private SocketService.MyBinder binder;
    private SocketService socketService;

    SwipeRefreshLayout swipeRefreshLayout;
    //    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<MessageEntity> messageEsays = new ArrayList<MessageEntity>();
    private LoadMoreWrapperMessageAdapter loadMoreWrapperAdapter;
    private ImageView friend;
    private ImageView mult;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    loadMoreWrapper.notifyDataSetChanged();
                break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mmessagelist,container,false);
        Log.e("FragmentMS","onCreateView");
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_message_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_message);
        friend = view.findViewById(R.id.friend);
        friend.setOnClickListener(v -> startActivity(new Intent(getActivity(),FriendActivity.class)));
        mult = view.findViewById(R.id.mult);
        //加号菜单栏
        mult.setOnClickListener((View v)-> {
            View v1 = LayoutInflater.from(getActivity()).inflate(R.layout.pop_layout1,null);

            CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                    .setView(v1)
                    .enableBackgroundDark(true)
                    .setBgDarkAlpha(0.8f)
                    .setFocusable(true)
                    .setOutsideTouchable(true)
                    .create();
            popWindow.showAsDropDown(mult,-30,20);

            LinearLayout addfriend = v1.findViewById(R.id.tv_addfriend);
            LinearLayout saoyisao = v1.findViewById(R.id.tv_saoyisao);
            View.OnClickListener listener = v2 -> {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (v2.getId()) {
                    case R.id.tv_addfriend:
                        Log.e("sd","addfriend");
                        fragmentTransaction.replace(R.id.drawlayout_content, new FragmentAddFriend())
                                .hide(FragmentMS2.this)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.tv_saoyisao:
                        Intent intent = new Intent();
                        intent.setClass(getContext() , TransImgAct.class);
                        startActivity(intent);
                        break;
                    case R.id.tv_fastSend_pop:
                        Intent intent2 = new Intent();
                        intent2.setClass(getContext() , FastActivity.class);
                        startActivity(intent2);
                        break;
                }
                popWindow.dissmiss();
            };
            addfriend.setOnClickListener(listener);
            saoyisao.setOnClickListener(listener);
        });

        // 模拟获取数据
        getData();
        //在LoadMoreWrapperAdapter设置自定义样式
        newLoad();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // 刷新数据
            //请求数据刷新
//                msgs.clear();
            getData();
            loadMoreWrapper.notifyDataSetChanged();

            // 延时1s关闭下拉刷新
            swipeRefreshLayout.postDelayed(() -> {
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    //关闭隐藏的刷新bar
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (messageEsays.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //主线程刷新UI
                            getActivity().runOnUiThread(() -> {
                                getData();
                                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("FragmentMS2","onCreate");
        //开启SocketService
        getActivity().bindService(new Intent(getActivity(), SocketService.class),this, Context.BIND_AUTO_CREATE);
        Log.e("bindService","bindService");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("FragmentMS","onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("FragmentMS","onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("FragmentMS","onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁的时候停止socket  也就是关闭SocketService
        getActivity().unbindService(this);

        Log.e("FragmentMS","onDestroy");
    }

    private void newLoad() {
        loadMoreWrapperAdapter = new LoadMoreWrapperMessageAdapter(getContext()
                , messageEsays
                , new LoadMoreWrapperMessageAdapter.OnItemClickListener() {//自定义接口
            @Override
            public void onClick(int position) {
                //消息详情页面跳转
                FragmentMmessageDetails3 fragmentMmessageDetails3 = new FragmentMmessageDetails3(
                        new FragmentMmessageDetails3.MyCallBack() {
                            @Override
                            public void callBack(int pos,MessageEntity messageEsay) {
                                //pos等于-1表示数据库为空
                                if(pos != -1) {
                                    //将数据跟新到数据
                                    messageEsays.get(pos).setMessage(messageEsay.getMessage());
                                    messageEsays.get(pos).setTime(messageEsay.getTime());
                                    //通知Adapter数据改变
                                    handler.sendEmptyMessage(0);
                                }
                            }
                        }
                );
                Bundle bundle = new Bundle();
                bundle.putInt("item",position);
                bundle.putInt("who",messageEsays.get(position).getFrom());
                //Toast.makeText(getContext(), messageEsays.get(position).toString(), Toast.LENGTH_SHORT).show();
                bundle.putInt("that1",getPicture2());
                bundle.putParcelable("this1",getPicture());
                fragmentMmessageDetails3.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.drawlayout_content,fragmentMmessageDetails3)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    //设置对方的头像
    private int getPicture2() {
        if(Integer.parseInt(SharedPreferencesUtils.getUserName("userId")) == 1){
            return R.drawable.lihua0;
        }return R.drawable.lihua1;
    }

    //设置自己的头像
    private Bitmap getPicture() {
        String userImagePath = SharedPreferencesUtils.getUserImagePath("userimage");
        Bitmap bitmap = null;
        if(!(userImagePath.equals("error"))) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            bitmap =  BitmapFactory.decodeFile(userImagePath, opts);
        }
        return bitmap;
    }

    private void getData() {
        messageEsays.clear();
        //创造模拟数据
//        createData();
//        数据库中查询数据
        MsgDao msgDao = new MsgDao(getActivity());
//        msgDao.addUser(new User(0,
//                "梨花",
//                R.drawable.user,
//                "我很帅",
//                TimeUtil.getTime(System.currentTimeMillis())));
//        msgDao.addUser(new User(1,
//                "中二青年",
//                R.drawable.user,
//                "我更加帅",
//                TimeUtil.getTime(System.currentTimeMillis())));

        List list = msgDao.getSimpleHistoryMessage(Integer.parseInt(SharedPreferencesUtils.getUserName("userId")));
        for(int flag = 0;flag<list.size();flag++){
            MessageEntity message = (MessageEntity) list.get(flag);
            if(message.getFrom() == Integer.parseInt(SharedPreferencesUtils.getUserName("userId"))){
                message.setFrom(Integer.parseInt(SharedPreferencesUtils.getUserName("userId")));
            }
            messageEsays.add(message);
        }
        handler.sendEmptyMessage(0);

    }

    private void createData() {
        MsgDao msgDao = new MsgDao(getActivity());

        MessageEntity message = new MessageEntity();
        message.setTime(TimeUtil.getTime(System.currentTimeMillis()));
        message.setFrom(0);
        message.setTo(1);
        message.setComeMsg(isCome(message.getFrom()));
        message.setMessage("hello 1 ! test1");
        msgDao.addMessage(message);

        MessageEntity message2 = new MessageEntity();
        message2.setTime(TimeUtil.getTime(System.currentTimeMillis()));
        message2.setFrom(1);
        message2.setTo(0);
        message2.setComeMsg(isCome(message2.getFrom()));
        message2.setMessage("hello 0 ! test2");
        msgDao.addMessage(message2);
        MessageEntity lastmessage = new MessageEntity();
        lastmessage.setTime(TimeUtil.getTime(System.currentTimeMillis()));
        lastmessage.setFrom(1);
        lastmessage.setTo(0);
        lastmessage.setComeMsg(isCome(message2.getFrom()));
        lastmessage.setMessage("hello 0 ! test2");
        lastMessage(lastmessage);

        MessageEntity message3 = new MessageEntity();
        message3.setTime(TimeUtil.getTime(System.currentTimeMillis()));
        message3.setFrom(0);
        message3.setTo(1);
        message3.setComeMsg(isCome(message3.getFrom()));
        message3.setMessage("hello 1 ! test2");
        msgDao.addMessage(message3);
        MessageEntity lastmessage2 = new MessageEntity();
        lastmessage2.setTime(TimeUtil.getTime(System.currentTimeMillis()));
        lastmessage2.setFrom(0);
        lastmessage2.setTo(1);
        lastmessage2.setComeMsg(isCome(message3.getFrom()));
        lastmessage2.setMessage("hello 1 ! test2");
        lastMessage(lastmessage2);
    }

    private void lastMessage(MessageEntity messageEntity) {
        MsgDao msgDao = new MsgDao(getActivity());
        //查询最新消息是否有历史消息
        List<MessageEntity> messageEntities = msgDao.queryLastMessage(1);
        if(messageEntities.size() != 0) {
            msgDao.updateLastMessage(messageEntity , 1);
        }else{
            msgDao.addLastMessage(messageEntity);
        }
    }

    private int isCome(int from) {
        Integer id = Integer.parseInt(SharedPreferencesUtils.getUserName("userId"));
        if(id == 1){
            if( from == 0)
                return 1;
            return 0;
        }
        if(id == 0) {
            if (from == 1) {
                return 1;
            }
            return 0;
        }
        else {
            return -1;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (SocketService.MyBinder) service;
        //拿到服务的对象
        socketService = binder.getService();
        //设置回调
        socketService.setMyServiceCallBack(new SocketService.MyServiceCallBack() {
            @Override
            public void onDataChanged(String data) {
                Log.e("onServiceConnected!","onServiceConnected");
                JSONObject json = null;
                try {
                    json = new JSONObject(data);
//                    MainActivity activity = (MalinActivity)getActivity();
                    MsgDao msgDao = new MsgDao(getActivity());

                    //将收到的消息写入消息队列
                    MessageEntity messageCome = new MessageEntity();
                    messageCome.setComeMsg(1);
                    messageCome.setTime(json.getString("time"));
                    //将发过来的to消息封装到from里面 Adapter里面用来显示发送者的name
                    messageCome.setFrom(json.getInt("from"));
                    messageCome.setTo(json.getInt("to"));
                    messageCome.setMessage(json.getString("msg"));
                    msgDao.addMessage(messageCome);

                    if(messageEsays.get(messageCome.getFrom()) != null) {
                        //更新替换新的messageEsays
                        messageEsays.set(messageCome.getFrom(), messageCome);
                    }else{
                        messageEsays.add(messageCome);
                    }
                    //替换之后通知handle更新UI
                    handler.obtainMessage(0).sendToTarget();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_addfriend:
                Toast.makeText(getActivity(),"添加好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting:
//                Toast.makeText(getActivity(),"设置", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.menu_exit:
//                Toast.makeText(this,"退出", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

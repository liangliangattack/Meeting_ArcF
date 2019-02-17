package com.example.administrator.access_school_client.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.access_school_client.MainActivity;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Service.SocketService;
import com.example.administrator.access_school_client.UI.FragmentMmessageDetails3;
import com.example.administrator.access_school_client.adapter.LoadMoreWrapperMessageAdapter;
import com.example.administrator.access_school_client.bean.MessageEntity;
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
    LoadMoreWrapperMessageAdapter loadMoreWrapperAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    for(MessageEntity messageEsay:messageEsays){
//                        Log.e("handle","!!!!!!!"+messageEsay.getMessage());
//                    }
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

        // 模拟获取数据
        getData();
        //在LoadMoreWrapperAdapter设置自定义样式
        newLoad();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                //请求数据刷新
//                msgs.clear();
                getData();
                loadMoreWrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            //关闭隐藏的刷新bar
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
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
                fragmentMmessageDetails3.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.drawlayout_content,fragmentMmessageDetails3)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void getData() {
        messageEsays.clear();
//        MessageEntity message1 = new MessageEntity();
//        message1.setFrom(0);
//        message1.setMessage("Hello");
//        message1.setTime("2019/1/30 10:48");
//        messageEsays.add(message1);
//
//        MessageEntity message2 = new MessageEntity();
//        message2.setFrom(1);
//        message2.setMessage("what`s your name?");
//        message2.setTime("2019/1/30 10:48");
//        messageEsays.add(message2);
//
//        MessageEntity message3 = new MessageEntity();
//        message3.setFrom(2);
//        message3.setMessage("过年");
//        message3.setTime("2019/1/30 10:48");
//        messageEsays.add(message3);


//        数据库中查询数据
        MsgDao msgDao = new MsgDao(getActivity());
        //默认为小米的账户
        List list = msgDao.getSimpleHistoryMessage(1);
        for(int flag = 0;flag<list.size();flag++){
            MessageEntity message = (MessageEntity) list.get(flag);
            messageEsays.add(message);
        }
        handler.sendEmptyMessage(0);

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

}

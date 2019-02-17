//package com.example.administrator.access_school_client.Fragments;
//
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.administrator.access_school_client.R;
//import com.example.administrator.access_school_client.UI.FragmentMmessageDetails2;
//import com.example.administrator.access_school_client.adapter.LoadMoreWrapperMessageAdapter;
//import com.example.administrator.access_school_client.listener.EndlessRecyclerOnScrollListener;
//import com.example.administrator.access_school_client.wrapper.LoadMoreWrapper;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * ..
// * author:liangliangattack 1364744931@.qq.com
// * Administrator on 2019/1/27 10:14.
// */
//public class FragmentMS extends Fragment{
//
//    SwipeRefreshLayout swipeRefreshLayout;
//    //    private Toolbar toolbar;
//    private RecyclerView recyclerView;
//    private LoadMoreWrapper loadMoreWrapper;
//    private List<MessageEsay> msgs = new ArrayList<MessageEsay>();
//    LoadMoreWrapperMessageAdapter loadMoreWrapperAdapter;
//
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    for(MessageEsay messageEsay:msgs){
//                        Log.e("handle","!!!!!!!"+messageEsay.getMessage());
//                    }
//                    loadMoreWrapper.notifyDataSetChanged();
//                    newLoad();
//                break;
//            }
//        }
//    };
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_mmessagelist,container,false);
//        Log.e("FragmentMS","onCreateView");
////        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_message_layout);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_message);
//
//        // 模拟获取数据
//        getData();
//        //在LoadMoreWrapperAdapter设置自定义样式
//        newLoad();
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(loadMoreWrapper);
//
//        // 设置下拉刷新
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 刷新数据
//                //请求数据刷新
////                msgs.clear();
////                getData();
//                loadMoreWrapper.notifyDataSetChanged();
//
//                // 延时1s关闭下拉刷新
//                swipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                            //关闭隐藏的刷新bar
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }, 1000);
//            }
//        });
//
//        // 设置加载更多监听
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
//
//                if (msgs.size() < 52) {
//                    // 模拟获取网络数据，延时1s
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            //主线程刷新UI
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    //getData();
//                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
//                                }
//                            });
//                        }
//                    }, 1000);
//                } else {
//                    // 显示加载到底的提示
//                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
//                }
//            }
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.e("FragmentMS","onCreate");
//        //开启SocketService
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.e("FragmentMS","onStop");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.e("FragmentMS","onPause");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e("FragmentMS","onResume");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //销毁的时候停止socket  也就是关闭SocketService
//
//        Log.e("FragmentMS","onDestroy");
//    }
//
//    private void newLoad() {
//        loadMoreWrapperAdapter = new LoadMoreWrapperMessageAdapter(getContext()
//                , msgs
//                , new LoadMoreWrapperMessageAdapter.OnItemClickListener() {//自定义接口
//            @Override
//            public void onClick(int position) {
//                //消息详情页面跳转
//                FragmentMmessageDetails2 fragmentMmessageDetails3 = new FragmentMmessageDetails2(
//                        new FragmentMmessageDetails2.MyCallBack() {
//                            @Override
//                            public void callBack(int pos,MessageEsay messageEsay) {
//                                //将数据跟新到数据
//                                msgs.get(pos).setMessage(messageEsay.getMessage());
//                                msgs.get(pos).setTime(messageEsay.getTime());
//                                //通知Adapter数据改变
//                                handler.sendEmptyMessage(0);
//                            }
//                        }
//                );
//                Bundle bundle = new Bundle();
//                bundle.putInt("item",position);
//                fragmentMmessageDetails3.setArguments(bundle);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.drawlayout_content,fragmentMmessageDetails3)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//    }
//
//    private void getData() {
//        char letter = 'A';
////        for (int i = 0; i < 3; i++) {
////            messagelist.add(String.valueOf(letter));
////            letter++;
////        }
//
//        MessageEsay message1 = new MessageEsay();
//        message1.setName("系统通知");
//        message1.setMessage("Hello");
//        message1.setTime("2019/1/30 10:48");
//        msgs.add(message1);
//
//        MessageEsay message2 = new MessageEsay();
//        message2.setName("小米");
//        message2.setMessage("what`s your name?");
//        message2.setTime("2019/1/30 10:48");
//        msgs.add(message2);
//
//        MessageEsay message3 = new MessageEsay();
//        message3.setName("vivo");
//        message3.setMessage("哈哈");
//        message3.setTime("2019/1/30 10:48");
//        msgs.add(message3);
//    }
//
//    private class MyItemDecoration extends RecyclerView.ItemDecoration {
//
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.set(0,0,0,3);
//        }
//    }
//}

package com.example.administrator.access_school_client.Fragments.FragmentSQ;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.UI.FragmentMSendDetails;
import com.example.administrator.access_school_client.adapter.LoadMoreWrapperAdapter;
import com.example.administrator.access_school_client.listener.EndlessRecyclerOnScrollListener;
import com.example.administrator.access_school_client.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentMList extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
//    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<Meet> meetinglist = new ArrayList<Meet>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mlist,container,false);
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // 使用Toolbar替换ActionBar
        //getContext().setSupportActionBar(toolbar);
        // 设置刷新控件颜色
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        // 模拟获取数据
        getData();
        //在LoadMoreWrapperAdapter设置自定义样式
        LoadMoreWrapperAdapter loadMoreWrapperAdapter = new LoadMoreWrapperAdapter(getContext()
                , meetinglist
                , position -> {
                    FragmentMSendDetails fragmentMSendDetails = new FragmentMSendDetails();
                    Bundle bundle = new Bundle();
                    bundle.putInt("item",position);
                    fragmentMSendDetails.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.drawlayout_content,fragmentMSendDetails)
                            .addToBackStack(null)
                            .commit();
                });
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> {
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

                if (meetinglist.size() < 52) {
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

    private void getData() {
        meetinglist.clear();
        meetinglist.add(new Meet("李华的会议",2,"李华","2019.3.27 17:46"));
        meetinglist.add(new Meet("李红的会议",1,"李红","2019.3.25 10:36"));
        meetinglist.add(new Meet("张三的会议",1,"张三","2019.3.20 09:24"));
    }
}

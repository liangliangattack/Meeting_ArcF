package com.example.administrator.access_school_client.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.access_school_client.Fragments.FragmentSY;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.Meeting;

import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/8/30 14:19.
 */
public class FragmentMymt extends Fragment {

    private ListView listView;
    private List<Meeting> meetings = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_three,null);
        listView = rootview.findViewById(R.id.listview);

        MyLVAdapter adapter1 = new MyLVAdapter();
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(),ActivityGG.class);

            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            intent.putExtra("data",bundle);

            intent.putExtra("position",position);
            Log.e("position",position+"");
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            View delview = parent.getChildAt(position).findViewById(R.id.linear_del);

            delview.setVisibility(View.VISIBLE);
            delview.findViewById(R.id.tv_del).setOnClickListener(v -> {
                delview.setVisibility(View.GONE);
//                curList.remove(position);
//                adapter.notifyDataSetChanged();
            });
            delview.findViewById(R.id.tv_cancel).setOnClickListener(v -> delview.setVisibility(View.GONE));

            return true;
        });

        return rootview;
    }

    class MyLVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rootview;

            //ListView的优化
            if(view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                rootview = inflater.inflate(R.layout.simplenewsitem2, null);
            }
            else{
                rootview = view;
            }
            TextView title = rootview.findViewById(R.id.simple_title2);
            TextView content = rootview.findViewById(R.id.simple_content2);
            TextView time = rootview.findViewById(R.id.simple_time2);
            ImageView imageView = rootview.findViewById(R.id.imageview002);
            Log.e("________","&*********"+i);
            title.setText("李华的会议");
            content.setText("重要讲话....");
            time.setText("2019/3/29 16：00");
//            imageView.setImageResource(R.drawable.news1);


            return rootview;
        }
    }
}

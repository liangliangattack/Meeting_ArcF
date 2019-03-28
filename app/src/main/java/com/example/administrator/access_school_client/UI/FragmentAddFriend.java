package com.example.administrator.access_school_client.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/3/14 15:30.
 */
public class FragmentAddFriend extends Fragment {

    @BindView(R.id.back_addfriend)
    public ImageView back_addfriend;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addfriend,container,false);
        ButterKnife.bind(this,view);

        searchView = view.findViewById(R.id.sv_searchview);
        searchView.setOnClickListener((View v)->startActivity(new Intent(getActivity(),SearchDemoActivity.class)));

        return view;
    }

    @OnClick(R.id.back_addfriend)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_addfriend:
                Toast.makeText(getActivity(),"dianji",Toast.LENGTH_SHORT);
                getActivity().onBackPressed();
                break;
        }
    }

}

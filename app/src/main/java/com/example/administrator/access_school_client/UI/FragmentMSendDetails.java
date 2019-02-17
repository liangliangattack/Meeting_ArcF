package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.access_school_client.R;

public class FragmentMSendDetails extends Fragment {
    private Button bt_publish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_msend_details,container,false);
        bt_publish = view.findViewById(R.id.ms_bt_publish);
        int pos = (int) getArguments().get("item");
        bt_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "加入成功！", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

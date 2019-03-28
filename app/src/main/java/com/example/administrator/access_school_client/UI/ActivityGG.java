package com.example.administrator.access_school_client.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.DataC;

public class ActivityGG extends AppCompatActivity {

    ImageView picture;
    TextView title;
    TextView content;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg);
        picture = findViewById(R.id.iv_details2);
        time = findViewById(R.id.time_detials2);
        title = findViewById(R.id.title_detials2);
        content = findViewById(R.id.content_detials2);
//        int position = Integer.parseInt(getIntent().getStringExtra("position"));
        Bundle bundle = getIntent().getBundleExtra("data");
        int position = bundle.getInt("position");

        Log.e("position",position+"");
        picture.setImageResource(DataC.picture[position]);
        time.setText(DataC.date[position]);
        title.setText(DataC.title1[position]);
        content.setText(DataC.content1[position]);

    }
}

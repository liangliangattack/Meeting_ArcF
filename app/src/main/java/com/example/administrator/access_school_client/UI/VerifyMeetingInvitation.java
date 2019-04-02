package com.example.administrator.access_school_client.UI;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyMeetingInvitation extends AppCompatActivity {

    @BindView(R.id.invite_user) TextView inviteuser;
    @BindView(R.id.invite_meeting) TextView invite_meeting;
    @BindView(R.id.invite_agree) Button inviteagree;
    @BindView(R.id.invite_cancel) Button invitecancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_meeting_invitation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.invite_agree,R.id.invite_cancel,R.id.invite_user,R.id.invite_meeting})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.invite_user://进入好友界面
                startActivity(new Intent(this,FriendDetailActivity.class));break;
            case R.id.invite_meeting:
                //首先需要拿到position这个值进行跳转 默认1
                FragmentMSendDetails fragmentMSendDetails = new FragmentMSendDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("item",1);
                fragmentMSendDetails.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.drawlayout_content,fragmentMSendDetails)
                        .addToBackStack(null)
                        .commit();break;
            case R.id.invite_agree:onBackPressed(); break;
            case R.id.invite_cancel:onBackPressed(); break;
            default:break;
        }
    }
}

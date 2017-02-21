package com.example.mammam.cowchat.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerMessage;
import com.example.mammam.cowchat.controll.ManagerStorage;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.MyMessage;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.adapter.RCConvesationAdapter;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.fragm.ConversationFragment;
import com.example.mammam.cowchat.ui.fragm.ImageFragment;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListSMs;
import com.example.mammam.cowchat.ui.interf.ISavePhoto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.io.IOException;
import java.util.List;

/**
 * Created by Mam  Mam on 12/26/2016.
 */

public class ConsersationActivity extends BaseActivity implements IConstand
        , View.OnClickListener {
    public ImageFragment imageFragment;
    public ConversationFragment conversationFragment;

    @Override
    protected void initComponents() {

        initFragment();
    }

    public void initFragment(){
        imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ROOM_ID,getIntent().getStringExtra(ROOM_ID));
        bundle.putString(FRIEND_ID,getIntent().getStringExtra(FRIEND_ID));

        conversationFragment = new ConversationFragment(getSupportFragmentManager());
        conversationFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                add(R.id.rLContentConV,
                conversationFragment,
                        ConversationFragment.class.getName()).
                commit();
    }
    public void openFragmentImage(String url){
        Bundle bundle= new Bundle();
        bundle.putString(URL_DOWNLOAD,url);
        imageFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                add(R.id.rLContentConV,imageFragment,
                        ImageFragment.class.getName()).addToBackStack("2").
               commit();

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void setEventViews() {

    }

    @Override
    protected void setViewRoot() {
        setContentView(R.layout.activity_conversation);
    }


    @Override
    public void onClick(View view) {

    }





}

package com.example.mammam.cowchat.ui.fragm;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.models.FriendOnline;
import com.example.mammam.cowchat.ui.adapter.RCOnlineAdapter;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IFriendOnline;
import com.example.mammam.cowchat.ui.interf.IListFriend;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/21/2016.
 */

public class OnlineFragment extends BaseFragment implements IListFriend, IFriendOnline {
    public static final  String TAG = "OnlineFragment";
    private RCOnlineAdapter adapter;
    private List<FriendOnline> friendOnlines;
    private RecyclerView rvOnline;
    private ManagerUser mManagerUser;


    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {

        return inflater.inflate(R.layout.fragment_online,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        rvOnline = (RecyclerView) view.findViewById(R.id.rvOnline);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        getContext();
        rvOnline.setLayoutManager(manager);
        rvOnline.setAdapter(adapter);
    }

    @Override
    public void initComponents() {
        mManagerUser = new ManagerUser(getContext());

        friendOnlines = new ArrayList<>();
        adapter = new RCOnlineAdapter(getContext(), friendOnlines,
                new IClickItemRycyclerView() {
                    @Override
                    public void onItemClick(int pos, View v) {

                    }

                    @Override
                    public void onItemLongCLick(int pos, View v) {

                    }
                });


        mManagerUser.setiFriendOnline(this);
        mManagerUser.setiListFriend(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"Vao  Start");
        mManagerUser.getListFiend();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"Vao resum");
    }

    @Override
    public void setEventViews() {

    }

    @Override
    public void listFriend(List<FriendChat> friendChats,boolean reuslt) {
        Log.d(TAG,friendChats.size() + " ");
        for (FriendChat friendChat: friendChats){
            Log.d(TAG,friendChat.getId());
        }
        mManagerUser.getUserOnline(friendChats);

    }

    @Override
    public void friendOnline(List<FriendOnline> friendOnlines) {
        adapter.setFriendOnlines(friendOnlines);
        adapter.notifyDataSetChanged();
    }
}

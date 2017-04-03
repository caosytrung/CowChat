package com.example.mammam.cowchat.ui.fragm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.ui.activity.ConsersationActivity;
import com.example.mammam.cowchat.ui.activity.MainActivity;
import com.example.mammam.cowchat.ui.activity.SearchActivity;
import com.example.mammam.cowchat.ui.adapter.RCViewListFiriendAdapter;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListFriend;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/21/2016.
 */

public class FriendFragment extends BaseFragment {
    private RecyclerView rvListFriend;
    private ProgressBar prgListFriend;
    private RCViewListFiriendAdapter adapter;
    private List<FriendChat> friendChats;
    private ManagerUser managerUser;
    private FriendBroadcast broadcast;


    public void registerBroadcast(){
        broadcast = new FriendBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getContext().registerReceiver(broadcast,intentFilter);
    }

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_list_friend,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        rvListFriend = (RecyclerView) view.findViewById(R.id.rvListFriend);
        prgListFriend = (ProgressBar) view.findViewById(R.id.prgListFriend);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListFriend.setLayoutManager(manager);
        rvListFriend.setAdapter(adapter);
    }

    @Override
    public void initComponents() {

        friendChats = new ArrayList<>();
        adapter = new RCViewListFiriendAdapter(getContext(), friendChats, new IClickItemRycyclerView() {
            @Override
            public void onItemClick(int pos, View v) {
                Log.d("asdasas","da nhan");
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.nextConvesation(friendChats.get(pos)
                        .getRoomId(),
                        friendChats.get(pos).getLinkAvatar(),friendChats.get(pos).getFullName());
            }

            @Override
            public void onItemLongCLick(int pos, View v) {

            }
        });
        managerUser = new ManagerUser(getContext());

    }

    @Override
    public void onResume() {

        super.onResume();
        registerBroadcast();

        managerUser.setiListFriend(new IListFriend() {
            @Override
            public void listFriend(List<FriendChat> friendChats1,boolean result) {
                if (result){
                    friendChats = friendChats1;
                    prgListFriend.setVisibility(View.GONE);
                    adapter.setFriendChats(friendChats1);
                    adapter.notifyDataSetChanged();
                }

            }
        });
        managerUser.getListFiend();
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(broadcast);
    }

    @Override
    public void setEventViews() {

    }

    public class FriendBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "android.net.conn.CONNECTIVITY_CHANGE":
                    managerUser.getListFiend();
                    break;
                default:
                    break;

            }
        }
    }
}

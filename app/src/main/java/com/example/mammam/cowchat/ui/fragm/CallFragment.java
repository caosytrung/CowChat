package com.example.mammam.cowchat.ui.fragm;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.EventCall;
import com.example.mammam.cowchat.models.EventVideo;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.ui.adapter.RVCallAdapter;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListFriend;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/21/2016.
 */

public class CallFragment extends BaseFragment implements IClickItemRycyclerView {
    private static final String TAG = CallFragment.class.getName();
    private RecyclerView rvCall;
    private RVCallAdapter adapter;
    private ManagerUser managerUser;
    private List<FriendChat> mFriendChats;
    private EventBus mEventBus;


    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_call,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        rvCall = (RecyclerView) view.findViewById(R.id.rvCall);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCall.setLayoutManager(manager);
        rvCall.setAdapter(adapter);
    }

    @Override
    public void initComponents() {
        mFriendChats = new ArrayList<>();
        mEventBus = EventBus.getDefault();
        adapter = new RVCallAdapter(getContext(),mFriendChats);
        adapter.setiClickItemRycyclerView(this);
        adapter.setiClickVideo(new IClickItemRycyclerView() {
            @Override
            public void onItemClick(int pos, View v) {
                EventVideo eventCall =
                        new EventVideo(mFriendChats.
                                get(pos).getFullName(),mFriendChats.
                                get(pos).getId(),mFriendChats.get(pos).getLinkAvatar(),
                                mFriendChats.get(pos).getRoomId());
                mEventBus.postSticky(eventCall);
                Log.d("ClickVIdeo","Clicked");
                Intent intent = new Intent();
                intent.setAction("VIDEO");
                intent.putExtra("DATA",eventCall);
                getContext().sendBroadcast(intent);
            }

            @Override
            public void onItemLongCLick(int pos, View v) {

            }
        });

        managerUser = new ManagerUser(getContext());

        managerUser.setiListFriend(new IListFriend() {
            @Override
            public void listFriend(List<FriendChat> friendChats, boolean result) {
                mFriendChats = friendChats;
                adapter.setFriendChats(friendChats);
                adapter.notifyDataSetChanged();

            }
        });
       // managerUser.getListFiend();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            managerUser.getListFiend();
        }
    }

    @Override
    public void setEventViews() {

    }

    @Override
    public void onItemClick(int pos, View v) {

        Toast.makeText(getContext(),"Da nhannnnnnnnnnn",Toast.LENGTH_LONG).show();
        Log.d(TAG,"Da vao roiiiiiiiii");

        EventCall eventCall =
                new EventCall(mFriendChats.
                get(pos).getFullName(),mFriendChats.
                get(pos).getId(),mFriendChats.get(pos).getLinkAvatar(),
                        mFriendChats.get(pos).getRoomId());
        mEventBus.postSticky(eventCall);
    }

    @Override
    public void onItemLongCLick(int pos, View v) {

    }


}

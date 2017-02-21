package com.example.mammam.cowchat.ui.fragm;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mammam.cowchat.R;

/**
 * Created by Mam  Mam on 12/21/2016.
 */

public class GroupChatFragment extends BaseFragment implements GroupChatP2Fragment.IGroupChat {

    private FragmentManager fragmentManager;
    private CreateGroupChatFragmenrt createGroupChatFragmenrt;
    private GroupChatP2Fragment groupChatP2Fragment;

    public GroupChatFragment(FragmentManager manager){
        fragmentManager = manager;
    }

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_group_chat,viewGroup,false);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initComponents() {
        createGroupChatFragmenrt = new CreateGroupChatFragmenrt();
        groupChatP2Fragment = new GroupChatP2Fragment();
        groupChatP2Fragment.setiGroupChat(this);
        fragmentManager.beginTransaction()
                .add(R.id.rlContenGC,groupChatP2Fragment,
                        GroupChatP2Fragment.class.getName()).addToBackStack("1").commit();
    }

    public void openFragmentCreteRoom(){
        fragmentManager.beginTransaction().replace(R.id.rlContenGC,createGroupChatFragmenrt,
                CreateGroupChatFragmenrt.class.getName()).commit();
    }


    @Override
    public void setEventViews() {

    }

    @Override
    public void IGroupChat() {
        openFragmentCreteRoom();
    }
}

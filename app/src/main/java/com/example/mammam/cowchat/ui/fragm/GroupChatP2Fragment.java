package com.example.mammam.cowchat.ui.fragm;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerRoom;
import com.example.mammam.cowchat.models.Room;
import com.example.mammam.cowchat.ui.activity.MainActivity;
import com.example.mammam.cowchat.ui.adapter.RVShowGCAdapter;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListRoom;

import java.util.List;

/**
 * Created by Mam  Mam on 12/28/2016.
 */

public class GroupChatP2Fragment extends BaseFragment implements IClickItemRycyclerView, IListRoom {
    private RVShowGCAdapter adapter;;
    private RecyclerView rvGC;
    private List<Room> rooms;
    private ManagerRoom managerRoom;
    private ProgressBar progressBar;
    private IGroupChat iGroupChat;
    private ImageView ivAddGroup;

    public void setiGroupChat(IGroupChat iGroupChat) {
        this.iGroupChat = iGroupChat;
    }

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_group_2,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        rvGC = (RecyclerView) view.findViewById(R.id.rvGC);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGC.setLayoutManager(manager);
        rvGC.setAdapter(adapter);
        progressBar = (ProgressBar) view.findViewById(R.id.prgGC2);
        progressBar.setVisibility(View.VISIBLE);
        ivAddGroup = (ImageView) view.findViewById(R.id.ivAddGroup);
    }

    @Override
    public void initComponents() {
        adapter = new RVShowGCAdapter(getContext());
        adapter.setiClickItemRycyclerView(this);
        managerRoom = ManagerRoom.getINSTANCE();
        managerRoom.setiListRoom(this);
        managerRoom.getListGroupChat();
    }

    @Override
    public void setEventViews() {
        ivAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGroupChat.IGroupChat();
            }
        });
    }

    @Override
    public void onItemClick(int pos, View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.nextConvesation(rooms.get(pos).getRoomID(),null);
    }

    @Override
    public void onItemLongCLick(int pos, View v) {

    }

    @Override
    public void listNameRoom(List<String> listName) {

    }


    @Override
    public void listRoom(List<Room> rooms) {
        progressBar.setVisibility(View.GONE);
        this.rooms = rooms;
        adapter.setRooms(rooms);
        adapter.notifyDataSetChanged();
    }

    public interface IGroupChat{
        void IGroupChat();
    }
}

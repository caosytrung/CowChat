package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.Room;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mam  Mam on 12/29/2016.
 */

public class RVShowGCAdapter extends RecyclerView.Adapter<RVShowGCAdapter.ViewHolder> {
    private List<Room> rooms;
    private Context mContext;
    private IClickItemRycyclerView  iClickItemRycyclerView;

    public RVShowGCAdapter(Context context){
        mContext = context;
        rooms = new ArrayList<>();
    }

    public void setiClickItemRycyclerView(IClickItemRycyclerView iClickItemRycyclerView) {
        this.iClickItemRycyclerView = iClickItemRycyclerView;
    }

    public void setRooms(List<Room> rooms) {

        this.rooms = rooms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        return new ViewHolder(LayoutInflater.
                from(mContext).inflate(R.layout.item_show_gc,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Room room = rooms.get(position);
        holder.tvNameRoom.setText(room.getRoomName());
        HashMap<String,String> map = room.getListId();
        String member = getAllName(map);
        holder.tvMember.setText(member);
        Picasso.with(mContext).load(room.getLinkAvatar()).into(holder.ivAvatar);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemRycyclerView.onItemClick(position,view);
            }
        });


    }
    private String getAllName(HashMap<String,String> map){
        String name = "";
        Iterator myVeryOwnIterator = map.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            String value=(String)map.get(key);
            name += value;
        }
        return name;
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameRoom;
        private TextView tvMember;
        private ImageView ivAvatar;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            tvMember =(TextView) itemView.findViewById(R.id.tvMember);
            tvNameRoom = (TextView) itemView.findViewById(R.id.tvNameRoom);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivItemSGC);
        }
    }
}

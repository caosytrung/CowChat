package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/31/2016.
 */

public class RVCallAdapter extends RecyclerView.Adapter<RVCallAdapter.ViewHolder> {
    private List<FriendChat> friendChats;
    private Context mContext;
    private IClickItemRycyclerView iClickItemRycyclerView;
    private IClickItemRycyclerView iClickVideo;

    public void setiClickVideo(IClickItemRycyclerView iClickVideo) {
        this.iClickVideo = iClickVideo;
    }

    public RVCallAdapter(Context context, List<FriendChat> friendChats){
        mContext = context;
        this.friendChats = friendChats;
    }

    public void setiClickItemRycyclerView(IClickItemRycyclerView iClickItemRycyclerView) {
        this.iClickItemRycyclerView = iClickItemRycyclerView;
    }

    public void setFriendChats(List<FriendChat> friendChats) {
        this.friendChats = friendChats;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.
                from(mContext).inflate(R.layout.item_rv_call,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FriendChat friendChat = friendChats.get(position);
        holder.tvName.setText(friendChat.getFullName());
        MrgTypeFace.setFontAnswesSome(holder.tvCall,mContext);
        holder.tvCall.setText("\uf095");
        Picasso.with(mContext).load(friendChat.getLinkAvatar()).into(holder.ivAvatar);
        holder.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemRycyclerView.onItemClick(position,view);
            }
        });

        MrgTypeFace.setFontAnswesSome(holder.tvVideo,mContext);
        holder.tvVideo.setText("\uf03d");

        holder.tvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickVideo.onItemClick(position,v);
            }
        });


    }

    @Override
    public int getItemCount() {
        return friendChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivAvatar;
        private TextView tvName;
        private TextView tvCall;
        public  TextView tvVideo;
        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivItemrCall);
            tvName = (TextView) itemView.findViewById(R.id.tvNameItemCall);
            tvCall = (TextView) itemView.findViewById(R.id.tvItemCall);
            tvVideo = (TextView) itemView.findViewById(R.id.tvItemVideo);
        }
    }


}

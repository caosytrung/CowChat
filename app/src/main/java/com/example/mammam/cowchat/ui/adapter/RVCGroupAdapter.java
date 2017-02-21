package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.FriendChat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/28/2016.
 */

public class RVCGroupAdapter extends RecyclerView.Adapter<RVCGroupAdapter.ViewHolder> {
    private List<FriendChat> friendChatList;
    private List<FriendChat> friendChatsCreate;
    private Context mContext;
    public RVCGroupAdapter(Context context){
        mContext = context;
        friendChatList = new ArrayList<>();
        friendChatsCreate = new ArrayList<>();
    }

    public void setFriendChatList(List<FriendChat> friendChatList) {
        this.friendChatList = friendChatList;
    }

    public List<FriendChat> getFriendChatsCreate() {
        return friendChatsCreate;
    }

    public void setFriendChatsCreate(List<FriendChat> friendChatsCreate) {
        this.friendChatsCreate = friendChatsCreate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_group_chat,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(friendChatList.get(position).getFullName());
        Picasso.with(mContext).load(friendChatList.
                get(position).getLinkAvatar()).into(holder.ivAvatar);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    friendChatsCreate.add(friendChatList.get(position));
                } else {
                    friendChatsCreate.remove(friendChatList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendChatList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        private ImageView ivAvatar;
        private TextView tvName;
        private CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivItemGC);
            tvName = (TextView) itemView.findViewById(R.id.tvItemGC);
            checkBox = (CheckBox) itemView.findViewById(R.id.cbItemGC);
        }
    }
}

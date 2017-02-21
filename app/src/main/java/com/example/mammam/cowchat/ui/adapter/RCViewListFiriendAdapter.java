package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mam  Mam on 12/23/2016.
 */

public class RCViewListFiriendAdapter extends RecyclerView.Adapter<RCViewListFiriendAdapter.MyViewHodler> {
    private List<FriendChat> friendChats;
    private Context mContext;
    private IClickItemRycyclerView iClickItemRycyclerView;

    public void setFriendChats(List<FriendChat> friendChats) {
        this.friendChats = friendChats;
    }

    public RCViewListFiriendAdapter(Context context,
                                    List<FriendChat> friendChats,
                                    IClickItemRycyclerView iClickItemRycyclerView){

        mContext = context;
        this.friendChats = friendChats;
        this.iClickItemRycyclerView = iClickItemRycyclerView;
    }


    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_list_firend,parent,false);

        return new MyViewHodler(v);
    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, final int position) {
        FriendChat friendChat = friendChats.get(position);
        Picasso.with(mContext).load(friendChat.getLinkAvatar()).into(holder.ivAvatar);
        holder.tvLastDate.setText(friendChat.getLastDateChat());
        holder.tvFullname.setText(friendChat.getFullName());
        if (
                null == friendChat.getLastSms()){
            holder.tvlastSms.setText("No thing");
        }
        holder.vContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemRycyclerView.onItemClick(position,view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendChats.size();
    }

    public static  class  MyViewHodler extends RecyclerView.ViewHolder{
        private ImageView ivAvatar;
        private TextView tvFullname;
        private TextView tvlastSms;
        private TextView tvLastDate;
        private View vContainer;
        public MyViewHodler(View itemView) {
            super(itemView);
            vContainer = itemView;
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarListFriend);
            tvFullname = (TextView) itemView.findViewById(R.id.tvFullnameLF);
            tvLastDate = (TextView) itemView.findViewById(R.id.tvLastDateLF);
            tvlastSms = (TextView) itemView.findViewById(R.id.tvLastSmSLF);
        }
    }
}


package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.models.FriendOnline;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;

import java.util.List;

/**
 * Created by Mam  Mam on 12/25/2016.
 */

public class RCOnlineAdapter extends RecyclerView.Adapter<RCOnlineAdapter.ViewHolder> {

    private List<FriendOnline> friendOnlines;
    private Context mContext;
    private IClickItemRycyclerView iClickItemRycyclerView;

    public void setFriendOnlines(List<FriendOnline> friendOnlines) {
        this.friendOnlines = friendOnlines;
    }

    public RCOnlineAdapter(Context context,List<FriendOnline> friendOnlines,
                           IClickItemRycyclerView iClickItemRycyclerView
                           ){
        this.friendOnlines = friendOnlines;
        this.iClickItemRycyclerView = iClickItemRycyclerView;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(mContext).inflate(R.layout.
                item_friend_online,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FriendOnline fOnline = friendOnlines.get(position);
        MrgTypeFace.setFontAnswesSome(holder.tvState,mContext);
        holder.tvFullName.setText(fOnline.getFullName());
        holder.tvState.setText("\uf111");
        if (1 == fOnline.getState()){
            holder.tvState.setTextColor(mContext.
            getResources().getColor(R.color.green_light));
        }
        else {
            holder.tvState.setTextColor(mContext.
                    getResources().getColor(R.color.gray));
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemRycyclerView.onItemClick(position,view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return friendOnlines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvFullName;
        private TextView tvState;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            tvFullName = (TextView) itemView.findViewById(R.id.tvFullNameFO);
            tvState = (TextView) itemView.findViewById(R.id.tvStateFO);
        }
    }
}

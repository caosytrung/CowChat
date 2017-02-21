package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mam  Mam on 12/22/2016.
 */

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter<RecyclerViewSearchAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewSearch";
    private List<UserChat> mUserChats;
    private Context mContext;
    private IClickItemRycyclerView iClickItemRycyclerView;


    public RecyclerViewSearchAdapter(List<UserChat> userChats,
                                     Context context,
                                     IClickItemRycyclerView iClickItemRycyclerView){
        mContext = context;
        mUserChats = userChats;
        this.iClickItemRycyclerView = iClickItemRycyclerView;
    }

    public void setmUserChats(List<UserChat> mUserChats) {
        this.mUserChats = mUserChats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_search,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserChat userChat = mUserChats.get(position);
        holder.tvFullname.setText(userChat.getFirstName() + " " + userChat.getLastName());
        holder.tvDes.setText(userChat.getDescription());
        holder.tvGmail.setText(userChat.getEmail());
        Picasso.with(mContext).load(userChat.getLinkAvartar()).into(holder.ivAvatar, new Callback() {
            @Override
            public void onSuccess() {
                holder.pbLoad.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        holder.vContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemRycyclerView.onItemClick(position,view);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG," " + mUserChats.size());

        return mUserChats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivAvatar;
        private ProgressBar pbLoad;
        private TextView tvFullname;
        private TextView tvDes;
        private TextView tvGmail;
        private View vContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            vContainer = itemView;
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivItemSearch);
            pbLoad = (ProgressBar) itemView.findViewById(R.id.progressLoadAvatar);
            tvFullname = (TextView) itemView.findViewById( R.id.tvItemFullName);
            tvDes = (TextView) itemView.findViewById(R.id.tvItemDes);
            tvGmail = (TextView) itemView.findViewById(R.id.tvItemSearch);

        }
    }
}

package com.example.mammam.cowchat.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;

/**
 * Created by dee on 31/03/2017.
 */

public class FRAdapter extends RecyclerView.Adapter<FRAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvRespond;
        private TextView tvFullName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivItemAvatarFR);
            tvRespond = (TextView) itemView.findViewById(R.id.tvItemRespondFR);
            tvFullName = (TextView) itemView.findViewById(R.id.tvItemFullNameFr);

        }
    }
}

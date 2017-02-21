package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.EventSticker;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dee on 16/02/2017.
 */

public class RVStickerAdapter extends RecyclerView.Adapter<RVStickerAdapter.ViewHolder> {
    private static final String TAG = "RCSickerAdapter";
    private Context mContext;
    private List<String> listSticker;
    private IClickItemRycyclerView iClickItemRycyclerView;
    private EventBus mEventBus;


    public RVStickerAdapter(Context context,List<String> strings){
        mEventBus = EventBus.getDefault();
        mContext = context;
        listSticker = strings;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sticker,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = listSticker.get(position);
        final EventSticker eventSticker = new EventSticker(name);
        try {
            InputStream is = mContext.getAssets().open(name);

//            Log.d("mmcmsd",stringsl[2]);
            Drawable d =  Drawable.createFromStream(is,null);
            holder.ivStckerView.setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  iClickItemRycyclerView.onItemClick(position,v);
                mEventBus.postSticky(eventSticker);
                Log.d(TAG,"SentEventSt");
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSticker.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivStckerView;
        public View vContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            vContainer = itemView;
            ivStckerView = (ImageView) itemView.findViewById(R.id.ivItemSticker);
        }
    }
}

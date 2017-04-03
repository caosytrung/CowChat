package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.ItemPenColor;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;

import java.util.List;

/**
 * Created by dee on 16/03/2017.
 */

public class PenColorAdapter extends BaseAdapter {
    private List<ItemPenColor> itemPenColors;
    private Context mContext;

    public PenColorAdapter(Context context,List<ItemPenColor> itemPenColors){
        this.itemPenColors = itemPenColors;
        mContext = context;
    }
    @Override
    public int getCount() {
        return itemPenColors.size();
    }

    @Override
    public Object getItem(int position) {
        return itemPenColors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       PenColorAdapter.MyViewHolder myViewHolder;
        if (null == convertView){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pen_color,null);
            myViewHolder = new PenColorAdapter.MyViewHolder();
            myViewHolder.tvColor = (TextView) convertView.findViewById(R.id.tvItempenColor);


            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (PenColorAdapter.MyViewHolder) convertView.getTag();
        }

        MrgTypeFace.setFontAnswesSome(myViewHolder.tvColor,mContext);
        myViewHolder.tvColor.setText("\uf040");
        myViewHolder.tvColor.setTextColor(Color.
                parseColor(itemPenColors.get(position).getColor()));

        return convertView;
    }
    private static  class MyViewHolder{
        TextView tvColor;
    }
}

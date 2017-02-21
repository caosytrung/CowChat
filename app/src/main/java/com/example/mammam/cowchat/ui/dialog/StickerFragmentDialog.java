package com.example.mammam.cowchat.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.ui.adapter.VPStickerAdapter;
import com.example.mammam.cowchat.ui.adapter.ViewPagerAdapter;
import com.example.mammam.cowchat.ui.fragm.CCCStickerFragment;
import com.example.mammam.cowchat.ui.fragm.DocStickerFragment;
import com.example.mammam.cowchat.ui.fragm.DoveStickerFragment;
import com.example.mammam.cowchat.ui.fragm.MeepStickerFragment;

/**
 * Created by dee on 16/02/2017.
 */

public class StickerFragmentDialog extends DialogFragment {
    private View vContainer;
    private TabLayout tbSticker;
    private ViewPager vpSticker;
    private VPStickerAdapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vContainer = inflater.inflate(R.layout.dialog_sticker,container,false);
        tbSticker = (TabLayout) vContainer.findViewById(R.id.tbStick);
        vpSticker = (ViewPager) vContainer.findViewById(R.id.vpSticker);
        viewPagerAdapter = new VPStickerAdapter(getChildFragmentManager());

        addFragment();


        vpSticker.setAdapter(viewPagerAdapter);
        tbSticker.setupWithViewPager(vpSticker);
        viewPagerAdapter.notifyDataSetChanged();
        addIconTabLayout();

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
       // getDialog().getWindow().setLayout(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        getDialog().getWindow().setBackgroundDrawable(d);


        return vContainer;
    }

    private void addFragment() {

        viewPagerAdapter.addFragment(new MeepStickerFragment());
        viewPagerAdapter.addFragment(new DoveStickerFragment());
        viewPagerAdapter.addFragment(new DocStickerFragment());
        viewPagerAdapter.addFragment(new CCCStickerFragment());
        viewPagerAdapter.addFragment(new MeepStickerFragment());
    }

    private void addIconTabLayout(){
        ImageView iv1 = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.customtab_sticker,null);
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(),R.drawable.m_ic5);
        iv1.setImageBitmap(bm1);
        tbSticker.getTabAt(0).setCustomView(iv1);

        ImageView ivDove = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.customtab_sticker,null);
        Bitmap bmDove = BitmapFactory.decodeResource(getResources(),R.drawable.dv_ic6);
        ivDove.setImageBitmap(bmDove);
        tbSticker.getTabAt(1).setCustomView(ivDove);

        ImageView iv2 = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.customtab_sticker,null);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(),R.drawable.dg_ic6);
        iv2.setImageBitmap(bm2);
        tbSticker.getTabAt(2).setCustomView(iv2);

        ImageView iv3 = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.customtab_sticker,null);
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.ccc_ic10);
        iv3.setImageBitmap(bm3);
        tbSticker.getTabAt(3).setCustomView(iv3);

        ImageView iv4 = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.customtab_sticker,null);
        Bitmap bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.m_ic4);
        iv4.setImageBitmap(bm4);
        tbSticker.getTabAt(4).setCustomView(iv4);
    }
}

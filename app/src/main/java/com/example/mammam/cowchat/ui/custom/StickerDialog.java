package com.example.mammam.cowchat.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.ui.adapter.VPStickerAdapter;
import com.example.mammam.cowchat.ui.adapter.ViewPagerAdapter;
import com.example.mammam.cowchat.ui.fragm.MeepStickerFragment;
import com.example.mammam.cowchat.ui.fragm.RegisterInforFragment;

/**
 * Created by dee on 16/02/2017.
 */

public class StickerDialog extends Dialog {
//    private TabLayout tbSticker;
//    private ViewPager vpSticker;
//    private VPStickerAdapter viewPagerAdapter;
//    private FragmentManager mFragmentManager;


    public StickerDialog(Context context,FragmentManager fragmentManager) {
        super(context);
        setContentView(R.layout.dialog_sticker);
     //   mFragmentManager = fragmentManager;
//        init();
//        initViews();
    }


//
//    public void init(){
//        setContentView(R.layout.dialog_sticker);
//        viewPagerAdapter = new VPStickerAdapter(mFragmentManager);
//       // viewPagerAdapter.addFragment(new MeepStickerFragment());
//        viewPagerAdapter.addFragment(new RegisterInforFragment());
//
//    }
//
//    public void initViews(){
//        tbSticker = (TabLayout) findViewById(R.id.tbStick);
//        vpSticker = (ViewPager) findViewById(R.id.vpSticker);
//        vpSticker.setAdapter(viewPagerAdapter);
//        vpSticker.setPageTransformer(true,new CubeOutTransformer());
//        tbSticker.setupWithViewPager(vpSticker);
//        viewPagerAdapter.notifyDataSetChanged();
//
//
//        customTab();
//
//    }
//    public void customTab(){
//        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_icon, null);
//        tabOne.setText("Meep");
//       // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.meep, 0, 0);
////        tbSticker.getTabAt(0).setCustomView(tabOne);
//    }
}

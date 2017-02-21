package com.example.mammam.cowchat.ui.fragm;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerSticker;
import com.example.mammam.cowchat.ui.adapter.RVStickerAdapter;

import java.util.List;

/**
 * Created by dee on 17/02/2017.
 */

public class DocStickerFragment extends BaseFragment {

    private RecyclerView rvStickerMeep;
    private RVStickerAdapter rvStickerAdapterMeep;
    private List<String> listMeep;

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_sticker_meep,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        Log.d("aaa","DAvaoroi");
        rvStickerMeep = (RecyclerView) view.findViewById(R.id.rvStickerMeep);
        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvStickerMeep.setLayoutManager(manager);
        rvStickerMeep.setAdapter(rvStickerAdapterMeep);
        rvStickerAdapterMeep.notifyDataSetChanged();
    }

    @Override
    public void initComponents() {
        listMeep = ManagerSticker.getINSTANCE().getListDog(getContext());
        rvStickerAdapterMeep = new RVStickerAdapter(getContext(),listMeep);
    }

    @Override
    public void setEventViews() {

    }
}

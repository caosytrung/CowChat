package com.example.mammam.cowchat.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.ui.adapter.RecyclerViewSearchAdapter;
import com.example.mammam.cowchat.ui.fragm.FragmentSearch;
import com.example.mammam.cowchat.ui.fragm.ViewInforUserFragment;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/22/2016.
 */

public class SearchActivity extends BaseActivity implements IConstand {

    private FragmentSearch mFragmentSearch;
    private ViewInforUserFragment viewInforUserFragment;



    @Override
    protected void initComponents() {

    }

    public void initFragment(){
        viewInforUserFragment = new ViewInforUserFragment(getSupportFragmentManager());
        mFragmentSearch = new FragmentSearch();
        getSupportFragmentManager().beginTransaction().
                add(R.id.rvContentSearch,mFragmentSearch).commit();
    }

    @Override
    protected void initViews() {
        initFragment();
    }

    @Override
    protected void setEventViews() {

    }
    public void openFragmentViewInfor(){

        getSupportFragmentManager().beginTransaction().
                replace(R.id.rvContentSearch,viewInforUserFragment,
                        ViewInforUserFragment.class.getName()).commit();

    }


    @Override
    protected void setViewRoot() {
        setContentView(R.layout.activity_search);
    }


}

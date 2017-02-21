package com.example.mammam.cowchat.ui.activity;

import android.os.Bundle;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.ui.fragm.RegisterAcountFragment;
import com.example.mammam.cowchat.ui.fragm.RegisterInforFragment;
import com.example.mammam.cowchat.ui.interf.INextFragment;

/**
 * Created by Mam  Mam on 12/12/2016.
 */

public class SignupActivity extends BaseActivity
        implements INextFragment,IConstand{

    private RegisterAcountFragment mRegisterAcountFragment;
    private RegisterInforFragment mRegisterInforFragment;

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initViews() {
        initFragmnent();
    }

    @Override
    protected void setEventViews() {

    }

    @Override
    protected void setViewRoot() {
        setContentView(R.layout.activity_signup);
    }

    @Override
    public void nextFragmentInfor(String email, String password) {
        Bundle bundle = new Bundle();
        bundle.putString(EMAIL,email);
        bundle.putString(PASSWORD,password);
        mRegisterInforFragment.setArguments(bundle);
        showFragment2();
    }
    private void initFragmnent(){
        mRegisterAcountFragment = new RegisterAcountFragment();
        mRegisterAcountFragment.setiNextFragment(this);
        mRegisterInforFragment = new RegisterInforFragment();

        getSupportFragmentManager().
                beginTransaction().add(R.id.content_fragment,
                mRegisterAcountFragment,
                RegisterAcountFragment.class.getName()).commit();
    }

    private void showFragment2(){
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content_fragment,
                        mRegisterInforFragment,
                        RegisterInforFragment.class.getName()).commit();
    }
}

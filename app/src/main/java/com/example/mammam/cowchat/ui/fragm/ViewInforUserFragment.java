package com.example.mammam.cowchat.ui.fragm;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.ui.adapter.ViewPagerInforAdapter;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.interf.IChapNLMKB;
import com.example.mammam.cowchat.ui.interf.ICheckRelation;
import com.example.mammam.cowchat.ui.interf.IGuiDiLMKB;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Mam  Mam on 12/22/2016.
 */

public class ViewInforUserFragment extends BaseFragment
        implements ICheckRelation, View.OnClickListener,IConstand {

    private static final String TAG ="ViewInforUserFragment" ;
    private ProgressBar progressBarRelative;
    private TabLayout tbInforFriend;
    private ViewPager vpgInforFriend;
    private ViewPagerInforAdapter adapter;
    private FragmentManager fragmentManager;
    private EventBus eventBus;
    private ImageView ivAvatar;
    private ProgressBar progressBar;
    private TextView tvRelation;
    private ManagerUser managerUser;
    private String idUser;
    private UserChat mUserChat;

    public ViewInforUserFragment(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onStart() {

    super.onStart();
    eventBus.register(this);

}
    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_view_infor_friend,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        progressBarRelative = (ProgressBar)
                view.findViewById(R.id.progressRelative);

        tvRelation = (TextView) view.findViewById(R.id.tvRelation);

        progressBar = (ProgressBar)
                view.findViewById(R.id.progressInforfriend);

        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatarViewInfor);
        tbInforFriend = (TabLayout) view.findViewById(R.id.tabInforUser);
        vpgInforFriend = (ViewPager) view.findViewById(R.id.vpgInforUSer);
        vpgInforFriend.setAdapter(adapter);
        vpgInforFriend.setPageTransformer(true,new CubeOutTransformer());
        tbInforFriend.setupWithViewPager(vpgInforFriend);
        adapter.notifyDataSetChanged();
        customTab();


    }

    public void customTab(){

        TextView    tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_icon,null);
        MrgTypeFace.setFontAnswesSome(tabOne,getContext());
        tabOne.setText("Information");
        tbInforFriend.getTabAt(0).setCustomView(tabOne);
        TextView    tabSecond = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_infor,null);
        MrgTypeFace.setFontAnswesSome(tabSecond,getContext());
        tabSecond.setText("Timeline");
        tbInforFriend.getTabAt(1).setCustomView(tabSecond);
    }

    @Subscribe(sticky = true ,threadMode = ThreadMode.MAIN)
    public void onEvent(UserChat userChat){
        idUser = userChat.getId();
        mUserChat = userChat;
        Picasso.with(getContext()).load(userChat.getLinkAvartar()).into(ivAvatar, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {

            }
        });
        managerUser.checkFriend(userChat.getId());


    }

    @Override
    public void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Override
    public void initComponents() {
        managerUser = new ManagerUser(getContext());
        managerUser.setiCheckRelation(this);
        adapter = new ViewPagerInforAdapter(fragmentManager);
        addFragment();
        eventBus = EventBus.getDefault();
        managerUser.setiGuiDiLMKB(new IGuiDiLMKB() {
            @Override
            public void guiLMKB(boolean result) {
                progressBarRelative.setVisibility(View.GONE);
                if (result){
                    tvRelation.setText(DGLMKB);
                }
                else {
                    tvRelation.setText(KB);
                }
            }
        });
        managerUser.setiChapNLMKB(new IChapNLMKB() {
            @Override
            public void chapNLMKB(boolean result) {
                progressBarRelative.setVisibility(View.GONE);
                if (result){
                    tvRelation.setText(BB);
                }
                else {
                    tvRelation.setText(KB);
                }
            }
        });

    }

    private void addFragment(){
        adapter.addFragment(new CoreInforFragment());
        adapter.addFragment(new TimeLineFragment());
    }
    @Override
    public void setEventViews() {
        tvRelation.setOnClickListener(this);
    }
    private void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void checkFiend(boolean result) {
        if (!result){
            managerUser.checkXemMinhDaGuiLoiMoiCHoHoChua(idUser);
            return;
        }
        tvRelation.setText(BB);
        hideProgress();

    }

    @Override
    public void checkDaGuiDiChua(boolean result) {
        if (!result){
            managerUser.checkXemCoGuiLoiDenMinh(idUser);
            return;
        }
        tvRelation.setText(DGLMKB);
        hideProgress();
    }

    @Override
    public void checkDaDuocGuiToiChua(boolean result) {
        if (!result){
            tvRelation.setText(KB);
        }
        else {
            tvRelation.setText(CNLMKB);
        }
        hideProgress();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvRelation:

                if (tvRelation.getText().equals(KB)){
                    progressBarRelative.setVisibility(View.VISIBLE);
                    managerUser.guiDiLoiMoiKetBan(mUserChat);
                    Log.d(TAG,"Da nhan gui");
                }
                else if (tvRelation.getText().equals(CNLMKB)){
                    progressBarRelative.setVisibility(View.VISIBLE);
                    Log.d("Usercha1",mUserChat.getEmail());
                    managerUser.chapNhanLoiMoiKeBan(mUserChat);
                }
                else {
                    // DO something
                }
                break;
            default:
                break;
        }
    }
}

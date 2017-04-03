package com.example.mammam.cowchat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.service.ChatService;
import com.example.mammam.cowchat.service.ServiceCall;
import com.example.mammam.cowchat.service.VideoCall;
import com.example.mammam.cowchat.ui.adapter.ViewPagerAdapter;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;

import com.example.mammam.cowchat.ui.fragm.CallFragment;
import com.example.mammam.cowchat.ui.fragm.DrawFragment;
import com.example.mammam.cowchat.ui.fragm.FriendFragment;
import com.example.mammam.cowchat.ui.fragm.GroupChatFragment;
import com.example.mammam.cowchat.ui.fragm.OnlineFragment;

/**
 * Created by Mam  Mam on 12/21/2016.
 */

public class MainActivity extends BaseActivity implements IConstand, View.OnClickListener {

    private static final String TAG = "MainActivityyyy" ;
    private TabLayout tabHead;
    private ViewPager vpgMain;
    private ViewPagerAdapter pagerAdapter;
    private TextView tvOpenDraw;
    private TextView tvSearch;
    private DrawerLayout drawerLayoutMain;
    private ImageView ivDrawAvatar;
    private ManagerDbLocal managerDbLocal;
    private UserLocal userLocal;
    private TextView tvFullNameDl;
    private TextView tvEmailDl;
    private TextView tvSignoutDl;


    @Override
    protected void initComponents() {
        startService(new Intent(this, ServiceCall.class));
        startService(new Intent(this, ChatService.class));
        startService(new Intent(this, VideoCall.class));
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        addFragment();
        saveDataDraw();
    }

    private void saveDataDraw(){

        SharedPreferences sharedPreferences =
                getSharedPreferences(DRAW,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_DEFAULT_WIDTH_PEN_DRAW,DEFAULT_WIDTH_PEN_DRAW);
        editor.putString(KEY_DEFAULE_COLOR_PEN,DEFAULE_COLOR_PEN);
        editor.putString(KEY__DEFAULT_COLOR_BG_DRAW,DEFAULT_COLOR_BG_DRAW);
        editor.commit();



    }
    private void addFragment(){
        pagerAdapter.addFragment(new FriendFragment());
        pagerAdapter.addFragment(new CallFragment());
        pagerAdapter.addFragment(new OnlineFragment());
        pagerAdapter.addFragment(new GroupChatFragment(getSupportFragmentManager()));

        managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(this);
        managerDbLocal.openDatatbase();
        userLocal = managerDbLocal.getUser();
        Log.d(TAG,userLocal.getFirstName() + " " + userLocal.getLastName());

        managerDbLocal.closeDatabase();
    }

    @Override
    protected void initViews() {
        tvFullNameDl = (TextView) findViewById(R.id.tvFullNameDL);
        tvFullNameDl.setText(userLocal.getFirstName() + " " + userLocal.getLastName());
        tvEmailDl = (TextView) findViewById(R.id.tvEmailDl);
        tvEmailDl.setText("( " + userLocal.getEmail() + " )");
        tvSignoutDl = (TextView) findViewById(R.id.tvSignOutDL);
        ivDrawAvatar = (ImageView) findViewById(R.id.ivDrawAvater);
        ivDrawAvatar.setImageBitmap(userLocal.getBitMapAvatar());

        drawerLayoutMain = (DrawerLayout) findViewById(R.id.drawerMain);
        tvOpenDraw = (TextView) findViewById(R.id.tvOpenDraw);
        tvSearch = (TextView) findViewById(R.id.tvSearchMain);
        MrgTypeFace.setFontAnswesSome(tvOpenDraw,this);
        MrgTypeFace.setFontAnswesSome(tvSearch,this);
        tvOpenDraw.setText("\uf0c9");
        tvSearch.setText("\uf002");

        tabHead = (TabLayout) findViewById(R.id.tabHead);



        vpgMain = (ViewPager) findViewById(R.id.vpgMain);
        vpgMain.setOffscreenPageLimit(1);
        vpgMain.setAdapter(pagerAdapter);
        vpgMain.setPageTransformer(true,new CubeOutTransformer());
        tabHead.setupWithViewPager(vpgMain);
        pagerAdapter.notifyDataSetChanged();
        createTabIcon();




    }

    private void createTabIcon(){
        TextView    tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_icon,null);
        MrgTypeFace.setFontAnswesSome(tabOne,this);
        tabOne.setText("\uf015");
        tabHead.getTabAt(0).setCustomView(tabOne);
        TextView    tabSecond = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_icon,null);
        MrgTypeFace.setFontAnswesSome(tabSecond,this);
        tabSecond.setText("\uf1d7");
        tabHead.getTabAt(2).setCustomView(tabSecond);
        TextView    tabThird = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_icon,null);
        MrgTypeFace.setFontAnswesSome(tabThird,this);
        tabThird.setText("\uf095");
        tabHead.getTabAt(1).setCustomView(tabThird);
        TextView    tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_icon,null);
        MrgTypeFace.setFontAnswesSome(tabFour,this);
        tabFour.setText("\uf2c0\uf2c0");
        tabHead.getTabAt(3).setCustomView(tabFour);

      //  tabHead.getTabAt(1).setCustomView(tabOne);
        //
      //  tabHead.getTabAt(2).setCustomView(tabOne);
        //
      //  tabHead.getTabAt(3).setCustomView(tabOne);
        //

       // tabOne.setCompoundDrawablesRelativeWithIntrinsicBounds();
    }

    @Override
    protected void setEventViews() {
        tvOpenDraw.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvSignoutDl.setOnClickListener(this);

    }
    public void nextConvesation(String roomId,String link,String name){
        Intent intent = new Intent(this,ConsersationActivity.class);
        intent.putExtra(ROOM_ID,roomId);
        intent.putExtra(LINK_AVATAR,link);
        intent.putExtra("NAME",name);

        startActivity(intent);
    }


    @Override
    protected void setViewRoot() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvOpenDraw:
                if (drawerLayoutMain.isDrawerOpen(Gravity.LEFT)) {

                    drawerLayoutMain.closeDrawer(Gravity.LEFT);
                }
                else {
                    drawerLayoutMain.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.tvSearchMain:
                startActivity(new Intent(this,SearchActivity.class));

                break;
            case R.id.tvSignOutDL:
                ManagerUser managerUser = new ManagerUser(this);
                managerUser.changeStatus(0);
                (new ManagerUser(this)).signOut();
                clearSharedPrefrence(this);
                startActivity(new Intent(this,LoginActivity.class));
                stopService(new Intent(this,VideoCall.class));
                stopService(new Intent(this,ChatService.class));
                stopService(new Intent(this,ServiceCall.class));
                break;
            default:
                break;
        }
    }
    public static void clearSharedPrefrence(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }


}

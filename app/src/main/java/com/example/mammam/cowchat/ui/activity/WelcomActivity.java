package com.example.mammam.cowchat.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.interf.ILogin;

public class WelcomActivity extends BaseActivity implements IConstand,Runnable,ILogin{
    private ImageView ivGrass1;
    private ImageView ivGrass2;
    private ImageView ivGrass3;
    private ImageView ivGrass4;
    private ImageView ivGrass5;
    private Animation aniGrass1;
    private Animation aniGrass2;
    private Animation aniGrass3;
    private Animation aniGrass4;
    private Animation aniGrass5;
    private TableRow tbrLoading;
    private TextView tvNoConect;
    private ManagerUser mManagerUser;
    private String email;
    private String password;
    private BroadcastInternet mBroadcastInternet;
    private ManagerDbLocal managerDbLocal;
    private ImageView ivAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initComponents() {
        managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(this);
        managerDbLocal.openDatatbase();

        mBroadcastInternet = new BroadcastInternet();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      //  registerReceiver(mBroadcastInternet,intentFilter);

        mManagerUser = new ManagerUser(this);
        mManagerUser.setiLogin(this);
//        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN,MODE_PRIVATE);
//        String a= sharedPreferences.getString(SAVE_USER,null);
//        String email = "";
//        String password = "";
//        if (null == a){
//            Intent intent = getIntent();
//            email = intent.getStringExtra(SAVE_USER);
//            password = intent.getStringExtra(SAVE_PASSWORD);
//
//        }
//        else {
////            email = sharedPreferences.getString(SAVE_USER,"");
////            password = sharedPreferences.getString(SAVE_PASSWORD,"");
//
//        }
    }

    private void loadData(){

        UserLocal user = managerDbLocal.getUser();
        Bitmap bitmap = user.getBitMapAvatar();
        ivAvatar.setImageBitmap(bitmap);
        managerDbLocal.closeDatabase();

        Intent intent = getIntent();
        switch (intent.getAction()){
            case WELCOME:
                (new Thread(this)).start();
                break;
            case LOGIN:

                if (isNetworkConnected(this)){
                    SharedPreferences sharedPreferences = getSharedPreferences(LOGIN,MODE_PRIVATE);
                    email = sharedPreferences.getString(SAVE_USER,"");
                    password = sharedPreferences.getString(SAVE_PASSWORD,"");
                    mManagerUser.login(email,password,true);
                }
                else {
                    showANoInternetConnect();
                }
                break;
            default:
                break;

        }

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void initViews() {
        ivGrass1 = (ImageView) findViewById(R.id.ivGrass1);
        ivGrass2 = (ImageView) findViewById(R.id.ivGrass2);
        ivGrass3 = (ImageView) findViewById(R.id.ivGrass3);
        ivGrass4 = (ImageView) findViewById(R.id.ivGrass4);
        ivGrass5 = (ImageView) findViewById(R.id.ivGrass5);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatarWelcome);

        tbrLoading = (TableRow) findViewById(R.id.tbrLoading);
        tvNoConect = (TextView) findViewById(R.id.tvNoInternet);
        initAnimation();
        loadData();

     //   ivGrass1.setAnimation(aniGrass1);
      //  ivGrass2.setAnimation(aniGrass2);
      //  ivGrass3.setAnimation(aniGrass3);
      // ivGrass4.setAnimation(aniGrass4);
       // ivGrass5.setAnimation(aniGrass5);

       // aniGrass1.start();
     //   aniGrass2.start();
       // aniGrass3.start();
      //  aniGrass4.start();
       // aniGrass5.start();

    }
    private void showLoading(){
        tvNoConect.setVisibility(View.GONE);
        tbrLoading.setVisibility(View.VISIBLE);
    }
    public void showANoInternetConnect(){
        tvNoConect.setVisibility(View.VISIBLE);
        tbrLoading.setVisibility(View.GONE);
    }

    private void initAnimation(){
        aniGrass1 = AnimationUtils.loadAnimation(this,R.anim.ani_grass1);
        aniGrass2 = AnimationUtils.loadAnimation(this,R.anim.ani_grass2);
        aniGrass3 = AnimationUtils.loadAnimation(this,R.anim.ani_grass3);
        aniGrass4 = AnimationUtils.loadAnimation(this,R.anim.ani_grass_4);
        aniGrass5 = AnimationUtils.loadAnimation(this,R.anim.ani_grass5);
    }

    @Override
    protected void setEventViews() {

    }

    @Override
    protected void setViewRoot() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void run() {
        for (int i = 0 ; i < 2 ; i ++){
            SystemClock.sleep(1000);
            if (i == 1){
                startActivity(new Intent(WelcomActivity.this,MainActivity.class));
                finish();
            }
        }
    }

    @Override
    public void login(boolean isSuc, String notif) {
        //startactivity and finis
    }

    public class BroadcastInternet extends  BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "android.net.conn.CONNECTIVITY_CHANGE":
                    if (isNetworkConnected(WelcomActivity.this)){
                        // start activity and finis this
                        showLoading();
                        mManagerUser.login(email,password,true);
                    }
                    else {
                        showANoInternetConnect();
                    }
            }
        }
    }
}

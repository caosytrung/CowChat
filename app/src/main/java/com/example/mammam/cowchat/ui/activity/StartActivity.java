package com.example.mammam.cowchat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.models.IConstand;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mam  Mam on 12/12/2016.
 */

public class StartActivity extends AppCompatActivity implements Animation.AnimationListener,IConstand {
    private TextView mChar1;
    private TextView mChar2;
    private TextView mChar3;
    private TextView mChar4;
    private TextView mChar5;
    private TextView mChar6;
    private TextView mChar7;
    private ImageView mIvCow;
    private RelativeLayout mRvStart;
    private Animation mAniChar1;
    private Animation mAniChar2;
    private Animation mAniChar3;
    private Animation mAniChar4;
    private Animation mAniChar5;
    private Animation mAniChar6;
    private Animation mAniChar7;
    private Animation mAniBg;
    private Animation mAniCow;
    private ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();

    Map<String, Object> value = new HashMap<>();
    Object dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dm = ServerValue.TIMESTAMP;
        Log.d("sdsa","afaf");
         DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        ref.child("cc").setValue(ServerValue.TIMESTAMP);
        ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
               // ref.setValue(dataSnapshot.getValue());
            }

            public void onCancelled(DatabaseError databaseError) { }
        });

  //      ref.setValue(ServerValue.TIMESTAMP);
//        ref.child("zzz").setValue(ServerValue.TIMESTAMP);

        setContentView(R.layout.activity_start);
        managerDbLocal.setContext(this);
        copyDatabase();
        managerDbLocal.openDatatbase();
       // managerDbLocal.deleteAllUser();
        managerDbLocal.closeDatabase();


        Typeface tp = Typeface.createFromAsset(getAssets(),"fontawesome-webfont.ttf");
        // tv.setType(tp);
        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1234);
            }
            else {
                initAndControllerViews();
            }
        }
        else {
            initAndControllerViews();
        }
    }
    @Exclude
    public long getTimestampCreatedLong(){
        return (long)dm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initAndControllerViews();
    }

    private void copyDatabase(){
        managerDbLocal.setContext(this);
        managerDbLocal.copyDatabaseToSystem();
    }

    private void initAndControllerViews() {
        mChar1 = (TextView)findViewById(R.id.char_1);
        mChar2 = (TextView)findViewById(R.id.char_2);
        mChar3 = (TextView)findViewById(R.id.char_3);
        mChar4 = (TextView)findViewById(R.id.char_4);
        mChar5 = (TextView)findViewById(R.id.char_5);
        mChar6 = (TextView)findViewById(R.id.char_6);
        mChar7 = (TextView)findViewById(R.id.char_7);
        mIvCow = (ImageView)findViewById(R.id.img_cow);
        mRvStart = (RelativeLayout) findViewById(R.id.rv_start);

        mIvCow.setVisibility(View.INVISIBLE);

        Typeface typeface = Typeface.
                createFromAsset(getAssets(),"font1.ttf");

        mChar1.setTypeface(typeface);
        mChar2.setTypeface(typeface);
        mChar3.setTypeface(typeface);
        mChar4.setTypeface(typeface);
        mChar5.setTypeface(typeface);
        mChar6.setTypeface(typeface);
        mChar7.setTypeface(typeface);

        mAniBg = AnimationUtils.loadAnimation(this,
                R.anim.ani_bg_start);
        mRvStart.setAnimation(mAniBg);
        mAniBg.setAnimationListener(this);
        mAniBg.start();

        mAniChar1 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char1
        );
        mChar1.setAnimation(mAniChar1);
        mAniChar1.start();

        mAniChar2 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char2
        );
        mChar2.setAnimation(mAniChar2);
        mAniChar2.start();

        mAniChar3 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char3
        );
        mChar3.setAnimation(mAniChar3);
        mAniChar3.start();

        mAniChar4 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char4
        );
        mChar4.setAnimation(mAniChar4);
        mAniChar4.start();

        mAniChar5 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char5
        );
        mChar5.setAnimation(mAniChar5);
        mAniChar5.start();

        mAniChar6 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char6
        );
        mChar6.setAnimation(mAniChar6);
        mAniChar6.start();

        mAniChar7 = AnimationUtils.loadAnimation(
                this,
                R.anim.ani_char7
        );
        mChar7.setAnimation(mAniChar7);
        mAniChar7.start();

        mAniCow =AnimationUtils.loadAnimation(
                this,
                R.anim.ani_bg_cow);
        mIvCow.setAnimation(mAniCow);
        mAniCow.start();


    }


    @Override
    public void onAnimationStart(Animation animation) {
        mIvCow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN,MODE_PRIVATE);
        String a= sharedPreferences.getString(SAVE_USER,null);

        Intent intent  = new Intent();
        if (isLogin(this)){
            intent.setClass(this,MainActivity.class);
        }
        else {
            intent.setClass(this,LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
    public static boolean isLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN,MODE_PRIVATE);
        String userName = sharedPreferences.getString(SAVE_USER,null);
        if (null == userName){
            return false;
        }
        return true;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}


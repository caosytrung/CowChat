package com.example.mammam.cowchat.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.activity.WelcomActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mam  Mam on 12/31/2016.
 */

public class CallService extends Service {
    private Call  mCallToFriend;
    private Call mCallReceiver;
    private CallClient mCallClient;
    private SinchClient mSinchClient;
    private EventBus mEventBus;




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    public class CallBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("CALL")){
                Log.d("sdasdas","sadasd");
            }
        }
    }
}

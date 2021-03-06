package com.example.mammam.cowchat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerRington;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.EventCall;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.custom.MyViewGroup;
import com.example.mammam.cowchat.ui.custom.RippleBackground;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;

/**
 * Created by Mam  Mam on 1/4/2017.
 */

public class ServiceCall extends Service implements IConstand,
        SinchClientListener, CallClientListener, View.OnClickListener,Runnable {
    private static final String TAG = ServiceCall.class.getName();
    private SinchClient mSinchClient;
    private CallClient mCallClient;
    private Call mCallTo;
    private Call mCallReceivei;
    private WindowManager mWindowManager;
    private MyViewGroup myViewGroupCaller;
    private MyViewGroup myViewGroupInComingCall;
    private MyViewGroup mViewGroupPrgReceiver;
    private WindowManager.LayoutParams mParams;
    private EventBus mEventBus;
    private ImageView ivCallTo;
    private TextView tvCallTo;
    private String idCall;
    private ImageView ivCallIncoming;
    private TextView tvCallIncoming;
    private TextView tvCallStopIncom;
    private TextView tvCallAnwserInco;
    private Button btnStopCallTo;
    private Handler handler;
    private Message message;
    private long mCallStart = 0;
    private boolean isCall = false;
    private TextView tvTimeCaller;
    private RippleBackground rpAnwserRei;
    private RippleBackground rpSopRei;
    private RippleBackground rpIvCallTo;
    private ManagerRington mManagerRington;
    private ImageView ivCallPrgRei;
    private TextView tvCallNamePrgRei;
    private TextView tvCallStopPrgRei;
    private TextView tvCallTimePrgRei;
    private RippleBackground rbIvPrgReceiver;
    private EventCall mEventCall;
    private UserChat mUserChatCaller;
    private Vibrator mVibrator;
    private TextView tvVolumeCaller;
    private TextView tvVoiceCaller;
    private ManagerRington mMrgRingtonCaller;
    private boolean isBiggerSpeaker;
    private boolean isMuteVoice;
    private TextView tvVolumeReceiver;
    private TextView tvVoiceReceiver;
    private LinearLayout lnCn;




    @Override
    public void onCreate() {
        Log.d(TAG,"Createdddddddddd");
        super.onCreate();
        initComponent();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEvent(EventCall eventCall){
       mEventCall = eventCall;
        Log.d(TAG,"DA calllllllllllllllll");

        Picasso.with(this).load(eventCall.getLinkAvatar()).into(ivCallTo);
        tvCallTo.setText(eventCall.getName());
        mCallTo = mCallClient.callUser(eventCall.getId());

        mCallTo.addCallListener(new CallListener() {
            @Override
            public void onCallProgressing(Call call) {
              Log.d("ihihihi","Progress");

            }

            @Override
            public void onCallEstablished(Call call) {
                isCall = true;
                Log.d("ihihihi","Conected");
                tvTimeCaller.setVisibility(View.VISIBLE);
                mCallStart = System.currentTimeMillis();
                startTHread();
            }

            @Override
            public void onCallEnded(Call call) {
                Log.d("ihihihi","Endd");
                tvTimeCaller.setVisibility(View.GONE);
                mWindowManager.removeView(myViewGroupCaller);
            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }
        });


        mWindowManager.addView(myViewGroupCaller,mParams);

    }
    
    


    public void initComponent(){
        isMuteVoice = false;
        isBiggerSpeaker = false;
        mManagerRington = new ManagerRington(this);
        mMrgRingtonCaller = new ManagerRington(this);
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                tvTimeCaller.setText(formatTimespan(System.currentTimeMillis() - mCallStart));
                tvCallTimePrgRei.setText(formatTimespan(System.currentTimeMillis() - mCallStart));
                return false;
            }
        });
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

    }
    
    private void initPrgReceiver(){
        mViewGroupPrgReceiver = new MyViewGroup(this);
        View v = View.inflate(this,R.layout.call_progres_receiver,mViewGroupPrgReceiver);
        ivCallPrgRei = (ImageView) v.findViewById(R.id.ivCallPrgRecei);
        rbIvPrgReceiver = (RippleBackground) v.findViewById(R.id.rbIvPrgReceiver);
        tvCallStopPrgRei = (TextView) v.findViewById(R.id.btnCallStopReceiver);
        tvCallTimePrgRei = (TextView) v.findViewById(R.id.tvTimeReceiver);
        tvCallNamePrgRei = (TextView) v.findViewById(R.id.tvCallPrgName);
        rbIvPrgReceiver.startRippleAnimation();
        tvVoiceReceiver = (TextView) v.findViewById(R.id.tvVoiceReceiver);
        tvVolumeReceiver = (TextView) v.findViewById(R.id.tvVolumeReceiver);
        tvVolumeReceiver.setOnClickListener(this);
        tvVolumeReceiver.setOnClickListener(this);

        tvCallStopPrgRei.setOnClickListener(this);

    }

    private void initIcomingCall(){
        myViewGroupInComingCall =  new MyViewGroup(this);
        View v = View.inflate(this, R.layout.call_incoming_layout,myViewGroupInComingCall);
        ivCallIncoming = (ImageView) v.findViewById(R.id.ivCallIncoming);
        tvCallIncoming = (TextView) v.findViewById(R.id.tvCallIncoming);
        tvCallAnwserInco = (TextView) v.findViewById(R.id.btnCallAnaserIncoming);
        tvCallStopIncom = (TextView) v.findViewById(R.id.btnCallTopInComing);
        tvCallStopIncom.setOnClickListener(this);
        tvCallAnwserInco.setOnClickListener(this);
        rpSopRei = (RippleBackground) v.findViewById(R.id.rpBgStopRei);
        rpAnwserRei = (RippleBackground) v.findViewById(R.id.rpBgAnserRei);
        rpSopRei.startRippleAnimation();
        rpAnwserRei.startRippleAnimation();





        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initCall();
        initWindowManagr();
        initWindowCall();
        initIcomingCall();
        initPrgReceiver();

        return START_STICKY;
    }
    private void initCall(){
        UserLocal userLocal = ManagerUser.getUserLocal(this);
        mSinchClient = Sinch.getSinchClientBuilder().
                context(this).
                userId(userLocal.getId()).
                applicationKey(APPLICATION_KEY).
                applicationSecret(APPLICATION_SECRET).
                environmentHost(HOST).
                build();
        mSinchClient.setSupportCalling(true);
        mSinchClient.addSinchClientListener(this);
        mSinchClient.startListeningOnActiveConnection();
        mSinchClient.start();

        mCallClient = mSinchClient.getCallClient();
        mCallClient.addCallClientListener(this);

    }

    public void initWindowManagr(){
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }
    public void initWindowCall(){

        myViewGroupCaller =  new MyViewGroup(this);
        View v = View.inflate(this, R.layout.call_layout,myViewGroupCaller);
        lnCn = (LinearLayout) v.findViewById(R.id.lnContainer);
         ivCallTo = (ImageView) v.findViewById(R.id.ivCallTo);
        rpIvCallTo = (RippleBackground) v.findViewById(R.id.rpIvCallTo);
        tvCallTo = (TextView) v.findViewById(R.id.tvCallTo);
         btnStopCallTo = (Button) v.findViewById(R.id.btnStopCallTo);
        tvTimeCaller = (TextView) v.findViewById(R.id.tvTimeCaller);
        tvTimeCaller.setVisibility(View.GONE);
        btnStopCallTo.setOnClickListener(this);
        rpIvCallTo.startRippleAnimation();
        tvVoiceCaller = (TextView) v.findViewById(R.id.tvVoiceCaller);
        tvVolumeCaller = (TextView) v.findViewById(R.id.tvVolumeCaller);
        MrgTypeFace.setFontAnswesSome(tvVoiceCaller,this);
        MrgTypeFace.setFontAnswesSome(tvVolumeCaller,this);
        tvVolumeCaller.setText("\uf027");
        tvVoiceCaller.setText("\uf130");
        tvVoiceCaller.setOnClickListener(this);
        tvVolumeCaller.setOnClickListener(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onClientStarted(SinchClient sinchClient) {

    }

    @Override
    public void onClientStopped(SinchClient sinchClient) {

    }

    @Override
    public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {

    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {

    }

    @Override
    public void onLogMessage(int i, String s, String s1) {

    }

    @Override
    public void onIncomingCall(CallClient callClient, Call call) {
        long[] pattern = {0, 1000, 1000};
        mVibrator.vibrate(pattern,0);
        mCallReceivei = call;
        mCallReceivei.addCallListener(new CallListener() {
            @Override
            public void onCallProgressing(Call call) {

            }

            @Override
            public void onCallEstablished(Call call) {
                mVibrator.cancel();
                isCall = true;
                mCallStart = System.currentTimeMillis();
                tvCallTimePrgRei.setVisibility(View.VISIBLE);
                startTHread();
            }

            @Override
            public void onCallEnded(Call call) {

                tvCallTimePrgRei.setVisibility(View.GONE);
                if (false == isCall){
                    mVibrator.cancel();
                    mManagerRington.release();
                    mWindowManager.removeView(myViewGroupInComingCall);
                }
                else {
                    mWindowManager.removeView(mViewGroupPrgReceiver);
                    isCall = false;
                }
            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }
        });

        idCall = mCallReceivei.getRemoteUserId();
        Log.d("asdasdas", call.getRemoteUserId() );
        getUserCall(idCall);
    }



    public void getUserCall(String id){
        DatabaseReference child = FirebaseDatabase.getInstance().getReference();
        child = child.child(USER).child(id).child(INFORMATION_USER);
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserChat userChat =  dataSnapshot.getValue(UserChat.class);
                mUserChatCaller = userChat;
                showCallProgress(userChat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private Uri getUriRington(){
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        if(alert == null){
            // alert is null, using backup
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            // I can't see this ever being null (as always have a default notification)
            // but just incase
            if(alert == null) {
                // alert backup is null, using 2nd backup
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            }
        }
        return alert;
    }

    private void showCallProgress(UserChat userChat){
        mManagerRington.openMedia(getUriRington(),true);
        mManagerRington.play();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        mWindowManager.addView(myViewGroupInComingCall,mParams);
        Picasso.with(this).load(userChat.
                getLinkAvartar()).into(ivCallIncoming);

        tvCallIncoming.setText(userChat.getFirstName() + " " +
                userChat.getLastName());

    }
    private void startTHread(){
        Thread thread = new Thread(this);
        thread.start();
    }

    private void loadDataForPrgReceiver(){
        Picasso.with(this).load(mUserChatCaller.
                getLinkAvartar()).into(ivCallPrgRei);

        tvCallNamePrgRei.setText(mUserChatCaller.getFirstName() +
                " " +
                mUserChatCaller.getLastName());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCallAnaserIncoming:
                isCall = true;
                mManagerRington.release();
                mWindowManager.removeView(myViewGroupInComingCall);
                mWindowManager.addView(mViewGroupPrgReceiver,mParams);
                loadDataForPrgReceiver();
                mCallReceivei.answer();
              //  startTHread();
                break;

            case R.id.btnCallTopInComing:
              //  mWindowManager.removeView(myViewGroupInComingCall);
                mManagerRington.release();
                isCall = false;
                mCallReceivei.hangup();
                break;

            case R.id.btnCallStopReceiver:
               // mWindowManager.removeView(mViewGroupPrgReceiver);
                mCallReceivei.hangup();
                break;

            case R.id.btnStopCallTo:
               isCall = false;
                mCallTo.hangup();
                mCallTo = null;
//                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//                ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.layout_map,null);
//                final PopupWindow popupWindow = new PopupWindow(vg,600,600,true);
//                popupWindow.showAtLocation(lnCn, Gravity.NO_GRAVITY,80,80);
//                vg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mWindowManager.removeView(myViewGroupCaller);
//                        popupWindow.dismiss();
//
//                    }
//                });

                break;
            case R.id.tvVolumeCaller:
                if (!isBiggerSpeaker){
                    isBiggerSpeaker = true;
                    tvVolumeCaller.setTextColor(getResources().getColor(R.color.green_light));
                }
                else {
                    isBiggerSpeaker = false;
                    tvVolumeCaller.setTextColor(getResources().getColor(R.color.whiteDack));
                }
                break;
            case R.id.tvVoiceCaller:
                if (!isMuteVoice){
                    isMuteVoice = true;
                    tvVoiceCaller.setTextColor(getResources().getColor(R.color.green_light));
                }
                else {
                    isMuteVoice = false;
                    tvVoiceCaller.setTextColor(getResources().getColor(R.color.whiteDack));
                }
                break;
            case R.id.tvVolumeReceiver:
                if (!isBiggerSpeaker){
                    isBiggerSpeaker = true;
                    tvVolumeReceiver.setTextColor(getResources().getColor(R.color.green_light));
                }
                else {
                    isBiggerSpeaker = false;
                    tvVolumeReceiver.setTextColor(getResources().getColor(R.color.whiteDack));
                }
                break;
            case R.id.tvVoiceReceiver:
                if (!isMuteVoice){
                    isMuteVoice = true;
                    tvVoiceReceiver.setTextColor(getResources().getColor(R.color.green_light));
                }
                else {
                    isMuteVoice = false;
                    tvVoiceReceiver.setTextColor(getResources().getColor(R.color.whiteDack));
                }
                break;

            default:
                break;
        }
    }

    private String formatTimespan(long timespan) {
        long totalSeconds = timespan / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    @Override
    public void run() {
        while (true){
            if (!isCall){
                return;
            }
            message = new Message();
            message.what = 1;
            message.setTarget(handler);
            message.sendToTarget();;
            SystemClock.sleep(1000);
        }
    }
}

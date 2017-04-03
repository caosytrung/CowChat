package com.example.mammam.cowchat.service;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerMessage;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.MyMessage;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.adapter.RCConvesationAdapter;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.custom.MyViewGroup;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListSMs;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.mammam.cowchat.R.id.tr;

/**
 * Created by dee on 22/03/2017.
 */

public class ChatService extends Service implements IConstand {
    private static final int KC = 140;
    private List<String> listRoom;
    private DatabaseReference mDatabaseReference;
    private ManagerDbLocal managerDbLocal;
    private UserLocal mUserLocal;
    private static final String TAG = "mChatService";
    private List<String> listKey;
    private BubblesManager mBubblesManager;
    private WindowManager mWindowManager;
    private RecyclerView rvConvesion;
    private List<MyMessage> myMessageList;
    private ManagerMessage managerMessage;
    private RCConvesationAdapter rcConvesationAdapter;
    private String currentRoomId;
    private WindowManager.LayoutParams mParams;
    private MyViewGroup mChatPopup;
    private boolean isShowPopUp;
    private ChatDialog chatDialog;
    private BubbleLayout mBubbleLayout;
    private FloatingActionsMenu fabMenu;
    private boolean isInitFab;
    private Bitmap tmpBitmap;
    private ImageView iv;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initComponent();
        registerEvent();
        return START_STICKY;

    }
    private void initComponent(){
        chatDialog = new ChatDialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        listKey = new ArrayList<>();
        myMessageList = new ArrayList<>();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        managerMessage = ManagerMessage.getManagerMessage();
        managerMessage.setiListSMs(new IListSMs() {
            @Override
            public void listSms(List<MyMessage> myMessages) {
                myMessageList = myMessages;
                chatDialog.initList(myMessages);

            }
        });
        managerMessage.setiAddSms(new ManagerMessage.IAddSms() {
            @Override
            public void iAddSms(boolean result) {
                managerMessage.getListSms(currentRoomId);
            }
        });




        listRoom = new ArrayList<>();
        initBubblesManger();
        initParam();
        initChatPopUp();

    }
    private void  getRoomId(final String idFriend, final boolean add){

        final DatabaseReference child= FirebaseDatabase.
                getInstance().getReference().
                child(USER).child(mUserLocal.getId()).
                child(LIST_FRIEND).child(idFriend).
                child("roomId");
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rommId = dataSnapshot.getValue(String.class);

                if (!isInitFab){
                    Log.d("TAGaa","aa");
                    chatDialog.addFabs();

                }

                managerMessage.getListSms(rommId);
                currentRoomId = rommId;
                child.removeEventListener(this);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    };
    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            managerMessage.getListSms(currentRoomId);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void initChatPopUp(){

        mChatPopup = new MyViewGroup(this);
        View v= View.inflate(this,R.layout.chat_popup_window,mChatPopup);
        rvConvesion = (RecyclerView) v.findViewById(R.id.rvConvPopup);

    }

    private void initParam(){
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mParams.gravity = Gravity.CENTER;
    }



    private void initBubblesManger(){
        mBubblesManager = new BubblesManager.Builder(this).
                setTrashLayout(R.layout.bubble_trash_layout).
                setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {

                    }
                }).build();
        mBubblesManager.initialize();

    }


    private void addBuble(String tag){
         if (null == mBubbleLayout ){
             mBubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.bubble_layout, null);


             mBubbleLayout.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
                 @Override
                 public void onBubbleRemoved(BubbleLayout bubble ){
                     Log.d("aaaa",bubble.getTag().toString());


                 }
             });
             mBubbleLayout.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

                 @Override
                 public void onBubbleClick(BubbleLayout bubble) {
                     Toast.makeText(getApplicationContext(), "Clicked !",
                             Toast.LENGTH_SHORT).show();
                     if (isInitFab){
                         getRoomId(mBubbleLayout.getTag().toString(),false);
                     } else {
                         getRoomId(mBubbleLayout.getTag().toString(),true);
                     }

                     chatDialog.show();
                     isShowPopUp = true;
                     mBubbleLayout.setVisibility(View.INVISIBLE);

                 }
             });
             mBubbleLayout.setTag(tag);

         } else if (mBubbleLayout.getTag().toString().equals(tag)){
             return;
         } else {
             mBubblesManager.removeBubble(mBubbleLayout);
             mBubbleLayout.setTag(tag);
         }

        final DatabaseReference chid = FirebaseDatabase.getInstance().getReference().
                child(USER).child(mBubbleLayout.getTag().toString()).child(INFORMATION_USER).
                child("linkAvartar");
        chid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String url = dataSnapshot.getValue(String.class);
                Log.d("URLLLL",url);
                iv = (ImageView) mBubbleLayout.findViewById(R.id.avatar);
                Picasso.with(ChatService.this).load(url).into(iv);
                chid.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mBubblesManager.addBubble(mBubbleLayout, 60, 20);

    }

    private void registerEvent(){
        managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(this);
        managerDbLocal.openDatatbase();
        mUserLocal = managerDbLocal.getUser();
        managerDbLocal.closeDatabase();
        Log.d(TAG,mUserLocal.getId());

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference child = mDatabaseReference.child(USER).
                child(mUserLocal.getId()).child(LIST_ROOM);
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    listRoom.add(childSnapshot.getKey());

                    Log.d(TAG,childSnapshot.getKey());
                }
                Log.d(TAG,listRoom.size() + " ");
                child.removeEventListener(this);
                getMessage();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    int tn = 0;

    private void getMessage() {
        String tmp = "";

        for (int i = 0; i < listRoom.size(); i++) {
            final DatabaseReference tmpDatabase = mDatabaseReference.child(LIST_ROOM).child(listRoom.get(i)).
                    child(CONVERSATION);
            tmpDatabase.limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                       MyMessage myMessage = snapshot.getValue(MyMessage.class);
                       if (!mUserLocal.getId().equals(myMessage.getIdSent()) && myMessage.getState() == 0){
                     //      mapCheckEvent.put(myMessage.getIdSent(),snapshot.getKey());
                           listKey.add(myMessage.getIdSent());
                           Log.d("cmmmcdd",myMessage.getBody());
                           addBuble(myMessage.getIdSent());
                           tn ++;
                       }



                       Log.d(TAG,myMessage.getBody());
                       tmpDatabase.removeEventListener(this);
                   }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        for (int i = 0 ; i < listRoom.size() ; i ++){
            final DatabaseReference tmpDatabase = mDatabaseReference.child(LIST_ROOM).child(listRoom.get(i)).
                    child(CONVERSATION);
            tmpDatabase.limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        MyMessage myMessage =snapshot.getValue(MyMessage.class);

                        if (!myMessage.getIdSent().equals(mUserLocal.getId())){
                            String key= myMessage.getIdSent();
                            Log.d(TAG+"m",myMessage.getBody());
                            if (!listKey.contains(key)){
                                listKey.add(key);
                                chatDialog.addFab(myMessage.getIdSent());

                            } else {

                            }
                        } else {
                            Log.d(TAG+"m","MyMESSAGESS");
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

//        for (int i = 0; i < listRoom.size(); i++) {
//            final DatabaseReference tmpDatabase = mDatabaseReference.child(LIST_ROOM).child(listRoom.get(i)).
//                    child(CONVERSATION);
//            tmpDatabase.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    Log.d(TAG,dataSnapshot.getChildrenCount() + "COUNTTTTTTTTTTTTTT")
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            })
//        }
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ChatDialog extends Dialog implements IClickItemRycyclerView {

        private TextView tvSned;
        private EditText edtBody;
        private TextView tvCall;
        private TextView tvMenu;
        private TextView tvIpput;
        private TextView tvDraw;
        private TextView tvGallery;
        private TextView tvSticker;

        private RecyclerView rvConv;
        private RCConvesationAdapter mAdapter;



        public ChatDialog(Context context, int themeResId) {
            super(context, themeResId);

            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            getWindow().setGravity(Gravity.CENTER);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            setContentView(R.layout.chat_popup_window);
            initCOmponent();
            initViews();
            this.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK){
                        isShowPopUp = false;
                        mBubbleLayout.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });


        }



        private void initCOmponent() {

            mAdapter = new RCConvesationAdapter(getContext(),this);
        }
        private void initList(List<MyMessage> myMessageList){
            mAdapter.setMyMessages(myMessageList);
            mAdapter.notifyDataSetChanged();
            Log.d("SCRODEE","sc");
            rvConv.scrollToPosition(myMessageList.size() -1);

        }
        private void addFab(String tag){
            FloatingActionButton actionTmp = new FloatingActionButton(getBaseContext());
            actionTmp.setTag(tag);
            actionTmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRoomId(v.getTag().toString(),false);
                    Log.d("aaaaadd","aadd");
                }
            });
            setImage(actionTmp);
            Log.d("aaaaadd","ddaaaa");
            fabMenu.addButton(actionTmp);
        }

        private void addFabs(){
            isInitFab = true;
            Log.d("zesiiiiii",listKey.size() + "");
            for (int i=0 ; i < listKey.size() ; i ++){

                FloatingActionButton actionTmp = new FloatingActionButton(getBaseContext());
                actionTmp.setTag(listKey.get(i));
                actionTmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRoomId(v.getTag().toString(),false);
                        Log.d("aaaaadd",v.getTag().toString());


                    }
                });

                Log.d("aaaaadd","ddaaaa");
                setImage(actionTmp);
                fabMenu.addButton(actionTmp);
            }
            fabMenu.expand();

        }



        private void initViews(){
            fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenuPopup);
            edtBody = (EditText) findViewById(R.id.edtConvPopup);
            tvSned = (TextView) findViewById(R.id.tvSentConvPopup);
            tvMenu = (TextView) findViewById(R.id.tvConvMenuPopUp);
            tvDraw = (TextView) findViewById(R.id.tvDrawConvPopup);
            tvGallery = (TextView) findViewById(R.id.tvGalleryConvPopup);
            tvCall = (TextView) findViewById(R.id.tvConvCallPopup);
            tvIpput = (TextView) findViewById(R.id.tvInputConvPopup);
            tvSticker = (TextView) findViewById(R.id.tvNhanDanConvPopup);

            MrgTypeFace.setFontAnswesSome(tvSned,getContext());
            MrgTypeFace.setFontAnswesSome(tvGallery,getContext());
            MrgTypeFace.setFontAnswesSome(tvMenu,getContext());
            MrgTypeFace.setFontAnswesSome(tvCall,getContext());
            MrgTypeFace.setFontAnswesSome(tvIpput,getContext());
            MrgTypeFace.setFontAnswesSome(tvDraw,getContext());
            MrgTypeFace.setFontAnswesSome(tvIpput,getContext());
            MrgTypeFace.setFontAnswesSome(tvSticker,getContext());

            tvSned.setText("\uf1d8");
            tvCall.setText("\uF095");
            tvMenu.setText("\uf142 ");
            tvSticker.setText("\uf118");
            tvGallery.setText("\uf1c5");
            tvDraw.setText("\uf044");
            tvIpput.setText("\uf20d");

            rvConv = (RecyclerView) findViewById(R.id.rvConvPopup);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvConv.setLayoutManager(linearLayoutManager);
            rvConv.setAdapter(mAdapter);

            tvSned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String body = edtBody.getText().toString();
                    MyMessage myMessage = new MyMessage(1,body,"Nothing",mUserLocal.getId(),0);
                    managerMessage.getTime(myMessage,currentRoomId);
                }
            });

        }





        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

        @Override
        public void onItemClick(int pos, View v) {

        }

        @Override
        public void onItemLongCLick(int pos, View v) {

        }


        public void setImage(final FloatingActionButton image) {
            String id = image.getTag().toString();
            DatabaseReference chid = FirebaseDatabase.getInstance().getReference().
                    child(USER).child(id).child(INFORMATION_USER).
                    child("linkAvartar");
            chid.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String url = dataSnapshot.getValue(String.class);
                    Picasso.with(ChatService.this).load(url).into(new Target() {

                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            image.setImageBitmap(getCroppedBitmap(getResizedBitmap(bitmap,128,128)));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}

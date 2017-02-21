package com.example.mammam.cowchat.controll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.models.FriendOnline;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.interf.IChapNLMKB;
import com.example.mammam.cowchat.ui.interf.ICheckRelation;
import com.example.mammam.cowchat.ui.interf.ICreateRoom;
import com.example.mammam.cowchat.ui.interf.IDeleteTmpAccount;
import com.example.mammam.cowchat.ui.interf.IFriendOnline;
import com.example.mammam.cowchat.ui.interf.IGuiDiLMKB;
import com.example.mammam.cowchat.ui.interf.IListFriend;
import com.example.mammam.cowchat.ui.interf.IListUser;
import com.example.mammam.cowchat.ui.interf.ILogin;
import com.example.mammam.cowchat.ui.interf.ISaveInformation;
import com.example.mammam.cowchat.ui.interf.ISignup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/12/2016.
 */

public class ManagerUser implements IConstand {

    private SimpleDateFormat dateFormat;
    private static final String TAG = "ManagerUser";
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private ILogin iLogin;
    private ISignup iSignup;
    private IDeleteTmpAccount iDeleteTmpAccount;
    private ISaveInformation iSaveInformation;
    private IListUser iListUser;
    private ICheckRelation iCheckRelation;
    private IGuiDiLMKB iGuiDiLMKB;
    private IChapNLMKB iChapNLMKB;
    private IListFriend iListFriend;
    private IFriendOnline iFriendOnline;

    public void setiFriendOnline(IFriendOnline iFriendOnline) {
        this.iFriendOnline = iFriendOnline;
    }

    public void setiListFriend(IListFriend iListFriend) {
        this.iListFriend = iListFriend;
    }

    public void setiChapNLMKB(IChapNLMKB iChapNLMKB) {
        this.iChapNLMKB = iChapNLMKB;
    }

    public void setiGuiDiLMKB(IGuiDiLMKB iGuiDiLMKB) {
        this.iGuiDiLMKB = iGuiDiLMKB;
    }

    private UserChat userChat;

    public void setiCheckRelation(ICheckRelation iCheckRelation) {
        this.iCheckRelation = iCheckRelation;
    }

    public void setiListUser(IListUser iListUser) {
        this.iListUser = iListUser;
    }

    public void setiSaveInformation(ISaveInformation iSaveInformation) {
        this.iSaveInformation = iSaveInformation;
    }

    public void setiDeleteTmpAccount(IDeleteTmpAccount iDeleteTmpAccount) {
        this.iDeleteTmpAccount = iDeleteTmpAccount;
    }

    public void setmDatabaseReference(DatabaseReference mDatabaseReference) {
        this.mDatabaseReference = mDatabaseReference;

    }
    public void checkFriend(String id){
        final DatabaseReference child = mDatabaseReference.child(USER).
                child(getCurrentId()).child(LIST_FRIEND).
                child(id);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    iCheckRelation.checkFiend(true);
                }
                else {
                    iCheckRelation.checkFiend(false);
                }
                child.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getCurrentId(){
        if (null != mFirebaseAuth.getCurrentUser()){
            return mFirebaseAuth.getCurrentUser().getUid();
        }
        return null;
    }

    public void checkXemCoGuiLoiDenMinh(String id){
        final DatabaseReference chid = mDatabaseReference.child(USER).child(getCurrentId()).
                child(LIST_DE_NGHI_KET_BAN).child(id);
        chid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d(TAG,"no ton tai ms vl 2");
                    iCheckRelation.checkDaDuocGuiToiChua(true);
                }
                else {
                    iCheckRelation.checkDaDuocGuiToiChua(false);
                }
                chid.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void checkXemMinhDaGuiLoiMoiCHoHoChua(String id){
        final DatabaseReference child = mDatabaseReference.child(USER).child(getCurrentId()).
                child(LISt_GUI_DI_LOI_MOI).child(id);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d(TAG,"no ton tai ms vl");
                    iCheckRelation.checkDaGuiDiChua(true);
                }
                else {
                    iCheckRelation.checkDaGuiDiChua(false);
                }
                child.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getCurrentEmail(){
        if (null == mFirebaseAuth.getCurrentUser()){
            return  null;
        }
        return mFirebaseAuth.getCurrentUser().getEmail();
    }

    public void guiDiLoiMoiKetBan(UserChat usFriend){
         mDatabaseReference.child(USER).
                child(getCurrentId()).child(LISt_GUI_DI_LOI_MOI).
                 child(usFriend.getId()).setValue(usFriend.getEmail());

         mDatabaseReference.child(USER).
                child(usFriend.getId()).child(LIST_DE_NGHI_KET_BAN).child(getCurrentId()).
                 setValue(getCurrentEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG,"Da co kq");
                if (task.isSuccessful()){
                    iGuiDiLMKB.guiLMKB(true);
                }
                else {
                    iGuiDiLMKB.guiLMKB(false);
                }
            }
        });
    }

    public void chapNhanLoiMoiKeBan(UserChat userChat1){
        Log.d("Usercha1",userChat1.getEmail());
        getCurrentInformationUser(false,userChat1);


    }

    public void getAllUser(){
        final List<UserChat> userChats    = new ArrayList<>();
        final DatabaseReference chid = mDatabaseReference.child(USER);
        chid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserChat s = snapshot.child(INFORMATION_USER).getValue(UserChat.class);
                    userChats.add(s);
                }
                chid.removeEventListener(this);
                iListUser.listUserEvent(userChats);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setmFirebaseAuth(FirebaseAuth mFirebaseAuth) {
        this.mFirebaseAuth = mFirebaseAuth;
    }

    public void setiSignup(ISignup iSignup) {
        this.iSignup = iSignup;
    }

    public void setiLogin(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    public ManagerUser(Context context){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mContext = context;
        initComponent();

    }
    private void initComponent(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void signUpWithEmailAndPassword(final String email, final String password){
        if (null != mFirebaseAuth.getCurrentUser()){
            mFirebaseAuth.signOut();
        }
        Log.d(TAG,email +"  " +  password);

        mFirebaseAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    iSignup.signUp(true,mFirebaseAuth.getCurrentUser().getUid());
                }
                else {
                    iSignup.signUp(false,null);
                }

            }
        });
    }
    public void deleteTmpAccount(String email,String  password){
        if (mFirebaseAuth.getCurrentUser() == null){
            Log.d(TAG,"User null");
        }
        mFirebaseAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iDeleteTmpAccount.deleteTmpAccount(true);
                }
                else {
                    iDeleteTmpAccount.deleteTmpAccount(false);
                }
            }
        });
    }

    public void login(String email, String password, final boolean save){
        Log.d(TAG,email  + " "  + password);
        if (null == mFirebaseAuth.getCurrentUser()){
            Log.d(TAG,"Chua log in");
            mFirebaseAuth.signInWithEmailAndPassword(email,password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (!save){
                            Log.d(TAG,"Thanh cong");
                            getCurrentInformationUser(true,new UserChat());
                        }
                        else {
                            Log.d(TAG,"That bai");
                            iLogin.login(true,"succ");
                        }

                    }
                    else {
                        iLogin.login(false,"fail");
                    }
                }
            });
        }
        else {
            Log.d(TAG,"DA login truoc roi");
            mFirebaseAuth.signOut();
            mFirebaseAuth.signInWithEmailAndPassword(email,password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (!save){
                                    getCurrentInformationUser(true,new UserChat());
                                }
                                else {
                                    iLogin.login(true,"succ");
                                }

                            }
                            else {
                                iLogin.login(false,"fail");
                            }
                        }
                    });

        }
    }

    public static UserLocal getUserLocal(Context context){
        ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(context);
        managerDbLocal.openDatatbase();
        UserLocal userLocal = managerDbLocal.getUser();
        managerDbLocal.closeDatabase();
        return userLocal;
    }

    public void getCurrentInformationUser(final boolean getBitmap, final UserChat userChat1){
        String id = mFirebaseAuth.getCurrentUser().getUid();
        Log.d(TAG,id);
        final DatabaseReference child = mDatabaseReference.child(USER).child(id).child(INFORMATION_USER);
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"Da lay dc user");
                userChat = dataSnapshot.getValue(UserChat.class);
               // Log.d(TAG,userChat.getLinkAvartar());
                child.removeEventListener(this);
              if (getBitmap){
                  getBitmapFromURL(userChat.getLinkAvartar());
              }else {
                  Log.d("Usercha1",userChat1.getEmail());
                  tiepTucChapNhan(userChat1);
              }
                child.removeEventListener(this);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void getListFiend(){
        if (getCurrentId() == null){
            iListFriend.listFriend(null, false);
            return;
        }
        final List<FriendChat> friendChats = new ArrayList<>();
        final DatabaseReference child = mDatabaseReference.child(USER).
                child(getCurrentId()).child(LIST_FRIEND);
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FriendChat friendChat = snapshot.getValue(FriendChat.class);
                    friendChats.add(friendChat);
                }
                child.removeEventListener(this);
                iListFriend.listFriend(friendChats,true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void tiepTucChapNhan(final UserChat userChat1){
        final String roomId = userChat.getId() + userChat1.getId();
        Log.d("Usercha1",userChat1.getEmail());
        FriendChat friend = new FriendChat(userChat1.getLinkAvartar(),
                userChat1.getEmail(),
                userChat1.getFirstName() + " " + userChat1.getLastName()
                ,dateFormat.format(new Date()),null,roomId,userChat1.getId());

        final FriendChat me = new FriendChat(userChat.getLinkAvartar(),userChat.getEmail(),
                userChat.getFirstName() + " " + userChat.getLastName(),
                dateFormat.format(new Date()),null,roomId,userChat.getId());


        DatabaseReference myChidDelete = mDatabaseReference.child(USER).
                child(getCurrentId()).child(LIST_DE_NGHI_KET_BAN).
                child(userChat1.getId());
        DatabaseReference friendChildDelete = mDatabaseReference.child(USER).
                child(userChat1.getId()).child(LISt_GUI_DI_LOI_MOI).
                child(getCurrentId());
        DatabaseReference myChildAdd = mDatabaseReference.child(USER).
                child(getCurrentId() ).child(LIST_FRIEND).child(userChat1.getId());
        final DatabaseReference friendChildAdd = mDatabaseReference.child(USER).
                child(userChat1.getId()).child(LIST_FRIEND).child(getCurrentId());

        myChidDelete.removeValue();
        friendChildDelete.removeValue();

        myChildAdd.setValue(friend);
        final ManagerRoom managerRoom = ManagerRoom.getINSTANCE();
        FriendChat friendChat = new FriendChat(userChat1.getLinkAvartar(),
                userChat1.getEmail(),
                userChat1.getFirstName() + " " + userChat1.getLastName(),
                dateFormat.format(new Date()),
                "Nothing",roomId,userChat1.getId());
        List<FriendChat> friendChats = new ArrayList<>();
        friendChats.add(friendChat);
        managerRoom.setiCreateRoom(new ICreateRoom() {
            @Override
            public void createRoom(boolean result, String rommId) {


                friendChildAdd.setValue(me).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            iChapNLMKB.chapNLMKB(true);
                        }
                        else{
                            iChapNLMKB.chapNLMKB(false);
                        }
                    }
                });
            }
        });


        managerRoom.createGroupChat(friendChats,"Nothing",null,null,1);

    }

    public void getBitmapFromURL(String src) {

       MyAsynTask myAsynTask = new MyAsynTask();
        myAsynTask.execute(src);

    }

    private   class MyAsynTask extends AsyncTask<String,Void,Bitmap>{
        public MyAsynTask(){

        }
        @Override
        protected Bitmap doInBackground(String... src) {
            try {
                URL url = new URL(src[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setConnectTimeout(10000);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (null == bitmap){
                    iLogin.login(false,"fail");
                    Log.d(TAG,"khong lay dc bimap");
                }
                else {

                if (null != userChat){
                    ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
                    managerDbLocal.setContext(mContext);
                    managerDbLocal.openDatatbase();
                    managerDbLocal.saveUser(userChat,bitmap);
                    managerDbLocal.closeDatabase();
                    iLogin.login(true,"success");
                    Log.d(TAG,"Lay dc bitmap");
                }
                }
        }
    }

    public void saveUserInfor(UserChat userChat){
        Log.d(TAG,userChat.getId() + " Lan 1");
        mDatabaseReference.child(USER).child(userChat.getId()).
                child(INFORMATION_USER).setValue(userChat).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iSaveInformation.saveInformation(true);
                }
                else {
                    iSaveInformation.saveInformation(false);
                }
            }
        });

    }


    public void changeStatus(int state){
        mDatabaseReference.child(STATUS).child(getCurrentId()).setValue(state);
    }

    public  void signOut(){
        if (null != mFirebaseAuth.getCurrentUser()){
            mFirebaseAuth.signOut();
        }
    }

    public void getUserOnline(final List<FriendChat> friendChats){
        final  List<FriendOnline> friendOnlines = new ArrayList<>();
        mDatabaseReference.child(STATUS).
                addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d("sadasdasd",snapshot.getKey());
                        for (int i  = 0 ; i < friendChats.size(); i ++){
                            Log.d("sadasdasd",friendChats.get(i).getId());
                            if (snapshot.getKey().equals(friendChats.get(i).getId())){
                                Log.d("sadasdasd", "vao if ");
                                friendOnlines.add(new FriendOnline(friendChats.get(i).getFullName(),
                                        snapshot.getValue(Integer.class),friendChats.get(i).getRoomId(),
                                        friendChats.get(i).getId()));
                                friendChats.remove(i);
                                break;
                            }
                        }
                }
                Log.d("sadasdasd",friendOnlines.size() + " ");
                mDatabaseReference.child(STATUS).removeEventListener(this);
                iFriendOnline.friendOnline(friendOnlines);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

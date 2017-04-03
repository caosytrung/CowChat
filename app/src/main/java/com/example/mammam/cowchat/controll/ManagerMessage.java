package com.example.mammam.cowchat.controll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.MyMessage;
import com.example.mammam.cowchat.ui.interf.IListSMs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mam  Mam on 12/26/2016.
 */

public class   ManagerMessage implements IConstand{
    private DatabaseReference mDatabaseReference;
    public static ManagerMessage INSTANCE;
    private IListSMs iListSMs;
    private IAddSms iAddSms;

    public void setiAddSms(IAddSms iAddSms) {
        this.iAddSms = iAddSms;
    }

    public void setiListSMs(IListSMs iListSMs) {
        this.iListSMs = iListSMs;
    }

    public static ManagerMessage getManagerMessage(){
        if (null == INSTANCE){
            INSTANCE = new ManagerMessage();
        }
        return INSTANCE;
    }

    private String getCurrentId(){
        if (null == FirebaseAuth.getInstance().getCurrentUser()){
            return null;
        }
         return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    private ManagerMessage() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void getListSms(final String roomId){
        final List<MyMessage> myMessages = new ArrayList<>();

        final DatabaseReference child = mDatabaseReference.
                child(LIST_ROOM).child(roomId).child(CONVERSATION);
        child.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MyMessage myMessage = snapshot.getValue(MyMessage.class);

                    if (1 == myMessage.getState()){
                        MyMessage tmp = new MyMessage(myMessage.getType(),myMessage.getBody(),
                                myMessage.getDesCription(),myMessage.getIdSent(),1);
                        tmp.setState(1);
                        updateSms(tmp,roomId);

                    }


                    Log.d("gsdfsdf",getCurrentId() + ", " + myMessage.getIdSent());
                    if (myMessage.getIdSent().equals(getCurrentId())){
                        int type = myMessage.getType();
                        int newType = (type * 2) - 1;
                        myMessage.setType(newType);
                    }
                    else {
                        int type = myMessage.getType();
                        int newType = type * 2;
                        myMessage.setType(newType);
                    }
                    Log.d("Tye",myMessage.getType() + " sadsada");
                    myMessages.add(myMessage);
                }
                child.removeEventListener(this);
                iListSMs.listSms(myMessages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String time;

    public void getTime(final MyMessage myMessage, final String roomId){
      //  final String time = {""};
        mDatabaseReference.child("TIME").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              //  mDatabaseReference.child("TIME").removeEventListener(this);
                mDatabaseReference.child("TIME").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDatabaseReference.child("TIME").removeEventListener(this);
                        Long  val = dataSnapshot.getValue(Long.class);

                        Date date=new Date(val);
                        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
                        Log.d("Succi","Da vao");
                        Log.d("asdasdasdasdasdas  ",df2.format(new Date(val)) );
                        addSms(df2.format(date),myMessage,roomId);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    public void addSms(String id,MyMessage myMessage,String roomId){
         mDatabaseReference.
                child(LIST_ROOM).child(roomId).child(CONVERSATION).
        child(id).setValue(myMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iAddSms.iAddSms(true);
                }
            }
        });
    }

    public void updateSms(MyMessage myMessage,String roomID){
        DatabaseReference child = mDatabaseReference.child(LIST_ROOM).child(roomID).child(CONVERSATION);
        myMessage.setState(1);
        child.setValue(myMessage);

    }


    public interface IAddSms{
        public void iAddSms(boolean result);
    }


}

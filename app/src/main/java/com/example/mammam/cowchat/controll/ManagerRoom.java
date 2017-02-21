package com.example.mammam.cowchat.controll;

import android.support.annotation.NonNull;

import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.Room;
import com.example.mammam.cowchat.ui.interf.ICreateRoom;
import com.example.mammam.cowchat.ui.interf.IListRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mam  Mam on 12/29/2016.
 */

public class ManagerRoom implements IConstand{
    private DatabaseReference mDatabaseReference;
    private static ManagerRoom INSTANCE ;
    private ICreateRoom iCreateRoom;
    private IListRoom iListRoom;

    public void setiListRoom(IListRoom iListRoom) {
        this.iListRoom = iListRoom;
    }

    public void setiCreateRoom(ICreateRoom iCreateRoom) {
        this.iCreateRoom = iCreateRoom;
    }

    private ManagerRoom(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public String getCurrentid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static ManagerRoom getINSTANCE(){
        if (null == INSTANCE){
            INSTANCE = new ManagerRoom();
            return INSTANCE;
        }
        return INSTANCE;
    };

    public void createGroupChat(List<FriendChat> friendChats,
                                String currentName,String linkAvatar,String roomName,int type){
        final String roomId = getNameRoom(friendChats);

        HashMap<String,String> map = getId(friendChats,currentName);
        Room room =
                new Room(roomName,friendChats.size() + 1,
                        getNameRoom(friendChats),map,linkAvatar,type);
        for (int i = 0 ; i < friendChats.size() ; i ++){
            mDatabaseReference.child(USER).child(friendChats.get(i).
                    getId()).child(LIST_ROOM).child(roomId).setValue(room);
        }
        mDatabaseReference.child(USER).child(getCurrentid()).child(LIST_ROOM).child(roomId).setValue(room);
        DatabaseReference childCreate = mDatabaseReference.child(LIST_ROOM).child(roomId).child(NUMBER_MEMBER);

        childCreate.setValue(room).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    iCreateRoom.createRoom(true,roomId);
                }
            }
        });

    }





    private HashMap<String,String> getId(List<FriendChat> friendChats,String cuurentName){
        HashMap<String,String> listId = new HashMap<>();
        for (int  i = 0 ; i < friendChats.size(); i ++){
            listId.put(friendChats.get(i).getId(),friendChats.get(i).getFullName());
        }
        listId.put(getCurrentid(),cuurentName);
        return listId;
    }

    private String getNameRoom(List<FriendChat> friendChats){
        String rommName = "";
        for (int i = 0 ; i < friendChats.size() ; i ++){
            rommName += friendChats.get(i).getId();
        }
        return rommName + getCurrentid();
    }

    public void getListGroupChat(){
        final List<Room> list = new ArrayList<>();
        final DatabaseReference child = mDatabaseReference.child(USER).child(getCurrentid()).child(LIST_ROOM);
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Room room = snapshot.getValue(Room.class);
                    if (room.getType() == 2){
                        list.add(room);

                    }

                }
                child.removeEventListener(this);
                iListRoom.listRoom(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

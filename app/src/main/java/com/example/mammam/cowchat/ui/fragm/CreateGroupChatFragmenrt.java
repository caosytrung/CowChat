package com.example.mammam.cowchat.ui.fragm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerRoom;
import com.example.mammam.cowchat.controll.ManagerStorage;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.FriendChat;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.activity.MainActivity;
import com.example.mammam.cowchat.ui.adapter.RVCGroupAdapter;
import com.example.mammam.cowchat.ui.adapter.RecyclerViewSearchAdapter;
import com.example.mammam.cowchat.ui.interf.ICreateRoom;
import com.example.mammam.cowchat.ui.interf.IListFriend;
import com.example.mammam.cowchat.ui.interf.ISavePhoto;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Mam  Mam on 12/29/2016.
 */

public class CreateGroupChatFragmenrt extends BaseFragment
        implements View.OnClickListener,IConstand {
    private RVCGroupAdapter adapter;
    private RecyclerView rv;
    private ManagerRoom managerRoom;
    private EditText edtNameRoom;
    private TextView tvCreateRoom;
    private ManagerUser managerUser;
    private TextView tvSelectAvatar;
    private ImageView ivAvatar;
    private ManagerStorage managerStorage;
    private Uri uriImage;




    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_create_group,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rvCreateGC);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        edtNameRoom = (EditText) view.findViewById(R.id.edtCGC);
        tvCreateRoom = (TextView) view.findViewById(R.id.tvCGD);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatarRoom);
        tvSelectAvatar = (TextView) view.findViewById(R.id.tvSelectRoomA);

    }

    @Override
    public void initComponents() {
        adapter = new RVCGroupAdapter(getContext());
        managerUser = new ManagerUser(getContext());
        managerUser.getListFiend();
        managerUser.setiListFriend(new IListFriend() {
            @Override
            public void listFriend(List<FriendChat> friendChats,boolean result) {
                adapter.setFriendChatList(friendChats);
                adapter.notifyDataSetChanged();
            }
        });

        managerStorage = ManagerStorage.getInstance();
        managerStorage.setiSavePhoto(new ISavePhoto() {
            @Override
            public void savePhoto(Uri uri) {
                List<FriendChat> list = adapter.
                        getFriendChatsCreate();

                ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
                managerDbLocal.setContext(getContext());
                managerDbLocal.openDatatbase();
                UserLocal local = managerDbLocal.getUser();
                managerDbLocal.closeDatabase();

                managerRoom.createGroupChat(list,
                        local.getFirstName() + "  " +  local.getLastName(),
                        String.valueOf(uri),edtNameRoom.getText().toString(),2);
            }
        });
        managerRoom = ManagerRoom.getINSTANCE();
        managerRoom.setiCreateRoom(new ICreateRoom() {
            @Override
            public void createRoom(boolean result,String roomId) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.nextConvesation(roomId,String.valueOf(uriImage),edtNameRoom.getText().toString());
            }
        });

    }

    @Override
    public void setEventViews() {
        tvCreateRoom.setOnClickListener(this);
        tvSelectAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvCGD:
              //  ManagerStorage managerStorage = ManagerStorage.getInstance();
                managerStorage.saveImageRoom(getBitmap(),uriImage);

//                List<FriendChat> friendChats = adapter.getFriendChatsCreate();
//                if (friendChats.size() < 2){
//                    return;
//                }
//                else {
//
//                }

                break;
            case R.id.tvSelectRoomA:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RQ_GALL);
                break;
            default:
                break;
        }
    }
    private Bitmap getBitmap(){
        BitmapDrawable drawable = (BitmapDrawable) ivAvatar.getDrawable();
        return  drawable.getBitmap();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RQ_GALL == requestCode && resultCode == getActivity().RESULT_OK){
            uriImage = data.getData();
            Picasso.with(getContext()).load(uriImage).into(ivAvatar);
        }
    }
}

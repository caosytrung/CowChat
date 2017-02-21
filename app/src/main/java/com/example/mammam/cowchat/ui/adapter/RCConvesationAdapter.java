package com.example.mammam.cowchat.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.MyMessage;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/26/2016.
 */

public class RCConvesationAdapter extends
        RecyclerView.Adapter<RCConvesationAdapter.CustomViewHolder>
        implements IConstand{
    private static final String TAG = "RCConvesationAdapter";
    private List<MyMessage> myMessages;
    private Context mContext;
    private IClickItemRycyclerView iClickItemRycyclerView;
    private UserLocal userLocal;



    public RCConvesationAdapter(Context context,
                                IClickItemRycyclerView iClickItemRycyclerView){
        mContext = context;
        this.iClickItemRycyclerView = iClickItemRycyclerView;
        myMessages = new ArrayList<>();
        ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(mContext);
        managerDbLocal.openDatatbase();
        userLocal = managerDbLocal.getUser();
        managerDbLocal.closeDatabase();
    }

    public void setMyMessages(List<MyMessage> myMessages) {
        this.myMessages = myMessages;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_1:
                LayoutInflater inflater1 = LayoutInflater.from(mContext);
                View view1 = inflater1.inflate(R.layout.item_chat_type_1,parent,false);
                return new ViewHolderType1(view1);
            case TYPE_2:
                LayoutInflater inflater2 = LayoutInflater.from(mContext);
                View view2 = inflater2.inflate(R.layout.item_chat_type_2,parent,false);
                return new ViewHolderType2(view2);
            case TYPE_3:
                LayoutInflater inflater3 = LayoutInflater.from(mContext);
                View view3 = inflater3.inflate(R.layout.item_chat_type_3,parent,false);
                return new ViewHolderType3(view3);
            case TYPE_4:
                LayoutInflater inflater4 = LayoutInflater.from(mContext);
                View view4 = inflater4.inflate(R.layout.item_chat_type_4,parent,false);
                return new ViewHolderType4(view4);
            case TYPE_5:
                LayoutInflater inflater5 = LayoutInflater.from(mContext);
                View view5 = inflater5.inflate(R.layout.item_chat_type_5,parent,false);
                return new ViewHolderType5(view5);
            case TYPE_6:
                LayoutInflater inflater6 = LayoutInflater.from(mContext);
                View view6 = inflater6.inflate(R.layout.item_chat_type_6,parent,false);
                return new ViewHolderType6(view6);
            case TYPE_7:
                LayoutInflater  inflater7 = LayoutInflater.from(mContext);
                View view7 = inflater7.inflate(R.layout.item_chat_type_7,parent,false);
                return new ViewHolderType7(view7);
            case TYPE_8:
                LayoutInflater inflater8 = LayoutInflater.from(mContext);
                View view8 = inflater8.inflate(R.layout.item_chat_type_8,parent,false);
                return new ViewHolderType8(view8);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        MyMessage myMessage = myMessages.get(position);
        SpannableString content = new SpannableString(myMessage.getDesCription());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        switch (holder.getItemViewType()){
            case TYPE_1:
                ViewHolderType1 viewHolderType1 = (ViewHolderType1)holder;
                viewHolderType1.ivAvatar.setImageBitmap(userLocal.getBitMapAvatar());
                viewHolderType1.tvSms.setText(myMessage.getBody());
                viewHolderType1.vContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iClickItemRycyclerView.onItemClick(position,view);
                    }
                });
                break;
            case  TYPE_2:
                ViewHolderType2 viewHolderType2 = (ViewHolderType2)holder;
                viewHolderType2.tvSms.setText(myMessage.getBody());
                viewHolderType2.vContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iClickItemRycyclerView.onItemClick(position,view);
                    }
                });
                break;

            case TYPE_3:
                final ViewHolderType3 viewHolderType3 = (ViewHolderType3)holder;

                Picasso.with(mContext).
                        load(myMessage.getBody()).into(viewHolderType3.ivSms, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolderType3.prgLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
                viewHolderType3.vContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iClickItemRycyclerView.onItemClick(position,view);
                    }
                });
                break;
            case TYPE_4:
                final ViewHolderType4 viewHolderType4 = (ViewHolderType4)holder;

                Picasso.with(mContext).
                        load(myMessage.getBody()).into(viewHolderType4.ivSms, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolderType4.prgLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
                viewHolderType4.vContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iClickItemRycyclerView.onItemClick(position,view);
                    }
                });
                break;
            case TYPE_5:

                ViewHolderType5 viewHolderType5 = (ViewHolderType5)holder;
                viewHolderType5.tvSms.setText(content );
                viewHolderType5.vContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iClickItemRycyclerView.onItemClick(position,view);
                    }
                });
                break;
            case TYPE_6:
                ViewHolderType6 viewHolderType6 = (ViewHolderType6)holder;
                viewHolderType6.tvSms.setText(content);
                viewHolderType6.vContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iClickItemRycyclerView.onItemClick(position,view);
                    }
                });
                break;
            case TYPE_7:
                ViewHolderType7 viewHolderType7 = (ViewHolderType7)holder;
                try {
                    Log.d(TAG,"VaoTYpe7");
                    InputStream is = mContext.getAssets().open(myMessage.getBody());
                    Drawable d = Drawable.createFromStream(is,null);
                    viewHolderType7.ivSticker.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewHolderType7.ivAvatar.setImageBitmap(userLocal.getBitMapAvatar());

                break;
            case TYPE_8:
                ViewHolderType8 viewHolderType8 = (ViewHolderType8)holder;
                try {
                    Log.d(TAG,"VaoTYpe8");
                    InputStream is = mContext.getAssets().open(myMessage.getBody());
                    Drawable d = Drawable.createFromStream(is,null);
                    viewHolderType8.ivSticker.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
              //  viewHolderType8.ivAvatar.setImageBitmap(userLocal.getBitMapAvatar());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return myMessages.size();
    }

    public class CustomViewHolder  extends  RecyclerView.ViewHolder{
        public View vContainer;
        public CustomViewHolder(View itemView) {
            super(itemView);
            vContainer = itemView;
        }
    }

    public class ViewHolderType1 extends CustomViewHolder{
        public ImageView ivAvatar;
        public TextView tvSms;


        public ViewHolderType1(View itemView) {
            super(itemView);

            tvSms = (TextView) itemView.findViewById(R.id.tvSmsType1);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarType1);
        }
    }
    public class ViewHolderType2 extends CustomViewHolder{
        public ImageView ivAvatar;
        public TextView tvSms;

        public ViewHolderType2(View itemView) {
            super(itemView);

            tvSms = (TextView) itemView.findViewById(R.id.tvSmsType2);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarType2);
        }
    }
    public class ViewHolderType3 extends CustomViewHolder{
        private ImageView ivSms;
        private ProgressBar prgLoad;
        public ViewHolderType3(View itemView) {
            super(itemView);
            ivSms = (ImageView) itemView.findViewById(R.id.ivSmsType3);
            prgLoad = (ProgressBar) itemView.findViewById(R.id.prgSmsType3);
        }
    }

    public class ViewHolderType4 extends CustomViewHolder{
        private ImageView ivSms;
        private ProgressBar prgLoad;
        public ViewHolderType4(View itemView) {
            super(itemView);
            ivSms = (ImageView) itemView.findViewById(R.id.ivSmsType4);
            prgLoad = (ProgressBar) itemView.findViewById(R.id.prgSmsType4);
        }
    }
    public class ViewHolderType5 extends CustomViewHolder{
        public ImageView ivAvatar;
        public TextView tvSms;
        public ViewHolderType5(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarType5);
            tvSms = (TextView) itemView.findViewById(R.id.tvSmsType5);
        }
    }
    public class ViewHolderType6 extends CustomViewHolder{
        public ImageView ivAvatar;
        public TextView tvSms;
        public ViewHolderType6(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarType6);
            tvSms = (TextView) itemView.findViewById(R.id.tvSmsType6);
        }
    }
    public class ViewHolderType7 extends CustomViewHolder{
        public ImageView ivAvatar;
        public ImageView ivSticker;
        public ViewHolderType7(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarType7);
            ivSticker = (ImageView) itemView.findViewById(R.id.ivItemChatTy7);
        }
    }

    public class ViewHolderType8 extends CustomViewHolder{
        public ImageView ivAvatar;
        public ImageView ivSticker;
        public ViewHolderType8(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatarType8);
            ivSticker = (ImageView) itemView.findViewById(R.id.ivItemChatTy8);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (myMessages.get(position).getType()){
            case 1:
                return TYPE_1;
            case 2:
                return TYPE_2;
            case 3:
                return TYPE_3;
            case 4:
                return TYPE_4;
            case 5:
                return TYPE_5;
            case 6:
                return TYPE_6;
            case 7:
                return TYPE_7;
            case 8:
                return TYPE_8;
            default:
                return TYPE_1;
        }
    }
}

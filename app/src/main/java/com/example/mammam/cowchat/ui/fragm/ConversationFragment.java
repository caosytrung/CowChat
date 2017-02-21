package com.example.mammam.cowchat.ui.fragm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerMessage;
import com.example.mammam.cowchat.controll.ManagerStorage;
import com.example.mammam.cowchat.models.EventSticker;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.MyMessage;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.service.DownloadService;
import com.example.mammam.cowchat.ui.activity.ConsersationActivity;
import com.example.mammam.cowchat.ui.adapter.RCConvesationAdapter;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.custom.SnowFlakesLayout;
import com.example.mammam.cowchat.ui.custom.StickerDialog;
import com.example.mammam.cowchat.ui.dialog.StickerFragmentDialog;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListSMs;
import com.example.mammam.cowchat.ui.interf.ISavePhoto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mam  Mam on 12/27/2016.
 */

public class ConversationFragment extends BaseFragment implements IConstand,
        IClickItemRycyclerView, ChildEventListener, View.OnClickListener {

    public static final String TAG = "ConversationFragment";
    private TextView tvCall;
    private TextView tvFullName;
    private TextView tvMenu;
    private TextView tvSent;
    private TextView tvCamera;
    private TextView tvGallery;
    private TextView tvNhanDan;
    private TextView tvFile;
    private TextView tvPaint;
    private TextView tvInputText;
    private EventBus mEventBus;
    private ManagerMessage managerMessage;
    private UserLocal userLocal;
    private RecyclerView rvChat;
    private RCConvesationAdapter rcConvesationAdapter;
    private EditText edtSent;
    public String roomId;
    public String idFriend;
    private ManagerStorage storage;
    private ProgressDialog mProgressDialog;
    private DownloadTask downloadTask;
    private List<MyMessage> mMessages;
    private StickerDialog mStickerDialog;
    private FragmentManager mFragmentManage;

    public ConversationFragment(){

    }


    public ConversationFragment(FragmentManager fragmentManager){
        mFragmentManage = fragmentManager;
    }

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.
                fragment_conversation,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        SnowFlakesLayout snowFlakesLayout;
        snowFlakesLayout = (SnowFlakesLayout) view.findViewById(R.id.snowflakelayout);
        snowFlakesLayout.init();
        snowFlakesLayout.startSnowing();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Downloading ...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMax(100);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
              //  downloadTask.cancel(true);
            }
        });

        rvChat = (RecyclerView) view.findViewById(R.id.rvConv) ;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChat.setLayoutManager(manager);
        rvChat.setAdapter(rcConvesationAdapter);
        edtSent = (EditText) view.findViewById(R.id.edtConv);
        tvCall = (TextView) view.findViewById(R.id.tvConvCall);
        tvFullName = (TextView) view.findViewById(R.id.tvFullNameConv);
        tvMenu = (TextView) view.findViewById(R.id.tvConvMenu);
        tvSent = (TextView) view.findViewById(R.id.tvSentConv);
        tvPaint = (TextView) view.findViewById(R.id.tvDrawConv);
        tvCamera = (TextView) view.findViewById(R.id.tvCameraConv);
        tvGallery = (TextView) view.findViewById(R.id.tvGalleryConv);
        tvFile = (TextView) view.findViewById(R.id.tvFileConv);
        tvNhanDan = (TextView) view.findViewById(R.id.tvNhanDanConv);
        tvInputText = (TextView) view.findViewById(R.id.tvInputConv);

        MrgTypeFace.setFontAnswesSome(tvPaint,getContext());
        MrgTypeFace.setFontAnswesSome(tvFile,getContext());
        MrgTypeFace.setFontAnswesSome(tvGallery,getContext());
        MrgTypeFace.setFontAnswesSome(tvNhanDan,getContext());
        MrgTypeFace.setFontAnswesSome(tvCamera,getContext());
        MrgTypeFace.setFontAnswesSome(tvInputText,getContext());

        MrgTypeFace.setFontAnswesSome(tvCall,getContext());
        MrgTypeFace.setFontAnswesSome(tvMenu,getContext());
        MrgTypeFace.setFontAnswesSome(tvSent,getContext());
        tvSent.setText("\uf1d8");
        tvCall.setText("\uF095");
        tvMenu.setText("\uf142 ");
        tvCamera.setText("\uf030");
        tvNhanDan.setText("\uf118");
        tvGallery.setText("\uf1c5");
        tvFile.setText("\uf15b");
        tvPaint.setText("\uf044");
        tvInputText.setText("\uf20d");
        managerMessage.setiListSMs(new IListSMs() {
            @Override
            public void listSms(List<MyMessage> myMessages) {
                mMessages = myMessages;
                rvChat.scrollToPosition(myMessages.size() - 1);
                rcConvesationAdapter.setMyMessages(myMessages);
                rcConvesationAdapter.notifyDataSetChanged();
            }
        });
        managerMessage.setiAddSms(new ManagerMessage.IAddSms() {
            @Override
            public void iAddSms(boolean result) {
                managerMessage.getListSms(roomId);
            }
        });
    }

    @Override
    public void initComponents() {
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        mStickerDialog = new StickerDialog(getContext(),getChildFragmentManager());
        Window window = mStickerDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        idFriend = getArguments().getString(FRIEND_ID);
        roomId = getArguments().getString(ROOM_ID);

        rcConvesationAdapter = new RCConvesationAdapter(getContext(),this);
        managerMessage = ManagerMessage.getManagerMessage();
        ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(getContext());
        managerDbLocal.openDatatbase();
        userLocal = managerDbLocal.getUser();
        managerDbLocal.closeDatabase();

        storage = ManagerStorage.getInstance();
        storage.setiSavePhoto(new ISavePhoto() {
            @Override
            public void savePhoto(Uri uri) {
                MyMessage myMessage =
                        new MyMessage(2,String.valueOf(uri),
                                "Nothing",userLocal.getId());
                managerMessage.getTime(myMessage,roomId);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        managerMessage.getListSms(roomId);
        FirebaseDatabase.getInstance().getReference().child(LIST_ROOM).
                child(roomId).child(CONVERSATION).addChildEventListener(this);
    }

    @Override
    public void onStop() {
        mEventBus.unregister(this);
        super.onStop();
    }

    @Override
    public void setEventViews() {
        tvGallery.setOnClickListener(this);
        tvSent.setOnClickListener(this);
        tvCamera.setOnClickListener(this);
        tvFile.setOnClickListener(this);
        tvInputText.setOnClickListener(this);
        tvNhanDan.setOnClickListener(this);
    }
    public void downloadFile(int pos){
        mProgressDialog.show();
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra(FILE_NAME,mMessages.get(pos).getDesCription());
        intent.putExtra(URL_DOWNLOAD,mMessages.get(pos).getBody());
        intent.putExtra(RECEIVER,new DownloadRecerver(new Handler()));
        getContext().startService(intent);
    }

    public void showImage(String url){
        Log.d(TAG,"da vao");
        ConsersationActivity consersationActivity = (ConsersationActivity)getActivity();
        consersationActivity.openFragmentImage(url);
    }

    @Override
    public void onItemClick(int pos, View v) {
        Log.d("sdsad","da vao");
        switch (mMessages.get(pos).getType()){
            case 1:
                break;
            case 2:

                break;
            case 3 :
                Log.d(TAG,"da vao");
                showImage(mMessages.get(pos).getBody());
                break;
            case 4:
                Log.d(TAG,"da vao");
                showImage(mMessages.get(pos).getBody());
                break;
            case 5:
                downloadFile(pos);
                break;
            case 6:
                downloadFile(pos);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongCLick(int pos, View v) {

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        managerMessage.getListSms(roomId);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSentConv:
                String body = edtSent.getText().toString();
                if(body.length() == 0 ){
                    return;
                }
                MyMessage myMessage = new MyMessage(1,body,"Nothing",userLocal.getId());
                managerMessage.getTime(myMessage,roomId);
                edtSent.setText("");
                break;
            case R.id.tvGalleryConv:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RQ_GALL);
                break;
            case R.id.tvCameraConv:
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera,RQ_CAMERA);
                break;
            case R.id.tvFileConv:
                File file = new File(Environment.getExternalStorageDirectory(),
                        "myFolder");

                Intent intentFile = new Intent(Intent.ACTION_GET_CONTENT);
                intentFile.setDataAndType(Uri.fromFile(file), "*/*");
                startActivityForResult(intentFile, RQ_FILE);
                break;
            case R.id.tvInputConv:
                Log.d("hj","bjbj");
                edtSent.requestFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtSent, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.tvNhanDanConv:
               // (new StickerDialog(getContext(),mFragmentManage)).show();
                new StickerFragmentDialog().show(mFragmentManage,"");
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            Uri uriImage  = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.
                        getBitmap(getContext().getContentResolver(), uriImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (requestCode){
                case RQ_GALL:

                    storage = ManagerStorage.getInstance();
                    storage.saveImageSms(bitmap,uriImage);
                    break;
                case RQ_CAMERA:

                    storage = ManagerStorage.getInstance();
                    storage.saveImageSms(bitmap,uriImage);
                    break;
                case RQ_FILE:
                    Uri uri = data.getData();
//            File file = new File(uri.getPath());
//            String url = String.valueOf(data.getData());
//            String cc = data.getData().getLastPathSegment();
                    final String fileName = getFileName(uri);


                    StorageReference child = FirebaseStorage.
                            getInstance().getReference().child("FILE").child(uri.getLastPathSegment());
                    child.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri uriDown = taskSnapshot.getDownloadUrl();

                            MyMessage myMessage =
                                    new MyMessage(3,String.valueOf(uriDown)
                                            ,fileName,userLocal.getId());
                            managerMessage.getTime(myMessage,roomId);

                        }
                    });
                    break;
                default:
                    break;
            }
        }

    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {

                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                File file = new File(ROOT_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                output = new FileOutputStream(ROOT_PATH + "/" + sUrl[1]);


                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {

                    // allow canceling with back button
                    if (isCancelled()) {
                     //   Log.d("davaocan", "cancle" + downloadTask.getStatus().name());
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....


                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                    if (total >= fileLength) {
                        return null;
                    }
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                    getClass().getName());
//            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //downloadTask.cancel(true);
           // Log.d("dda", downloadTask.getStatus().name() + " ");
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadRecerver extends ResultReceiver{

        public DownloadRecerver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == UPDATE_PROGRESS){
                int progress = resultData.getInt(PROGRESS);
                mProgressDialog.setProgress(progress);
                Log.d(TAG,progress + "");
                if (progress == 100){

                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(),"Download Succesfully!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEvent(EventSticker eventSticker){
        MyMessage myMessage = new MyMessage(4,eventSticker.getContent(),"",userLocal.getId());
        managerMessage.getTime(myMessage,roomId);

    }
}

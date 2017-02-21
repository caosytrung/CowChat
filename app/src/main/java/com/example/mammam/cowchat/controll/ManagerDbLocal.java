package com.example.mammam.cowchat.controll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;

import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.models.UserLocal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Mam  Mam on 12/17/2016.
 */

public class ManagerDbLocal implements IConstand {
    private static final String TAG = "ManagerDbLocal" ;
    private static ManagerDbLocal INSTANCE ;
    private SQLiteDatabase mDatabase;
    private DateFormat mFormat = new SimpleDateFormat(FORMAT_DATE);
    private Context mContext;

    private ManagerDbLocal(){

    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static ManagerDbLocal getINSTANCE(){
        if (null == INSTANCE){
            INSTANCE = new ManagerDbLocal();
        }
        return INSTANCE;
    }

    public void openDatatbase(){
        mDatabase = mContext.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
    }

    public void closeDatabase(){
        mDatabase.close();
    }

    public void copyDatabaseToSystem(){
        File dbFile = mContext.getDatabasePath(DB_NAME);
        if (!dbFile.exists()){
            InputStream in = null;
            OutputStream out = null;

            try {
                Log.d("asdasd","da vao");
                in = mContext.getAssets().open(DB_NAME);
                String outFile = getPathSystem();
                File file =     new File(mContext.getApplicationInfo().
                dataDir + DB_PATH_SUFF);
                if(!file.exists()){
                    file.mkdir();
                }

                out = new FileOutputStream(outFile);
                byte[] byteArray = new byte[1024];
                int length;
                while ((length = in.read(byteArray)) > 0){
                    out.write(byteArray,0,length);
                }
                out.close();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateUser(UserChat userChat,Bitmap bitmap){
        byte[] avatar = ManagerStorage.convertToBytes(bitmap);
        ContentValues cv = new ContentValues();
        cv.put(DB_FIRST_NAME,userChat.getFirstName()); //These Fields should be your String values of actual column names
        cv.put(DB_LAST_NAME,userChat.getLastName());
        cv.put(DB_DES,userChat.getGender());
        cv.put(DB_PASSWORD,userChat.getPassword());
        cv.put(DB_EMAIL,userChat.getEmail());
        cv.put(DB_AVATAR,avatar);
        cv.put(DB_GENDER,userChat.getGender());
        cv.put(DB_ID,userChat.getId());
        mDatabase.update("User",cv,"",null);
    }

    public void deleteAllUser(){

        mDatabase.delete("User","",null);
        Log.d(TAG,"Da delete all user");
    }

    public void saveUser(UserChat userChat, Bitmap bitmap){
        String strQuery = "SELECT * FROM User";
        Cursor cursor = mDatabase.rawQuery(strQuery,null);

        if (cursor.getCount() > 0){
            updateUser(userChat,bitmap);
            return;
        }

        String firstName = userChat.getFirstName();
        String lastName  = userChat.getLastName();
        String email = userChat.getEmail();
        String password = userChat.getPassword();
        int gender = userChat.getGender();
        String description = userChat.getDescription();
        String id = userChat.getId();
        byte[] avatar = ManagerStorage.convertToBytes(bitmap);

        ContentValues values = new ContentValues();

        values.put(DB_AVATAR,avatar);
        values.put(DB_DES,description);
        values.put(DB_ID,id);
        values.put(DB_EMAIL,email);
        values.put(DB_GENDER,gender);
        values.put(DB_PASSWORD,password);
        values.put(DB_FIRST_NAME,firstName);
        values.put(DB_LAST_NAME,lastName);
        mDatabase.insert("User",null,values);
        Log.d(TAG,"DA insert");
    }

    public UserLocal getUser(){
        String strQuery = "SELECT * FROM User";
        Cursor cursor = mDatabase.rawQuery(strQuery,null);
        Log.d(TAG,cursor.getCount()  +  " ");

        int indexDescription = cursor.getColumnIndex(DB_DES);
        int indexId = cursor.getColumnIndex(DB_ID);
        int indexFirstName = cursor.getColumnIndex(DB_FIRST_NAME);
        int indexLastname = cursor.getColumnIndex(DB_LAST_NAME);
        int indexEmail = cursor.getColumnIndex(DB_EMAIL);
        int indexGender = cursor.getColumnIndex(DB_GENDER);
        int indexAvatar = cursor.getColumnIndex(DB_AVATAR);
        int indexPassword = cursor.getColumnIndex(DB_PASSWORD);

        Log.d(TAG,cursor.getCount() + "");
        cursor.moveToFirst();
        UserLocal userLocal = null;

        String id = cursor.getString(indexId);
        String email  =  cursor.getString(indexEmail);
        String password = cursor.getString(indexPassword);
        String firstName = cursor.getString(indexFirstName);
        String lastName = cursor.getString(indexLastname);
        int gender = cursor.getInt(indexGender);
        String description = cursor.getString(indexDescription);
        byte[] byteAvatar  =cursor.getBlob(indexAvatar);
        Bitmap bitmap = getBitmapFromByteArray(byteAvatar);
            userLocal = new UserLocal(id,email,password,firstName, lastName,gender,description,bitmap);


        return userLocal;

    }
    private Bitmap getBitmapFromByteArray(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    private byte[] convertImageToByteArray(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }


    private String getPathSystem(){
        return mContext.getApplicationInfo().dataDir  +
                DB_PATH_SUFF + DB_NAME;
    }

}

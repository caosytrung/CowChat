package com.example.mammam.cowchat.controll;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.MyMessage;
import com.example.mammam.cowchat.ui.interf.ISavePhoto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Mam  Mam on 12/17/2016.
 */

public class ManagerStorage  implements IConstand{
    private StorageReference mStorageReference;
    private ISavePhoto iSavePhoto;

    public void setiSavePhoto(ISavePhoto iSavePhoto) {
        this.iSavePhoto = iSavePhoto;
    }

    private static  ManagerStorage INSTANCE ;
    private ManagerStorage(){
        mStorageReference = FirebaseStorage.getInstance().getReference();

    }
    public static ManagerStorage getInstance(){
        if (null == INSTANCE){
            INSTANCE = new ManagerStorage();
        }
        return INSTANCE;
    }
    public static byte[] convertToBytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
    public void saveAvater(final Bitmap bitmap,Uri uri){
        StorageReference child = mStorageReference.child(AVATAR).child(uri.getLastPathSegment());
        Bitmap newBitmap = scaleDown(bitmap,(128 *4),true);
        byte[] bytes = convertToBytes(newBitmap);
        child.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uriLink = taskSnapshot.getDownloadUrl();
                iSavePhoto.savePhoto(uriLink);
            }
        });

    }

    public void saveImageSms(Bitmap bitmap,Uri uri){
        StorageReference child = mStorageReference.child(IMAGE_SMS).child(uri.getLastPathSegment());
        Bitmap newBitmap = scaleDown(bitmap,(128  * 4),true);
        byte[] bytes = convertToBytes(newBitmap);
        child.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uriLink = taskSnapshot.getDownloadUrl();
                iSavePhoto.savePhoto(uriLink);
            }
        });
    }

    public void saveImageRoom(Bitmap bitmap,Uri uri){
        StorageReference child = mStorageReference.child(IMAGE_ROOM).child(uri.getLastPathSegment());
        Bitmap newBitmap = scaleDown(bitmap,(128  * 4),true);
        byte[] bytes = convertToBytes(newBitmap);
        child.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uriLink = taskSnapshot.getDownloadUrl();
                iSavePhoto.savePhoto(uriLink);
            }
        });
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }


}

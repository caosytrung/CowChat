package com.example.mammam.cowchat.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.IConstand;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Mam  Mam on 12/27/2016.
 */

public class DownloadService extends IntentService implements IConstand {

    private void createNotification(String nameFile,String url){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_tich);
        builder.setTicker("My ticker");
        builder.setContentTitle("Download complete!");
        builder.setContentText( nameFile);
        Intent intent = openFile(new File(url),
                nameFile.substring(nameFile.length() - 4,nameFile.length()));
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this,2,intent
                        ,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationService  =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notificationService.notify(1,notification);



    }
    private Intent openFile(File file, String exFile) {
        Intent intent = null;
        switch (exFile) {
            case ".txt":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "text/plain");
                //  mContext.startActivity(intent);
                break;
            case ".xml":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "text/xml");
                //  mContext.startActivity(intent);
                break;
            case ".apk":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                //  mContext.startActivity(intent);
                break;
            case ".png":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/png");
                //  mContext.startActivity(intent);
                break;
            case ".jpg":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/jpeg");
                // mContext.startActivity(intent);
                break;
            case ".mp4":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "video/mp4");
                //  mContext.startActivity(intent);
                break;
            case ".rar":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/x-rar-compressed");
                //  mContext.startActivity(intent);
                break;
            case ".zip":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/x-rar-compressed");
                //   mContext.startActivity(intent);
                break;
            case ".mp3":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "audio/mp3");
                //  mContext.startActivity(intent);
                break;
            case ".m4a":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "audio/m4a");
                //  mContext.startActivity(intent);
                break;
            case ".ogg":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "audio/ogg");
                //  mContext.startActivity(intent);
                break;
            case ".doc":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/msword");
                //  mContext.startActivity(intent);
                break;
            case "docx":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/msword");
                //  mContext.startActivity(intent);
                break;
            case ".pdf":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                //    mContext.startActivity(intent);
                break;
            case "pptx":
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
                break;

        }
        return intent;
    }



    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlDownload = intent.getStringExtra(URL_DOWNLOAD);
        String fileName = intent.getStringExtra(FILE_NAME);
        ResultReceiver resultReceiver = (ResultReceiver)
                intent.getParcelableExtra(RECEIVER);
        try {
            URL url = new URL(urlDownload);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(ROOT_PATH + "/" + fileName);

            byte[] bytes = new byte[1024];

            int total = 0;
            int count;
            while ((count = input.read(bytes)) != -1){
                total += count;
                Bundle bundleData = new Bundle();
                bundleData.putInt(PROGRESS,(int) (total * 100 / fileLength));
                resultReceiver.send(UPDATE_PROGRESS,bundleData);
                output.write(bytes,0,count);
            }
            output.flush();
            input.close();
            output.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle finalBu = new Bundle();
        finalBu.putInt(PROGRESS,100);
        resultReceiver.send(UPDATE_PROGRESS,finalBu);
        createNotification(fileName,ROOT_PATH + "/" + fileName);

    }
}
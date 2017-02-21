package com.example.mammam.cowchat.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.service.ServiceCall;
import com.example.mammam.cowchat.ui.activity.StartActivity;
import com.example.mammam.cowchat.ui.interf.ILogin;

/**
 * Created by Mam  Mam on 1/4/2017.
 */

public class MyBroadCast extends BroadcastReceiver implements IConstand {

    private static final String TAG = MyBroadCast.class.getName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        switch (intent.getAction()){
            case "android.net.conn.CONNECTIVITY_CHANGE":
                Log.d(TAG,"da bat dc broadCast");

                if (isNetworkConnected(context)){
                    if (StartActivity.isLogin(context)){
                        UserLocal userLocal = ManagerUser.getUserLocal(context);
                        ManagerUser managerUser = new ManagerUser(context);
                        managerUser.setiLogin(new ILogin() {
                            @Override
                            public void login(boolean isSuc, String notif) {
                                if (isSuc){
                                    Toast.makeText(context,"Login Success",Toast.LENGTH_LONG).show();
                                    //start service
                                    Intent intentService = new Intent(context, ServiceCall.class);
                                    context.startService(intentService);
                                }
                            }
                        });
                        managerUser.login(userLocal.getEmail(),
                                userLocal.getPassword(),false);
                    }
                }
                else {
                    // stop service
                    context.stopService(new Intent(context,ServiceCall.class));
                    Toast.makeText(context,"Thoat",Toast.LENGTH_LONG).show();
                }

                break;
            default:
                break;

        }
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}

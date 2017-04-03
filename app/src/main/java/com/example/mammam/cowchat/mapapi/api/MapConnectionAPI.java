package com.example.mammam.cowchat.mapapi.api;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dee on 01/03/2017.
 */

public class MapConnectionAPI {
    private MapResAPI mMapResAPI;
    private EventBus mEventBus;
    private OkHttpClient mOkHttpClient;
    private static MapConnectionAPI INSTANCE;


    private MapConnectionAPI(){
        mEventBus = new EventBus();
        initConnect();
    }

    public void initConnect(){
        OkHttpClient.Builder builderHttp = new OkHttpClient.Builder();
        builderHttp.connectTimeout(2, TimeUnit.MINUTES);
        builderHttp.readTimeout(2,TimeUnit.MINUTES);
        builderHttp.writeTimeout(2,TimeUnit.MINUTES);
        mOkHttpClient = builderHttp.build();


        Retrofit.Builder builderRes = new Retrofit.Builder();
        builderRes.baseUrl("https://maps.googleapis.com/");
        builderRes.addConverterFactory(GsonConverterFactory.create(new Gson()));
        builderRes.client(mOkHttpClient);

        mMapResAPI = builderRes.build().create(MapResAPI.class);
    }

    public void registerCallback(Object o){
        mEventBus.register(o);
    }

    public void unregisterCallback(Object o){
        mEventBus.unregister(o);
    }



    public synchronized MapConnectionAPI getINSTANCE(){
        if (null == INSTANCE){
            INSTANCE = new MapConnectionAPI();
        }
        return INSTANCE;
    }

}

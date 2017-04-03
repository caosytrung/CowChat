package com.example.mammam.cowchat.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Mam  Mam on 12/26/2016.
 */

public class MyMessage {
    private int type;
    private String body;
    private String desCription;

    HashMap<String, Object> timestampCreated;
    private String idSent;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MyMessage() {
    }

    public MyMessage(int type, String body, String desCription, String idSent,int state) {
        this.type = type;
        this.body = body;
        this.desCription = desCription;

        this.idSent = idSent;
        this.state = state;

        HashMap<String, Object> timestampNow = new HashMap<>();
        timestampNow.put("timestamp", ServerValue.TIMESTAMP);

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDesCription() {
        return desCription;
    }

    public void setDesCription(String desCription) {
        this.desCription = desCription;
    }
    public HashMap<String, Object> getTimestampCreated(){
        return timestampCreated;
    }

    @Exclude
    public long getTimestampCreatedLong(){
        return (long)timestampCreated.get("timestamp");
    }


    public String getIdSent() {
        return idSent;
    }

    public void setIdSent(String idSent) {
        this.idSent = idSent;
    }
}

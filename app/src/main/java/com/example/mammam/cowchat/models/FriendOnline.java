package com.example.mammam.cowchat.models;

/**
 * Created by Mam  Mam on 12/25/2016.
 */

public class FriendOnline {
    private String fullName;
    private int state;
    private String roomId;
    private String id;

    public FriendOnline(String fullName, int state, String roomId, String id) {
        this.fullName = fullName;
        this.state = state;
        this.roomId = roomId;
        this.id = id;
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

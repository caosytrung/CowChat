package com.example.mammam.cowchat.models;

import java.io.Serializable;

/**
 * Created by Mam  Mam on 1/4/2017.
 */

public class EventCall implements Serializable{
    private String name;
    private String id;
    private String linkAvatar;
    private String roomId;

    public EventCall(String name, String id, String linkAvatar,String roomId) {
        this.name = name;
        this.id = id;
        this.linkAvatar = linkAvatar;
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }
}

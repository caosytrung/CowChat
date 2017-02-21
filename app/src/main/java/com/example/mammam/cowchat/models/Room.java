package com.example.mammam.cowchat.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mam  Mam on 12/29/2016.
 */

public class Room {
    private String roomName;
    private int numberOfMenber;
    private String roomID;
    private HashMap<String,String> listId;
    private String linkAvatar;
    private int type;

    public Room(String roomName, int numberOfMenber, String roomID,
                HashMap<String, String> listId, String linkAvatar, int type) {
        this.roomName = roomName;
        this.numberOfMenber = numberOfMenber;
        this.roomID = roomID;
        this.listId = listId;
        this.linkAvatar = linkAvatar;
        this.type = type;
    }

    public Room() {
    }

    public String getRoomName() {
        return roomName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getNumberOfMenber() {
        return numberOfMenber;
    }

    public void setNumberOfMenber(int numberOfMenber) {
        this.numberOfMenber = numberOfMenber;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public HashMap<String,String> getListId() {
        return listId;
    }

    public void setListId(HashMap<String,String> listId) {
        this.listId = listId;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }
}

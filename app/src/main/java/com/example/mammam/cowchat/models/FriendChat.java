package com.example.mammam.cowchat.models;

/**
 * Created by Mam  Mam on 12/23/2016.
 */

public class FriendChat {
    private String linkAvatar;
    private String email;
    private String fullName;
    private String lastDateChat;
    private String lastSms;
    private String roomId;
    private String id;

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

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastDateChat() {
        return lastDateChat;
    }

    public void setLastDateChat(String lastDateChat) {
        this.lastDateChat = lastDateChat;
    }

    public FriendChat() {

    }

    public String getLastSms() {
        return lastSms;
    }

    public void setLastSms(String lastSms) {
        this.lastSms = lastSms;
    }

    public FriendChat(String linkAvatar, String email,
                      String fullName, String lastDateChat
            , String lastSms, String roomId, String id) {

        this.linkAvatar = linkAvatar;
        this.email = email;
        this.fullName = fullName;
        this.lastDateChat = lastDateChat;
        this.lastSms = lastSms;
        this.roomId = roomId;
        this.id = id;
    }
}

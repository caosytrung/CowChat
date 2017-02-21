package com.example.mammam.cowchat.models;

/**
 * Created by Mam  Mam on 1/4/2017.
 */

public class EventCall {
    private String name;
    private String id;
    private String linkAvatar;

    public EventCall(String name, String id, String linkAvatar) {
        this.name = name;
        this.id = id;
        this.linkAvatar = linkAvatar;
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

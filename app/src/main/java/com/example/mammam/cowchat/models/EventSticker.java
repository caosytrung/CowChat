package com.example.mammam.cowchat.models;

/**
 * Created by dee on 18/02/2017.
 */

public class EventSticker {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EventSticker(String content) {

        this.content = content;
    }
}

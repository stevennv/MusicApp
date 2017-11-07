package com.example.admin.myapplication.model;

/**
 * Created by Admin on 11/7/2017.
 */

public class NotiMessage {
    public NotiMessage() {
    }

    private String avatar;
    private String sender;
    private String content;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
}

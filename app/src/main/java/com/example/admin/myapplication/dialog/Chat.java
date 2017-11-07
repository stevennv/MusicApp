package com.example.admin.myapplication.dialog;

/**
 * Created by Admin on 11/7/2017.
 */

public class Chat {

    public Chat(String content, String sender, String key, String avatar) {
        this.content = content;
        this.sender = sender;
        this.key = key;
        this.avatar = avatar;
    }

    public Chat() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String content;
    private String sender;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;
}

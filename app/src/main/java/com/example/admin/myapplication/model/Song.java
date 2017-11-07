package com.example.admin.myapplication.model;

/**
 * Created by Admin on 11/4/2017.
 */

public class Song {
    private String id;

    public Song() {
    }

    public Song(String id, String name, String url, String urlImage, String singer, String length) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.urlImage = urlImage;
        this.singer = singer;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    private String name;
    private String url;
    private String urlImage;
    private String singer;
    private String length;
}

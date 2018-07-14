package com.hassan.a.abubakr.pojecttemplate.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Announcement {
    @SerializedName("created_at")
    String date;
    String title;
    String body;
    String image;

    public Announcement(String data, String title, String body, String image) {
        this.date = data;
        this.title = title;
        this.body = body;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date data) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

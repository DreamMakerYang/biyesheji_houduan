package com.lois.domain.Setting;

import java.io.Serializable;

public class Picture implements Serializable {
    private int id;
    private String date;
    private String imageUrl;

    public Picture() {
    }

    public Picture(String date, String imageUrl) {
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

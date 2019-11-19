package com.lois.domain.Result;

import java.io.Serializable;

public class ResultUser implements Serializable {
    private String token;
    private String name;
    private String image;
    private int state;

    public ResultUser(String token, String name, String image, int state) {
        this.token = token;
        this.name = name;
        this.image = image;
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

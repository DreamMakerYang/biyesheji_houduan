package com.lois.domain.Result;

import java.io.Serializable;

public class ResultUser implements Serializable {
    private String token;
    private String name;
    private String gender;
    private String phone;
    private String pass;
    private String date;
    private String image;
    private int state;

    public ResultUser(String token, String name, String gender, String phone, String pass, String date, String image, int state) {
        this.token = token;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.pass = pass;
        this.date = date;
        this.image = image;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

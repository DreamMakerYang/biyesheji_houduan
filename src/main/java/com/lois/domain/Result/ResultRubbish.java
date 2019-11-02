package com.lois.domain.Result;

import java.io.Serializable;

public class ResultRubbish implements Serializable {
    private int id;
    private int authorId;
    private String authorName;
    private String authorPhone;
    private String rubbishName;
    private String rubbishType;
    private int rubbishTypeNub;
    private String rubbishIntro;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public String getRubbishName() {
        return rubbishName;
    }

    public void setRubbishName(String rubbishName) {
        this.rubbishName = rubbishName;
    }

    public String getRubbishType() {
        return rubbishType;
    }

    public void setRubbishType(String rubbishType) {
        this.rubbishType = rubbishType;
    }

    public int getRubbishTypeNub() {
        return rubbishTypeNub;
    }

    public void setRubbishTypeNub(int rubbishTypeNub) {
        String rubbishType = "";
        switch (rubbishTypeNub){
            case 1: rubbishType = "可回收垃圾";break;
            case 2: rubbishType = "湿垃圾";break;
            case 3: rubbishType = "干垃圾";break;
            case 4: rubbishType = "有害垃圾";break;
        }
        setRubbishType(rubbishType);
        this.rubbishTypeNub = rubbishTypeNub;
    }

    public String getRubbishIntro() {
        return rubbishIntro;
    }

    public void setRubbishIntro(String rubbishIntro) {
        this.rubbishIntro = rubbishIntro;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

package com.lois.domain.Search;

public class SearchUser {
    private int state;
    private String name;
    private String phone;
    private int start;
    private int size;

    public SearchUser(int state, String name, String phone, int start, int size) {
        this.state = state;
        this.name = name;
        this.phone = phone;
        this.start = start;
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

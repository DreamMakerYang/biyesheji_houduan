package com.lois.domain.Search;

public class SearchRubbish {
    private int state;
    private String authorName;
    private String authorPhone;
    private String rubbishName;
    private int optionsValue;
    private int start;
    private int size;

    public SearchRubbish() {
    }

    public SearchRubbish(int state, String authorName, String authorPhone, String rubbishName, int optionsValue, int start, int size) {
        this.state = state;
        this.authorName = authorName;
        this.authorPhone = authorPhone;
        this.rubbishName = rubbishName;
        this.optionsValue = optionsValue;
        this.start = start;
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getOptionsValue() {
        return optionsValue;
    }

    public void setOptionsValue(int optionsValue) {
        this.optionsValue = optionsValue;
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

package com.lois.domain.Search;

public class SearchDynamic {
    private int state;
    private String authorName;
    private String authorPhone;
    private String title;
    private int start;
    private int size;

    public SearchDynamic(int state, String authorName, String authorPhone, String title, int start, int size) {
        this.state = state;
        this.authorName = authorName;
        this.authorPhone = authorPhone;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

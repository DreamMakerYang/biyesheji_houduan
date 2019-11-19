package com.lois.domain.Result;

public class ResultComment {
    private int id;
    private int dynamic_id;
    private int authorId;
    private String authorName;
    private String authorImage;
    private String date;
    private String content;

    public ResultComment() {
    }

    public ResultComment(int id, int dynamic_id, int authorId, String authorName, String authorImage, String date, String content) {
        this.id = id;
        this.dynamic_id = dynamic_id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
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

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

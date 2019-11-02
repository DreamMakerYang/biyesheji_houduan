package com.lois.domain.Setting;

import java.io.Serializable;
import java.util.List;

public class Init implements Serializable {
    private boolean autoAttention;
    private boolean autoNews;
    private int autoAttentionNub;
    private int autoNewsNub;
    private List<String> urlList;
    private List<String> srcList;

    public boolean isAutoAttention() {
        return autoAttention;
    }

    public void setAutoAttention(boolean autoAttention) {
        if (autoAttention){
            this.autoAttentionNub = 1;
        }else{
            this.autoAttentionNub = 0;
        }
        this.autoAttention = autoAttention;
    }

    public boolean isAutoNews() {
        return autoNews;
    }

    public void setAutoNews(boolean autoNews) {
        if (autoNews){
            this.autoNewsNub = 1;
        }else{
            this.autoNewsNub = 0;
        }
        this.autoNews = autoNews;
    }

    public int getAutoAttentionNub() {
        return autoAttentionNub;
    }

    public void setAutoAttentionNub(int autoAttentionNub) {
        this.autoAttention = autoAttentionNub == 1;
        this.autoAttentionNub = autoAttentionNub;
    }

    public int getAutoNewsNub() {
        return autoNewsNub;
    }

    public void setAutoNewsNub(int autoNewsNub) {
        this.autoNews = autoNewsNub == 1;
        this.autoNewsNub = autoNewsNub;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public List<String> getSrcList() {
        return srcList;
    }

    public void setSrcList(List<String> srcList) {
        this.srcList = srcList;
    }
}

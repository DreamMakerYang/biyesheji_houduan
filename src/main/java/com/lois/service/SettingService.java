package com.lois.service;

import com.lois.domain.Setting.Init;
import com.lois.domain.Setting.Picture;
import com.lois.domain.Setting.ResultHotAttention;

import java.util.List;

public interface SettingService {
    Init init();

    void deletePictures(List<Integer> pictures,String path);

    List<Picture> getPictures();

    List<ResultHotAttention> getHotAttention();

    void deleteAttentions(List<String> list);

    List<ResultHotAttention> getHotNews();

    void deleteHotNews(List<Integer> list);

    void changeSetting(Init init);

    void addPicture(String projectServerPath);
}

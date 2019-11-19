package com.lois.service;

import com.lois.domain.Result.ResultNews;
import com.lois.utils.entity.PageResult;

import java.util.Map;

public interface NewsService {
    PageResult<ResultNews> findSearch(Map<String, String> searchMap, int page, int size);

    ResultNews findOneById(int id);

    void changeNews(ResultNews resultNews);

    void changeState(int id, int state);

    void setHot(int id);

    void setContent(int id, String content);

    void addNews(ResultNews news);
}

package com.lois.service;

import com.lois.domain.Result.ResultKnow;
import com.lois.domain.Result.ResultNews;
import com.lois.utils.entity.PageResult;

import java.util.Map;

public interface KnowService {
    PageResult<ResultKnow> findSearch(Map<String, String> searchMap, int page, int size);

    ResultKnow findOneById(int id);

    void changeKnow(ResultKnow resultKnow);

    void changeState(int id, int state);

    void setHot(int id);

    void setContent(int id, String content);

    void addNews(ResultKnow know);
}

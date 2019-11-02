package com.lois.service;

import com.lois.domain.Result.ResultNews;
import com.lois.domain.Result.ResultRubbish;
import com.lois.utils.entity.PageResult;

import java.util.Map;

public interface RubbishService {
    PageResult<ResultRubbish> findSearch(Map<String, String> searchMap, int page, int size);


    void changeRubbish(ResultRubbish resultRubbish);

    void changeState(int id, int state);

    ResultRubbish findOneById(int id);
}

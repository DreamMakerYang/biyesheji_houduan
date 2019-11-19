package com.lois.service;

import com.lois.domain.Result.ResultNews;
import com.lois.domain.Result.ResultRubbish;
import com.lois.domain.Result.ResultRubbishName;
import com.lois.utils.entity.PageResult;

import java.util.List;
import java.util.Map;

public interface RubbishService {
    PageResult<ResultRubbish> findSearch(Map<String, String> searchMap, int page, int size);


    void changeRubbish(ResultRubbish resultRubbish);

    void changeState(int id, int state);

    ResultRubbish findOneById(int id);

    void addRubbish(ResultRubbish rubbish);

    List<ResultRubbishName> findByName(String queryString);
}

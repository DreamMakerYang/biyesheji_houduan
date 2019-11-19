package com.lois.service;

import com.lois.domain.Dynamic;
import com.lois.domain.Result.ResultComment;
import com.lois.domain.Result.ResultDynamic;
import com.lois.utils.entity.PageResult;

import java.util.List;
import java.util.Map;

public interface DynamicService {
    PageResult<ResultDynamic> findSearch(Map<String, String> searchMap, int page, int size);

    List<ResultComment> findComment(int id);

    void deleteComments(List<Integer> list);

    void changeState(int id, int state);

    void changeDynamic(ResultDynamic resultDynamic);

    ResultDynamic findOneById(int id);

    void setHot(int id);

    void addDynamic(ResultDynamic dynamic);

    String[] findImageById(int id);

    void addComment(ResultComment resultComment);

    boolean hasStar(int id, int uid);

    void setStar(int id, int uid, int flag);
}

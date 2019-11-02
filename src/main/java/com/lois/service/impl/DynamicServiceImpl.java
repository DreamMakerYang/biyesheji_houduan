package com.lois.service.impl;

import com.lois.dao.DynamicDao;
import com.lois.domain.Dynamic;
import com.lois.domain.Result.ResultComment;
import com.lois.domain.Result.ResultDynamic;
import com.lois.domain.Search.SearchDynamic;
import com.lois.service.DynamicService;
import com.lois.utils.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("dynamicService")
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicDao dynamicDao;

    @Override
    public PageResult<ResultDynamic> findSearch(Map<String, String> searchMap, int page, int size) {
        SearchDynamic searchDynamic = new SearchDynamic(Integer.valueOf(searchMap.get("state")),searchMap.get("authorName"),searchMap.get("authorPhone"),searchMap.get("title"),(page-1) * 10,size);
        List<ResultDynamic> resultDynamic = dynamicDao.findAllBySearch(searchDynamic);
        int count = dynamicDao.findCountBySearch(searchDynamic);
        PageResult<ResultDynamic> pageResult = new PageResult<>(count,resultDynamic);
        return pageResult;
    }

    @Override
    public List<ResultComment> findComment(int id) {
        return dynamicDao.findComment(id);
    }

    @Override
    public void deleteComments(List<Integer> list) {
        dynamicDao.deleteComments(list);
    }

    @Override
    public void changeState(int id, int state) {
        dynamicDao.changeState(id,state);
    }

    @Override
    public void changeDynamic(ResultDynamic resultDynamic) {
        dynamicDao.changeDynamic(resultDynamic);
    }

    @Override
    public ResultDynamic findOneById(int id) {
        return dynamicDao.findOneById(id);
    }

    @Override
    public void setHot(int id) {
        dynamicDao.setHot(id);
    }
}

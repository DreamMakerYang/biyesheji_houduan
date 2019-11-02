package com.lois.service.impl;

import com.lois.dao.NewsDao;
import com.lois.dao.RubbishDao;
import com.lois.domain.Result.ResultNews;
import com.lois.domain.Result.ResultRubbish;
import com.lois.domain.Search.SearchDynamic;
import com.lois.domain.Search.SearchRubbish;
import com.lois.service.NewsService;
import com.lois.service.RubbishService;
import com.lois.utils.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("rubbishService")
public class RubbishServiceImpl implements RubbishService {

    @Autowired
    RubbishDao rubbishDao;

    /**
     * 这里是用的SearchDynamic，因为内容差不多
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<ResultRubbish> findSearch(Map<String, String> searchMap, int page, int size) {
        if (searchMap.get("optionsValue") == null){
            searchMap.put("optionsValue","0");
        }
        SearchRubbish searchRubbish = new SearchRubbish(Integer.valueOf(searchMap.get("state")),searchMap.get("authorName"),searchMap.get("authorPhone"),searchMap.get("rubbishName"),Integer.valueOf(searchMap.get("optionsValue")),(page-1) * 10,size);
        List<ResultRubbish> resultRubbishes = rubbishDao.findAllBySearch(searchRubbish);
        int count = rubbishDao.findCountBySearch(searchRubbish);
        PageResult<ResultRubbish> pageResult = new PageResult<>(count,resultRubbishes);
        return pageResult;
    }

    @Override
    public ResultRubbish findOneById(int id) {
        return rubbishDao.findOneById(id);
    }

    @Override
    public void changeRubbish(ResultRubbish resultRubbish) {
        rubbishDao.changeRubbish(resultRubbish);
    }

    @Override
    public void changeState(int id, int state) {
        rubbishDao.changeState(id,state);
    }

}

package com.lois.service.impl;

import com.lois.dao.KnowDao;
import com.lois.dao.NewsDao;
import com.lois.domain.Result.ResultKnow;
import com.lois.domain.Result.ResultNews;
import com.lois.domain.Search.SearchDynamic;
import com.lois.service.KnowService;
import com.lois.service.NewsService;
import com.lois.utils.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("knowService")
public class KnowServiceImpl implements KnowService {

    @Autowired
    KnowDao knowDao;

    /**
     * 这里是用的SearchDynamic，因为内容差不多
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<ResultKnow> findSearch(Map<String, String> searchMap, int page, int size) {
        SearchDynamic searchDynamic = new SearchDynamic(Integer.valueOf(searchMap.get("state")),searchMap.get("authorName"),searchMap.get("authorPhone"),searchMap.get("title"),(page-1) * size,size);
        List<ResultKnow> resultNews = knowDao.findAllBySearch(searchDynamic);
        int count = knowDao.findCountBySearch(searchDynamic);
        PageResult<ResultKnow> pageResult = new PageResult<>(count,resultNews);
        return pageResult;
    }

    @Override
    public ResultKnow findOneById(int id) {
//        浏览量增加
        knowDao.countUp(id);
        return knowDao.findOneById(id);
    }

    @Override
    public void changeKnow(ResultKnow resultKnow) {
        knowDao.changeKnow(resultKnow);
    }

    @Override
    public void setContent(int id, String content) {
        knowDao.setContent(id,content);
    }

    @Override
    public void addNews(ResultKnow know) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        know.setState(0);
        know.setDate(df.format(new Date()));
        know.setCount(0);
        know.setHot(0);

        knowDao.addNews(know);
    }

    @Override
    public void changeState(int id, int state) {
        knowDao.changeState(id,state);
    }

    @Override
    public void setHot(int id) {
        knowDao.setHot(id);
    }
}

package com.lois.service.impl;

import com.lois.dao.NewsDao;
import com.lois.domain.Result.ResultDynamic;
import com.lois.domain.Result.ResultNews;
import com.lois.domain.Search.SearchDynamic;
import com.lois.service.NewsService;
import com.lois.utils.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsDao newsDao;

    /**
     * 这里是用的SearchDynamic，因为内容差不多
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<ResultNews> findSearch(Map<String, String> searchMap, int page, int size) {
        SearchDynamic searchDynamic = new SearchDynamic(Integer.valueOf(searchMap.get("state")),searchMap.get("authorName"),searchMap.get("authorPhone"),searchMap.get("title"),(page-1) * size,size);
        List<ResultNews> resultNews = newsDao.findAllBySearch(searchDynamic);
        int count = newsDao.findCountBySearch(searchDynamic);
        PageResult<ResultNews> pageResult = new PageResult<>(count,resultNews);
        return pageResult;
    }

    @Override
    public ResultNews findOneById(int id) {
        //        浏览量增加
        newsDao.countUp(id);
        return newsDao.findOneById(id);
    }

    @Override
    public void changeNews(ResultNews resultNews) {
        newsDao.changeNews(resultNews);
    }

    @Override
    public void setContent(int id, String content) {
        newsDao.setContent(id,content);
    }

    @Override
    public void addNews(ResultNews news) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        news.setState(0);
        news.setDate(df.format(new Date()));
        news.setCount(0);
        news.setHot(0);

        newsDao.addNews(news);
    }

    @Override
    public void changeState(int id, int state) {
        newsDao.changeState(id,state);
    }

    @Override
    public void setHot(int id) {
        newsDao.setHot(id);
    }
}

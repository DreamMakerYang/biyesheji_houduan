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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("dynamicService")
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicDao dynamicDao;

    @Override
    public PageResult<ResultDynamic> findSearch(Map<String, String> searchMap, int page, int size) {
        SearchDynamic searchDynamic = new SearchDynamic(Integer.valueOf(searchMap.get("state")),searchMap.get("authorName"),searchMap.get("authorPhone"),searchMap.get("title"),(page-1) * size,size);
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

    @Override
    public void addDynamic(ResultDynamic dynamic) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dynamic.setState(0);
        dynamic.setDate(df.format(new Date()));
        dynamic.setComment(0);
        dynamic.setHot(0);
        dynamic.setStar(0);

        dynamicDao.addDynamic(dynamic);

        String[] imageUrls = dynamic.getImageUrl().split(",");
        for (String imageUrl : imageUrls) {
            if (!"".equals(imageUrl)) {
                dynamicDao.addPicture(dynamic.getId(),imageUrl);
            }
        }
    }

    @Override
    public String[] findImageById(int id) {
        return dynamicDao.findImageById(id);
    }

    @Override
    public void addComment(ResultComment resultComment) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        resultComment.setDate(df.format(new Date()));
        dynamicDao.addComment(resultComment);
        dynamicDao.commentUp(resultComment.getDynamic_id());
    }

    @Override
    public boolean hasStar(int id, int uid) {
        return dynamicDao.isStar(uid, id) > 0;
    }

    @Override
    public void setStar(int id, int uid, int flag) {
        dynamicDao.setStar(id,flag);
        if(flag == 1){
//            点赞
            dynamicDao.setStarRela(id,uid);
        }else{
            dynamicDao.deleteStarRela(id,uid);
        }
    }
}

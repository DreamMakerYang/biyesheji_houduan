package com.lois.service.impl;

import com.lois.dao.SettingDao;
import com.lois.domain.Setting.*;
import com.lois.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("settingService")
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingDao settingDao;

    @Override
    public Init init() {
        Init init = settingDao.findIsAuto();
        List<String> list = settingDao.findImageUrls();
        init.setSrcList(list);
        init.setUrlList(list);
        return init;
    }

    @Override
    public void changeSetting(Init init) {
        settingDao.changeSetting(init);
    }

    @Override
    public void deletePictures(List<Integer> pictures,String path) {
        for(Integer id : pictures){
            Picture picture = settingDao.getPictureById(id);
            String[] s = picture.getImageUrl().split("/");
            File file = new File(path + s[s.length-1]);
            if (file.exists()){
                file.delete();
            }
        }
        settingDao.deletePictures(pictures);
    }

    @Override
    public List<Picture> getPictures() {
        return settingDao.getPictures();
    }

    /**
     * 获取动态和小知识中的热门信息，在id中设置D和K来分辨是哪个里面的
     * @return
     */
    @Override
    public List<ResultHotAttention> getHotAttention() {
        List<ResultHotAttention> dynamicHotList = settingDao.getDynamicHot();
        List<ResultHotAttention> knowHotList = settingDao.getKnowHot();
        for (ResultHotAttention aDynamicHotList : dynamicHotList) {
            aDynamicHotList.setId("D:" + aDynamicHotList.getId());
        }
        for (ResultHotAttention aKnowHotList : knowHotList) {
            aKnowHotList.setId("K:" + aKnowHotList.getId());
        }
        dynamicHotList.addAll(knowHotList);
        return dynamicHotList;
    }

    @Override
    public void deleteAttentions(List<String> list) {
        List<Integer> dynamicIds = new ArrayList<>();
        List<Integer> knowIds = new ArrayList<>();
        for (String s: list
             ) {
            Integer id = Integer.parseInt(s.split(":")[1]);
            if (s.charAt(0) == 'D'){
                dynamicIds.add(id);
            }else{
                knowIds.add(id);
            }
        }
        settingDao.setDynamicNotHot(dynamicIds);
        settingDao.setKnowNotHot(knowIds);
    }

    @Override
    public List<ResultHotAttention> getHotNews() {
        return settingDao.getHotNews();
    }

    @Override
    public void deleteHotNews(List<Integer> list) {
        settingDao.deleteHotNews(list);
    }

    @Override
    public void addPicture(String projectServerPath) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Picture picture = new Picture(df.format(new Date()),projectServerPath);
        settingDao.addPicture(picture);
    }
}

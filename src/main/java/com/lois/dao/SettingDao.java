package com.lois.dao;

import com.lois.domain.Setting.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingDao {

    @Select("select AutoAttention as AutoAttentionNub,AutoNews as AutoNewsNub from setting")
    Init findIsAuto();

    @Select("select imageUrl from setting_image")
    List<String> findImageUrls();


    void deletePictures(List<Integer> pictures);

    @Select("select * from setting_image")
    List<Picture> getPictures();

    @Select("select * from setting_image where id = #{id}")
    Picture getPictureById(Integer id);


    @Select("select d.id,u.name as authorName, u.phone as authorPhone,d.content,d.title \n" +
            "from user u,dynamic d \n" +
            "where d.hot = 1 and u.id = d.uid")
    List<ResultHotAttention> getDynamicHot();

    @Select("select k.id,u.name as authorName, u.phone as authorPhone,k.title,k.intro as content\n" +
            "from user u,know k\n" +
            "where u.id = k.uid and k.hot = 1")
    List<ResultHotAttention> getKnowHot();

    void setDynamicNotHot(List<Integer> dynamicIds);

    void setKnowNotHot(List<Integer> knowIds);

    @Select("select n.id,u.name as authorName, u.phone as authorPhone,n.title,n.intro as content\n" +
            "from user u,news n\n" +
            "where u.id = n.uid and n.hot = 1")
    List<ResultHotAttention> getHotNews();

    void deleteHotNews(List<Integer> list);

    @Update("update setting set autoAttention = #{autoAttention},autoNews = #{autoNews}")
    void changeSetting(Init init);

    @Insert("insert into setting_image(date,imageUrl) values(#{date},#{imageUrl})")
    void addPicture(Picture picture);
}

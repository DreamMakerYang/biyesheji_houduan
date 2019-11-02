package com.lois.dao;

import com.lois.domain.Result.ResultNews;
import com.lois.domain.Search.SearchDynamic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao {

    List<ResultNews> findAllBySearch(SearchDynamic searchDynamic);

    int findCountBySearch(SearchDynamic searchDynamic);

    @Select("select n.id,u.id as authorId,u.name as authorName, u.phone as authorPhone,n.date,n.title,n.count,n.intro,n.imageUrl,n.hot,n.content\n" +
            " from user u,news n where n.id = #{id} and n.uid = u.id")
    ResultNews findOneById(int id);

    @Update("update news set title = #{title},date = #{date},count = #{count},imageUrl = #{imageUrl},intro = #{intro} where id = #{id}")
    void changeNews(ResultNews resultNews);

    @Update("update news set state = #{state} where id = #{id}")
    void changeState(@Param("id") int id,@Param("state") int state);

    @Update("update news set hot = 1 where id = #{id}")
    void setHot(int id);

    @Update("update news set content = #{content} where id = #{id}")
    void setContent(@Param("id") int id,@Param("content") String content);
}

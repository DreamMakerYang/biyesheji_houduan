package com.lois.dao;

import com.lois.domain.Result.ResultKnow;
import com.lois.domain.Result.ResultNews;
import com.lois.domain.Search.SearchDynamic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowDao {

    List<ResultKnow> findAllBySearch(SearchDynamic searchDynamic);

    int findCountBySearch(SearchDynamic searchDynamic);

    @Select("select k.id,u.id as authorId,u.name as authorName, u.phone as authorPhone,k.date,k.title,k.count,k.intro,k.imageUrl,k.hot,k.content\n" +
            " from user u,know k where k.id = #{id} and k.uid = u.id")
    ResultKnow findOneById(int id);

    @Update("update know set title = #{title},date = #{date},count = #{count},imageUrl = #{imageUrl},intro = #{intro} where id = #{id}")
    void changeKnow(ResultKnow resultKnow);

    @Update("update know set state = #{state} where id = #{id}")
    void changeState(@Param("id") int id, @Param("state") int state);

    @Update("update know set hot = 1 where id = #{id}")
    void setHot(int id);

    @Update("update know set content = #{content} where id = #{id}")
    void setContent(@Param("id") int id, @Param("content") String content);

    @Insert("INSERT INTO `know`(`uid`, `title`, `count`, `imageUrl`, `intro`, `content`, `date`, `hot`, `state`) " +
            "VALUES (#{authorId},#{title},#{count},#{imageUrl},#{intro},#{content},#{date},#{hot},#{state})")
    void addNews(ResultKnow know);

    @Update("update know set count = count+1 where id = #{id}")
    void countUp(int id);
}

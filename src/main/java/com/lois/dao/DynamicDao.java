package com.lois.dao;

import com.lois.domain.Result.ResultComment;
import com.lois.domain.Result.ResultDynamic;
import com.lois.domain.Search.SearchDynamic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicDao {

    int findCountBySearch(SearchDynamic searchDynamic);

    List<ResultDynamic> findAllBySearch(SearchDynamic searchDynamic);

    @Select("select c.id,u.id as authorId,u.name as authorName,u.image as authorImage,c.date,c.content\n" +
            "from user u,dynamic d,dynamic_comment c\n" +
            "where d.id = c.dynamic_id and u.id = c.uid and d.id = #{id}")
    List<ResultComment> findComment(int id);

    void deleteComments(List<Integer> list);

    @Update("update dynamic set state = #{state} where id = #{id}")
    void changeState(@Param("id") int id, @Param("state") int state);

    @Update("update dynamic set star = #{star} , content = #{content} , title = #{title} where id = #{id}")
    void changeDynamic(ResultDynamic resultDynamic);

    @Select("select d.id,u.id as authorId,u.name as authorName, u.phone as authorPhone,d.star,d.comment,d.content,d.title,d.hot,d.date\n" +
            " from user u,dynamic d where d.id = #{id} and d.uid = u.id")
    ResultDynamic findOneById(int id);

    @Update("update dynamic set hot = 1 where id = #{id}")
    void setHot(int id);
}

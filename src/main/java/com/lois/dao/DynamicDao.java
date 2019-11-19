package com.lois.dao;

import com.lois.domain.Result.ResultComment;
import com.lois.domain.Result.ResultDynamic;
import com.lois.domain.Search.SearchDynamic;
import org.apache.ibatis.annotations.*;
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

    @Select("select d.id,u.id as authorId,u.name as authorName, u.phone as authorPhone,u.image as authorImage,d.star,d.comment,d.content,d.title,d.hot,d.date\n" +
            " from user u,dynamic d where d.id = #{id} and d.uid = u.id")
    ResultDynamic findOneById(int id);

    @Update("update dynamic set hot = 1 where id = #{id}")
    void setHot(int id);

    void addDynamic(ResultDynamic dynamic);

    @Insert("insert into dynamic_image values (#{id},#{imageUrl})")
    void addPicture(@Param("id") int id,@Param("imageUrl") String imageUrl);

    @Select("select imageUrl from dynamic_image where did = #{id}")
    String[] findImageById(int id);

    @Insert("insert into dynamic_comment(dynamic_id,uid,content,date) values(#{dynamic_id},#{authorId},#{content},#{date})")
    void addComment(ResultComment resultComment);

    @Select("select count(*) from dynamic_star where uid = #{uid} and did = #{did}")
    int isStar(@Param("uid") int uid,@Param("did") int id);

    @Update("update dynamic set star = star + #{flag} where id = #{id}")
    void setStar(@Param("id") int id,@Param("flag") int flag);

    @Insert("insert into dynamic_star values(#{uid},#{did})")
    void setStarRela(@Param("did") int id,@Param("uid") int uid);

    @Delete("delete from dynamic_star where did = #{did} and uid = #{uid}")
    void deleteStarRela(@Param("did") int id,@Param("uid") int uid);

    @Update("update dynamic set comment = comment + 1 where id = #{id}")
    void commentUp(int dynamic_id);
}

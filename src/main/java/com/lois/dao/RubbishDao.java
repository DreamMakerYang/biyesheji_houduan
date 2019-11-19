package com.lois.dao;

import com.lois.domain.Result.ResultNews;
import com.lois.domain.Result.ResultRubbish;
import com.lois.domain.Result.ResultRubbishName;
import com.lois.domain.Search.SearchDynamic;
import com.lois.domain.Search.SearchRubbish;
import com.lois.domain.Search.SearchUser;
import com.lois.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RubbishDao {

    List<ResultRubbish> findAllBySearch(SearchRubbish searchRubbish);

    int findCountBySearch(SearchRubbish searchRubbish);

    @Update("update rubbish set name = #{rubbishName},type = #{rubbishTypeNub},imageUrl = #{imageUrl},intro = #{rubbishIntro} where id = #{id}")
    void changeRubbish(ResultRubbish resultRubbish);

    @Update("update rubbish set state = #{state} where id = #{id}")
    void changeState(@Param("id") int id,@Param("state") int state);

    @Select("select r.id,u.id as authorId,u.name as authorName, u.phone as authorPhone,r.name as rubbishName,r.type as rubbishTypeNub,r.intro as rubbishIntro,r.imageUrl\n" +
            "from user u,rubbish r where r.id = #{id} and r.uid = u.id")
    ResultRubbish findOneById(int id);

    @Insert("insert into rubbish(uid,name,type,imageUrl,intro,state) values(#{authorId},#{rubbishName},#{rubbishTypeNub},#{imageUrl},#{rubbishIntro},#{state})")
    void addRubbish(ResultRubbish rubbish);

    @Select("select name as value from rubbish where name like #{queryString}")
    List<ResultRubbishName> findByName(String queryString);
}

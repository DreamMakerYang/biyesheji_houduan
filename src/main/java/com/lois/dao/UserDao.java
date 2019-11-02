package com.lois.dao;

import com.lois.domain.Search.SearchUser;
import com.lois.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from user where phone = #{phone} and pass = #{pass}")
    User findOneByPhoneAndPass(User user);

    @Insert("insert into user(name,gender,phone,pass,date,image,state) values(#{name},#{gender},#{phone},#{pass},#{date},#{image},#{state})")
    int save(User registerUser);

    List<User> findAllBySearch(SearchUser searchUser);

    int findCountBySearch(SearchUser searchUser);

    @Update("update user set state = #{state} where id = #{id}")
    int setState(@Param("id") int id,@Param("state") int state);

    @Update("update user set name = #{name},gender = #{gender},phone=#{phone},pass=#{pass},image=#{image},date=#{date} where id = #{id}")
    int changeUser(User user);

    @Select("select * from user where id = #{id}")
    User findById(int id);

    @Select("select count(*) from user where phone = #{phone}")
    int findUserCountByPhone(String phone);
}

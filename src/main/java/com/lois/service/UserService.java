package com.lois.service;

import com.lois.domain.Result.ResultUser;
import com.lois.domain.Result.ResultUserPublish;
import com.lois.domain.User;
import com.lois.utils.entity.PageResult;

import java.util.Map;

public interface UserService {
    ResultUser login(User loginUser);

    boolean register(User registerUser);

    PageResult<User> findSearch(Map<String,String> searchMap, int page, int size);

    boolean setState(int id, int state);

    boolean changeUser(User user);

    User findById(int id);

    String managerLogin(Map<String, String> map);

    int findUserCountByPhone(String phone);

    ResultUserPublish findUserPublish(Integer id);
}

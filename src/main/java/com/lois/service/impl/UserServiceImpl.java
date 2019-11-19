package com.lois.service.impl;

import com.lois.dao.UserDao;
import com.lois.domain.Result.ResultUser;
import com.lois.domain.Result.ResultUserPublish;
import com.lois.domain.Search.SearchUser;
import com.lois.domain.User;
import com.lois.service.UserService;
import com.lois.utils.JwtUtil;
import com.lois.utils.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResultUser login(User loginUser) {
        ResultUser resultUser = null;
        User user = userDao.findOneByPhoneAndPass(loginUser);
        if(user != null){
            String token = jwtUtil.createJWT(user.getId().toString(), user.getName());
            resultUser = new ResultUser(token,user.getName(),user.getImage(),user.getState());
        }
        return resultUser;
    }

    @Override
    public boolean register(User registerUser) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        registerUser.setDate(df.format(new Date()));
        // 默认头像
        registerUser.setImage("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        registerUser.setState(1);
        if (registerUser.getGender().equals("false")){
            registerUser.setGender("男");
        }else{
            registerUser.setGender("女");
        }
        int count = userDao.save(registerUser);
        if (count == 0){
            return false;
        }
        return true;
    }

    @Override
    public int findUserCountByPhone(String phone) {
        return userDao.findUserCountByPhone(phone);
    }

    @Override
    public ResultUserPublish findUserPublish(Integer id) {
        int dynamicNo = userDao.findCountDynamicByIdAndState(id,0);
        int dynamicYes = userDao.findCountDynamicByIdAndState(id,1);
        int dynamicStar = userDao.findCountDynamicStarById(id);
        int newNo = userDao.findCountNewByIdAndState(id,0);
        int newYes = userDao.findCountNewByIdAndState(id,1);
        int newLook = userDao.findCountNewLookById(id);
        int rubbishNo = userDao.findCountRubbishByIdAndState(id,0);
        int rubbishYes = userDao.findCountRubbishByIdAndState(id,1);
        int knowNo = userDao.findCountKnowByIdAndState(id,0);
        int knowYes = userDao.findCountKnowByIdAndState(id,1);
        int knowLook = userDao.findCountKnowLookById(id);
        return new ResultUserPublish(dynamicNo,dynamicYes,dynamicStar,newNo,newYes,newLook,rubbishNo,rubbishYes,knowNo,knowYes,knowLook);
    }

    @Override
    public PageResult<User> findSearch(Map<String,String> searchMap, int page, int size) {
        SearchUser searchUser = new SearchUser(Integer.valueOf(searchMap.get("state")),searchMap.get("name"),searchMap.get("phone"),(page-1) * 10,size);
        List<User> users = userDao.findAllBySearch(searchUser);
        int count = userDao.findCountBySearch(searchUser);
        PageResult<User> pageResult = new PageResult<>(count,users);
        return pageResult;
    }

    @Override
    public boolean setState(int id, int state) {
        int i = userDao.setState(id, state);
        if(i == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean changeUser(User user) {
        int i = userDao.changeUser(user);
        if(i == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public User findById(int id) {
        return (userDao.findById(id));

    }

    @Override
    public String managerLogin(Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String token = "";
        if (username.equals("admin") && password.equals("11111111")){
            token = jwtUtil.createJWT("1", "admin");
        }
        return token;
    }
}

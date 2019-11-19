package com.lois.controller;

import com.lois.domain.Manager;
import com.lois.domain.Result.ResultUser;
import com.lois.domain.Result.ResultUserPublish;
import com.lois.domain.User;
import com.lois.service.UserService;
import com.lois.utils.JwtUtil;
import com.lois.utils.entity.PageResult;
import com.lois.utils.entity.Result;
import com.lois.utils.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 用户登录
     *
     * @param loginUser
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User loginUser) {
        ResultUser user = userService.login(loginUser);
        if (user == null) {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
        } else if (user.getState() != 1) {
            return new Result(true, StatusCode.ACCESSERROR, "账号已被删除，请联系管理员");
        } else {
            return new Result(true, StatusCode.OK, "登录成功", user);
        }
    }

    /**
     * 用户信息查询
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public Result findOneByToken(@RequestHeader("User-Token") String token) {
        Integer id = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        User user = userService.findById(id);
        if (user == null){
            return new Result(false, StatusCode.ERROR, "查询失败");
        }else{
            return new Result(true, StatusCode.OK, "查询成功", user);
        }

    }

    /**
     * 用户发布信息概览
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getPublish", method = RequestMethod.GET)
    public Result findUserPublish(@RequestHeader("User-Token") String token) {
        Integer id = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        ResultUserPublish userPublish= userService.findUserPublish(id);
        return new Result(true, StatusCode.OK, "查询成功",userPublish);
    }

    /**
     * 用户注册
     *
     * @param registerUser
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody User registerUser) {
        if (userService.register(registerUser)) {
            return new Result(true, StatusCode.OK, "注册成功");
        } else {
            return new Result(false, StatusCode.LOGINERROR, "注册失败");
        }
    }

    /**
     * 用户注册时查询电话号是否已经注册
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/findUserCountByPhone/{phone}", method = RequestMethod.GET)
    public Result findUserCountByPhone(@PathVariable String phone) {
        if (userService.findUserCountByPhone(phone) == 0) {
            return new Result(true, StatusCode.OK, "未注册");
        } else {
            return new Result(false, StatusCode.OK, "已被注册");
        }
    }

    /**
     * 分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map<String, String> searchMap, @PathVariable int page, @PathVariable int size) {
        PageResult<User> pageResult = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @RequestMapping(value = "/{id}/{state}", method = RequestMethod.PUT)
    public Result setState(@PathVariable int id, @PathVariable int state) {
        if (userService.setState(id, state)) {
            String message = "修改成功";
            switch (state){
                case 0:message="删除成功";break;
                case 1:message="恢复成功";break;
            }
            return new Result(true, StatusCode.OK, message);
        } else {
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }

    @RequestMapping(value = "/changeUser", method = RequestMethod.POST)
    public Result changeUser(@RequestBody User user,@RequestHeader(value = "User-Token",required = false) String token) {
        if(token != null && !"".equals(token)){
            Integer id = Integer.valueOf(jwtUtil.parseJWT(token).getId());
            user.setId(id);
        }
        if (userService.changeUser(user)) {
            return new Result(true, StatusCode.OK, "修改成功");
        } else {
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable int id) {
        User user = userService.findById(id);
        if (user != null){
            return new Result(true, StatusCode.OK, "查询成功",user);
        }else{
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
    }


    @RequestMapping(value = "/managerLogin", method = RequestMethod.POST)
    public Result managerLogin(@RequestBody Map<String,String> map) {
        String token = userService.managerLogin(map);
        Manager manager = new Manager();
        manager.setToken(token);
        if (token.equals("")){
            return new Result(false, StatusCode.ACCESSERROR, "登录失败",manager);
        }
        return new Result(true, StatusCode.OK, "登录成功",manager);
    }

    @RequestMapping(value = "/managerInfo", method = RequestMethod.GET)
    public Result managerInfo(String token) {
        if (token.equals("")){
            return new Result(true, StatusCode.OK, "获取信息失败");
        }else{
            Manager manager = new Manager();
            manager.setName("小白");
            manager.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            manager.setToken(token);
            return new Result(true, StatusCode.OK, "获取信息成功",manager);
        }
    }

    /**
     * 上传头像
     * @param request
     * @param upload
     * @return
     */
    @RequestMapping(value = "/uploadPictures",method = RequestMethod.POST)
    public Result uploadPictures(HttpServletRequest request, @RequestParam("file") MultipartFile upload) {
        //项目服务器地址路径
        String projectServerPath = request.getScheme() + "://"+request.getServerName()+":" + request.getServerPort() + request.getContextPath() + "/images/user/";
        //获取需要上传文件的位置
        String path = request.getSession().getServletContext().getRealPath("/images/user");
        // 创建File对象，一会向该路径下上传文件
        try {
            File file = new File(path);
            // 判断路径是否存在，如果不存在，创建该路径
            if(!file.exists()) {
                file.mkdirs();
            }
            //获取上传文件名称
            String fileName = upload.getOriginalFilename();
            //把文件名唯一化
            String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            fileName = uuid + "-" + fileName;
            //上传文件
            upload.transferTo(new File(file,fileName));
            projectServerPath += fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"图片上传出错，请联系管理员");
        }

        return new Result(true,StatusCode.OK,"图片上传成功",projectServerPath);
    }
}
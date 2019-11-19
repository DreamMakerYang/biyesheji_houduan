package com.lois.controller;

import com.lois.domain.Result.ResultComment;
import com.lois.domain.Result.ResultDynamic;
import com.lois.domain.Result.ResultNews;
import com.lois.service.DynamicService;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/dynamic/dynamic")
public class DynamicController {

    @Autowired
    DynamicService dynamicService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map<String, String> searchMap, @PathVariable int page, @PathVariable int size) {
        PageResult<ResultDynamic> pageResult;
        pageResult = dynamicService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据id查询动态详细信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findOneById(@PathVariable int id) {
        ResultDynamic resultDynamic = dynamicService.findOneById(id);
        return new Result(true, StatusCode.OK, "查询成功", resultDynamic);
    }

    /**
     * 根据id查询动态图片
     * @param id
     * @return
     */
    @RequestMapping(value = "image/{id}", method = RequestMethod.GET)
    public Result findImageById(@PathVariable int id) {
        String[] imageList = dynamicService.findImageById(id);
        return new Result(true, StatusCode.OK, "查询成功", imageList);
    }


    /**
     * 修改动态信息（但是只修改了点赞数/内容/题目）
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result changeDynamic(@RequestBody ResultDynamic resultDynamic) {
        dynamicService.changeDynamic(resultDynamic);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 修改动态状态
     * @param
     * @return
     */
    @RequestMapping(value = "/{id}/{state}", method = RequestMethod.PUT)
    public Result changeState(@PathVariable int id,@PathVariable int state) {
        dynamicService.changeState(id,state);
        if (state == -1){
            return new Result(true, StatusCode.OK, "删除成功");
        }else if(state == 1){
            return new Result(true, StatusCode.OK, "发布成功");
        }else{
            return new Result(true, StatusCode.OK, "修改成功");
        }
    }

    /**
     * 根据动态id查询评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public Result findComment(@PathVariable int id) {
        List<ResultComment> resultComments = dynamicService.findComment(id);
        return new Result(true, StatusCode.OK, "查询成功", resultComments);
    }

    /**
     * 添加动态评论
     * @param
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Result addComment(@RequestBody Map<String,String> map,@RequestHeader("User-Token") String token) {
        String content = map.get("data");
        Integer dynamic_id = Integer.parseInt(map.get("id"));
        Integer authorId = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        ResultComment resultComment = new ResultComment();
        resultComment.setAuthorId(authorId);
        resultComment.setContent(content);
        resultComment.setDynamic_id(dynamic_id);
        dynamicService.addComment(resultComment);
        return new Result(true, StatusCode.OK, "评论成功");
    }

    /**
     * 根据集合中的id删除评论
     * @param list
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public Result deleteComments(@RequestBody List<Integer> list) {
        dynamicService.deleteComments(list);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 设置热门
     * @param
     * @return
     */
    @RequestMapping(value = "/hot/{id}", method = RequestMethod.PUT)
    public Result setHot(@PathVariable int id) {
        dynamicService.setHot(id);
        return new Result(true, StatusCode.OK, "设置成功");
    }

    /**
     * 查询用户是否已经为动态点赞
     * @param id
     * @return
     */
    @RequestMapping(value = "/star/{id}", method = RequestMethod.GET)
    public Result hasStar(@PathVariable int id,@RequestHeader("User-Token") String token) {
        int uid = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        boolean flag =  dynamicService.hasStar(id,uid);
        return new Result(true, StatusCode.OK, "查询成功", flag);
    }

    /**
     * 用户点赞或者取消点赞
     * @param id
     * @return
     */
    @RequestMapping(value = "/star/{id}", method = RequestMethod.PUT)
    public Result setStar(@PathVariable int id,@RequestHeader("User-Token") String token,@RequestBody Map<String,Integer> map) {
        int flag = map.get("flag");
        int uid = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        dynamicService.setStar(id,uid,flag);
        return new Result(flag == 1, StatusCode.OK, flag == 1 ?"点赞成功":"取消点赞");
    }

    /**
     * 添加动态
     * @param dynamic
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addDynamic(@RequestBody ResultDynamic dynamic, @RequestHeader("User-Token") String token) {
        Integer id = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        dynamic.setAuthorId(id);
        dynamicService.addDynamic(dynamic);
        return new Result(true,StatusCode.OK,"成功提交动态，等待审批！");
    }

    /**
     * 上传动态图片
     * @param request
     * @param upload
     * @return
     */
    @RequestMapping(value = "/uploadPictures",method = RequestMethod.POST)
    public Result uploadPictures(HttpServletRequest request, @RequestParam("file") MultipartFile upload) {
        //项目服务器地址路径
        String projectServerPath = request.getScheme() + "://"+request.getServerName()+":" + request.getServerPort() + request.getContextPath() + "/images/dynamic/";
        //获取需要上传文件的位置
        String path = request.getSession().getServletContext().getRealPath("/images/dynamic");
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

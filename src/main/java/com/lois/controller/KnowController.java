package com.lois.controller;

import com.lois.domain.Result.ResultKnow;
import com.lois.domain.Result.ResultNews;
import com.lois.service.KnowService;
import com.lois.service.NewsService;
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
@RequestMapping("/know/know")
public class KnowController {

    @Autowired
    KnowService knowService;

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
        PageResult<ResultKnow> pageResult = knowService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据id查询知识详细信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findOneById(@PathVariable int id) {
        ResultKnow resultKnow = knowService.findOneById(id);
        return new Result(true, StatusCode.OK, "查询成功", resultKnow);
    }

    /**
     * 修改知识信息（但是只修改了基本信息）
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result changeKnow(@RequestBody ResultKnow resultKnow) {
        knowService.changeKnow(resultKnow);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 修改知识内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/{id}", method = RequestMethod.PUT)
    public Result setContent(@PathVariable int id,@RequestBody String content) {
        knowService.setContent(id,content);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 修改知识状态
     * @param
     * @return
     */
    @RequestMapping(value = "/{id}/{state}", method = RequestMethod.PUT)
    public Result changeState(@PathVariable int id,@PathVariable int state) {
        knowService.changeState(id,state);
        if (state == -1){
            return new Result(true, StatusCode.OK, "删除成功");
        }else if(state == 1){
            return new Result(true, StatusCode.OK, "发布成功");
        }else{
            return new Result(true, StatusCode.OK, "修改成功");
        }
    }

    /**
     * 设置热门
     * @param
     * @return
     */
    @RequestMapping(value = "/hot/{id}", method = RequestMethod.PUT)
    public Result setHot(@PathVariable int id) {
        knowService.setHot(id);
        return new Result(true, StatusCode.OK, "设置成功");
    }

    /**
     * 添加新闻
     * @param know
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addNews(@RequestBody ResultKnow know,@RequestHeader("User-Token") String token) {
        Integer id = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        know.setAuthorId(id);
        knowService.addNews(know);
        return new Result(true,StatusCode.OK,"成功提交新闻，等待审批！");
    }

    /**
     * 上传封面
     * @param request
     * @param upload
     * @return
     */
    @RequestMapping(value = "/uploadPictures",method = RequestMethod.POST)
    public Result uploadPictures(HttpServletRequest request, @RequestParam("file") MultipartFile upload) {
        //项目服务器地址路径
        String projectServerPath = request.getScheme() + "://"+request.getServerName()+":" + request.getServerPort() + request.getContextPath() + "/images/know/";
        //获取需要上传文件的位置
        String path = request.getSession().getServletContext().getRealPath("/images/know");
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

//    /**
//     * 根据动态id查询评论
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
//    public Result findComment(@PathVariable int id) {
//        List<ResultComment> resultComments = dynamicService.findComment(id);
//        return new Result(true, StatusCode.OK, "查询成功", resultComments);
//    }
//
//    /**
//     * 根据集合中的id删除评论
//     * @param list
//     * @return
//     */
//    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
//    public Result deleteComments(@RequestBody List<Integer> list) {
//        dynamicService.deleteComments(list);
//        return new Result(true, StatusCode.OK, "删除成功");
//    }

}

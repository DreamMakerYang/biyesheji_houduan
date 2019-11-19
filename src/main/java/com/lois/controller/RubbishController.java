package com.lois.controller;

import com.lois.domain.Result.ResultNews;
import com.lois.domain.Result.ResultRubbish;
import com.lois.domain.Result.ResultRubbishName;
import com.lois.service.NewsService;
import com.lois.service.RubbishService;
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
@RequestMapping("/rubbish/rubbish")
public class RubbishController {

    @Autowired
    RubbishService rubbishService;

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
        PageResult<ResultRubbish> pageResult = rubbishService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }


    /**
     * 查询垃圾信息
     * @param
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findOneById(@PathVariable int id) {
        ResultRubbish resultRubbish = rubbishService.findOneById(id);
        return new Result(true, StatusCode.OK, "查询成功", resultRubbish);
    }


    /**
     * 模糊查询垃圾名称，给前端提供搜索建议
     * @param
     * @return
     */
    @RequestMapping(value = "/name/{queryString}", method = RequestMethod.GET)
    public Result findByName(@PathVariable String queryString) {
        List<ResultRubbishName> list = rubbishService.findByName(queryString);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
    /**
     * 修改垃圾信息
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result changeNews(@RequestBody ResultRubbish resultRubbish) {
        rubbishService.changeRubbish(resultRubbish);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /**
     * 修改垃圾状态
     * @param
     * @return
     */
    @RequestMapping(value = "/{id}/{state}", method = RequestMethod.PUT)
    public Result changeState(@PathVariable int id,@PathVariable int state) {
        rubbishService.changeState(id,state);
        if (state == -1){
            return new Result(true, StatusCode.OK, "删除成功");
        }else if(state == 1){
            return new Result(true, StatusCode.OK, "发布成功");
        }else{
            return new Result(true, StatusCode.OK, "修改成功");
        }
    }

    /**
     * 添加新闻
     * @param rubbish
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addRubbish(@RequestBody ResultRubbish rubbish,@RequestHeader("User-Token") String token) {
        Integer id = Integer.valueOf(jwtUtil.parseJWT(token).getId());
        rubbish.setAuthorId(id);
        rubbishService.addRubbish(rubbish);
        return new Result(true,StatusCode.OK,"成功提交新闻，等待审批！");
    }

    /**
     * 上传图片
     * @param request
     * @param upload
     * @return
     */
    @RequestMapping(value = "/uploadPictures",method = RequestMethod.POST)
    public Result uploadPictures(HttpServletRequest request, @RequestParam("file") MultipartFile upload) {
        //项目服务器地址路径
        String projectServerPath = request.getScheme() + "://"+request.getServerName()+":" + request.getServerPort() + request.getContextPath() + "/images/rubbish/";
        //获取需要上传文件的位置
        String path = request.getSession().getServletContext().getRealPath("/images/rubbish");
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

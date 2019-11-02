package com.lois.controller;

import com.lois.domain.Result.ResultKnow;
import com.lois.domain.Result.ResultNews;
import com.lois.service.KnowService;
import com.lois.service.NewsService;
import com.lois.utils.entity.PageResult;
import com.lois.utils.entity.Result;
import com.lois.utils.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/know/know")
public class KnowController {

    @Autowired
    KnowService knowService;

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

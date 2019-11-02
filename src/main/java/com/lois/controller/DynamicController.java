package com.lois.controller;

import com.lois.domain.Result.ResultComment;
import com.lois.domain.Result.ResultDynamic;
import com.lois.service.DynamicService;
import com.lois.utils.entity.PageResult;
import com.lois.utils.entity.Result;
import com.lois.utils.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dynamic/dynamic")
public class DynamicController {

    @Autowired
    DynamicService dynamicService;

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
        ResultDynamic resultDynamic;
        resultDynamic = dynamicService.findOneById(id);
        return new Result(true, StatusCode.OK, "查询成功", resultDynamic);
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
}

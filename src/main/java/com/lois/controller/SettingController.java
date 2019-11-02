package com.lois.controller;

import com.lois.domain.Setting.Init;
import com.lois.domain.Setting.Picture;
import com.lois.domain.Setting.ResultHotAttention;
import com.lois.service.SettingService;
import com.lois.utils.entity.Result;
import com.lois.utils.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setting/setting")
public class SettingController {
    @Autowired
    SettingService settingService;

    @RequestMapping(value = "/init" ,method = RequestMethod.POST)
    public Result init(){
        Init init = settingService.init();
        return new Result(true, StatusCode.OK,"初始化成功",init);
    }

    @RequestMapping(value = "/changeSetting" ,method = RequestMethod.PUT)
    public Result changeSetting(@RequestBody Init init){
        settingService.changeSetting(init);
        return new Result(true, StatusCode.OK,"设置成功");
    }

    @RequestMapping(value = "/picture",method = RequestMethod.DELETE)
    public Result deletePictures(HttpServletRequest request,@RequestBody List<Integer> pictures){
        //项目服务器地址路径
        String path = request.getSession().getServletContext().getRealPath("/images/setting/");
        settingService.deletePictures(pictures,path);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/picture",method = RequestMethod.GET)
    public Result getPictures(){
        List<Picture> list =  settingService.getPictures();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/attention",method = RequestMethod.GET)
    public Result getHotAttention(){
        List<ResultHotAttention> list =  settingService.getHotAttention();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/attention",method = RequestMethod.DELETE)
    public Result deleteAttentions(@RequestBody List<String> list){
        settingService.deleteAttentions(list);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/news",method = RequestMethod.GET)
    public Result getHotNews(){
        List<ResultHotAttention> list =  settingService.getHotNews();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/news",method = RequestMethod.DELETE)
    public Result deleteHotNews(@RequestBody List<Integer> list){
        settingService.deleteHotNews(list);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/uploadPictures",method = RequestMethod.POST)
    public Result uploadPictures(HttpServletRequest request,@RequestParam("file") MultipartFile upload) {
        //项目服务器地址路径
        String projectServerPath = request.getScheme() + "://"+request.getServerName()+":" + request.getServerPort() + request.getContextPath() + "/images/setting/";
        //获取需要上传文件的位置
        String path = request.getSession().getServletContext().getRealPath("/images/setting");
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
        settingService.addPicture(projectServerPath);

        return new Result(true,StatusCode.OK,projectServerPath);
    }

}

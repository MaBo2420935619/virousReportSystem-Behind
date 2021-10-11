package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.UserService;

import java.util.Map;

public class UserController extends BaseController{
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @Author mabo
     * @Description   获取今天用户上报情况
     */
    public Object todayReportInfo(Map map){
        JSONObject json= userService.todayReportInfo(map);
        return  JSONObject.toJSON(json);

    }
}

package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.ClassesService;
import com.mabo.service.LoginService;

import java.util.Map;

/**
 * @Author mabo
 * @Description   在这里写请求方法
 */
public class LoginController extends BaseController{

    private LoginService loginService;

    private ClassesService classesService;

    public void setClassesService(ClassesService classesService) {
        this.classesService = classesService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @Author mabo
     * @Description   登录操作
     */
    public Object login(Map map){
        JSONObject json=new JSONObject();
        String uname = map.get("uname").toString();
        String upwd = map.get("upwd").toString();
        String type = map.get("type").toString();
        Boolean login = loginService.isLogin(uname, upwd,type);
        if (login){
            json.put("message","true");
        }
        else   json.put("message","false");
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description   注册操作
     */
    public Object register(Map map){
        JSONObject json=new JSONObject();
        String uname = map.get("uname").toString();
        String upwd = map.get("upwd").toString();
        String type = map.get("type").toString();
        String classId=null;
        if (type.equals("student")){
            if (map.get("class")!=null)
            {
                classId= map.get("class").toString();
            }
            else
                classId="";
        }
        //开始业务操作
        int register = loginService.register(uname, upwd, type, classId);
        if (register==1)
            json.put("message","true");
        else if (register==0)
            json.put("message","false");
        else if (register==2)
            json.put("message","学生班级信息注册失败");
        else
            json.put("message","error");
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description 获取登录页全部班级信息
     */
    public Object getAllclasses(Map map){
        JSONObject allClasses = classesService.getAllClasses();
        return  JSONObject.toJSON(allClasses);
    }
}

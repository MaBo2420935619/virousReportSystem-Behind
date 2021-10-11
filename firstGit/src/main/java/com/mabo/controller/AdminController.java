package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.AdminService;

import java.util.Map;

public class AdminController extends BaseController{
    private AdminService adminService;

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
    /**
     * @Author mabo
     * @Description   获取特定日期系统日志
     */
    public Object getSystemLog(Map map){
        JSONObject json= adminService.getSystemLog(map);
        return  JSONObject.toJSON(json);
    }
    /**
     * @Author mabo
     * @Description   删除老师分配的班级
     */
    public Object deleteTeacherClass(Map map){
        JSONObject json=new JSONObject();
        boolean b = adminService.deleteTeacherClass(map);
        if (b){
            json.put("message","true");
        }
        else   json.put("message","false");
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description   老师分配的班级
     */
    public Object setTeacherClassesInfo(Map map){
        JSONObject json=new JSONObject();
        boolean b = adminService.setTeacherClassesInfo(map);
        if (b){
            json.put("message","true");
        }
        else   json.put("message","false");
        return  JSONObject.toJSON(json);
    }

}

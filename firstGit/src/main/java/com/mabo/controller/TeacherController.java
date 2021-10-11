package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.TeacherService;

import java.util.Map;

public class TeacherController extends BaseController{
    private TeacherService teacherService;


    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    /**
     * @Author mabo
     * @Description   获取老师的学生今天上报情况
     */
    public Object teacherStudentTodayReportInfo(Map map){
        JSONObject json= teacherService.teacherStudentTodayReportInfo(map);
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description   获取教师分配班级的控制器
     */
    public Object getTeacherClassesInfo(Map map){
        JSONObject json= teacherService.getTeacherClassesInfo();
        return  JSONObject.toJSON(json);
    }
}

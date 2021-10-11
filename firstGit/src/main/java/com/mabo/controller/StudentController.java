package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.StudentService;

import java.util.Map;

/**
 * @Author mabo
 * @Description   学生相关控制器
 */

public class StudentController extends BaseController{
    private StudentService studentService;

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @Author mabo
     * @Description   获取用户基本信息
     */
    public Object getUserBaseInfo(Map map){
        JSONObject json=new JSONObject();
        JSONObject j = studentService.getUserBaseInfo(map);
        if(j==null){
            json.put("message","false");
            return  JSONObject.toJSON(json);
        }
        return  JSONObject.toJSON(j);

    }

    /**
     * @Author mabo
     * @Description   完善学生信息
     */
    public Object submitUserBaseInfo(Map map){
        int i = studentService.submitUserBaseInfo(map);
        JSONObject json=new JSONObject();
        if (i==1)
            json.put("message","true");
        else if (i==0)
            json.put("message","false");
        else
            json.put("message","error");
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description   填写学生上报信息
     */
    public Object setReportInfo(Map map){
        JSONObject json=new JSONObject();
        int i = studentService.setReportInfo(map);
        if (i==1)
            json.put("message","true");
        else if (i==2)
            json.put("message","已上报无需重复上报");
        else if (i==0)
            json.put("message","false");
        else if (i==3)
            json.put("message","不能超期上报");
        else
            json.put("message","error");
        return  JSONObject.toJSON(json);
    }

    /**
     * @Author mabo
     * @Description   获取学生上报历史
     */
    public Object getStudentReportHistory(Map map){
        JSONObject allClasses = studentService.getStudentReportHistory(map);
        return  JSONObject.toJSON(allClasses);
    }
    /**
     * @Author mabo
     * @Description   删除学生的一条上报记录
     */
    public Object deleteStudentReport(Map map){
        JSONObject json = studentService.deleteStudentReport(map);
        return  JSONObject.toJSON(json);
    }

}

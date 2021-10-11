package com.mabo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.StudentDao;
import com.mabo.entity.ReportInfoEntity;
import com.mabo.entity.UserBaseInfo;
import com.mabo.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class StudentService extends BaseService{
    StudentDao studentDao;
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * @Author mabo
     * @Description   获取用户基本信息
     */
    public JSONObject getUserBaseInfo(Map map){
        String id = StringUtil.getMapValueString(map, "id");
        String type = StringUtil.getMapValueString(map, "type");
        String history = jedisSharkUtil.get("getUserBaseInfo"+id);
        if (history==null){
            Map userBaseInfo = studentDao.getUserBaseInfo(id, type);
            if (userBaseInfo!=null){
                String s = JSON.toJSONString(userBaseInfo);
                JSONObject jsonObject = JSONObject.parseObject(s);
                jedisSharkUtil.set("getUserBaseInfo"+id,s);
                return jsonObject;
            }
            else return null;
        }
        else {
            JSONObject jsonObject = JSONObject.parseObject(history);
            return jsonObject;
        }

    }

    /**
     * @Author mabo
     * @Description   完善学生基本信息
     */
    public int submitUserBaseInfo(Map map){
        String id = StringUtil.getMapValueString(map, "id");
        jedisSharkUtil.del("getUserBaseInfo"+id);
        jedisSharkUtil.del("getTeacherClassesInfo");
        String name = StringUtil.getMapValueString(map, "name");
        String birthday = StringUtil.getMapValueString(map, "birthday");
        String email = StringUtil.getMapValueString(map, "email");
        String phone = StringUtil.getMapValueString(map, "phone");
        String sex = StringUtil.getMapValueString(map, "sex");
        String idCardNumber = StringUtil.getMapValueString(map, "idCardNumber");
        String type = StringUtil.getMapValueString(map, "type");
        UserBaseInfo userBaseInfo=new UserBaseInfo(id,name,birthday,email,phone,sex,idCardNumber,type);
        if (studentDao.exitUserBaseInfo(id)){
            boolean b = studentDao.updateUserBaseInfo(userBaseInfo);
            if (b)
                return 1;
            return 0;
        }
        else {
            boolean b = studentDao.insertUserBaseInfo(userBaseInfo);
            if (b)
                return 1;
            return 0;
        }
    }

    /**
     * @Author mabo
     * @Description   学生上报内容
     */
    public int setReportInfo(Map map){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String uameId = StringUtil.getMapValueString(map, "uameId");
        String reportDate = StringUtil.getMapValueString(map, "reportDate");
        boolean reportToday = studentDao.isReportToday(reportDate, uameId);
        if (reportToday){
            //已经上报
            return 2;
        }else {
            try {
                Date date = simpleDateFormat.parse(reportDate);
                if (date.getTime()>new Date().getTime())
                    return 3;
                reportDate=simpleDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String realReportDate = sdf.format(new Date());
            String bodyTemperature = StringUtil.getMapValueString(map, "bodyTemperature");
            String isConfirm = StringUtil.getMapValueString(map, "isConfirm");
            isConfirm= StringUtil.getBoolean(isConfirm);
            String isArea = StringUtil.getMapValueString(map, "isArea");
            isArea= StringUtil.getBoolean(isArea);
            String address = StringUtil.getMapValueString(map, "address");
            ReportInfoEntity entity=new ReportInfoEntity(
                    reportDate,realReportDate,bodyTemperature
                    ,isConfirm,isArea,address,uameId);
            boolean b = studentDao.reportInfo(entity);
            if (b){
                //上报成功
                jedisSharkUtil.del("getStudentReportHistory"+uameId);
                return 1;
            }
            //上报失败
            return 0;
        }

    }

/**
 * @Author mabo
 * @Description   获取当前学生上报历史
 */
    public JSONObject getStudentReportHistory(Map map){
        String uname = StringUtil.getMapValueString(map, "uname");
        String history = jedisSharkUtil.get("getStudentReportHistory"+uname);
        if (history==null){
            Map<String, Object> studentReportHistory = studentDao.getStudentReportHistory(uname);
            String s = JSON.toJSONString(studentReportHistory);
            JSONObject jsonObject = JSONObject.parseObject(s);
            jedisSharkUtil.set("getStudentReportHistory"+uname,s);
            return jsonObject;
        }
        else {
            JSONObject jsonObject = JSONObject.parseObject(history);
            return jsonObject;
        }
    }

    /**
     * @Author mabo
     * @Description   删除一条学生上报信息
     */
    public JSONObject deleteStudentReport(Map map){
        JSONObject json=new JSONObject();
        String uameId = StringUtil.getMapValueString(map, "uameId");
        String reportDate = StringUtil.getMapValueString(map, "reportDate");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=simpleDateFormat.parse(reportDate);
            reportDate=simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean b = studentDao.deleteStudentReport(uameId, reportDate);
        if (b){
            try {
                jedisSharkUtil.del("getStudentReportHistory"+uameId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        json.put("message",b);
        return json;
    }

}

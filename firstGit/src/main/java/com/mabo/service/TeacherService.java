package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.TeacherDao;
import com.mabo.utils.JedisSharkUtil;
import com.mabo.utils.LogUtil;
import com.mabo.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TeacherService extends BaseService{
    private TeacherDao teacherDao;
    private LogUtil log;

    public void setLog(LogUtil log) {
        this.log = log;
    }

    public static   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    public void setJedisSharkUtil(JedisSharkUtil jedisSharkUtil) {
        this.jedisSharkUtil = jedisSharkUtil;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    /**
     * @Author mabo
     * @Description   获取老师学生今日上报信息
     */
    public JSONObject teacherStudentTodayReportInfo(Map<String, Object> map){
        String id = StringUtil.getMapValueString(map, "id");
        if (id.equals("")){
            JSONObject json=new JSONObject();
            json.put("message","false");
            return json;
        }
        String ss = jedisSharkUtil.get("teacherStudentTodayReportInfo" + id);
        if (ss==null){
            //获取到老师的学生全部信息
            List<Map<String, Object>> mapList = teacherDao.teacherStudentReportInfo(id);
            List<Map<String, Object>> yesList=new ArrayList<>();
            List<Map<String, Object>> noList=new ArrayList<>();
            //找出今天上报和没有上报的人
            for (int i=mapList.size()-1;i>=0;i--) {
                Map<String, Object> m=mapList.get(i);
                String s = StringUtil.getMapValueString(m, "reportDate");
                if (!s.equals("")){
                    try {
                        Date reportDate = sdf.parse(s);
                        Date today=new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(new Date()));
                        if (reportDate.getTime()==today.getTime()){
                            yesList.add(m);
                        }
                        else {
                            noList.add(m);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    noList.add(m);

                }
            }
            //获取老师的所有班级]
            List<Map<String, Object>> teacherAllClass = teacherDao.getTeacherAllClass(id);
            Map<String, Object> a = new HashMap<String, Object>();
            //查询各个班级上报情况
            for (Map<String, Object> oneClass: teacherAllClass) {
                Map<String, Object> add = new HashMap<String, Object>();
                String classId = StringUtil.getMapValueString(oneClass, "classId");
                int yesNumber=0;
                int noNumber=0;
                for (Map<String, Object> yes: yesList) {
                    String classIdYes = StringUtil.getMapValueString(yes, "classId");
                    if ((!classId.equals(""))&&classId.equals(classIdYes))
                        yesNumber++;
                }
                for (Map<String, Object> no: noList) {
                    String classIdNo = StringUtil.getMapValueString(no, "classId");
                    if ((!classId.equals(""))&&classId.equals(classIdNo))
                        noNumber++;
                }
                oneClass.put("yesNumber",yesNumber);
                oneClass.put("noNumber",noNumber);
            }
            //划分班级
            Map<String, Object> sendMap=new HashMap<>();
            sendMap.put("reportStudent",yesList);
            sendMap.put("noReportStudent",noList);
            sendMap.put("reportNumber",yesList.size());
            sendMap.put("noReportNumber",noList.size());
            a.put("classId", "全部");
            teacherAllClass.add(a);
            sendMap.put("teacherAllClass",teacherAllClass);
            JSONObject json = new JSONObject(sendMap);
            jedisSharkUtil.set("teacherStudentTodayReportInfo"+id,json.toJSONString());
            log.info(id+"老师的学生上报信息更新成功");
            return json;
        }
        else {
            JSONObject jsonObject = JSONObject.parseObject(ss);
            return jsonObject;
        }
    }
    /**
     * @Author mabo
     * @Description   获取所有教师分配班级
     */
    public JSONObject getTeacherClassesInfo(){
        String getTeacherClassesInfo = jedisSharkUtil.get("getTeacherClassesInfo");
        if (getTeacherClassesInfo!=null){
            JSONObject jsonObject = JSONObject.parseObject(getTeacherClassesInfo);
            return jsonObject;
        }
        else {
            List<Map<String, Object>> teacherClassesInfo =
                    teacherDao.getTeacherClassesInfo();
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("getTeacherClassesInfo",teacherClassesInfo);
            JSONObject json=new JSONObject(map);
            jedisSharkUtil.set("getTeacherClassesInfo",json.toJSONString());
            return json;
        }

    }
}

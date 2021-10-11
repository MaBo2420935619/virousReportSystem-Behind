package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.UserDao;
import com.mabo.utils.LogUtil;
import com.mabo.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserService extends BaseService{
    public static   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private UserDao userDao;
    private LogUtil log;

    public void setLog(LogUtil log) {
        this.log = log;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * @Author mabo
     * @Description   获取老师学生今日上报信息
     */

    public JSONObject todayReportInfo(Map<String, Object> map){
        String studentType="student";
        String teacherType="teacher";
        String ss = jedisSharkUtil.get("todayReportInfo");
        if (ss==null){
            //获取到全部信息
            Map<String, Object> map1 = todayReportInfoType(studentType);
            Map<String, Object> map2 = todayReportInfoType(teacherType);
            Map<String, Object> m=new HashMap<String, Object>();
            m.put(studentType,map1);
            m.put(teacherType,map2);
            JSONObject jsonObject=new JSONObject(m);
            jedisSharkUtil.set("todayReportInfo",jsonObject.toJSONString());
            return jsonObject;

        }
        else {
            JSONObject jsonObject = JSONObject.parseObject(ss);
            return jsonObject;
        }
    }
    public Map<String,Object> todayReportInfoType(String type){
        if (type.equals("student")){
            List<Map<String, Object>> mapList = userDao.todayReportInfoForAdminType(type);
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
            //划分班级
            Map<String, Object> sendMap=new HashMap<>();
            sendMap.put("report",yesList);
            sendMap.put("noReport",noList);
            sendMap.put("reportNumber",yesList.size());
            sendMap.put("noReportNumber",noList.size());
            if (type.equals("student")){
                //获取老师的所有班级]
                List<Map<String, Object>> allClasses = userDao.getAllClasses();
                //查询各个班级上报情况
                Map a=new HashMap();
                for (Map<String, Object> oneClass: allClasses) {
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
                a.put("classId", "全部");
                allClasses.add(a);
                sendMap.put("allClasses",allClasses);
            }
            return sendMap;
        }
        else if (type.equals("teacher")){
            List<Map<String, Object>> mapList = userDao.todayReportInfoForAdminType(type);
            List<Map<String, Object>> yesList=new ArrayList<>();
            List<Map<String, Object>> noList=new ArrayList<>();
            //找出今天上报和没有上报的人
            for (int i=mapList.size()-1;i>=0;i--) {
                Map<String, Object> m=mapList.get(i);
                String s = StringUtil.getMapValueString(m, "reportDate");
                String id = StringUtil.getMapValueString(m, "id");
                if (!s.equals("")){
                    try {
                        Date reportDate = sdf.parse(s);
                        Date today=new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(new Date()));
                        if (reportDate.getTime()==today.getTime()){
                            //已经上报的学生
                            yesList.add(m);
                        }
                        else {
                            if (noList.size()>0){
                                boolean flag=true;
                                for (int j=noList.size()-1;j>=0;j--) {
                                    Map<String, Object> l=noList.get(j);
                                    String id1 = StringUtil.getMapValueString(l, "id");
                                    String id2 = StringUtil.getMapValueString(m, "id");
                                    if (id1.equals(id2)){
                                        String reportDate3 = StringUtil.getMapValueString(l, "reportDate");
                                        Date reportDate1 = sdf.parse(reportDate3);
                                        if (reportDate1.getTime()<reportDate.getTime()){
                                            noList.remove(l);
                                            noList.add(m);
                                            flag=false;
                                        }
                                    }
                                }
                                if (flag)
                                    noList.add(m);
                            }
                            else  noList.add(m);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    noList.add(m);
                }
            }
            Map<String, Object> sendMap=new HashMap<>();
            sendMap.put("report",yesList);
            sendMap.put("noReport",noList);
            sendMap.put("reportNumber",yesList.size());
            sendMap.put("noReportNumber",noList.size());
            return sendMap;
            }
        return null;
        }

}

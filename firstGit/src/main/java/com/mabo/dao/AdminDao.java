package com.mabo.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.dao.DataAccessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminDao extends BaseDao{
    /**
     * @Author mabo
     * @Description   获取特定日期系统日志
     */
    public  JSONObject getSystemLog(String format){
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        Date startDate =null;
        try {
            startDate=sd.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        String endDate = sdf.format(calendar.getTime());
        String sql = propertyUtil.get("sql/systemLog.properties", "getSystemLog");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, startDate,endDate);
        JSONObject json=new JSONObject();
        json.put("getSystemLog",mapList);
        return json;
    }
    /**
     * @Author mabo
     * @Description   删除老师分配的班级
     */
    public boolean deleteTeacherClass(String id,String classId){
        String sql = propertyUtil.get("sql/admin.properties", "deleteTeacherClass");
        int update = 0;
        try {
            update = jdbcTemplate.update(sql, id, classId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            log.error(sql+"执行失败");
        }
        if (update>0){
            return true;
        }
        return  false;
    }

    /**
     * @Author mabo
     * @Description   给老师分配班级
     */
    public boolean setTeacherClassesInfo(String id,String classId){
        String sql = propertyUtil.get("sql/admin.properties", "setTeacherClassesInfo");
        int update = 0;
        try {
            update = jdbcTemplate.update(sql, classId, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            log.error(sql+"执行失败");
        }
        if (update>0){
            return true;
        }
        return  false;
    }
}

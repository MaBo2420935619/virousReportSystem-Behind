package com.mabo.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public class UserDao extends BaseDao{
    /**
     * @Author mabo
     * @Description   根据传入的是学生还是教师，返回其今日上报信息
     */
    public List<Map<String, Object>> todayReportInfoForAdminType(String type){
        if (type.equals("student")){
            String sql = propertyUtil.get("sql/user.properties", "todayReportInfoForAdminType");
            try {
                List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
                return mapList;
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("teacher")){
            String sql = propertyUtil.get("sql/user.properties", "todayReportInfoForAdminTypeTeacher");
            try {
                List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
                return mapList;
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @Author mabo
     * @Description   获取所有的班级
     */
    public List<Map<String, Object>>  getAllClasses() {
        String sql = propertyUtil.get("sql/login.properties", "getAllClasses");
        List<Map<String, Object>> maps = null;
        try {
            maps = jdbcTemplate.queryForList(sql);
            return maps;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Author mabo
     * @Description   获取所有用户的id
     */
    public List<Map<String, Object>> getAllUserId(){
        try {
            String sql = propertyUtil.get("sql/user.properties", "getAllUserId");
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
            return maps;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

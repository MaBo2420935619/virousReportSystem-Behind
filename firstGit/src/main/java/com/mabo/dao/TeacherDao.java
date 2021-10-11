package com.mabo.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public class TeacherDao extends BaseDao {
    /**
     * @Author mabo
     * @Description   获取教师的班级学生上报情况
     */
    public List<Map<String, Object>>  teacherStudentReportInfo(String id){
        String sql = propertyUtil.get("sql/teacher.properties", "teacherStudentReportInfo");
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, id);
            return mapList;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @Author mabo
     * @Description   获取当前老师的所有班级
     */
    public List<Map<String, Object>>  getTeacherAllClass(String id){
        String sql = propertyUtil.get("sql/teacher.properties", "getTeacherAllClass");
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, id);
            return mapList;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Author mabo
     * @Description   获取所有教师登录名称
     */
    public List<Map<String, Object>> getAllTeacherLoginName(){
        String sql = propertyUtil.get("sql/teacher.properties", "getAllTeacherLoginName");
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
            return mapList;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Author mabo
     * @Description   获取教师分配班级
     */
    public List<Map<String, Object>> getTeacherClassesInfo(){
        String sql = propertyUtil.get("sql/teacher.properties", "getTeacherClassesInfo");
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
            return mapList;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

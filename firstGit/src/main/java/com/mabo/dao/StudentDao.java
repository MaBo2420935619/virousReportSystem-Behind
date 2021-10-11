package com.mabo.dao;

import com.mabo.entity.ReportInfoEntity;
import com.mabo.entity.UserBaseInfo;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentDao extends BaseDao{
    /**
     * @Author mabo
     * @Description   获取用户基本信息
     */
    public  Map getUserBaseInfo(String id,String type){
        String sql = propertyUtil.get("sql/student.properties", "getUserBaseInfo");
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, id, type);
            Map<String, Object> map =null;
            if (mapList.size()>0)
            {
                map=mapList.get(0);
                return map;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author mabo
     * @Description   用户基础信息填写
     */
    public boolean insertUserBaseInfo(UserBaseInfo userBaseInfo){
        String sql = propertyUtil.get("sql/student.properties", "insertUserBaseInfo");
        try {
            int update = jdbcTemplate.update(sql,
                    userBaseInfo.getId(),
                    userBaseInfo.getName(),
                    userBaseInfo.getBirthday(),
                    userBaseInfo.getEmail(),
                    userBaseInfo.getPhone(),
                    userBaseInfo.getSex(),
                    userBaseInfo.getIdCardNumber(),
                    userBaseInfo.getType());
            if (update>0)
                return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @Author mabo
     * @Description   用户基础信息修改
     */
    public boolean updateUserBaseInfo(UserBaseInfo userBaseInfo){
        String sql = propertyUtil.get("sql/student.properties", "updateUserBaseInfo");
        try {
            int update = jdbcTemplate.update(sql,
                    userBaseInfo.getName(),
                    userBaseInfo.getBirthday(),
                    userBaseInfo.getEmail(),
                    userBaseInfo.getPhone(),
                    userBaseInfo.getSex(),
                    userBaseInfo.getIdCardNumber(),
                    userBaseInfo.getId(),
                    userBaseInfo.getType());
            if (update>0)
                return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * @Author mabo
     * @Description   查询是否存在用户基础信息
     */
    public boolean exitUserBaseInfo(String id){
        String sql = propertyUtil.get("sql/student.properties", "exitUserBaseInfo");
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, id);
            if (mapList.size()>0)
                return true;
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }




    /**
     * @Author mabo
     * @Description   学生上报信息写入数据库
     */

    public  boolean reportInfo(ReportInfoEntity infoEntity){
        String sql = propertyUtil.get("sql/student.properties", "reportInfo");
        try {
            int update = jdbcTemplate.update(sql,
                    infoEntity.getReportDate(),
                    infoEntity.getRealReportDate(),
                    infoEntity.getBodyTemperature(),
                    infoEntity.getIsConfirm(),
                    infoEntity.getIsArea(),
                    infoEntity.getAddress(),
                    infoEntity.getUameId());
            if (update>0){
                return  true;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @Author mabo
     * @Description   判断今天是否上报
     */
    public  boolean isReportToday(String date,String uname){
        String sql = propertyUtil.get("sql/student.properties", "isReportToday");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, date, uname);
        if (mapList.size()>0){
            return true;
        }
        else return false;
    }
    /**
     * @Author mabo
     * @Description   获取当前学生上报历史
     */
    public Map<String, Object> getStudentReportHistory(String uname){
        String sql = propertyUtil.get("sql/student.properties", "getStudentReportHistory");
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, uname);
        Map map=new HashMap();
        map.put("getStudentReportHistory",mapList);
        return map;
    }
    /**
     * @Author mabo
     * @Description   删除一条学生上报信息
     */
    public boolean deleteStudentReport(String name,String date){
        String sql = propertyUtil.get("sql/student.properties", "deleteStudentReport");
        int update = jdbcTemplate.update(sql, name, date);
        if (update>0)
            return true;
        return false;
    }


}

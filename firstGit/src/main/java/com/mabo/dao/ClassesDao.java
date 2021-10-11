package com.mabo.dao;

import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassesDao  extends BaseDao{

    public Map getAllClasses() {
        String sql = propertyUtil.get("sql/login.properties", "getAllClasses");
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Map map=new HashMap();
        map.put("getAllClasses",maps);
        return map;
    }

    /**
     * @Author mabo
     * @Description   注册学生的班级信息
     */
    public boolean registerStudentAndClass(String uname,String classId){
        String sql = propertyUtil.get("sql/login.properties", "registerStudentAndClass");
        int update = 0;
        try {
            update = jdbcTemplate.update(sql, classId, uname);
        } catch (DataAccessException e) {
            e.printStackTrace();
            log.error(sql+"执行失败");
        }
        if (update>0){
            log.info("学生对应班级信息添加成功");
            return true;
        }
        return  false;
    }
}

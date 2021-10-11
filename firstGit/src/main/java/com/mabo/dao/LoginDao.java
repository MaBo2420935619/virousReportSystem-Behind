package com.mabo.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public class LoginDao extends BaseDao{



    /**
     * @Author mabo
     * @Description   验证当前用户名是否存在
     */
    public boolean isHaveUname(String uname){
        String sql = propertyUtil.get("sql/login.properties", "isHaveUname");
        List<Map<String, Object>> maps = null;
        try {
            maps = jdbcTemplate.queryForList(sql,uname);
        } catch (DataAccessException e) {
            e.printStackTrace();
            log.error(sql+"执行错误");
            return false;
        }
        if (maps.size()>0)
        {
            return true;
        }
        else   return false;
    }



    /**
     * @Author mabo
     * @Description   登录验证
     */
    public boolean isLogin(String uname, String upwd,String type){
        String sql = propertyUtil.get("sql/login.properties", "login");
        List<Map<String, Object>> maps = null;
        try {
            maps = jdbcTemplate.queryForList(sql,uname,upwd,type);
        } catch (DataAccessException e) {
            e.printStackTrace();
            log.error(sql+"执行错误");
            return false;
        }
        //当前结果写入redis
        if (maps.size()>0)
        {
           return true;
        }
         else   return false;
    }



/**
 * @Author mabo
 * @Description   注册用户
 */
    public boolean registerUserDao(String uname, String upwd,String type){
        String sql = propertyUtil.get("sql/login.properties", "registerUser");
        int update = 0;
        try {
            update = jdbcTemplate.update(sql, uname, upwd, type);
            if (update>0){
                log.info("用户"+uname+"注册成功");
                return true;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

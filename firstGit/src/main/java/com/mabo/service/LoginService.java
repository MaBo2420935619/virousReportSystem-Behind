package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.ClassesDao;
import com.mabo.dao.LoginDao;
import com.mabo.utils.MD5Util;

public class LoginService extends BaseService{
    private LoginDao loginDao;
    private ClassesDao classesDao;

    public void setClassesDao(ClassesDao classesDao) {
        this.classesDao = classesDao;
    }
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    /**
     * @Author mabo
     * @Description   判断是否登录
     */
    public  Boolean isLogin(String uname, String upwd,String type){
        upwd= MD5Util.getKey(upwd);
        JSONObject json=new JSONObject();
        json.put("uname",uname);
        json.put("type",type);
        String s = json.toJSONString();
        String pwd = jedisSharkUtil.get(s);
        if (pwd!=null){
            if (pwd.equals(upwd))
                return true;
            else return false;
        }
        else if(loginDao.isLogin(uname,  upwd,type)){
            jedisSharkUtil.set(s,upwd);
            return true;
        }
        return false;

    }


    /**
     * @Author mabo
     * @Description   判断是否存在用户
     */
    public  int register(String uname, String upwd,String type,String classId){
        //成功
        upwd=MD5Util.getKey(upwd);
        if (!loginDao.isHaveUname(uname)){
            jedisSharkUtil.del("getTeacherClassesInfo");
            //注册人员成功
            loginDao.registerUserDao(uname, upwd, type);
            //如果是学生，注册班级信息
            if (type.equals("student")){
                boolean b = classesDao.registerStudentAndClass(uname, classId);
                if (!b){
                    //学生班级信息注册失败
                    return 2;
                }
                //正常退出
                return 1;
            }
            return  1;
        }
        //存在，注册失败
        return 0;
    }



}

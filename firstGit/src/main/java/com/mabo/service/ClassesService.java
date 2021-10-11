package com.mabo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.ClassesDao;

import java.util.Map;

public class ClassesService extends BaseService{
    private ClassesDao classesDao;

    public ClassesDao getClassesDao() {
        return classesDao;
    }

    public void setClassesDao(ClassesDao classesDao) {
        this.classesDao = classesDao;
    }

    public JSONObject getAllClasses(){
        String allClassesInfo = jedisSharkUtil.get("allClasses");
        if (allClassesInfo==null){
            Map allClasses = classesDao.getAllClasses();
            String s = JSON.toJSONString(allClasses);
            JSONObject jsonObject = JSONObject.parseObject(s);
            jedisSharkUtil.set("allClasses",s);
            return jsonObject;
        }
        else {
            JSONObject jsonObject = JSONObject.parseObject(allClassesInfo);
            return jsonObject;
        }

    }
}

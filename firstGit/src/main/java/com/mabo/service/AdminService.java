package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.dao.AdminDao;
import com.mabo.utils.StringUtil;

import java.util.Map;

public class AdminService extends BaseService{
    private AdminDao adminDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    /**
     * @Author mabo
     * @Description   获取特定日期系统日志
     */
    public JSONObject getSystemLog(Map map){
        String date = StringUtil.getMapValueString(map, "date");
        if (!date.equals("")){
            String s = jedisSharkUtil.get("getSystemLog" + date);
            if (s!=null) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                return jsonObject;
            }
            else {
                JSONObject systemLog = adminDao.getSystemLog(date);
                jedisSharkUtil.set("getSystemLog" + date,systemLog.toJSONString());
                return systemLog;
            }

        }
        JSONObject json=new JSONObject();
        json.put("message","false");
        return json;
    }
    /**
     * @Author mabo
     * @Description   删除老师分配的班级
     */
    public boolean deleteTeacherClass(Map map){
        jedisSharkUtil.del("getTeacherClassesInfo");
        String id = StringUtil.getMapValueString(map, "id");
        String classId = StringUtil.getMapValueString(map, "classId");
        if (!id.equals("")&&!classId.equals("")){
            return adminDao.deleteTeacherClass(id, classId);
        }
       return  false;
    }

    /**
     * @Author mabo
     * @Description   给老师分配班级
     */
    public boolean setTeacherClassesInfo(Map map){
        jedisSharkUtil.del("getTeacherClassesInfo");
        String id = StringUtil.getMapValueString(map, "id");
        String classId = StringUtil.getMapValueString(map, "classId");
        if (!id.equals("")&&!classId.equals("")){
            return adminDao.setTeacherClassesInfo(id, classId);
        }
        return  false;
    }
}

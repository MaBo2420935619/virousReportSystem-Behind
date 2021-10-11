package com.mabo.manager;

import com.mabo.dao.TeacherDao;
import com.mabo.utils.JedisSharkUtil;
import com.mabo.utils.StringUtil;

import java.util.List;
import java.util.Map;

public class TeacherManager extends BaseManager {
    private JedisSharkUtil jedisSharkUtil;
    private TeacherDao teacherDao;

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public void setJedisSharkUtil(JedisSharkUtil jedisSharkUtil) {
        this.jedisSharkUtil = jedisSharkUtil;
    }
    /**
     * @Author mabo
     * @Description  删除redis中所有教师的学生上报信息信息
     */
    public void updateAllTeacherStudentTodayReportInfo(){
        List<Map<String, Object>> allTeacherLoginName = teacherDao.getAllTeacherLoginName();
        for (Map map: allTeacherLoginName) {
            String uname = StringUtil.getMapValueString(map, "uname");
            if (!uname.equals("")){
                jedisSharkUtil.del("teacherStudentTodayReportInfo" + uname);
            }
        }
    }
}

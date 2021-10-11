package com.mabo.manager;

import com.mabo.dao.UserDao;
import com.mabo.utils.StringUtil;

import java.util.List;
import java.util.Map;

public class UserManager extends BaseManager{
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @Author mabo
     * @Description   定时清除用户baseInfo
     */
    public void updateUserBaseInfo(){
        try {
            List<Map<String, Object>> allUserId =
                    userDao.getAllUserId();
            for (Map<String, Object> map: allUserId) {
                String id = StringUtil.getMapValueString(map, "id");
                jedisSharkUtil.del("getUserBaseInfo"+id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.mabo.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mabo.entity.SystemLogEntity;
import org.springframework.dao.DataAccessException;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SystemLogManager extends BaseManager{
   private  static   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
/**
 * @Author mabo
 * @Description   删除缓存中3天前的系统日志
 */
public void deleteSystemLog(){
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    Date date =new Date();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(calendar.DATE,3);//把日期往后增加一天.整数往后推,负数往前移动
    String endDate = sdf.format(calendar.getTime());
    //进行删除业务
    jedisSharkUtil.del("getSystemLog" + endDate);
    try {
        int update = jdbcTemplate.update
                ("DELETE FROM systemlog WHERE date<?",endDate);
        if (update>0)
            log.info("历史日志清理成功");
    } catch (DataAccessException e) {
        e.printStackTrace();
        log.info("历史日志清理失败");
    }
}

/**
 * @Author mabo
 * @Description   向缓存写入系统日志
 */
    public void setSystemLog(Socket socket, String requestType, String issuccess, long time){
        SystemLogEntity systemLogEntity=new SystemLogEntity(socket.getInetAddress().getHostName(),socket.getPort(),requestType,issuccess,time);
        String systemLog = jedisSharkUtil.get("systemLog");
        if (systemLog!=null){
            JSONArray jsonArray = JSONArray.parseArray(systemLog);
            jsonArray.add(systemLogEntity);
            jedisSharkUtil.set("systemLog",jsonArray.toString());
        }
        else if (systemLog==null){
            JSONArray jsonArray=new JSONArray();
            jsonArray.add(systemLogEntity);
            jedisSharkUtil.set("systemLog",jsonArray.toString());
        }
    }
    /**
     * @Author mabo
     * @Description   向数据库定时写入系统日志
     */
    public void setDateBaseSystemLog(){
        String systemLog = jedisSharkUtil.get("systemLog");
        if (systemLog!=null){
            List<SystemLogEntity> systemLogEntities = JSONObject.parseArray(systemLog, SystemLogEntity.class);
            try {
                for (SystemLogEntity systemLogEntity: systemLogEntities) {
                    String sql = propertyUtil.get("sql/systemLog.properties", "setSystemLog");
                    String date=sdf.format(new Date());
                    jdbcTemplate.update(sql, systemLogEntity.getIp(),systemLogEntity.getPort(), systemLogEntity.getRequestype(),
                            systemLogEntity.getIssuccess(), systemLogEntity.getTime(),date);
                }
                jedisSharkUtil.del("systemLog");
            } catch (DataAccessException e) {
                e.printStackTrace();
                log.error("系统日志写入失败");
            }
        }

    }

}

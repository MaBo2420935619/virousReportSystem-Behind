package com.mabo.utils;
import redis.clients.jedis.*;

/**
 * @Author mabo
 * @Description   用于连接redis的工具类    *****弃用
 */

public class JedisUtil {
    private  LogUtil log;

    public void setLog(LogUtil log) {
        this.log = log;
    }

    /**
     * @Author mabo
     * @Description   获取jedis连接对象
     */

    public  Jedis getJedis(){
        log.info("jedis连接---------开始");
        PropertyUtil properties=new PropertyUtil();
        String jedisIp = properties.get("config.properties", "jedisIp");
        int jedisPort = Integer.parseInt(properties.get("config.properties", "jedisPort")) ;
        int jedisMaxTotal = Integer.parseInt(properties.get("config.properties", "jedisMaxTotal"));
        //连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //设置最多连接数为200个
        poolConfig.setMaxTotal(jedisMaxTotal);
        //创建连接池
        JedisPool pool = null;
        try {
            pool = new JedisPool(poolConfig, jedisIp, jedisPort);
            //获取连接对象jedis
            Jedis resource = pool.getResource();
            if (resource==null){
                log.info("jedisIp:"+jedisIp+"端口"+jedisPort+"连接失败");
            }
            else log.info("jedisIp:"+jedisIp+"端口"+jedisPort+"连接成功");
            log.info("jedis连接---------结束");
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("jedisIp:"+jedisIp+"端口"+jedisPort+"连接失败");
            log.info("jedis连接---------结束");
        }
        return null;
    }
}

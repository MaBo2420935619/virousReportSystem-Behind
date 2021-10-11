package com.mabo.dao;

import com.mabo.utils.JedisSharkUtil;
import com.mabo.utils.LogUtil;
import com.mabo.utils.PropertyUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
    public JdbcTemplate jdbcTemplate;
    public LogUtil log;
    public PropertyUtil propertyUtil;
    public JedisSharkUtil jedisSharkUtil;

    public void setLog(LogUtil log) {
        this.log = log;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setPropertyUtil(PropertyUtil propertyUtil) {
        this.propertyUtil = propertyUtil;
    }

    public void setJedisSharkUtil(JedisSharkUtil jedisSharkUtil) {
        this.jedisSharkUtil = jedisSharkUtil;
    }

}

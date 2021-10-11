package com.mabo.manager;

import com.mabo.utils.JedisSharkUtil;
import com.mabo.utils.LogUtil;
import com.mabo.utils.PropertyUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseManager {
    public JedisSharkUtil jedisSharkUtil;
    public JdbcTemplate jdbcTemplate;
    public PropertyUtil propertyUtil;
    public LogUtil log;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJedisSharkUtil(JedisSharkUtil jedisSharkUtil) {
        this.jedisSharkUtil = jedisSharkUtil;
    }

    public void setPropertyUtil(PropertyUtil propertyUtil) {
        this.propertyUtil = propertyUtil;
    }

    public void setLog(LogUtil log) {
        this.log = log;
    }
}

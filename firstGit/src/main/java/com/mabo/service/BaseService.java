package com.mabo.service;
import com.mabo.utils.JedisSharkUtil;

public class BaseService {
    public JedisSharkUtil jedisSharkUtil;

    public void setJedisSharkUtil(JedisSharkUtil jedisSharkUtil) {
        this.jedisSharkUtil = jedisSharkUtil;
    }
}

package com.mabo.manager;

public class AdminManager extends BaseManager{

    public void updateTodayReportInfo(){
        jedisSharkUtil.del("todayReportInfo");
    }
}

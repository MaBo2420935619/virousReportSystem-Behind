package com.mabo.utils;
public class TimeUtil{
   /**
    * @Author mabo
    * @Description   当前毫秒数
    */
    public static long startTime(){
        return System.currentTimeMillis();
    }

 /**
  * @Author mabo
  * @Description  时间差
  */

    public static long diffMs( long startMillis){
        return(System.currentTimeMillis()-startMillis);
    }


}

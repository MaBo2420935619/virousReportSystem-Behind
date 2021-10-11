package com.mabo.utils;

import java.util.Map;

public class StringUtil {
    public static String getMapValueString(Map map, String key){
        if (map.get(key)!=null){
            return map.get(key).toString();
        }
        else return "";
    }
    public static String getBoolean(String booleanString){
        if (booleanString.equals("false")){
            return "否";
        }
        else if (booleanString.equals("true")){
            return "是";
        }
        else return "";
    }

    public static String doAppend(String string1,String string2) {
        StringBuilder sb = new StringBuilder();
        sb.append(string1);
        sb.append(string2);
        return sb.toString();
    }

}

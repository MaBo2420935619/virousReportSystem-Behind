package com.mabo.utils;

import java.util.UUID;

/**
 * 生成唯一的UUID
 */
public class UUIDUtils {

    /**
     * 得到32位的uuid
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }


    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };

    /**
     * 生成8位UUId
     *
     * @return
     */
    public static  String generateUuid8() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
//    public static void main(String[] args) {
//        Map<String,String> map=new HashMap<>();
//        for (int i=0;i<999999;i++){
//            String fileName = generateUuid8();
//            String s = map.get(fileName);
//            if (s!=null){
//                System.out.println(fileName+"已经存在");
//            }
//            else{
//                map.put(fileName,fileName);
////                System.out.println(fileName);
//            }
//        }
//    }
}



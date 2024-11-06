package com.sxlinks.modules.system.util;


/**
 * @Description:
 * @author:
 * @date: 2022-06-05
 */
public class ArrayUtil {

    /**
     * @param arr
     * @return
     */
    public static String arrayToString(String[] arr) {
        if (arr != null){
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                buffer.append(arr[i]).append(",");
            }
            return buffer.substring(0,buffer.length() -1 );
        }
        return "";
    }
}

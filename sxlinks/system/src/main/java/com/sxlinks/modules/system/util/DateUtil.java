package com.sxlinks.modules.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    private static String defaultYmdHmsPattern = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat getYyyyMMdd(){
        if (yyyyMMdd != null){
            return yyyyMMdd;
        } else {
            SimpleDateFormat yyyyMMdd1 = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd = yyyyMMdd1;
            return yyyyMMdd1;
        }
    }

    /**字符串yyyy-MM-dd HH:mm:ss转日期
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parseHmsTime(String dateStr) {
        return parse(dateStr, defaultYmdHmsPattern);
    }

    /**字符串转日期
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        try {
            Date d = sdf.parse(dateStr);
            return d;
        } catch (ParseException e) {
            System.out.println("日期转换错误: " + e.getMessage());
            return null;
        }
    }
}

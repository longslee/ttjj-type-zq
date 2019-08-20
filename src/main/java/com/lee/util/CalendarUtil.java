package com.lee.util;

import java.text.SimpleDateFormat;

/**
 * Created by longslee on 2019/8/20.
 */
public class CalendarUtil {

    private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat yyyymmddhhmiss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取今天的年月日
     * @return
     */
    public static String getNowYYYYMMDD(){
        long now = getNowMils();
        return getFormatString(now,yyyymmdd);
    }

    /**
     * 从日期格式化为字符串
     * @param time 毫秒
     * @param formater 楼上的 SimpleDateFormat
     * @return 字符串形式
     */
    private static String getFormatString(long time,SimpleDateFormat formater){
        return formater.format(time);
    }

    /**
     * 获取当前毫秒
     * @return
     */
    private static long getNowMils(){
        return System.currentTimeMillis();
    }
}

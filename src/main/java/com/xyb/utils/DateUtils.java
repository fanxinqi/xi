package com.xyb.utils;

import org.joda.time.DateTime;

public class DateUtils {
    public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";

    /**
     * 将毫秒时间转换为yyyy-MM-dd格式的时间
     * @author yangwenkui
     * @time 2017年10月6日 下午5:56:40
     * @param time 毫秒数
     * @return
     */
    public static String longToString(long time) {
        return longToString(time, DATE_FORMAT_DEFAULT);
    }

    /**
     * 将毫秒时间转换为指定格式的时间
     * @author yangwenkui
     * @time 2017年10月6日 下午5:56:40
     * @param time 毫秒数
     * @param format 日期格式
     * @return
     */
    public static String longToString(long time, String format) {
        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_DEFAULT;
        }
        DateTime dTime = new DateTime(time);
        return dTime.toString(format);
    }
}

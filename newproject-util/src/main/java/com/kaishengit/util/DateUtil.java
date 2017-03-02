package com.kaishengit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 方法【date2String】：是将日期格式转化成String类型
 * 方法【string2Date】：是将String类型转化成日期格式
 * 方法【getCurrentTime】：获取当前时间
 * 方法【getWantDate】：获取想要的时间格式
 * 方法【getAfterTime】：获取该时间的几分钟之后的时间
 * 方法【getBeforeTime】：获取该时间的几分钟之前的时间
 */
public class DateUtil {

    public static final String PATTERN_STANDARD08W = "yyyyMMdd";
    public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
    public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
    public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
    public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
    public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";

    private static String getString(int len) {
        String pattern;
        switch (len) {
            case 8:
                pattern = PATTERN_STANDARD08W;
                break;
            case 10:
                pattern = PATTERN_STANDARD10H;
                break;
            case 12:
                pattern = PATTERN_STANDARD12W;
                break;
            case 14:
                pattern = PATTERN_STANDARD14W;
                break;
            case 16:
                pattern = PATTERN_STANDARD16H;
                break;
            case 17:
                pattern = PATTERN_STANDARD17W;
                break;
            case 19:
                pattern = PATTERN_STANDARD19H;
                break;
            default:
                pattern = PATTERN_STANDARD14W;
                break;
        }
        return pattern;
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @Title: date2String
     * @Description: 日期格式的时间转化成字符串格式的时间
     * @author YFB
     */
    public static String date2String(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals("")) ? PATTERN_STANDARD19H : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * @param strDate
     * @param pattern
     * @return
     * @Title: string2Date
     * @Description: 字符串格式的时间转化成日期格式的时间
     * @author YFB
     */
    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("strDate is null");
        }
        pattern = (pattern == null || pattern.equals("")) ? PATTERN_STANDARD19H : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * @param format 格式 17位(yyyyMMddHHmmssSSS) (14位:yyyyMMddHHmmss) (12位:yyyyMMddHHmm) (8位:yyyyMMdd)
     * @return
     * @Title: getCurrentTime
     * @Description: 取得当前系统时间
     * @author YFB
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        String date = formatDate.format(new Date());
        return date;
    }

    /**
     * @param dateStr
     * @param wantFormat
     * @return
     * @Title: getWantDate
     * @Description: 获取想要的时间格式
     * @author YFB
     */
    public static String getWantDate(String dateStr, String wantFormat) {
        if (!"".equals(dateStr) && dateStr != null) {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 10:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD10H : PATTERN_STANDARD10X;
                    break;
                case 16:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD16H : PATTERN_STANDARD16X;
                    break;
                case 19:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD19H : PATTERN_STANDARD19X;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(wantFormat);
            try {
                SimpleDateFormat sdfStr = new SimpleDateFormat(pattern);
                Date date = sdfStr.parse(dateStr);
                dateStr = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateStr;
    }

    /**
     * @param dateStr
     * @param minute
     * @return
     * @Title: getAfterTime
     * @Description: 获取该时间的几分钟之后的时间
     * @author YFB
     */
    public static String getAfterTime(String dateStr, int minute) {
        String returnStr = "";
        try {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            pattern = getString(len);
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() + (60000 * minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }


    /**
     * @param dateStr
     * @param minute
     * @return
     * @Title: getBeforeTime
     * @Description: 获取该时间的几分钟之前的时间
     * @author YFB
     */
    public static String getBeforeTime(String dateStr, int minute) {
        String returnStr = "";
        try {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            pattern = getString(len);
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() - (60000 * minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    public static Integer getDiff(String starDay, String endDay) {
        Date s = string2Date(starDay, PATTERN_STANDARD10H);
        Date e = string2Date(endDay, PATTERN_STANDARD10H);
        Integer day = Math.toIntExact((e.getTime() - s.getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }
}

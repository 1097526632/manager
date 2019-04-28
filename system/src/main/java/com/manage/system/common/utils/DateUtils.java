package com.manage.system.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {


    /**
     * 当天 昨天 前天 最近七天 本周 本月
     */
    public enum DateUtilsEnum {
        day, yesterday, beyesterday, sevenday, week, month
    }

    public static String START_DATE_KEY = "startTime";
    public static String END_DATE_KEY = "endTime";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 获取两个时间相差的秒数
     * @param afterDate
     * @param beforeDate
     * @return
     */
    public static long diffSecond(Date afterDate, Date beforeDate) {
        return (afterDate.getTime()-beforeDate.getTime())/1000;
    }

    /**
     * 获取时间相差的秒数
     * @param afterDate
     * @param beforeDate
     * @return
     */
    public static double diffSecondDouble(Date afterDate, Date beforeDate) {
        return (afterDate.getTime()-beforeDate.getTime())/1000.0;
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取两个日期之间的日期
     * @param start
     * @param end
     * @return
     */
    public static List<String> getBetweenDates(Date start, Date end) {
        List<String> result = Lists.newArrayList();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        // 日期加1(包含结束)
        tempEnd.add(Calendar.DATE, +1);
        while (tempStart.before(tempEnd)) {
            result.add(DateUtils.formatDate(tempStart.getTime(),"yyyy-MM-dd"));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 获取两个日期之间的所有日期，（格式 yyyy-MM-dd）
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenDates(String start, String end) throws ParseException {
        start=StringUtils.substring(start,0,10);
        end=StringUtils.substring(end,0,10);
        Date startTime = DateUtils.parseDate( start,"yyyy-MM-dd");
        Date endTime = DateUtils.parseDate(  end,"yyyy-MM-dd");
        return DateUtils.getBetweenDates(startTime,endTime);
    }

    /**
     * 获取两个日期之间的所有月份，（格式 yyyy-MM-dd）
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenMonths(String start, String end) throws ParseException {
        start=StringUtils.substring(start,0,7);
        end=StringUtils.substring(end,0,7);
        Date startTime = DateUtils.parseDate( start,"yyyy-MM");
        Date endTime = DateUtils.parseDate(  end,"yyyy-MM");
        return DateUtils.getBetweenMonths(startTime,endTime);
    }
    /**
     * 获取两个日期之间的所有月份，（格式 yyyy-MM-dd）
     * @param start
     * @param end
     * @return
     */
    public static List<String> getBetweenMonths(Date start, Date end) {
        List<String> result = Lists.newArrayList();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.set(tempStart.get(Calendar.YEAR), tempStart.get(Calendar.MONTH), 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.set(tempEnd.get(Calendar.YEAR), tempEnd.get(Calendar.MONTH), 2);
        while (tempStart.before(tempEnd)) {
            result.add(DateUtils.formatDate(tempStart.getTime(),"yyyy-MM"));
            tempStart.add(Calendar.MONTH, 1);
        }
        return result;
    }
    /**
     * 获取显示日期中的开始时间与结束时间
     *
     * @param viewType
     * @param showDate
     * @return
     */
    public static Map<String, Date> getCalendarViewFormat(String viewType, Date showDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(showDate);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Map<String, Date> result = Maps.newHashMap();
        //今天
        if (DateUtilsEnum.day.name().equals(viewType)) {
            result.put(START_DATE_KEY, cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
            result.put(END_DATE_KEY, cal.getTime());
        } else if (DateUtilsEnum.yesterday.name().equals(viewType)) {
            //昨天
            cal.add(Calendar.DAY_OF_MONTH, -1);
            result.put(START_DATE_KEY, cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
            result.put(END_DATE_KEY, cal.getTime());
        } else if (DateUtilsEnum.beyesterday.name().equals(viewType)) {
            //前天
            cal.add(Calendar.DAY_OF_MONTH, -2);
            result.put(START_DATE_KEY, cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
            result.put(END_DATE_KEY, cal.getTime());
        } else if (DateUtilsEnum.sevenday.name().equals(viewType)) {
            //最近7天
            cal.add(Calendar.DAY_OF_MONTH, -6);
            result.put(START_DATE_KEY, cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 7);
            result.put(END_DATE_KEY, cal.getTime());
        } else if (DateUtilsEnum.week.name().equals(viewType)) {
            //本周
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
            if (weekDay == 1) {
                cal.add(Calendar.DAY_OF_MONTH, -6);
                result.put(START_DATE_KEY, cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 7);
                result.put(END_DATE_KEY, cal.getTime());
            } else {
                cal.add(Calendar.DAY_OF_MONTH, -1 * weekDay + 2);
                result.put(START_DATE_KEY, cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 7);
                result.put(END_DATE_KEY, cal.getTime());
            }

        } else if (DateUtilsEnum.month.name().equals(viewType)) {
            //本月
            int monthDay = cal.get(Calendar.DAY_OF_MONTH);
            cal.add(Calendar.DAY_OF_MONTH, -1 * monthDay + 1);
            result.put(START_DATE_KEY, cal.getTime());
            cal.add(Calendar.MONTH, 1);
            result.put(END_DATE_KEY, cal.getTime());
        }

        return result;
    }

    /**
     * 获取当前日期中的范围类型的开始时间与结束时间
     *
     * @param viewType 枚举DateUtilsEnum
     * @return
     */
    public static Map<String, String> getCalendarViewFormat(String viewType) {
        Map<String, Date> date = DateUtils.getCalendarViewFormat(viewType, new Date());
        Map<String, String> result = Maps.newHashMap();
        Date startTime = (Date) MapUtils.getObject(date, START_DATE_KEY);
        result.put(START_DATE_KEY, DateUtils.formatDateTime(startTime));

        Date endTime = (Date) MapUtils.getObject(date, END_DATE_KEY);
        result.put(END_DATE_KEY, DateUtils.formatDateTime(endTime));
        return result;
    }

    /**
     * 获取当前时间所有范围类型的开始时间与结束时间
     * @return
     */
    public static String getAllTypeFormat() {
        List<Map<String, String>> list = Lists.newArrayList();

        for (DateUtilsEnum e : DateUtilsEnum.values()) {
            Map<String, String> map = DateUtils.getCalendarViewFormat(e.toString());
            map.put("type",e.toString());
            list.add(map);
        }
        return JsonUtils.toJson(list);
    }

    public static String formateQueryDate(String date) {
        if(StringUtils.isNotBlank(date)&&date.indexOf("T")>0) {
            return date.replace("T"," ").replace("Z","");
        }
        return date;
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
      //  System.out.println(DateUtils.getAllTypeFormat());
        Date date = parseDate("2018-11-19");
        List<String> list  =DateUtils.getBetweenMonths("2018-09-09 12:23:23","2019-10-09 12:23:23");
        System.out.println(list);
    }
}

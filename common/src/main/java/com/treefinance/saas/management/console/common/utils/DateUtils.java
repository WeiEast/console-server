package com.treefinance.saas.management.console.common.utils;

import com.alibaba.fastjson.JSON;
import com.datatrees.crawler.core.processor.format.unit.TimeUnit;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 时间工具类
 * Created by haojiahong on 2017/7/6.
 */
public class DateUtils {

    protected static final Logger logger = LoggerFactory.getLogger(DateUtils.class);


    /**
     * 获取dataTime在间隔时间内的开始时间,如dataTime=19:41,intervalMinutes=5,则19:40
     *
     * @param dataTime
     * @return
     */
    public static Date getIntervalDateTime(Date dataTime, Integer intervalMinutes) {
        Date intervalTime = org.apache.commons.lang.time.DateUtils.truncate(dataTime, Calendar.MINUTE);
        Long currentMinute = org.apache.commons.lang.time.DateUtils.getFragmentInMinutes(intervalTime, Calendar.HOUR_OF_DAY);
        if (currentMinute % intervalMinutes == 0) {
            return intervalTime;
        }
        intervalTime = org.apache.commons.lang.time.DateUtils.addMinutes(intervalTime, (-currentMinute.intValue() % intervalMinutes));
        return intervalTime;
    }


    public static List<Date> getIntervalDateRegion(Date startTime, Date endTime, Integer intervalMinutes) {
        Date intervalStartTime = org.apache.commons.lang.time.DateUtils.truncate(startTime, Calendar.MINUTE);
        Long currentStartMinute = org.apache.commons.lang.time.DateUtils.getFragmentInMinutes(intervalStartTime, Calendar.HOUR_OF_DAY);
        if (currentStartMinute % intervalMinutes != 0) {
            intervalStartTime = org.apache.commons.lang.time.DateUtils.addMinutes(intervalStartTime, (-currentStartMinute.intValue() % intervalMinutes));
        }

        Date intervalEndTime = org.apache.commons.lang.time.DateUtils.truncate(endTime, Calendar.MINUTE);
        Long currentEndMinute = org.apache.commons.lang.time.DateUtils.getFragmentInMinutes(intervalEndTime, Calendar.HOUR_OF_DAY);
        if (currentEndMinute % intervalMinutes != 0) {
            intervalEndTime = org.apache.commons.lang.time.DateUtils.addMinutes(intervalEndTime, (-currentEndMinute.intValue() % intervalMinutes));
        }
        List<Date> list = Lists.newArrayList();


        for (; intervalStartTime.compareTo(intervalEndTime) < 0; intervalStartTime = org.apache.commons.lang3.time.DateUtils.addMinutes(intervalStartTime, intervalMinutes)) {
            list.add(intervalStartTime);
        }
        return list;
    }


    public static List<String> getIntervalDateStrRegion(Date startTime, Date endTime, Integer intervalMinutes) {
        Date intervalStartTime = org.apache.commons.lang.time.DateUtils.truncate(startTime, Calendar.MINUTE);
        Long currentStartMinute = org.apache.commons.lang.time.DateUtils.getFragmentInMinutes(intervalStartTime, Calendar.HOUR_OF_DAY);
        if (currentStartMinute % intervalMinutes != 0) {
            intervalStartTime = org.apache.commons.lang.time.DateUtils.addMinutes(intervalStartTime, (-currentStartMinute.intValue() % intervalMinutes));
        }

        Date intervalEndTime = org.apache.commons.lang.time.DateUtils.truncate(endTime, Calendar.MINUTE);
        Long currentEndMinute = org.apache.commons.lang.time.DateUtils.getFragmentInMinutes(intervalEndTime, Calendar.HOUR_OF_DAY);
        if (currentEndMinute % intervalMinutes != 0) {
            intervalEndTime = org.apache.commons.lang.time.DateUtils.addMinutes(intervalEndTime, (-currentEndMinute.intValue() % intervalMinutes));
        }
        List<String> list = Lists.newArrayList();
        for (; intervalStartTime.compareTo(intervalEndTime) < 0; intervalStartTime = org.apache.commons.lang3.time.DateUtils.addMinutes(intervalStartTime, intervalMinutes)) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateUtils.date2SimpleHm(intervalStartTime));
            Date mediTime = org.apache.commons.lang3.time.DateUtils.addMinutes(intervalStartTime, intervalMinutes);
            sb.append("-").append(DateUtils.date2SimpleHm(mediTime));
            list.add(sb.toString());
        }
        return list;
    }


    public static String date2Ymd(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String date2YmdPoint(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return simpleDateFormat.format(date);
    }

    public static String date2YmdChinese(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日");
        return simpleDateFormat.format(date);
    }

    public static String date2Md(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String date2Hms(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleHms(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    public static String date2Hm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String date2HmChinese(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日 HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleHm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleYmd(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String date2SimpleYm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(date);
    }

    public static Date string2Date(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date ymdString2Date(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date oneMinuteLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }

    public static boolean between(Date start, Date end) {
        if (start.after(end)) return false;
        Date today = new Date();
        return start.before(today) && end.after(today);
    }

    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return (day2 - day1);
    }

    public static Date strToDate(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (Exception e) {
            logger.error("字符串转化时间出错:", e);
        }
        return date;
    }


    //获取今天凌晨的时间戳
    public static Integer getTodayBeginTimeStamp() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //获取明天凌晨的时间戳
    public static Integer getTomorrowBeginTimeStamp() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE) + 1;
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static Integer getTomorrowBeginTimeStamp(Date date) {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static Date getTodayBeginDate() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return cal.getTime();
    }

    public static Date getTodayBeginDate(Date date) {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return cal.getTime();
    }

    public static Date getTodayEndDate(Date date) {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE);
        cal.set(y, m, d, 23, 59, 59);//时、分、秒，设置成0，获取凌晨的时间
        return cal.getTime();
    }

    public static Date getTomorrowBeginDate() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE) + 1;
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return cal.getTime();
    }

    public static Date getTomorrowBeginDate(Date date) {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DATE) + 1;
        cal.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        return cal.getTime();
    }


    public static Integer getTimeStampByStr(String time) {
        return getTimeStampByStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static Integer getTimeStamp(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (int) (c.getTimeInMillis() / 1000);
    }

    public static Integer getTimeStampByStr(String time, String format) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(format).parse(time));
        } catch (Exception e) {
            logger.error("转化日期出错", e);
        }
        return (int) (c.getTimeInMillis() / 1000);
    }

    public static Integer now() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获得当前日期所在月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return calendar.getTime();

    }

    /**
     * 获取当前日期所在月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        return calendar.getTime();
    }


    /**
     * 获得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 获取当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }


    /**
     * 日期转时间戳
     *
     * @param date
     * @return
     */
    public static Integer date2TimeStamp(Date date) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(date);
        } catch (Exception e) {
            logger.error("转化日期出错", e);
        }
        return (int) (c.getTimeInMillis() / 1000);
    }

    public static Long date2LongTimeStamp(Date date) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(date);
        } catch (Exception e) {
            logger.error("转化日期出错", e);
        }
        return c.getTimeInMillis() / 1000;
    }


    /**
     * 获取当前日期在当年中的周数
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }


    public static String getWeekStrOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year + "-" + DateUtils.getWeekOfYear(date) + "周";
    }


    /**
     * 获取一段时间区间内的所有日期,精确到时分秒
     *
     * @param start
     * @param end
     * @return
     */
    public static List<Date> getDateLists(Date start, Date end) {
        List<Date> ret = Lists.newArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Date tmpDate = calendar.getTime();
        while (tmpDate.before(end)) {
            ret.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            tmpDate = calendar.getTime();
        }
        return ret;
    }

    public static List<Date> getDayDateLists(Date start, Date end) {
        List<Date> ret = Lists.newArrayList();
        int y, m, d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DATE) + 1;
        calendar.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        Date tmpDate = calendar.getTime();
        while (tmpDate.before(end)) {
            ret.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            tmpDate = calendar.getTime();
        }
        return ret;
    }

    /**
     * 取时间区间内的天(左开右闭)
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> getDayStrDateLists(Date start, Date end) {
        List<String> ret = Lists.newArrayList();
        int y, m, d;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DATE);
        calendar.set(y, m, d, 0, 0, 0);//时、分、秒，设置成0，获取凌晨的时间
        Date tmpDate = calendar.getTime();
        while (tmpDate.before(end) || tmpDate.equals(end)) {
            ret.add(DateUtils.date2Ymd(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            tmpDate = calendar.getTime();
        }
        return ret;
    }


    public static Date getSpecificDayDate(Date source, int interval) {
        return getSpecificDayDate(source,interval,TimeUnit.DAY);
    }
    /**
     * 获取某一天加上或者减去某一间隔时间后的日期
     * @param source 原目标日期
     * @param interval 日期间隔
     * @param timeUnit 时间单位
     */
    public static Date getSpecificDayDate(Date source, int interval, TimeUnit timeUnit) {

        long ms = timeUnit.toMillis(interval);

        Long time = source.getTime();

        return new Date(time+ms);
    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(DateUtils.getWeekOfYear(new Date()));
//        System.out.println(DateUtils.getFirstDayOfWeek(new Date()));
//        System.out.println(DateUtils.getLastDayOfWeek(new Date()));
//        System.out.println(DateUtils.getFirstDayOfMonth(new Date()));
//        System.out.println(DateUtils.getLastDayOfMonth(new Date()));
//        System.out.println(DateUtils.getWeekStrOfYear(new Date()));
//        System.out.println(DateUtils.date2SimpleYm(new Date()));
//        System.out.println(DateUtils.getDayStrDateLists(DateUtils.ymdString2Date("2017-07-01"), DateUtils.ymdString2Date("2017-07-08")));
//        LocalDate localDate = LocalDate.now();
//        System.out.println("localDate:" + localDate);
//
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//        System.out.println("tomorrow:" + tomorrow);
//
//        LocalDate yesterday = LocalDate.now().minusDays(1);
//        System.out.println("yesterday:" + yesterday);
//
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println("默认格式化: " + now);
//        System.out.println("自定义格式化: " + now.format(dateTimeFormatter));
//        LocalDateTime localDateTime = LocalDateTime.parse("2017-07-20 15:27:44", dateTimeFormatter);
//        System.out.println("字符串转LocalDateTime: " + localDateTime);
//        Date date = org.apache.commons.lang3.time.DateUtils.parseDate("2017-11-24 19:01:00", "yyyy-MM-dd HH:mm:ss");
        //8,16,24,32,40,48,56
//        System.out.println(DateUtils.date2Hms(DateUtils.getIntervalDateTime(date, 8)));

//        System.out.println(DateUtils.getTodayEndDate(new Date()));
          System.err.println(date2Ymd(getSpecificDayDate(new Date(),-3,TimeUnit.MONTH)));


        List<String> list = DateUtils.getIntervalDateStrRegion(new Date(), org.apache.commons.lang3.time.DateUtils.addMinutes(new Date(), 100), 5);
        System.out.println(JSON.toJSONString(list, true));




    }


}

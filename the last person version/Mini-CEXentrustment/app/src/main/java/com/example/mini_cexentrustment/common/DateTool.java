package com.example.mini_cexentrustment.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 信威 on 2017/10/23.
 */
public class DateTool {
    /**
     * 取得當下日期時間
     * @param
     */
    public final static String getDateTimeByNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return  dateFormat.format(date);
    }
    /**
     * 取得當下日期時間
     * @param
     */
    public final static String getDateTimeByNow2(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        return  dateFormat.format(date);
    }
    /**
     * 取得當下日期時間
     * @param
     */
    public final static String getDateByNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return  dateFormat.format(date);
    }
    /**
     * 取得當下時間 :分
     * @param
     */
    public final static String getDate(String datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat1.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return  dateFormat.format(date);
    }
    /**
     * 取得當下時間 :分
     * @param
     */
    public final static String getTimeMin(String datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat1.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return  dateFormat.format(date);
    }
    /**
     * 取得當下時間 :分
     * @param
     */
    public final static String getConverDateTime(Date datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateStr = null;
        dateStr = dateFormat1.format(datetime);
        return  dateStr;
    }
    /**
     * 取得當下時間 :分
     * @param
     */
    public final static String getConverDate(Date datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = null;
        dateStr = dateFormat1.format(datetime);
        return  dateStr;
    }
    /**
     * 取得當下時間 :分
     * @param
     */
    public final static Date getDateTime(String datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat1.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    /**
     * 取得當下:分
     * @param
     */
    public final static Date getDate2(String datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat1.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    /**
     * 取得當下 yyyy年MM月dd日
     * @param
     */
    public final static String getDateTimeCh(String datetime){
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        int thisYear = 0;
        int thisMonth =  0;
        int thisDate =  0;
        try {
            date = dateFormat1.parse(datetime);
            thisYear = date.getYear() + 1900;//thisYear = 2003
            thisMonth = date.getMonth() + 1;//thisMonth = 5
            thisDate = date.getDate();//thisDate = 30

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return thisYear + "年"+ thisMonth +"月"+ thisDate +"日";
    }



    public final static String[] getOneWeekChartItem(){
        String [] result = new String[]{};
        String incDate;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(getDateByNow()));
            cal.add(java.util.Calendar.DATE, -7); // 向前一周；如果需要向后一周，用正数即可
            Date sDate =  cal.getTime();
            incDate = getConverDate(sDate);
            for(int co=0; co<7; co++){
                cal.add(Calendar.DATE, 1);
                incDate +="," + sdf.format(cal.getTime());
            }
            result = incDate.split(",");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  result;
    }
    public final static String[] getOneMonthChartItem(){
        String [] result = new String[]{};
        String incDate;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(getDateByNow()));
            cal.add(java.util.Calendar.MONTH, -1); // 向前一個月；如果需要向后一個月，用正数即可
            Date sDate =  cal.getTime(); //回推起始時間
            incDate = getConverDate(sDate);
            Date nowDate = new Date(); //當下時間
            long dayCount = (nowDate.getTime() - sDate.getTime()) /(24*60*60*1000);
            for(int co=0; co<dayCount; co++){
                cal.add(Calendar.DATE, 1);
                incDate +="," + sdf.format(cal.getTime());
            }
            result = incDate.split(",");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  result;
    }
    public final static String[] getThreeMonthChartItem(){
        String [] result = new String[]{};
        String incDate;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(getDateByNow()));
            cal.add(java.util.Calendar.MONTH, -3); // 向前三個月；如果需要向后三個月，用正数即可
            Date sDate =  cal.getTime(); //回推起始時間
            incDate = getConverDate(sDate);
            Date nowDate = new Date(); //當下時間
            long dayCount = (nowDate.getTime() - sDate.getTime()) /(24*60*60*1000);
            for(int co=0; co<dayCount; co++){
                cal.add(Calendar.DATE, 1);
                incDate +="," + sdf.format(cal.getTime());
            }
            result = incDate.split(",");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  result;
    }
    public final static String[] getOneYearChartItem(){
        String [] result = new String[]{};
        String incDate;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(getDateByNow()));
            cal.add(Calendar.YEAR, -1); // 向前一年；如果需要向后一年，用正数即可
            Date sDate =  cal.getTime(); //回推起始時間
            incDate = getConverDate(sDate);
            Date nowDate = new Date(); //當下時間
            long dayCount = (nowDate.getTime() - sDate.getTime()) /(24*60*60*1000);
            for(int co=0; co<dayCount; co++){
                cal.add(Calendar.DATE, 1);
                incDate +="," + sdf.format(cal.getTime());
            }
            result = incDate.split(",");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     *  日期  = > 年齡
     * @param birthDay
     * @return
     * @throws Exception
     */
    public final static  String getAge(Date birthDay){
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }

        return age +"";
    }
}

package com.base.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 */
public class DateTimeUtils {
    public static String INPUT_DATE_DD_MM_YY_TS = "dd/MM/yyyyHH:mm:ss";
    public static String INPUT_DATE_YYYY_MM_DD_TS = "yyyy/MM/dd HH:mm:ss";
    public static String INPUT_DATE_YYYY_MM_DD_TS_HYPHON = "yyyy-MM-dd HH:mm:ss";
    public static String INPUT_DATE_YY_MM = "yyyy/MM/dd";
    public static String INPUT_DATE_DD_MM = "dd/MM/yyyy";
    public static String OUTPUT_DATE_DD_MM = "dd/MM/yyyy";
    public static String OUTPUT_DATE_MMM_YYYY = "MMMM, yyyy";
    public static String OUTPUT_DATE_DD_MMM_YYYY = "dd MMM yyyy";
    public static String OUTPUT_DATE_MMYYYY = "MMyyyy";
    public static String OUTPUT_DATE_YYYYMM = "yyyyMM";
    public static String OUTPUT_DATE_YYYYMMdd = "yyyyMMdd";
    public static String OUTPUT_DATE_YYYY = "yyyy";
    public static String OUTPUT_DATE_dd = "dd";
    public static String OUTPUT_DATE_ddMM = "ddMM";
    public static String OUTPUT_DATE_ddMMM = "ddMMM";
    public static String OUTPUT_DATE_dd_MMM = "dd MMM";
    public static String OUTPUT_DATE_MMM = "MMM";
    public static String OUTPUT_DATE_MMM_YY = "MMM yy";
    public static String OUTPUT_DATE_MMM_YY_APOSTROPHY = "MMM yy";
    public static String OUTPUT_DATE_MM = "MM";
    public static String OUTPUT_DATE_YYYY_MM = "yyyy/MM";
    public static String OUTPUT_DATE_dd_MMM_yy = "dd MMM yy";
    public static String OUTPUT_HYPHEN_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static String OUTPUT_HYPHEN_DATE_DD_MM_YYYY = "dd-MM-yyyy";
    public static String TIME_UNIT_HOUR = "hours";
    public static String TIME_UNIT_MINUTES = "minutes";
    public static String TIME_UNIT_SECONDS = "seconds";
    public static String TIME_UNIT_MILLI_SECOND = "seconds";
    public static String SORT_MONTH_AND_FULL_YEAR = "MMM, yyyy";
    public static String SORT_DATE_AND_FULL_YEAR = "MMM dd, yyyy";
    public static String MMM_YY_APOSTROPHE = "MMM ''yy";
    public static String DD_MMM_YY_APOSTROPHE = "dd MMM ''yy";
    public static String TIME_FORMAT = "hh:mm a";



    public static String changeDateFormat(String date, String inputDateFormet,
                                          String outputDateFormat) {
        try {
            String formatedDate = "";

            if (date != null && !date.isEmpty() && !date.equalsIgnoreCase("None")) {
                SimpleDateFormat format1 = new SimpleDateFormat(inputDateFormet, Locale.getDefault());
                SimpleDateFormat format2 = new SimpleDateFormat(outputDateFormat, Locale.getDefault());
                Date inDate = null;
                try {
                    inDate = format1.parse(date);
                    formatedDate = format2.format(inDate);
                } catch (Exception e) {
                    e.printStackTrace();
                    formatedDate = date;
                }

            }
            return formatedDate;
        } catch (Exception e) {
            return date;
        }
    }


    public static String getCurrentDateFormated() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        return (dateFormat.format(cal.getTime()));
    }

    public static String getNextDayDateFormated(String date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();

        try {
            Date formattedDate = dateFormat.parse(date);
            cal.setTime(formattedDate);
            cal.add(Calendar.DATE, +2);

        } catch (Exception e) {
            // Do nothing
        }
        return dateFormat.format(cal.getTime());
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        return (dateFormat.format(cal.getTime()));
    }
    public static String getCurrentDate(Long timeMillis) {
        DateFormat dateFormat = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        return (dateFormat.format(cal.getTime()));
    }
    public static String getCurrentDate(String outputPattern) {
            DateFormat dateFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        return (dateFormat.format(cal.getTime()));
    }

    public static String getNextDayDate(String date) {

        DateFormat dateFormat = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
        Calendar cal = Calendar.getInstance();

        try {
            Date formattedDate = dateFormat.parse(date);
            cal.setTime(formattedDate);
            cal.add(Calendar.DATE, +1);

        } catch (Exception e) {
            // Do nothing
        }
        return dateFormat.format(cal.getTime());
    }

    public static long convertDateInMiliSec(String date, String inputDateFormet) {
        long dateInMiliSec;
        try {
            Date dateFormat = new SimpleDateFormat(inputDateFormet).parse(date);
            dateInMiliSec = dateFormat.getTime();
        } catch (ParseException e) {
            dateInMiliSec = 0l;
            e.printStackTrace();
        }
        return dateInMiliSec;
    }

    public static int getDaysOfCurrentMonth() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static long getDaysBetweenDate(String startDate, String endDate, String inputFormat) {
        try {
            return (convertDateInMiliSec(endDate, inputFormat) - convertDateInMiliSec(startDate,
                                                                                      inputFormat))
                    / (24 * 60 * 60 * 1000);
        } catch (Exception exception) {
            return 0L;
        }
    }

    public static HashMap<String, Integer> getTimeUnitMapUsingMinut(Long minutes) {
        HashMap<String, Integer> timeUnitMap = new HashMap<>();
        if (minutes > 0) {
            long hours = minutes / 60;
            long minuts = minutes - hours*60;
            long seconds = 0;
            long milliSecond= 0;
            timeUnitMap.put(TIME_UNIT_HOUR, (int)hours);
            timeUnitMap.put(TIME_UNIT_MINUTES, (int)minuts);
            timeUnitMap.put(TIME_UNIT_SECONDS,(int)seconds);
            timeUnitMap.put(TIME_UNIT_MILLI_SECOND,(int)milliSecond);
        }
        return timeUnitMap;
    }

    public static String getDateBefore(int dateBefore) {
        DateFormat dateFormat = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, dateBefore);
        Date newDate = calendar.getTime();
        return dateFormat.format(newDate);
    }

    public static String getLastDateOfMonth(int differenceFromCurrentMonth) {
        try {

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MONTH, differenceFromCurrentMonth);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            DateFormat sdf = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
            return sdf.format(calendar.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static String getStartDateOfMonth(int differenceFromCurrentMonth) {
        try {
            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MONTH, differenceFromCurrentMonth);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            DateFormat sdf = new SimpleDateFormat(INPUT_DATE_YYYY_MM_DD_TS, Locale.getDefault());
            return sdf.format(calendar.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static String getMonthName(int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthNum);
        //format it to MMM-yyyy // January-2012
        String previousMonthYear = new SimpleDateFormat("MMM-yyyy", Locale.getDefault()).format(cal.getTime());
        if (previousMonthYear != null) { return previousMonthYear; } else { return ""; }
    }

    public static int getDaysOfMonth(int iYear, int iMonth) {
        int iDay = 1;

// Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth - 1, iDay);

// Get the number of days in that month
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getPreviousMontthYear(int iYear, int iMonth) {
        int iDay = 1;

// Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth - 1, iDay);

// Get the number of days in that month
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}

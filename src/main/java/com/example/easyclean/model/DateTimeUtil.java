package com.example.easyclean.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private static final Logger L= LogManager.getLogger(DateTimeUtil.class);

    private DateTimeUtil() {
    }

    public static Date convertTimestamp(Timestamp tmp) {
        long time = tmp.getTime();
        Date derivedDate = DateTimeUtil.getDateFor(time);
        return derivedDate;
    }

    public static java.sql.Date getCurrentSQlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date getCurrentDate() {
        Date currentDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            String format = dateFormat.format(new Date());
            currentDate = dateFormat.parse(format);
        } catch (ParseException ex) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public static Date getCurrentDate(Date date) {
        Date currentDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            String format = dateFormat.format(date);
            currentDate = dateFormat.parse(format);
        } catch (ParseException ex) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public static Date getCurrentDate(String date) {
        Date currentDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            String format = dateFormat.format(date);
            currentDate = dateFormat.parse(format);
        } catch (ParseException ex) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public static Date addHoursToDate(Date date, int hours) {
        long addedMillis = hours * 60000 * 60;
//        L.info("Calculated milliseconds is " + addedMillis);
        long time = date.getTime() + addedMillis;
        return getDateFor(time);
    }

    public static Date addDaysToDate(Date date, int days) {
        long time = date.getTime();
        for (int i = 0; i < days; i++) {
            time += (24 * 60000 * 60);
        }
        return getDateFor(time);
    }

    public static Date substractDaysFromDate(Date date, int days) {
        long time = date.getTime();
        for (int i = 0; i < days; i++) {
            time -= (24 * 60000 * 60);
        }
        return getDateFor(time);
    }

    public static Date addMinutesToDate(Date date, int minutes) {
        int addedMillis = minutes * 60000;
        //L.info("Calculated milliseconds is " + addedMillis);
        long time = date.getTime() + addedMillis;
        return getDateFor(time);
    }

    public static Date getLockedOutUntil(Date date, int elapse, String elapseType) {
        Date retVal = null;
        if (elapseType.equals("MINUTES")) {
            //L.info("Adding minutes to date!!!!");
            retVal = addMinutesToDate(date, elapse);
            //L.info("Date retrieved is : " + retVal);
        } else if (elapseType.equals("HOURS")) {
            retVal = addHoursToDate(date, elapse);
        }
        return retVal;
    }

    public static long distance(Date start, Date end) {
        return Math.abs(start.getTime() - end.getTime());
    }

    public static long distanceFromNow(Date date) {
        return distance(date, getCurrentDate());
    }

    public static long daysToMillis(int days) {
        return days * 24 * 60 * 60 * 1000;
    }

    public static long hoursToMillis(int hours) {
        return hours * 60 * 60 * 1000;
    }

    public static long minutesToMillis(int minutes) {
        return minutes * 60 * 1000;
    }

    public static String toPrettyString(Date date) {
        return DATE_FORMATTER.format(date);
    }


    public static Date getDateFor(long millis) {
        Date date = null;
        try {
            String format = DATE_FORMATTER.format(new Date(millis));
            date = DATE_FORMATTER.parse(format);
        } catch (ParseException ex) {

        }
        return date;
    }

    public static Date addHoursToDate(int hours) {
        return addHoursToDate(new Date(), hours);
    }

    public static Date addMillisToDate(Date date, long millis) {
        return new Date(date.getTime() + millis);
    }

    public static Date subtractMillisFromDate(Date date, long millis) {
        return new Date(date.getTime() - millis);
    }

    public static Date addMillisToDate(long millis) {
        return addMillisToDate(new Date(), millis);
    }

    public static List<Date> datesBetween(Date d1, Date d2, int style) {
        List<Date> ret = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        while (c.getTimeInMillis() < d2.getTime()) {
            c.add(style, 1);
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(c.getTime());
            //System.out.println(format);
            Date parse = null;
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(format);
            } catch (ParseException ex) {

            }
            //This is to ensure that only the days preceeding the endDate are in the search
            long m = c.getTimeInMillis() - d2.getTime();
            if (m < 0) {
                ret.add(parse);
            }
        }
        return ret;
    }

    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public static List<String> getMonths(List<Date> dates) {
        List<String> resp = new ArrayList<String>();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        for (Date date : dates) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int num = cal.get(Calendar.MONTH);
            if (num >= 0 && num <= 11) {
                resp.add(months[num]);
                System.out.println(months[num]);
            }
        }
        return resp;
    }

    public static List<String> getDays(List<Date> dates) {
        List<String> resp = new ArrayList<String>();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] days = dfs.getShortWeekdays();
        for (Date date : dates) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int num = cal.get(Calendar.DAY_OF_WEEK);
            if (num >= 0 && num <= 11) {
                resp.add(days[num]);
                System.out.println(days[num]);
            }
        }
        return resp;
    }

    public static List<Date> datesBetween(Date d1, Date d2) {
        List<Date> ret = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        while (c.getTimeInMillis() < d2.getTime()) {
            c.add(Calendar.MONTH, 1);
            String format = DATE_FORMATTER.format(c.getTime());
            try {
                ret.add(DATE_FORMATTER.parse(format));
            } catch (ParseException ex) {

            }
        }
        return ret;
    }

    public static Date getStartOfToday() {
        Date startofTodayDate;
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(new Date());
        Calendar instance2 = Calendar.getInstance();
        instance2.set(
                instance1.get(Calendar.YEAR),
                instance1.get(Calendar.MONTH),
                instance1.get(Calendar.DAY_OF_MONTH), 0, 0);

        Date time = instance2.getTime();
        startofTodayDate = getCurrentDate(time);
        return startofTodayDate;
    }

    public static Date getOneWeekFromDate(Date date) {
        long sevenDays = daysToMillis(7);
        return subtractMillisFromDate(getCurrentDate(), sevenDays);
    }

    //Revisit implementation to properly calculate start date of month
    public static Date getOneMonthFromDate(Date date) {
        long thirtyDays = daysToMillis(7);
        return subtractMillisFromDate(getCurrentDate(), thirtyDays);
    }

    public static Date getStartOfDate(Date date) {
        Date startofDate;
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.set(
                instance1.get(Calendar.YEAR),
                instance1.get(Calendar.MONTH),
                instance1.get(Calendar.DAY_OF_MONTH), 0, 0);

        Date time = instance2.getTime();
        startofDate = getCurrentDate(time);
        return startofDate;
    }

    public static Date getEndOfDate(Date date) {
        Date startofDate;
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.set(
                instance1.get(Calendar.YEAR),
                instance1.get(Calendar.MONTH),
                instance1.get(Calendar.DAY_OF_MONTH), 23, 59);

        Date time = instance2.getTime();
        startofDate = getCurrentDate(time);
        return startofDate;
    }

    public static Date getShortDate() {
        Date parse = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(new Date());
            parse = sdf.parse(format);
        } catch (ParseException ex) {

        }
        return parse;
    }

    public static String getShortDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDescriptiveDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss.SSSZ");
        return sdf.format(date);
    }

    public static String getLongDate(Date date) {
        return DATE_FORMATTER.format(date);
    }

    public static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static String getCurrentYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }

    public static Date getFirsDateFormat(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM+dd,+yyyy+h:mm:ss+a");
            String formatted = sdf.format(date);
            return sdf.parse(formatted);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date getStartOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        System.out.println(cal.getTime());
        return cal.getTime();
    }

    public static Date getEndOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, 8);
        System.out.println(cal.getTime());
        return cal.getTime();
    }

    public static Date getStartOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        c.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(c.getTime());
        return c.getTime();
    }

    public static Date getEndOfMonth() {
        Calendar c = Calendar.getInstance();
        int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, actualMaximum - 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        System.out.println(c.getTime());
        return c.getTime();
    }

    public static Date getStartOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    public static Date getEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.clear(Calendar.MILLISECOND);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, 8);
        return cal.getTime();
    }

    public static Date getStartOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getEndOfMonth(Date date) {
        Calendar call = Calendar.getInstance();
        call.setTime(date);
        int actualMaximum = call.getActualMaximum(Calendar.DAY_OF_MONTH);
        call.set(Calendar.DAY_OF_MONTH, 1);
        call.add(Calendar.DAY_OF_MONTH, actualMaximum - 1);
        call.set(Calendar.HOUR_OF_DAY, 23);
        call.set(Calendar.MINUTE, 59);
        call.set(Calendar.SECOND, 59);
        call.clear(Calendar.MILLISECOND);
        return call.getTime();
    }

    public static Date getStartOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getEndOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MILLISECOND);
        return cal.getTime();
    }

    public static Date getDate(String year, String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
        return cal.getTime();
    }

    public static Date getDate(String year, String month, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), Integer.parseInt(month), dayOfMonth, 0, 0);
        return cal.getTime();
    }

    public static Date getDate(int year, int month, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        return cal.getTime();
    }

    public static Date getDate(Date actualDate, Date dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(dayOfMonth);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(actualDate);
        cal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), day);
        return cal.getTime();
    }

    public static Date getDateFlipped(Date actualDate, Date dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(actualDate);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dayOfMonth);
        cal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), day);
        cal.add(Calendar.DAY_OF_MONTH, 14);
        return cal.getTime();
    }

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public static String getDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date getDateFromString(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }
}


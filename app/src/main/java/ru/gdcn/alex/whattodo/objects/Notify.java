package ru.gdcn.alex.whattodo.objects;

import java.util.Calendar;

public class Notify {

    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Notify() {
        this.calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DATE);
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    public Notify(long millis) {
        this.calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DATE);
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    public void setCalendarData() {
        calendar.setTimeInMillis(0);
        calendar.set(year, month, day, hour, minute);
    }

    public void setYMD(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setHM(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}

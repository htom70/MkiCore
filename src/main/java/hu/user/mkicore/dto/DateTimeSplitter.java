package hu.user.mkicore.dto;

import lombok.Getter;

import javax.xml.datatype.XMLGregorianCalendar;

@Getter
public class DateTimeSplitter {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private int millisec;

    public DateTimeSplitter(XMLGregorianCalendar xmlGregorianCalendar) {
        this.year = xmlGregorianCalendar.getYear();
        this.month = xmlGregorianCalendar.getMonth();
        this.day = xmlGregorianCalendar.getDay();
        this.hour = xmlGregorianCalendar.getHour();
        this.min = xmlGregorianCalendar.getMinute();
        this.sec = xmlGregorianCalendar.getSecond();
        this.millisec = xmlGregorianCalendar.getMillisecond();
    }
}

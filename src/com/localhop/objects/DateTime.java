package com.localhop.objects;

import android.content.Context;
import android.content.res.Resources;
import com.localhop.R;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class formats/performs operations on given Date/Time objects
 */
public class DateTime {

    private Resources mResources;   //< Access to program resources such as the strings.xml
    private Calendar mDate;         //< The Calendar Object being accessed
    private String mDaysOfWeek[];   //< String array containing the names of each day of the week
    private Logger mLog;

    /**
     * Constructor
     */
    public DateTime(Context context, Date date){

        // Translate the Date object into a Calendar Object
        mDate = Calendar.getInstance();
        mDate.setTime(date);

        mResources = context.getResources();
        mDaysOfWeek = mResources.getStringArray(R.array.days_of_week);
        mLog = Logger.getLogger(this.getClass().getPackage().getName());

    } // end of Constructor DateTime()

    /**
     * Constructor
     */
    public DateTime(Context context, Calendar cal){

        mDate = cal;
        mResources = context.getResources();
        mDaysOfWeek = mResources.getStringArray(R.array.days_of_week);
        mLog = Logger.getLogger(this.getClass().getPackage().getName());

    } // end of Constructor DateTime()

    /**
     * Returns the current calendar object
     * @return
     */
    public Calendar getCalendar(){

        return mDate;

    } // end of function getCalendar()

    /**
     * Returns the Date's DAY_OF_MONTH value
     * @return
     */
    public int getCalendarDayOfMonth(){
        return mDate.get(Calendar.DAY_OF_MONTH);
    } // end of function getCalendarDayOfMonth()

    /**
     * Returns the Date's HOUR_OF_DAY value for 24 hour time
     * @return
     */
    public int getCalendarHourOfDay(){
            return mDate.get(Calendar.HOUR_OF_DAY);
    } // end of function getCalendarHourOfDay()

    /**
     * Returns the Date's HOUR value for 12 hour time
     * @return
     */
    public int getCalendarHour(){
        return mDate.get(Calendar.HOUR);
    } // end of function getCalendarHour()

    /**
     * Returns the Date's MINUTE value
     * @return
     */
    public int getCalendarMinute(){
        return mDate.get(Calendar.MINUTE);
    } // end of function getCalendarMinute()

    /**
     * Returns the Date's MONTH value
     * @return
     */
    public int getCalendarMonth(){
        return mDate.get(Calendar.MONTH);
    } // end of function getCalendarMonth()

    /**
     * Returns the Date's YEAR value
     * @return
     */
    public int getCalendarYear(){
        return mDate.get(Calendar.YEAR);
    } // end of function getCalendarYear()

    /**
     * Returns true if the Calendar should be in 24hour time vs 12 hour time format.
     * @return
     */
    public boolean getCalendarIs24Hour(){
        // TODO: Need to get from user prefs if 24hour or 12hour is preferred
        return false;
    } // end of function getCalendarIs24Hour()

    /**
     * Returns the name of a day of the week
     * @return
     */
    public String getDayOfWeekString()
    {
        if (mDate != null)
        {
            return mDaysOfWeek[mDate.get(Calendar.DAY_OF_WEEK) - 1];
        }
        else
        {
            Throwable ex = new NullPointerException("Error: null mDate parameter");
            mLog.log(Level.SEVERE, "", ex);
            return "";
        }
    } // end of function getDayOfWeekSting

    /**
     * Return the date in the format Day/Month/Year
     * @return
     */
    public String getMonthDayYearFormat()
    {
        if (mDate != null) {
            return (mDate.get(Calendar.MONTH) + 1) + "/" +
                    mDate.get(Calendar.DAY_OF_MONTH) + "/" +
                    (mDate.get(Calendar.YEAR));
        }
        else
        {
            Throwable ex = new NullPointerException("Error: null date parameter");
            mLog.log(Level.SEVERE, "", ex);
            return "";
        }

    } // end of function getDayMonthYearFormat


    /**
     * Formats time from a Calendar object into HH:MM or HH:MM AM/PM
     * @return
     */
    public String getTimeFormat(){

        if(mDate != null) {

            String ampm = "";

            int hours = mDate.get(Calendar.HOUR_OF_DAY);
            int minutes = mDate.get(Calendar.MINUTE);

            // HOUR is used instead if the time is in 12hr format
            if(!getCalendarIs24Hour())
            {
                hours = mDate.get(Calendar.HOUR);
                if(mDate.get(Calendar.AM_PM) == Calendar.AM)
                {
                    ampm = "AM";
                }
                else {
                    ampm = "PM";
                }
            }

            // TODO: Check user prefs for 12hr or 24hr time - This determines whether or not to
            // TODO:   format a time into 12hr with AM or PM

            String hoursFormatted = String.valueOf(hours);
            String minutesFormatted = String.valueOf(minutes);

            // Append a 0 to the front of minutes
            if (minutes < 10) {
                minutesFormatted = "0" + minutesFormatted;
            }

            return hoursFormatted + ":" + minutesFormatted + " " + ampm;
        }
        else
        {
            Throwable ex = new NullPointerException("Error: null date parameter");
            mLog.log(Level.SEVERE, "", ex);
            return "";
        }

    } // end of function getTimeFormat()

    /**
     * Sets whether or not the 12 hour clock is currently AM or PM
     * @param am_pm
     */
    public void setAMPM(int am_pm){

        switch (am_pm)
        {
            case Calendar.AM:
                mDate.set(Calendar.AM_PM, Calendar.AM);
                break;
            case Calendar.PM:
                mDate.set(Calendar.AM_PM, Calendar.PM);
                break;
        }
    } // end of function setAMPM()

    /**
     * Sets the year, month, and day of month.
     * @param year
     * @param month
     * @param dayOfMonth
     */
    public void setDate(int year, int month, int dayOfMonth){

        mDate.set(Calendar.YEAR, year);
        mDate.set(Calendar.MONTH, month);
        mDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    } // end of function setDate()

    /**
     * Sets the Date
     * @param date
     */
    public void setTime(Date date)
    {
        mDate.clear();
        mDate.setTime(date);
    } // end of function setDate()

    /**
     * Sets the Date
     * @param cal
     */
    public void setTime(Calendar cal)
    {
        mDate.clear();
        mDate = cal;
    } // end of function setDate()

    /**
     * Sets the hour and minute
     * @param hour
     * @param minutes
     */
    public void setTime(int hour, int minutes)
    {

        if(getCalendarIs24Hour())
        {
            mDate.set(Calendar.HOUR_OF_DAY, hour);
        }
        else
        {
            mDate.set(Calendar.HOUR, hour);
        }
        mDate.set(Calendar.MINUTE, minutes);
    }

    /**
     * Sets the hour and minute
     * @param hour
     * @param minutes
     */
    public void setTime(int hour, int minutes, boolean is24Hour)
    {

        if(is24Hour)
        {
            mDate.set(Calendar.HOUR_OF_DAY, hour);
        }
        else
        {
            mDate.set(Calendar.HOUR, hour);
        }
        mDate.set(Calendar.MINUTE, minutes);
    }

    /**
     * Sets the time to the next 30 minutes
     * (i.e. current time is 2:14, 2:30 would be set)

     */
    public void setTimeToNextHalfHour()
    {
        int minToNearestHalfHour = 30 - (mDate.get(Calendar.MINUTE) % 30);
        mDate.setTimeInMillis(mDate.getTimeInMillis() + (minToNearestHalfHour * 60000));

    } // setTimeToNextHalfHour()


} // end of class DateTime

package com.localhop.objects;

import android.content.Context;
import android.content.res.Resources;
import com.localhop.R;

import java.text.SimpleDateFormat;
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
     * Formats time from a Calendar object into HH:MM or HH:MM AM/PM
     * @return
     */
    public String getTimeFormat(){

        if(mDate != null) {

            int hours = mDate.get(Calendar.HOUR_OF_DAY);
            int minutes = mDate.get(Calendar.MINUTE);

            if( hours < 1 )
            {
                hours = 24 - Math.abs(hours);
            }

            String ampm = "";

            // TODO: Check user prefs for 12hr or 24hr time - This determines whether or not to
            // TODO:   format a time into 12hr with AM or PM

            // if 12-hour time is preferred then
//            ampm = " " + mResources.getString(R.string.am);
//            if (hours > 12) {
//                hours = 24 - hours;
//                ampm = " " + mResources.getString(R.string.pm);
//            }
            // end if

            String hoursFormatted = String.valueOf(hours);
            String minutesFormatted = String.valueOf(minutes);

            // Append a 0 to the front of minutes
            if (minutes < 10) {
                minutesFormatted = "0" + minutesFormatted;
            }

            return hoursFormatted + ":" + minutesFormatted + ampm;
        }
        else
        {
            Throwable ex = new NullPointerException("Error: null date parameter");
            mLog.log(Level.SEVERE, "", ex);
            return "";
        }

    } // end of function getTimeFormat()


    /**
     * Sets the Date
     * @param date
     */
    public void setTime(Date date)
    {
        mDate.clear();
        mDate.setTime(date);
    } // end of function setDate()


} // end of class DateTime

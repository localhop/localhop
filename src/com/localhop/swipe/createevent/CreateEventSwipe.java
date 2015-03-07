package com.localhop.swipe.createevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.localhop.R;
import com.localhop.objects.DateTime;
import com.localhop.objects.Friend;
import com.localhop.objects.Group;
import com.localhop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Controls the custom Swipe View on the Events List tab.  The user will be given the ability
 * to swipe between Past, Today, and Future events.
 */
public class CreateEventSwipe extends Fragment {

    private int mCurrentPage;
    private ArrayList<Friend> mFriends;
    private ArrayList<Group> mGroups;
    private View mCreateEventView;
    private DateTime mStartDateTime;
    private DateTime mEndDateTime;
    private Button mStartDateButton;
    private Button mEndDateButton;
    private Button mStartTimeButton;
    private Button mEndTimeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the arguments to the Bundle object
        Bundle data = getArguments();

        // Getting integer data of the key current_page from the bundle
        mCurrentPage = data.getInt("current_page", 0);

    } // end of function onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Setup the correct view based on the current page in the swipe view
        switch ( mCurrentPage ) {

            case 0:
                mCreateEventView = inflater.inflate(R.layout.tab_create_event_details, container, false);
                setupDetailsPage();
                break;
            case 1:
                mCreateEventView = inflater.inflate(R.layout.tab_create_event_invite, container, false);
                mFriends = getFriends();
                mGroups = getGroups();
                setupInvitesPage();
                break;
        }

        return mCreateEventView;
    } // end of function onCreateView()


    /**
     * Retrieve the Friends for a particular user
     * @return
     */
    private ArrayList<Friend> getFriends() {

        ArrayList<Friend> items = new ArrayList<Friend>();
        items.add(new Friend("Adam Smith"));
        items.add(new Friend("Kendal Harland"));
        items.add(new Friend("Michelle Perz"));
        items.add(new Friend("Ryan Scott"));
        items.add(new Friend("Zach Flies"));

        return items;
    } // end of function getFriends()

    /**
     * Retrieve the Groups for a particular user
     * @return
     */
    private ArrayList<Group> getGroups() {

        ArrayList<Group> items = new ArrayList<Group>();
        items.add(new Group("Adam, Kendal, Michelle, Ryan, Zach", "Senior Design Group"));
        items.add(new Group("Adam, Connor, Ryan, Orion", "Food Group"));
        items.add(new Group("Adam, Paydon, Whitney, Sean", "Climbing Buddies"));

        return items;
    } // end of function getGroups()


    /**
     * Setup the UI and everything needed for the create event details page
     */
    private void setupDetailsPage() {

        // Start Date/Time
        mStartDateTime = new DateTime(mCreateEventView.getContext(), Calendar.getInstance());
        mStartDateTime.setTimeToNextHalfHour(); // Need to set the startDateTime to the nearest half hour.

        // End Date/Time (This is set 30 minutes past the starting time)
        mEndDateTime = new DateTime(mCreateEventView.getContext(),mStartDateTime.getCalendar().getTime());
        mEndDateTime.setTimeToNextHalfHour();

        // Initialize Date/Time Picker Buttons
        mStartDateButton = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_date_from);
        mEndDateButton = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_date_to);
        mStartTimeButton = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_time_from);
        mEndTimeButton = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_time_to);
        updateDateTimeButtons();

        // Setup Date/Time Picker Dialogs
        setupStartDatePickerDialog();
        setupEndDatePickerDialog();
        setupStartTimePickerDialog();
        setupEndTimePickerDialog();

        // TODO: All Day Option

        // TODO: Event Details

        // TODO: Event Location

        // TODO: Event Name

        // TODO: Create button and validation before POST query to DB

    } // end of function setupDetailsPage()

    /**
     * Sets up the UI for the create event Invites page
     */
    private void setupInvitesPage() {

        final AdapterExpandFriendGroupList expandAdapter =
                new AdapterExpandFriendGroupList(mCreateEventView.getContext(), mGroups, mFriends);

        ExpandableListView elvInvite = ViewUtils.findViewById(mCreateEventView, R.id.elvCreateEventInvite);
        elvInvite.setAdapter(expandAdapter);
        elvInvite.expandGroup(0);
        elvInvite.expandGroup(1);

        elvInvite.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //TODO: Do Something

                return false;
            }
        });


        // Invite Settings Spinner
        Spinner inviteSettingsSpinner = ViewUtils.findViewById(mCreateEventView, R.id.spin_create_event_invite_settings);
        inviteSettingsSpinner.setSelection(0);

    } // end of function setupInvitesPage()


    /**
     * Update the Date/Time Buttons with the current/selected dates/times
     */
    private void updateDateTimeButtons(){
        mStartDateButton.setText(mStartDateTime.getMonthDayYearFormat());
        mEndDateButton.setText(mEndDateTime.getMonthDayYearFormat());
        mStartTimeButton.setText(mStartDateTime.getTimeFormat());
        mEndTimeButton.setText(mEndDateTime.getTimeFormat());
    } // end of function updateDateTimeButtons()


    /**
     * Updates the end date of the event being created and validates the date/time pickers
     */
    private void setupEndDatePickerDialog(){

        // End Date Picker Dialog
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(
                        mCreateEventView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {

                                mEndDateTime.setDate(year, monthOfYear, dayOfMonth);
                                validateDateTimePickers();
                            }
                        },
                        mEndDateTime.getCalendarYear(),
                        mEndDateTime.getCalendarMonth(),
                        mEndDateTime.getCalendarDayOfMonth());
                dpd.getDatePicker().setMinDate(mStartDateTime.getCalendar().getTimeInMillis() - 1000);
                dpd.show();
            }
        });

    } // end of function setupEndDatePickerDialog()


    /**
     * Updates the end time of the event being created and validates the date/time pickers
     */
    private void setupEndTimePickerDialog(){

        // End Time Picker Dialog
        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(
                        mCreateEventView.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                // Must pass true since hourOfDay is in 24hr format, even if user
                                // prefs is set to 12
                                mEndDateTime.setTime(hourOfDay, minute, true);
                                validateDateTimePickers();
                            }
                        },
                        mEndDateTime.getCalendarHourOfDay(),
                        mEndDateTime.getCalendarMinute(),
                        mEndDateTime.getCalendarIs24Hour());
                tpd.show();
            }
        });

    } // end of function setupEndTimePickerDialog()

    /**
     * Updates the start date of the event being created and validates the date/time pickers
     */
    private void setupStartDatePickerDialog(){

        // Start Date Picker Dialog
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(
                        mCreateEventView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {

                                mStartDateTime.setDate(year, monthOfYear, dayOfMonth);
                                validateDateTimePickers();
                            }
                        },
                        mStartDateTime.getCalendarYear(),
                        mStartDateTime.getCalendarMonth(),
                        mStartDateTime.getCalendarDayOfMonth());
                dpd.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);
                dpd.show();
            }
        });

    } // end of function setupStartDatePickerDialog()


    /**
     * Updates the start time of the event being created and validates the date/time pickers
     */
    private void setupStartTimePickerDialog(){

        // Start Time Picker Dialog
        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(
                        mCreateEventView.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                // Must pass true since hourOfDay is in 24hr format, even if user
                                // prefs is set to 12
                                mStartDateTime.setTime(hourOfDay, minute, true);
                                validateDateTimePickers();
                            }
                        },
                        mStartDateTime.getCalendarHourOfDay(),
                        mStartDateTime.getCalendarMinute(),
                        mStartDateTime.getCalendarIs24Hour());

                tpd.show();
            }
        });

    } // end of function setupStartTimePickerDialog()

    /**
     * Validate the Date/Time Pickers. Read the comments on how these are being validated.
     */
    private void validateDateTimePickers(){

        // Case 1: The start date/time is later than the current end date/time.
        // Reset the end date/time.
        // We cannot have an end time be before the start time.
        if (mEndDateTime.getCalendar().getTime().getTime() <
                mStartDateTime.getCalendar().getTime().getTime())
        {
            // First, set the new end date. We want to try to keep the end time that is currently
            // set in case the user is setting the dates and times in a weird order.
            mEndDateTime.setDate(mStartDateTime.getCalendarYear(),
                    mStartDateTime.getCalendarMonth(),
                    mStartDateTime.getCalendarDayOfMonth());

            // In the situation that the new end date/time is still before the start date/time,
            // we will need to update the end time to match the start time
            if (mEndDateTime.getCalendar().getTime().getTime() <
                    mStartDateTime.getCalendar().getTime().getTime())
            {
                mEndDateTime.setTime(mStartDateTime.getCalendarHourOfDay(),
                        mStartDateTime.getCalendarMinute(), true);
            }
        }

        // Case 2: The start date is today, and the start time has already passed
        // (i.e. start time is 7:00 and the current time is 7:05)
        // We need to set the new start time to the current time.
        // The reason for this, is if a user is trying to create an event for now, and the time
        // picker is set to the current time, the user may spend several minutes creating the event
        // before it is submitted. To prevent the time from being rejected over and over, let us
        // give the user 5 minutes. For example, I want to create an event that starts now. If the
        // time in the time picker is 7:05 and the current time is 7:07 when I submit the event to
        // be created, I don't want to have to update the time. However, if the current time is 7:10
        // or later, then I think the user should have to update the event being created.
        Calendar cal = Calendar.getInstance();
        if((mStartDateTime.getCalendar().getTime().getTime() + (5 * 60000)) <
                cal.getTime().getTime())
        {
            if(mStartDateTime.getCalendarIs24Hour()) {
                mStartDateTime.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
            }
            else
            {
                mStartDateTime.setTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
                mStartDateTime.setAMPM(cal.get(Calendar.AM_PM));
            }
        }

        // Update all date/time buttons
        updateDateTimeButtons();

    } // end of function validateDateTimePickers()

} // end of class EventListSwipe
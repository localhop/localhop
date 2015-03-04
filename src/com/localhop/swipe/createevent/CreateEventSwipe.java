package com.localhop.swipe.createevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TimePicker;

import com.localhop.R;
import com.localhop.objects.DateTime;
import com.localhop.objects.Friend;
import com.localhop.objects.Group;
import com.localhop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

        // Create the view for the event list items to be returned

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
        updateDateButtons();
        updateTimeButtons();

        // Setup Date/Time Picker Dialogs
        setupStartDatePickerDialog();
        setupEndDatePickerDialog();
        setupStartTimePickerDialog();
        setupEndTimePickerDialog();

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

    } // end of function setupInvitesPage()


    /**
     * Update the Date Buttons with the current/selected date
     */
    private void updateDateButtons(){
        mStartDateButton.setText(mStartDateTime.getMonthDayYearFormat());
        mStartTimeButton.setText(mStartDateTime.getTimeFormat());

    } // end of function updateDateTimeButtons()

    /**
     * Update the Time Buttons with the current/selected time
     */
    private void updateTimeButtons(){
        mEndDateButton.setText(mEndDateTime.getMonthDayYearFormat());
        mEndTimeButton.setText(mEndDateTime.getTimeFormat());
    } // end of function updateTimeButtons()


    private void setupEndDatePickerDialog(){

        // End Date Picker Dialog
        mEndDateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(
                        mCreateEventView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {




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


    private void setupEndTimePickerDialog(){

        // End Time Picker Dialog
        mEndTimeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(
                        mCreateEventView.getContext(),
                        new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //updateEndTime(hourOfDay, minute);
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
     * Updates the start date of the event being created. If the start date is later than the
     * current ending date, then the ending date will be set to the start date. In the situation
     * that the start date is today, and the start time is set earlier than the current time, then
     * the start/end time will be updated as needed.
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

                                // If the start date is changed to a date/time ahead of the current end date/time,
                                // reset the end date/time
                                if (mEndDateTime.getCalendar().getTime().getTime() <=
                                        mStartDateTime.getCalendar().getTime().getTime())
                                {
                                    mEndDateTime.setTime(mStartDateTime.getCalendar().getTime());
                                    //mEndDateTime.setTimeToNextHalfHour();
                                }

                                // If the start date is today, and the start time is earlier than the current time, reset
                                // both the start and end times
                                if (mStartDateTime.getCalendar().getTime().getTime() <
                                        Calendar.getInstance().getTime().getTime())
                                {
                                    // Set Date to today
                                    Calendar cal = Calendar.getInstance();
                                    mStartDateTime.setDate(cal.get(Calendar.YEAR),
                                            cal.get(Calendar.MONTH),
                                            cal.get(Calendar.DAY_OF_MONTH));

                                    //mStartDateTime.setTimeToNextHalfHour();
                                }

                                updateDateButtons();
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

    private void setupStartTimePickerDialog(){

        // Start Time Picker Dialog
        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(
                        mCreateEventView.getContext(),
                        new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //updateStartTime(hourOfDay, minute);
                            }
                        },
                        mStartDateTime.getCalendarHourOfDay(),
                        mStartDateTime.getCalendarMinute(),
                        mStartDateTime.getCalendarIs24Hour());

                tpd.show();
            }
        });


    } // end of function setupStartTimePickerDialog()

} // end of class EventListSwipe
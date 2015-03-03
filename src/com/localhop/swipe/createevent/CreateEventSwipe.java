package com.localhop.swipe.createevent;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;

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


    private ArrayList<Friend> getFriends() {

        ArrayList<Friend> items = new ArrayList<Friend>();
        items.add(new Friend("Adam Smith"));
        items.add(new Friend("Kendal Harland"));
        items.add(new Friend("Michelle Perz"));
        items.add(new Friend("Ryan Scott"));
        items.add(new Friend("Zach Flies"));

        return items;
    } // end of function getFriends()

    private ArrayList<Group> getGroups() {

        ArrayList<Group> items = new ArrayList<Group>();
        items.add(new Group("Adam, Kendal, Michelle, Ryan, Zach", "Senior Design Group"));
        items.add(new Group("Adam, Connor, Ryan, Orion", "Food Group"));
        items.add(new Group("Adam, Paydon, Whitney, Sean", "Climbing Buddies"));

        return items;
    } // end of function getGroups()

    private void setupDetailsPage() {

        final DateTime startDateTime = new DateTime(mCreateEventView.getContext(), Calendar.getInstance());
        startDateTime.setTimeToNearestHalfHour(); // Need to set the startDateTime to the nearest half hour.

        final DateTime endDateTime = new DateTime(mCreateEventView.getContext(), startDateTime.getCalendar());
        endDateTime.setTimeToNearestHalfHour(); // Need to set the endDateTime to the nearest half hour from the startDateTime.

        // Create Event Details UI
        final EditText etStartDate = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_date_from);
        final EditText etEndDate = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_date_to);
        final EditText etStartTime = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_time_from);
        final EditText etEndTime = ViewUtils.findViewById(mCreateEventView, R.id.et_create_event_time_to);

        // Start Date
        etStartDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpdStartDate = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        Calendar startCal = Calendar.getInstance();
                        startCal.set(Calendar.YEAR, year);
                        startCal.set(Calendar.MONTH, monthOfYear);
                        startCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        startDateTime.setTime(startCal);

                        etStartDate.setText(startDateTime.getMonthDayYearFormat());

                        // If the start date is changed to a date/time ahead of the current end date/time, reset the end date/time
                        if(endDateTime.getCalendar().getTime().getTime() < startDateTime.getCalendar().getTime().getTime())
                        {
                            //TODO: Set endDate text and cal
                            endDateTime.setTime(startDateTime.getCalendar());
                            endDateTime.setTimeToNearestHalfHour();
                            etEndDate.setText(endDateTime.getMonthDayYearFormat());

                        }
                    }
                };
            }
        });

        // End Date
        etEndDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Start Time
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // End Time
        etEndTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    } // end of function setupDetailsPage()

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

} // end of class EventListSwipe
package com.localhop.activities.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.localhop.activities.ActivityMain;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import com.localhop.objects.DateTime;
import com.localhop.objects.Event;
import com.localhop.objects.Friend;
import com.localhop.swipe.createevent.CreateEventSwipeAdapter;
import com.localhop.R;
import com.localhop.swipe.viewpagersupport.ZoomOutPageTransformer;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for the Create Event tab in the main navigation tabs.
 */
public class ActivityTabCreateEvent extends FragmentActivity {

    private ViewPager mViewPager;
    private Event mNewEvent;
    private CreateEventSwipeAdapter mViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_create_event_swipe);

        // Set up the custom Swipe View for Create Event Details and Invite Pages
        mViewPager = (ViewPager) findViewById(R.id.pCreateEventDetailInvite);
        FragmentManager fm = getSupportFragmentManager();
        mViewPagerAdapter = new CreateEventSwipeAdapter(fm);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        // On the invite page, we need to grab the data from the Details page.
        // Unfortunately, we cannot do this from within CreateEventSwipe.java because
        // the fragment gets blown away and the UI components are null.
        // However, this seems to work fine for the time being.
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not Used
            }

            @Override
            public void onPageSelected(int position) {

                // If the Invites page is shown, then handle any actions that require components
                // from the Details page
                if(position == 1)
                {
                        ImageButton createEventImageButton = (ImageButton)mViewPager.findViewById(R.id.ib_create_event);
                        createEventImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createEvent();
                            }
                        });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not Used
            }
        });

        // Setup the back button to get out of the create new event page
        setupBackButton();

    } // end of function onCreate()

    /**
     * Setup the Back button that allows the user to navigate back from the create event page
     */
    private void setupBackButton() {
        ImageButton backImageButton = (ImageButton) findViewById(R.id.ib_create_event_back_button);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Once the favorites/recent events page is complete, set this to finish
            }
        });
    } // end of function setupBackButton()


    /**
     * Grab the UI Data from the Details and Invites page fragments and create the new event
     */
    private void createEvent(){

        // Event Name
        EditText eventNameEditText = (EditText)mViewPager.findViewById(R.id.et_create_event_name);

        if(!eventNameEditText.getText().toString().isEmpty())
        {
            // Date/Time Picker Buttons
            TextView startDateTimeDataTextView = (TextView)
                    mViewPager.findViewById(R.id.tv_create_event_date_from_datafield);
            TextView endDateTimeDataTextView = (TextView)
                    mViewPager.findViewById(R.id.tv_create_event_date_to_datafield);
            DateTime startDateTime = new DateTime(
                    mViewPager.getContext(), startDateTimeDataTextView.getText().toString());
            DateTime endDateTime = new DateTime(
                    mViewPager.getContext(), endDateTimeDataTextView.getText().toString());

            // All Day Switch
            Switch allDaySwitch = (Switch)mViewPager.findViewById(R.id.sw_create_event_all_day);

            // Event Details
            EditText descriptionEditText = (EditText)
                    mViewPager.findViewById(R.id.et_create_event_details);

            // Event Location
            EditText locationEditText = (EditText)
                    mViewPager.findViewById(R.id.et_create_event_location);

            // Invite Settings
            Spinner inviteSettingsSpinner = (Spinner)
                    mViewPager.findViewById(R.id.spin_create_event_invite_settings);

            // TODO: Need to conditional check if the event should be All Day from the spinner
            // TODO: If so, we need to alter this event's dates, or do something else?
            mNewEvent = new Event(Event.EventType.Future,
                    eventNameEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    locationEditText.getText().toString(),
                    startDateTime.getCalendar().getTime(),
                    endDateTime.getCalendar().getTime(),
                    new ArrayList<Friend>(),
                    (int)inviteSettingsSpinner.getSelectedItemId(), // TODO: make a boolean instead
                    2,// organizerID                                // TODO: User current user's ID
                    0 // notification count.                        // TODO: what is this for exactly?
            );
            submitEvent(mNewEvent.toNameValuePair());
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.missing_event_name_error, Toast.LENGTH_SHORT).show();
        }
    } // end of function createEvent()

    /**
     * Pushes a new event to the server
     */
    private void submitEvent(List<NameValuePair> eventData) {

        new HttpServerRequest<Activity, String>(this, HttpRequest.POST, eventData) {

            @Override
            protected String onResponse(final String response) {
                return "";
            }

            @Override
            protected void onPostExecute(String response) {

                super.onPostExecute(response);

                // As of now, we need to make sure the events list is pushed first
                //  so that if the user uses the back button on the newly created event,
                //  it will send them to the events list.
                Intent eventMain = new Intent(mViewPager.getContext(), ActivityMain.class);
                eventMain.putExtra("position", 1);
                startActivity(eventMain);

                // Send the user to the newly created event
                Intent eventSelection = new Intent(mViewPager.getContext(), ActivityEventSelection.class);
                eventSelection.putExtra("event", mNewEvent);
                startActivity(eventSelection);

                finish();
            }

            @Override
            protected void onCancelled() {

            }
        }.execute("http://24.124.60.119/event/add");
    } // end of function createEvent()

} // end of class ActivityTabCreateEvent

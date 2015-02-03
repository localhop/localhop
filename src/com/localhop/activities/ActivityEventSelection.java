package com.localhop.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.localhop.R;

/**
 * Activity for a specific Event selected from the Events List tab.
 * This activity will control the Details, Chat, and Maps tabs for a
 * specific event
 */
public class ActivityEventSelection extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_event_select_main);

        // Get the selected Event's id
        // TODO: get "eventID" that was passed into this Intent from EventListSwipe.java

        // Retrieve all data for the event
        // TODO: generate a ListItemEvent data object

        // Set up the main UI
        setupUI();

        // Set the UI and data for each tab
        setEventDetails();
        // TODO: setEventChat();
        // TODO: setEventMap();

    } // end of function onCreate()


    /**
     * Sets the Details tab for a selected event.
     */
    public void setEventDetails() {

        // UI Components
        TextView tvEventName = (TextView)findViewById(R.id.tvEventName);
        TextView tvEventStartDate = (TextView)findViewById(R.id.tvEventStartDate);
        TextView tvEventStartTime = (TextView)findViewById(R.id.tvEventStartTime);
        EditText etEventDetails = (EditText)findViewById(R.id.etEventDetails);
        EditText etEventLocation = (EditText)findViewById(R.id.etEventLocation);
        ImageButton ibEventCalendar = (ImageButton)findViewById(R.id.ibEventCalendar);
        ImageButton ibEventLocation = (ImageButton)findViewById(R.id.ibEventLocation);
        final Button ibEventInvited = (Button) findViewById(R.id.ibEventInvited);
        final Button ibEventAttending = (Button) findViewById(R.id.ibEventAttending);
        RelativeLayout rlEventRSPV = (RelativeLayout)findViewById(R.id.rlEventRSPV);
        RelativeLayout rlEventDetails = (RelativeLayout)findViewById(R.id.rlEventDetails);

        // Add Border lines between sections of the Details Page
        ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class
        Paint paint = rectShapeDrawable.getPaint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        rlEventRSPV.setBackgroundDrawable(rectShapeDrawable);
        rlEventDetails.setBackgroundDrawable(rectShapeDrawable);

        //Temp Data TODO: Delete after DB integration is done
        final CharSequence attendees[] = {"Michelle Perz", "Ryan Scott", "Zach Flies"};
        final CharSequence invited[] = {"Adam Smith", "Kendal Harland"};

        // Add Attending/Invited buttons and setup corresponding dialogs
        // TODO: Modify list items once DB integration is done
        ibEventAttending.setBackgroundResource(R.drawable.ic_notification_circle_black_36dp);
        ibEventAttending.setText("3");
        ibEventAttending.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ibEventAttending.setEnabled( false );
                new AlertDialog.Builder(ActivityEventSelection.this)
                        .setTitle("Attendees")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ibEventAttending.setEnabled(true);
                                dialog.cancel();
                            }
                        })
                        .setItems(attendees, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: What option do we want to give the user when they select a name from the list?
                                ibEventAttending.setEnabled(true);
                            }
                        })
                        .show();

                return false;
            }
        });
        // TODO: Modify list items once DB integration is done
        ibEventInvited.setBackgroundResource(R.drawable.ic_notification_circle_black_36dp);
        ibEventInvited.setText("2");
        ibEventInvited.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ibEventInvited.setEnabled(false);
                new AlertDialog.Builder(ActivityEventSelection.this)
                        .setTitle("Invited")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ibEventInvited.setEnabled(true);
                                dialog.cancel();
                            }
                        })
                        .setItems(invited, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: What option do we want to give the user when they select a name from the list?
                                ibEventInvited.setEnabled(true);
                            }
                        })
                        .show();
                return false;
            }
        });

        /**
         * TODO: Change all data below have DB integration is complete
         */
        // Set Temp Data to UI components
        tvEventName.setText("Chipotle");

        tvEventStartDate.setText("Monday, 12/1/2015");
        tvEventStartTime.setText("5:00 PM - 6:00 PM");

        etEventDetails.setText("Hey Gang!\n\nYou know the drill, come to Chipotle and " +
                "grab some grub. We can discuss our plans for the week " +
                "and anything else worth talking about.");

        etEventLocation.setText("Chipotle Mexican Grill\n" +
                "1420 W. 23rd St.\n" +
                "Lawrence, KS 66046");

        // Event Calendar button
        ibEventCalendar.setBackgroundResource(R.drawable.ic_add_box_selector);
        ibEventCalendar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: List Item Direction Image Button Click
            }
        });

        // Event Directions button
        ibEventLocation.setBackgroundResource(R.drawable.ic_directions_selector);
        ibEventLocation.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: List Item Direction Image Button Click
            }
        });

    } // end of function setEventDetails()

    /**
     * Link and setup the UI components for this Activity
     */
    public void setupUI() {

        Resources res = getResources();

        // Setup the Back button for user navigation out of the selected event
        ImageButton ibBack = (ImageButton)findViewById(R.id.ibEventBack);
        ibBack.setBackgroundResource(R.drawable.ic_back_arrow_selector);
        ibBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });

        // Create the tabHost and assign TabSpecs for each event select tab
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabDetails = tabHost.newTabSpec("tabDetails");
        TabHost.TabSpec tabChat = tabHost.newTabSpec("tabChat");
        TabHost.TabSpec tabMap = tabHost.newTabSpec("tabMap");

        // Set the Tab name and layout
        // that will be opened when particular Tab will be selected
        tabDetails.setIndicator("", res.getDrawable(R.drawable.tab_event_details_selector));
        getLayoutInflater().inflate(R.layout.tab_event_select_details, tabHost.getTabContentView(), true);
        tabDetails.setContent(R.id.tab_event_select_details);

        tabChat.setIndicator("", res.getDrawable(R.drawable.tab_event_chat_selector));
        getLayoutInflater().inflate(R.layout.tab_event_select_chat, tabHost.getTabContentView(), true);
        tabChat.setContent(R.id.tab_event_select_chat);

        tabMap.setIndicator("", res.getDrawable(R.drawable.tab_event_map_selector));
        getLayoutInflater().inflate(R.layout.tab_event_select_map, tabHost.getTabContentView(), true);
        tabMap.setContent(R.id.tab_event_select_map);

        // Add the tabs  to the TabHost to display.
        tabHost.addTab(tabDetails);
        tabHost.addTab(tabChat);
        tabHost.addTab(tabMap);

    } // end of function setUI()

} // end of class ActivityEventSelection
